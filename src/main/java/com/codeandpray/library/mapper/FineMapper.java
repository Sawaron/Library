package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.FineResponse;
import com.codeandpray.library.entity.Fine;
import org.springframework.stereotype.Component;

@Component
public class FineMapper {

    public FineResponse toResponse(Fine fine) {
        if (fine == null) return null;

        return FineResponse.builder()
                .id(fine.getId())
                .loanId(fine.getLoan() != null ? fine.getLoan().getId() : null)
                .amount(fine.getAmount())
                .reason(fine.getReason())
                .status(fine.getStatus() != null ? fine.getStatus().name() : null)
                .createdAt(fine.getCreatedAt())
                .build();
    }
}