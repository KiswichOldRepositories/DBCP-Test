package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.service.BaseDatasourceRunner;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;

public class TomcatJdbcDatasourceRunner extends BaseDatasourceRunner {

    private org.apache.tomcat.jdbc.pool.DataSource tomcatDatasource;

    public TomcatJdbcDatasourceRunner(DatabaseBean databaseBean) {
        super(databaseBean);
        this.DbcpName = "tomcat-jdbc";
    }

    @Override
    public DataSource open() {
        PoolProperties p = new PoolProperties();
        p.setUrl(databaseBean.getUrl());
        p.setDriverClassName(databaseBean.getDriverClassName());
        p.setUsername(databaseBean.getUsername());
        p.setPassword(databaseBean.getPassword());
        p.setInitialSize(mode.getInitSize());
        p.setMaxActive(mode.getPoolSize());

        this.tomcatDatasource = new org.apache.tomcat.jdbc.pool.DataSource();
        this.tomcatDatasource.setPoolProperties(p);
        return this.tomcatDatasource;
    }

    @Override
    public void close() {
        try {
            this.tomcatDatasource.close();
        } catch (Exception ignore) {

        }
    }

    @Override
    public void test() {
        testProxy(tomcatDatasource);
    }
}
