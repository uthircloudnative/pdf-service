package com.learntech.pdfmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GroupingData {

    private String ticketCount;
    private String bookingPercentage;
    private String groupingCategory; //Q1,Q2 or SHIP,AIR
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<BookingInfo> bookings;
}
