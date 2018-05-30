package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.TestModeBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Properties;


public class DbcpDatasourceRunner extends BaseDatesourceRunner {
    protected BasicDataSource dbcpDataSource;

    public DbcpDatasourceRunner(DatabaseBean databaseBean) {
        super(databaseBean);
        this.DbcpName = "dbcp";
    }

    @Override
    public DataSource open() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("driverClassName", databaseBean.getDriverClassName());
        properties.setProperty("url", databaseBean.getUrl());
        properties.setProperty("username", databaseBean.getUsername());
        properties.setProperty("password", databaseBean.getPassword());
        properties.setProperty("initialSize", String.valueOf(mode.getPoolSize()));
        properties.setProperty("maxActive", String.valueOf(mode.getPoolSize()));
        properties.setProperty("maxTotal", String.valueOf(mode.getPoolSize()));
        properties.setProperty("minIdle", String.valueOf(mode.getPoolSize()));
        this.dbcpDataSource = BasicDataSourceFactory.createDataSource(properties);
        return this.dbcpDataSource;
    }

    @Override
    public void close() {
        try {
            dbcpDataSource.close();
        } catch (Exception ignore) {
        }
    }

    @Override
    public void test() {
        testProxy(this.dbcpDataSource);
    }
}
