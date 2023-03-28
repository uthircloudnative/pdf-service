package com.learntech.pdfmanager.service;

import com.learntech.pdfmanager.document.TicketDetails;
import com.learntech.pdfmanager.helper.BookingGroupingHelper;
import com.learntech.pdfmanager.model.BookingGroupingDetails;
import com.learntech.pdfmanager.model.BookingInfo;
import com.learntech.pdfmanager.model.SearchBooking;
import com.learntech.pdfmanager.repository.TicketDetailsRepository;
import com.learntech.pdfmanager.util.PdfGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfServiceImpl implements PdfService {

    private final TicketDetailsRepository ticketDetailsRepository;
    private final BookingGroupingHelper bookingGroupingHelper;
    private final PdfGenerator pdfGenerator;

    public PdfServiceImpl(TicketDetailsRepository ticketDetailsRepository,
                          BookingGroupingHelper bookingGroupingHelper,
                          PdfGenerator pdfGenerator) {
        this.ticketDetailsRepository = ticketDetailsRepository;
        this.bookingGroupingHelper   = bookingGroupingHelper;
        this.pdfGenerator            = pdfGenerator;
    }

    @Override
    public BookingInfo saveBooking(BookingInfo bookingInfo) {

        TicketDetails ticketDetails = new TicketDetails();
        BeanUtils.copyProperties(bookingInfo, ticketDetails);
        ticketDetails = ticketDetailsRepository.save(ticketDetails);

        BookingInfo bookingInfoResp = new BookingInfo();
        BeanUtils.copyProperties(ticketDetails, bookingInfoResp);

        return bookingInfo;
    }

    @Override
    public List<BookingInfo> getBookingInfo(SearchBooking searchBooking) {

        List<TicketDetails> ticketDetails = ticketDetailsRepository.getTicketDetailsByDateRange(searchBooking);
        List<BookingInfo> bookingInfoDetails = ticketDetails.stream()
                                                    .map(ticket -> {
                                                        BookingInfo bookingInfo = new BookingInfo();
                                                        bookingInfo.setId(ticket.getId().toString());
                                                        BeanUtils.copyProperties(ticket,bookingInfo);
                                                        return bookingInfo;
                                                    }).collect(Collectors.toList());

        return bookingInfoDetails;
    }

    @Override
    public BookingGroupingDetails getBookingSummary(SearchBooking searchBooking) {
        List<TicketDetails> ticketDetails = ticketDetailsRepository.getTicketDetailsByDateRange(searchBooking);
        return bookingGroupingHelper.groupBookingByQuarterly(ticketDetails);
    }

    @Override
    public byte[] generateBookingSummaryPdf(SearchBooking searchBooking) throws IOException {
        BookingGroupingDetails bookingGroupingDetails = getBookingSummary(searchBooking);
        return pdfGenerator.generatePdf(bookingGroupingDetails);
    }

}
