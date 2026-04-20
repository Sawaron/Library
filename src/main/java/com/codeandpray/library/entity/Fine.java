package com.codeandpray.library.entity;

import com.codeandpray.library.enums.FineStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fines")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fine_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(name = "fine_amount")
    private BigDecimal amount;

    @Column(name = "fine_reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "fine_status")
    private FineStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}