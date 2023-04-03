package com.onionit.ebank.payload;

import lombok.Data;

@Data
public class ExchangeResponse {
    private Long id;
    private Long amount;
    private String message;
    private Long albumId;

    public ExchangeResponse(Long id, long amount, String message, Long albumId) {
        this.id = id;
        this.amount = amount;
        this.message = message;
        this.albumId = albumId;
    }

}
