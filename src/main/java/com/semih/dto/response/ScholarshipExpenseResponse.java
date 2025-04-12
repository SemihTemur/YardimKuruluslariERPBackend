package com.semih.dto.response;

import com.semih.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipExpenseResponse  extends BaseResponse {

    private String studentName;

    private String studentSurname;

    private BigDecimal scholarshipExpense;

    private CurrencyType currencyType;

}
