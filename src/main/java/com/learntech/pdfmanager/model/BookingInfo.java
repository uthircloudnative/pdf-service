package com.learntech.pdfmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BookingInfo {

    private String id;
    private String travelCategory;
    private LocalDate bookedDate;
    private String fromLocation;
    private String toLocation;

}
