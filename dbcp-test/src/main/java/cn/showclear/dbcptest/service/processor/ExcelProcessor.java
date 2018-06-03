package cn.showclear.dbcptest.service.processor;

import cn.showclear.dbcptest.pojo.SystemInfoEntity;
import cn.showclear.dbcptest.pojo.TestResultsEntity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * 打印输出excel,excel格式固定
 * <p>环境信息</p>
 * <ul>
 * <li>CPU</li>
 * <li>内存</li>
 * <li>系统</li>
 * </ul>
 * <p>测试版本</p>
 * <ul>
 * <li>MYSQL版本</li>
 * <li>DBCP版本</li>
 * <li>Tomcat-Jdbc版本</li>
 * <li>C3p0版本</li>
 * <li>druid版本</li>
 * <li>HikariCP版本</li>
 * </ul>
 * <p>测试结果</p>
 * <ul>
 * <li>模式 + 五个连接池所花的时间</li>
 * </ul>
 */
@Service
public class ExcelProcessor {
    private Workbook workbook;

    public ExcelProcessor() {
        workbook = new HSSFWorkbook();
        workbook.createSheet("数据库连接池对比");
    }

    public void printExcel(TestResultsEntity testResultsEntity) {
        SystemInfoEntity systemInfoEntity = testResultsEntity.getSystemInfoEntity();
        printTitle();
        printSystemInfo(testResultsEntity);
        printDependencyVersion(testResultsEntity);
        printTestData(testResultsEntity);
        try {
            printToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("已输出excel统计表格...");
    }

    private void printTitle() {
        Sheet sheet = workbook.getSheetAt(0);
        sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        Cell cell = workbook.getSheetAt(0).getRow(0).createCell(0);
        cell.setCellStyle(getTitleStyle());
        cell.setCellValue("数据库连接测测试");
        sheet.setDefaultColumnWidth(24);
        sheet.setColumnWidth(0, 16384);

    }

    private void printSystemInfo(TestResultsEntity testResultsEntity) {
        SystemInfoEntity systemInfoEntity = testResultsEntity.getSystemInfoEntity();
        Sheet sheet = workbook.getSheetAt(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
        Cell cell = row2.createCell(0);
        cell.setCellValue("测试环境");
        cell.setCellStyle(getMiddleTitleStyle());

        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 6));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 6));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 6));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 6));

        Row row3 = sheet.createRow(3);
        Row row4 = sheet.createRow(4);
        Row row5 = sheet.createRow(5);
        Row row6 = sheet.createRow(6);

        row3.createCell(0).setCellValue("OS-Info");
        row4.createCell(0).setCellValue("CPU");
        row5.createCell(0).setCellValue("Memory");
        row6.createCell(0).setCellValue("Mysql-Version");

        row3.createCell(1).setCellValue(systemInfoEntity.getOsName());
        row4.createCell(1).setCellValue(systemInfoEntity.getCpuInfo());
        row5.createCell(1).setCellValue(systemInfoEntity.getRamInfo());
        row6.createCell(1).setCellValue(systemInfoEntity.getMysqlVersion());
    }

    private void printDependencyVersion(TestResultsEntity testResultsEntity) {
        Sheet sheet = workbook.getSheetAt(0);
        SystemInfoEntity systemInfoEntity = testResultsEntity.getSystemInfoEntity();
        for (int i = 7; i < 13; i++) {
            sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
        }
        Cell cell = sheet.createRow(7).createCell(0);
        cell.setCellValue("测试连接池版本");
        cell.setCellStyle(getMiddleTitleStyle());

        int row = 8;
        for (String dependency : systemInfoEntity.getDbcpInfoList()) {
            sheet.createRow(row).createCell(0).setCellValue(dependency);
            row++;
        }
    }

    private void printTestData(TestResultsEntity testResultsEntity) {
        Sheet sheet = workbook.getSheetAt(0);
        sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 6));
        Cell cell = sheet.createRow(13).createCell(0);
        cell.setCellValue("测试详情(ms)");
        cell.setCellStyle(getMiddleTitleStyle());

        Set<String> set = testResultsEntity.getTestResults().get(0).getTimes().keySet();
        sheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 1));
        Row row14 = sheet.createRow(14);
        row14.createCell(0).setCellValue("Mode( ThreadNum * QueryCount in [ initPoolSize,maxPoolSize ] sql )");
        ArrayList<String> nameList = new ArrayList<>(set);
        int cellNum = 2;
        for (String name : nameList) {
            row14.createCell(cellNum).setCellValue(name);
            cellNum++;
        }

        int rowNum = 15;
        for (TestResultsEntity.TestResult result : testResultsEntity.getTestResults()) {
            cellNum = 2;
            Row row = sheet.createRow(rowNum);
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
            row.createCell(0).setCellValue(result.getTestMode());
            for (String poolName : nameList) {
                row.createCell(cellNum).setCellValue(result.getTimes().get(poolName));
                cellNum++;
            }
            rowNum++;
        }
    }

    private void printToFile() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File("连接池测试报告.xls"))) {
            workbook.write(fileOutputStream);
        }
    }

    private CellStyle getTitleStyle() {
        //标题样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 24);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private CellStyle getMiddleTitleStyle() {
        //标题样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);
        cellStyle.setFont(font);
        return cellStyle;
    }

}
