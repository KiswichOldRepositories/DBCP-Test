package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.TestModeBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import cn.showclear.dbcptest.service.DatasourceRunner;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Properties;

public class DruidDatasourceRunner extends BaseDatesourceRunner {
    protected DruidDataSource druidDataSource;

    public DruidDatasourceRunner(DatabaseBean databaseBean) {
        super(databaseBean);
        this.DbcpName = "druid";
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
        this.druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        return this.druidDataSource;
    }

    @Override
    public void close() {
        try {
            this.druidDataSource.close();
        } catch (Exception ignore) {

        }
    }

    @Override
    public void test() {
        testProxy(this.druidDataSource);
    }
}
