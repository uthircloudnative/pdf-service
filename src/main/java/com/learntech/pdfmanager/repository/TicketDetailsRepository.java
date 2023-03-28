package com.learntech.pdfmanager.repository;

import com.learntech.pdfmanager.document.TicketDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDetailsRepository extends MongoRepository<TicketDetails, ObjectId> ,
                                                  TicketDetailsRepositoryCustom{
}
