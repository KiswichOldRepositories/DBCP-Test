package cn.showclear.dbcptest.service.processor;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 柱状图打印
 */
public class ChartProcessor {
    private DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    private String imageName = "统计图表";
    private String title;
    private String xTitle;
    private String yTitle;

    public ChartProcessor(String title, String xTitle, String yTitle) {
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
    }

    public ChartProcessor(String title, String xTitle, String yTitle, String imageName) {
        this.imageName = imageName;
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
    }

    public void addValue(Integer value, String category, String columnKey) {
        categoryDataset.addValue(value, category, columnKey);
    }

    public void print() {
        JFreeChart barChart = ChartFactory.createBarChart(
                this.title,
                this.xTitle,
                this.yTitle,
                this.categoryDataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        processChinese(barChart);
        writeChartAsPng(barChart);
    }

    private void processChinese(JFreeChart jFreeChart) {
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        ValueAxis rAxis = plot.getRangeAxis();
        jFreeChart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        TextTitle textTitle = jFreeChart.getTitle();
        textTitle.setFont(new Font("宋体", Font.PLAIN, 20));
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
        rAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        jFreeChart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
    }

    public void writeChartAsPng(JFreeChart jFreeChart) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(imageName + ".png")) {
            ChartUtils.writeChartAsPNG(fileOutputStream, jFreeChart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
