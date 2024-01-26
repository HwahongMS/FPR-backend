package com.fpr.financialProduct.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FinancialProductResponse {
    private Result result;
    // Getter와 Setter 메서드
}

