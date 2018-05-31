package cn.showclear.dbcptest;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.TestModeBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import cn.showclear.dbcptest.service.ChartProcesser;
import cn.showclear.dbcptest.service.DatasourceRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    List<BaseDatesourceRunner> datasourceRunners;

    @Override
    public void run(String... args) throws Exception {
        ChartProcesser chartProcesser = new ChartProcesser("数据库连接池对比", "连接池", "毫秒");
        for (TestModeBean.Mode mode : testModeBean.getModes()) {

            String modeTitle = String.format("%d*%din%d sql:%s", mode.getThreadNumber(), mode.getQueryCount(), mode.getPoolSize(), mode.getSql());
            for (BaseDatesourceRunner datasourceRunner : datasourceRunners) {

                Integer time;
                Integer sum = 0;
                for (int i = 0; i < testModeBean.getAverageCount(); i++) {
                    Date date = new Date();
                    datasourceRunner.open(mode);
                    datasourceRunner.test();
                    datasourceRunner.close();
                    sum = new Long(new Date().getTime() - date.getTime()).intValue() + sum;
                }
                time = sum / testModeBean.getAverageCount();

                chartProcesser.addValue(time, datasourceRunner.getDbcpName(), modeTitle);
                System.out.println(datasourceRunner.getDbcpName() + " : " + time + " ms | at mode : {poolSIze:" +
                        mode.getPoolSize() + ", queryCount: " + mode.getQueryCount() + " ,threadNumber: " + mode.getThreadNumber() + "}");
            }
            System.out.println("`````````````````````````````````````````````");
        }
        chartProcesser.print();
    }
}
