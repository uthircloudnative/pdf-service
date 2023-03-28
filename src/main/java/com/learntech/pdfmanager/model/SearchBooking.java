package com.learntech.pdfmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SearchBooking {

    private String searchType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String searchByYear;
    private String userId;
}
