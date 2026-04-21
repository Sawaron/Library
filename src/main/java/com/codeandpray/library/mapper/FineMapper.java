package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.FineResponse;
import com.codeandpray.library.entity.Fine;

public class FineMapper {

    public static FineResponse toResponse(Fine fine) {
        if (fine == null) {
            return null;
        }

        FineResponse response = new FineResponse();
        response.setId(fine.getId());
        response.setLoanId(fine.getLoan() != null ? fine.getLoan().getId() : null);
        response.setAmount(fine.getAmount());
        response.setReason(fine.getReason());
        response.setStatus(fine.getStatus());
        response.setCreatedAt(fine.getCreatedAt());

        return response;
    }
}