package com.onionit.ebank.payload;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ExchangeRequest {

    @Min(1000)
    private long amount;

    @NotBlank
    @Size(min = 10)
    private String message;

    @NotNull
    private Long cardId;
}
