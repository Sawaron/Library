package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Reservation;
import com.codeandpray.library.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    boolean existsByBookIdAndStatus(Long bookId, ReservationStatus status);
}