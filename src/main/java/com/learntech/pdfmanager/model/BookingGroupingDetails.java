package com.learntech.pdfmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BookingGroupingDetails {

    private int totalBookings;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<BookingGrouping> bookingGroupings; //Quarterly data or Booking Type Data
}
