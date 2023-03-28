//package com.learntech.pdfmanager.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.learntech.pdfmanager.DataLoader;
//import com.learntech.pdfmanager.constant.ApplicationConstant;
//import com.learntech.pdfmanager.document.TicketDetails;
//import com.learntech.pdfmanager.model.BookingGrouping;
//import com.learntech.pdfmanager.model.BookingGroupingDetails;
//import com.learntech.pdfmanager.model.GroupingData;
//import org.springframework.util.CollectionUtils;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.time.temporal.TemporalAdjuster;
//import java.time.temporal.TemporalAdjusters;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class GroupByQuarter {
//
//    private static final String Q1 = "Q1";
//    private static final String Q2 = "Q2";
//    private static final String Q3 = "Q3";
//    private static final String Q4 = "Q4";
//
//
//    public static void main(String[] args){
//        int year = 2023;
//
//        //Find first and last date of given year
//       LocalDate startDate = LocalDate.of(year, Month.JANUARY,1);
//       LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfYear());
//
//        System.out.println(startDate);
//        System.out.println(endDate);
//
//        //DataLoader dataLoader = new DataLoader();
//
//        List<TicketDetails> airTickets = dataLoader.getTickets(3,startDate,"AIR","USER001");
//        List<TicketDetails> shipTickets = dataLoader.getTickets(3,startDate,"SHIP","USER001");
//        List<TicketDetails> trainTickets = dataLoader.getTickets(3,startDate,"TRAIN","USER001");
//
//        List<TicketDetails> allTickets = new ArrayList<>();
//        allTickets.addAll(airTickets);
//        allTickets.addAll(shipTickets);
//        allTickets.addAll(trainTickets);
//
//        allTickets.forEach(a -> System.out.println(a.toString()));
//
//        Map<Integer, List<TicketDetails>> ticketsByMonth = allTickets.stream()
//                .collect(Collectors.groupingBy(ticket -> ticket.getBookedDate().getMonthValue()));
//
//        int totalTickets = allTickets.size();
//
//        BookingGrouping bookingGrouping = groupTicketsByQuarterly(ticketsByMonth,totalTickets);
//
//        BookingGroupingDetails bookingGroupingDetails = new BookingGroupingDetails();
//
//        bookingGroupingDetails.setTotalBookings(totalTickets);
//        bookingGroupingDetails.setBookingGroupings(Arrays.asList(bookingGrouping));
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookingGrouping));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static BookingGrouping groupTicketsByQuarterly(Map<Integer, List<TicketDetails>> ticketsByMonth,int totalTicketCount){
//
//        var q1TicketCount = 0;
//        var q2TicketCount = 0;
//        var q3TicketCount = 0;
//        var q4TicketCount = 0;
//
//        List<GroupingData> groupingDataList = new ArrayList<>();
//        BookingGrouping bookingGrouping     = new BookingGrouping();
//
//        bookingGrouping.setGroupBy(ApplicationConstant.QUARTERLY);
//
//        for(Map.Entry<Integer, List<TicketDetails>> entrySet:ticketsByMonth.entrySet()){
//            if(entrySet.getKey()<=3){
//                //Q1
//                List<TicketDetails> ticketDetails = entrySet.getValue();
//                if(!CollectionUtils.isEmpty(ticketDetails)){
//                    q1TicketCount +=ticketDetails.size();
//                    groupingDataList.add(getGroupingData(q1TicketCount,Q1, totalTicketCount));
//                }
//            }else if(entrySet.getKey()<=6){
//                //Q2
//                List<TicketDetails> ticketDetails = entrySet.getValue();
//                if(!CollectionUtils.isEmpty(ticketDetails)){
//                    q2TicketCount +=ticketDetails.size();
//                    groupingDataList.add(getGroupingData(q2TicketCount,Q2, totalTicketCount));
//                }
//            }else if(entrySet.getKey()<=9){
//                //Q3
//                List<TicketDetails> ticketDetails = entrySet.getValue();
//                if(!CollectionUtils.isEmpty(ticketDetails)){
//                    q3TicketCount +=ticketDetails.size();
//                    groupingDataList.add(getGroupingData(q3TicketCount,Q3, totalTicketCount));
//                }
//            }else{
//                //Q4
//                List<TicketDetails> ticketDetails = entrySet.getValue();
//                if(!CollectionUtils.isEmpty(ticketDetails)){
//                    q4TicketCount +=ticketDetails.size();
//                    groupingDataList.add(getGroupingData(q4TicketCount,Q4, totalTicketCount));
//                }
//            }
//        }
//        bookingGrouping.setGroupDataList(groupingDataList);
//        return bookingGrouping;
//    }
//
//    private static GroupingData getGroupingData(int ticketCount, String quarter,int totalTicketCount){
//        GroupingData groupingData = null;
//        if(ticketCount !=0){
//            groupingData = new GroupingData();
//
//            groupingData.setGroupingCategory(quarter);
//            groupingData.setBookingPercentage(String.valueOf(getPercentage(ticketCount,totalTicketCount)));
//            groupingData.setTicketCount(String.valueOf(ticketCount));
//        }
//        return groupingData;
//    }
//
//    private static float getPercentage(int partCount, int totalCount){
//        return Math.multiplyExact(partCount,100)/totalCount;
//    }
//}
