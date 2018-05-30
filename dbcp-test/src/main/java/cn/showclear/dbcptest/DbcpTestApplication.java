package cn.showclear.dbcptest;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.TestModeBean;
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

@SpringBootApplication
public class DbcpTestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DbcpTestApplication.class, args);
    }

    @Qualifier("tomcatDS")
    @Autowired
    private DataSource tomcatDataSource;

    @Qualifier("c3p0DS")
    @Autowired
    private DataSource c3p0Datasource;

//    @Autowired
//    @Qualifier("tomcatDS")
//    public DbcpTestApplication setTomcatDataSource(DataSource tomcatDataSource) {
//        this.tomcatDataSource = tomcatDataSource;
//        return this;
//    }
//
//    @Autowired
//    @Qualifier("c3p0DS")
//    public DbcpTestApplication setC3p0Datasource(DataSource c3p0Datasource) {
//        this.c3p0Datasource = c3p0Datasource;
//        return this;
//    }

    @Autowired
    DatabaseBean databaseBean;
    @Autowired
    TestModeBean testModeBean;
    @Override
    public void run(String... args) throws Exception {
        System.out.println(databaseBean);
//        test(c3p0Datasource);
//        test(c3p0Datasource);
//        test(tomcatDataSource);
//        test(tomcatDataSource);
////        Thread.sleep(120000);
////        test(tomcatDataSource);
////        Thread.sleep(120000);
////        test(c3p0Datasource);
////        Thread.sleep(120000);
////        Thread.sleep(120000);
    }

    private void test(DataSource dataSource) throws SQLException {
        Date date = new Date();
        for (int i = 0; i < 10000; i++) {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        System.out.println(new Date().getTime() - date.getTime());
    }
}
