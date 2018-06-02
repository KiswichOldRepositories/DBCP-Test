package cn.showclear.dbcptest;

import cn.showclear.dbcptest.pojo.SystemInfoEntity;
import cn.showclear.dbcptest.pojo.TestModeBean;
import cn.showclear.dbcptest.pojo.TestResultsEntity;
import cn.showclear.dbcptest.service.BaseDatasourceRunner;
import cn.showclear.dbcptest.service.processor.ChartProcessor;
import cn.showclear.dbcptest.service.processor.ExcelProcessor;
import cn.showclear.dbcptest.service.processor.SystemInfoProcessor;
import cn.showclear.dbcptest.service.processor.TestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DbcpTestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DbcpTestApplication.class, args);
    }

    @Autowired
    TestModeBean testModeBean;

    @Qualifier("datasourceRunner")
    @Autowired
    List<BaseDatasourceRunner> datasourceRunners;
    @Autowired
    SystemInfoProcessor systemInfoProcessor;
    @Autowired
    TestProcessor testProcessor;
    @Autowired
    ExcelProcessor excelProcessor;
    @Override
    public void run(String... args) throws Exception {
        //获取测试数据
        TestResultsEntity testResultsEntity = new TestResultsEntity();
        testResultsEntity.setSystemInfoEntity(systemInfoProcessor.getSystemInfo());
        testResultsEntity.setTestResults(testProcessor.test());
        //输出测试数据
        new ChartProcessor("数据库连接池对比", "连接池", "毫秒").print(testResultsEntity);
        excelProcessor.printExcel(testResultsEntity);
    }
}
