package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Reservation;
import com.codeandpray.library.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    boolean existsByBookIdAndStatus(Long bookId, ReservationStatus status);

    @Query(value = "SELECT set_config('lock_timeout', :lockTimeout, true)", nativeQuery = true)
    String setLocalLockTimeout(@Param("lockTimeout") String lockTimeout);

    @Query(value = """
            SELECT *
            FROM reservation
            WHERE status = 'ACTIVE'
              AND available_date IS NOT NULL
              AND NOW() >= available_date + (:expireDays * INTERVAL '1 day')
            ORDER BY available_date, id
            LIMIT :batchSize
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    List<Reservation> findExpiredActiveReservationsForUpdate(
            @Param("expireDays") int expireDays,
            @Param("batchSize") int batchSize
    );
}