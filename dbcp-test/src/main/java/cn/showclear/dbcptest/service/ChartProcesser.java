package cn.showclear.dbcptest.service;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class ChartProcesser {
    private DefaultCategoryDataset categoryDataset;
    private String imageNmae = "统计图表";


    public void addValue(Integer value, String category, String cloumnKey) {
        categoryDataset.addValue(value, category, cloumnKey);
    }

    public void writeChartAsPng(JFreeChart jFreeChart) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(imageNmae + ".png")) {
            ChartUtils.writeChartAsPNG(fileOutputStream, jFreeChart, 800, 600);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
