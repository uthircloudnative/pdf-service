package com.learntech.pdfmanager;

import com.learntech.pdfmanager.document.TicketDetails;
import com.learntech.pdfmanager.repository.TicketDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final TicketDetailsRepository ticketDetailsRepository;

    public DataLoader(TicketDetailsRepository ticketDetailsRepository) {
        this.ticketDetailsRepository = ticketDetailsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LocalDate startDate = LocalDate.of(2023, Month.JANUARY,1);

        List<TicketDetails> airTickets = getTickets(3,startDate,"AIR","USER001");
        List<TicketDetails> shipTickets = getTickets(3,startDate,"SHIP","USER001");
        List<TicketDetails> trainTickets = getTickets(3,startDate,"TRAIN","USER001");

        List<TicketDetails> allTickets = new ArrayList<>();
        allTickets.addAll(airTickets);
        allTickets.addAll(shipTickets);
        allTickets.addAll(trainTickets);

        List<TicketDetails> savedTickets = ticketDetailsRepository.saveAll(allTickets);
        logger.info("******Mongo DB is populated with data********");
        logger.info("******Total Size of the data********"+ticketDetailsRepository.findAll().size());

        savedTickets.forEach(a -> System.out.println(a.toString()));

    }

    public List<TicketDetails> getTickets(int count,LocalDate date,String tavelCategory,String userId) {
        List<TicketDetails> airTickets = new ArrayList<>();
        int month = 1;
        for (int i = 0; i <= count; i++) {
            if(i != 0){
                month +=3;
            }
            TicketDetails ticketDetails = new TicketDetails();
            ticketDetails.setUserId(userId);
            ticketDetails.setBookedDate(date.plusMonths(month));
            ticketDetails.setFromLocation("US");
            ticketDetails.setToLocation("INDIA");
            ticketDetails.setTravelCategory(tavelCategory);
            airTickets.add(ticketDetails);
        }
        return airTickets;
    }


//    public List<TicketDetails> getShipTickets(int count,LocalDate date) {
//        List<TicketDetails> shipTickets = new ArrayList<>();
//        for (int i = 0; i <= count; i++) {
//            TicketDetails ticketDetails = new TicketDetails();
//            ticketDetails.setBookedDate(date.plusMonths(i+3));
//            ticketDetails.setFromLocation("US");
//            ticketDetails.setToLocation("INDIA");
//            ticketDetails.setTravelCategory("SHIP");
//            shipTickets.add(ticketDetails);
//        }
//        return shipTickets;
//    }
//
//    public List<TicketDetails> getTrainTickets(int count,LocalDate date) {
//        List<TicketDetails> trainTickets = new ArrayList<>();
//        for (int i = 0; i <= count; i++) {
//            TicketDetails ticketDetails = new TicketDetails();
//            ticketDetails.setBookedDate(date.plusMonths(i+3));
//            ticketDetails.setFromLocation("US");
//            ticketDetails.setToLocation("INDIA");
//            ticketDetails.setTravelCategory("TRAIN");
//            trainTickets.add(ticketDetails);
//        }
//        return trainTickets;
//    }
}
