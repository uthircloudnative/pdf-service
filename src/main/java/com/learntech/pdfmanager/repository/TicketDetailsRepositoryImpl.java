package com.learntech.pdfmanager.repository;

import com.learntech.pdfmanager.document.TicketDetails;
import com.learntech.pdfmanager.model.SearchBooking;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class TicketDetailsRepositoryImpl implements TicketDetailsRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public TicketDetailsRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<TicketDetails> getTicketDetailsByDateRange(SearchBooking searchBooking) {

        Criteria criteria = Criteria.where("userId").is(searchBooking.getUserId())
                                    .andOperator(Criteria.where("bookedDate").gte(searchBooking.getFromDate()),
                                                 Criteria.where("bookedDate").gte(searchBooking.getFromDate()));

        Query query = new Query(criteria);

        List<TicketDetails> ticketDetails = mongoTemplate.find(query, TicketDetails.class);

        return ticketDetails;
    }
}
