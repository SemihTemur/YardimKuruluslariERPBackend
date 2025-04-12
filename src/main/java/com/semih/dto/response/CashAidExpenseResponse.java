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
public class CashAidExpenseResponse  extends BaseResponse {

    private String familyName;

    private BigDecimal totalAidAmount;

    private CurrencyType currencyType;

}
