package com.learntech.pdfmanager.document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "ticketDetails")
@Getter
@Setter
@ToString
public class TicketDetails {

    @Id
    private ObjectId id;
    private String userId;
    private String travelCategory;
    private LocalDate bookedDate;
    private String fromLocation;
    private String toLocation;
}
