package com.learntech.pdfmanager.util;

import com.learntech.pdfmanager.model.GroupingData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PieChartGenarator {

    public JFreeChart generateBookingSummaryPieChart(List<GroupingData> categoryData){

        DefaultPieDataset dataset = new DefaultPieDataset();

        categoryData
                .forEach(data -> {
                    dataset.setValue(data.getGroupingCategory(),Double.valueOf(data.getTicketCount()));
                });

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Bookings by Category Trend",
                dataset,
                false, true, false);
        pieChart.setTitle(new TextTitle("Bookings by Category Trend"));


//        PiePlot piePlot = (PiePlot) pieChart.getPlot();
//
//        ChartFrame chartFrame = new ChartFrame("Pie Chart",pieChart);
//        chartFrame.setVisible(true);
//        chartFrame.setSize(500,500);
        return pieChart;
    }

}
