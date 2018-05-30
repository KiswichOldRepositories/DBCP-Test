package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.TestModeBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import cn.showclear.dbcptest.service.DatasourceRunner;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;
import sun.awt.image.GifImageDecoder;

import javax.sql.DataSource;

public class HikariCPDatasourceRunner extends BaseDatesourceRunner {
    protected HikariDataSource hikariDataSource;

    public HikariCPDatasourceRunner(DatabaseBean databaseBean) {
        super(databaseBean);
        this.DbcpName = "HikariCP";
    }

    @Override
    public DataSource open() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(databaseBean.getUsername());
        hikariDataSource.setPassword(databaseBean.getPassword());
        hikariDataSource.setJdbcUrl(databaseBean.getUrl());
        hikariDataSource.setDriverClassName(databaseBean.getDriverClassName());
        hikariDataSource.setMaximumPoolSize(mode.getPoolSize());
        hikariDataSource.setMinimumIdle(mode.getPoolSize());
        this.hikariDataSource = hikariDataSource;
        return hikariDataSource;
    }

    @Override
    public void close() {
        try {
            this.hikariDataSource.close();
        } catch (Exception ignore) {

        }
    }

    @Override
    public void test() {
        testProxy(this.hikariDataSource);
    }
}
