package com.learntech.pdfmanager.repository;

import com.learntech.pdfmanager.document.TicketDetails;
import com.learntech.pdfmanager.model.SearchBooking;

import java.util.List;

public interface TicketDetailsRepositoryCustom {

    List<TicketDetails> getTicketDetailsByDateRange(SearchBooking searchBooking);
}
