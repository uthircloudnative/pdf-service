package com.learntech.pdfmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BookingGrouping {

    private String groupBy; //Quarterly or BOOKING_TYPE
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<GroupingData> groupDataList;

}
