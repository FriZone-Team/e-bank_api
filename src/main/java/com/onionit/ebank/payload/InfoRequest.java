package com.onionit.ebank.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InfoRequest {

    @NotBlank
    private Long departmentId;

    @NotBlank
    private String suite;

    @NotBlank
    private String city;

    @NotBlank
    private String zipcode;

    private String companyName;

    private String website;

    private String phone;
}
