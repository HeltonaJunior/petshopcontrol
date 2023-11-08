package com.example.petshopcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ClientRecordDto(
        @NotBlank String name,
        @NotBlank String cpf,
        @NotBlank String address,
        @NotBlank String namePet,
        @NotNull BigDecimal valueCredit
) {

}
