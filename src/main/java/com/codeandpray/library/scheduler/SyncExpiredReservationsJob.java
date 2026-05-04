package com.codeandpray.library.scheduler;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Reservation;
import com.codeandpray.library.enums.BookStatus;
import com.codeandpray.library.enums.ReservationStatus;
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.ReservationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SyncExpiredReservationsJob {

    private final ReservationRepo reservationRepo;
    private final BookRepo bookRepo;

    @Value("${library.reservation.expire-days:3}")
    private int reservationExpireDays;

    @Value("${library.reservation.expire-batch-size:500}")
    private int reservationExpireBatchSize;

    @Value("${library.reservation.expire-lock-timeout-ms:5000}")
    private int reservationExpireLockTimeoutMs;

    @Scheduled(cron = "${library.reservation.expire-schedule:0 0 2 * * *}")
    @Transactional
    public void syncExpiredReservations() {
        reservationRepo.setLocalLockTimeout(reservationExpireLockTimeoutMs + "ms");

        List<Reservation> expiredReservations = reservationRepo.findExpiredActiveReservationsForUpdate(
                reservationExpireDays,
                reservationExpireBatchSize
        );

        int shiftedBooks = 0;

        for (Reservation reservation : expiredReservations) {
            reservation.setStatus(ReservationStatus.EXPIRED);

            Book book = reservation.getBook();

            if (book != null) {
                book.setCount(book.getCount() + 1);
                book.setStatus(BookStatus.AVAILABLE);

                bookRepo.save(book);
                shiftedBooks++;
            }
        }

        reservationRepo.saveAll(expiredReservations);

        log.info(
                "Expired reservations sync finished: expired={}, shiftedBooks={}, expireDays={}, batchSize={}",
                expiredReservations.size(),
                shiftedBooks,
                reservationExpireDays,
                reservationExpireBatchSize
        );
    }
}