package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.service.BaseDatasourceRunner;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class DruidDatasourceRunner extends BaseDatasourceRunner {
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
        properties.setProperty("initialSize", String.valueOf(mode.getInitSize()));
        properties.setProperty("maxActive", String.valueOf(mode.getPoolSize()));
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
