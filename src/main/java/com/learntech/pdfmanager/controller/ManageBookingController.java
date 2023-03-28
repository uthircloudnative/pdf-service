package com.learntech.pdfmanager.controller;

import com.learntech.pdfmanager.model.BookingGroupingDetails;
import com.learntech.pdfmanager.model.BookingInfo;
import com.learntech.pdfmanager.model.SearchBooking;
import com.learntech.pdfmanager.service.PdfService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;


@RestController
public class ManageBookingController {

    private final PdfService pdfService;

    public ManageBookingController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping(path = "/bookings/{userId}/datapopulation")
    public List<BookingInfo> getAllBookings(@PathVariable(name = "userId") String userId,
                                            @RequestParam(name = "searchYear") String searchYear){

        SearchBooking searchBooking = getSearchBookingData(userId,searchYear);
        return pdfService.getBookingInfo(searchBooking);
    }

    @GetMapping(path = "/bookings/{userId}/summary")
    public BookingGroupingDetails getBookingSummary(@PathVariable(name = "userId") String userId,
                                                    @RequestParam(name = "searchYear") String searchYear){
        SearchBooking searchBooking = getSearchBookingData(userId,searchYear);
        return pdfService.getBookingSummary(searchBooking);
    }

    @GetMapping(path = "/bookings/{userId}/summarypdf",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateBookingSummaryPdf(@PathVariable(name = "userId") String userId,
                                                                         @RequestParam(name = "searchYear") String searchYear) throws IOException {
        SearchBooking searchBooking = getSearchBookingData(userId,searchYear);
        byte[] pdfContentBytes = pdfService.generateBookingSummaryPdf(searchBooking);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfContentBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=bookingsummary.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }
    private SearchBooking getSearchBookingData(String userId,String searchYear){
        SearchBooking searchBooking = new SearchBooking();
        searchBooking.setUserId(userId);
        searchBooking.setSearchByYear(searchYear);
        //Setting FromDate and ToDate based on given Year
        LocalDate startDate = LocalDate.of(Integer.valueOf(searchYear), Month.JANUARY,1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfYear());

        searchBooking.setFromDate(startDate);
        searchBooking.setToDate(endDate);
        return searchBooking;
    }

}
