package com.fpr.financialProduct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Result {

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("max_page_no")
    private int maxPageNo;

    @JsonProperty("now_page_no")
    private int nowPageNo;

    @JsonProperty("baseList")
    private List<FinancialProduct> baseList;

    @JsonProperty("optionList")
    private List<FinancialProductOption> optionList;

    // Getter와 Setter 메서드
}
