package cn.showclear.dbcptest;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.TestModeBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
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
        for (TestModeBean.Mode mode : testModeBean.getModes()) {
            for (BaseDatesourceRunner datasourceRunner : datasourceRunners) {
                Date date = new Date();
                datasourceRunner.setMode(mode);
                datasourceRunner.open();
                datasourceRunner.test();
                datasourceRunner.close();
                System.out.println(datasourceRunner.getDbcpName() + " : " + (new Date().getTime() - date.getTime()));
            }
            System.out.println("`````````````````````````````````````````````");
        }

    }
}
