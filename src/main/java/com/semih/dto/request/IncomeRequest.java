package com.semih.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class IncomeRequest {

    private BigDecimal amount; // Gelir miktarı

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate income_date; // Gelirin kaydedildiği tarih

    private String description;

    private Long id;

}
