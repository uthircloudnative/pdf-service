package com.learntech.pdfmanager.util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.learntech.pdfmanager.constant.ApplicationConstant;
import com.learntech.pdfmanager.model.BookingGrouping;
import com.learntech.pdfmanager.model.BookingGroupingDetails;
import com.learntech.pdfmanager.model.GroupingData;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Component;


//import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class PdfGenerator {

    private final PieChartGenarator pieChartGenarator;

    public PdfGenerator(PieChartGenarator pieChartGenarator) {
        this.pieChartGenarator = pieChartGenarator;
    }

    public byte[] generatePdf(BookingGroupingDetails bookingGroupingDetails) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter pdfWriter = new PdfWriter(outputStream);
        ////Create PDF Document
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        //Create Document
        Document document = new Document(pdfDocument, PageSize.A4.rotate());
        //Set Font
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        //PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

        PdfFont titleFont = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
        //Set the title of the document
        Text title = new Text("Ticket Booking Summary").setFont(titleFont);

        document.add(new Paragraph(title).setTextAlignment(TextAlignment.CENTER));

        document.setMargins(20, 20, 20, 20);
        Table table = new Table(UnitValue.createPercentArray(new float[]{10, 10, 10}))
                                         .useAllAvailableWidth();

        Cell cell1 = new Cell(1,3);
        cell1.setTextAlignment(TextAlignment.CENTER);
        cell1.add(new Paragraph("Total Booking Count is"))
              .add(new Paragraph(String.valueOf(bookingGroupingDetails.getTotalBookings()))).setFont(font).setBold();

        table.addHeaderCell(cell1).setFont(font).setBold();

        //Header Cell
        table.addHeaderCell(new Cell().add(new Paragraph("Quarter")).setFont(font).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Booking Count")).setFont(font).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Booking Percentage")).setFont(font).setBold());

        List<BookingGrouping> quarterlyBookings = bookingGroupingDetails.getBookingGroupings();
        List<GroupingData> categoryData        = null;
        for(BookingGrouping bookingGrouping:quarterlyBookings){
            if(ApplicationConstant.QUARTERLY_GROUPING.equals(bookingGrouping.getGroupBy())){
                List<GroupingData> quarterlyData = bookingGrouping.getGroupDataList();
                for(GroupingData groupingData:quarterlyData){
                    table.addCell(new Cell().add(new Paragraph(groupingData.getGroupingCategory()).setFont(font)));
                    table.addCell(new Cell().add(new Paragraph(groupingData.getTicketCount()).setFont(font)));
                    table.addCell(new Cell().add(new Paragraph(groupingData.getBookingPercentage()).setFont(font)));
                }
            }else if(ApplicationConstant.CATEGORY_GROUPING.equals(bookingGrouping.getGroupBy())){
                categoryData = bookingGrouping.getGroupDataList();
            }
        }
        document.add(table);

        //Apend a piechart
        if(null != categoryData){
            JFreeChart pieChart = pieChartGenarator.generateBookingSummaryPieChart(categoryData);

            BufferedImage bufferedImage = pieChart.createBufferedImage(1000,1000);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] bytes = baos.toByteArray();

            ImageData imageData = ImageDataFactory.create(bytes);

            // Create layout image object and provide parameters. Page number = 1
            Image image = new Image(imageData).scaleAbsolute(200, 200)
                                              .setFixedPosition(25, 25);
            // This adds the image to the page
            document.add(image);

        }
        document.close();
        return outputStream.toByteArray();
    }
}
