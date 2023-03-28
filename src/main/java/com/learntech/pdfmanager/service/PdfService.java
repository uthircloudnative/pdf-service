package com.learntech.pdfmanager.service;

import com.learntech.pdfmanager.model.BookingGroupingDetails;
import com.learntech.pdfmanager.model.BookingInfo;
import com.learntech.pdfmanager.model.SearchBooking;

import java.io.IOException;
import java.util.List;

public interface PdfService {

    BookingInfo saveBooking(BookingInfo bookingInfo);
    List<BookingInfo> getBookingInfo(SearchBooking searchBooking);
    BookingGroupingDetails getBookingSummary(SearchBooking searchBooking);
    byte[] generateBookingSummaryPdf(SearchBooking searchBooking) throws IOException;
}
