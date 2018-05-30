package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.TestModeBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import cn.showclear.dbcptest.service.DatasourceRunner;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;


public class C3p0DatasourceRunner extends BaseDatesourceRunner {

    private ComboPooledDataSource comboPooledDataSource;

    public C3p0DatasourceRunner(DatabaseBean databaseBean) {
        super(databaseBean);
        this.DbcpName = "c3p0";
    }


    @Override
    public DataSource open() throws PropertyVetoException {
        this.comboPooledDataSource = new ComboPooledDataSource();
        this.comboPooledDataSource.setDriverClass(databaseBean.getDriverClassName());
        this.comboPooledDataSource.setJdbcUrl(databaseBean.getUrl());
        this.comboPooledDataSource.setUser(databaseBean.getUsername());
        this.comboPooledDataSource.setPassword(databaseBean.getPassword());

        this.comboPooledDataSource.setMinPoolSize(mode.getPoolSize());
        this.comboPooledDataSource.setMaxPoolSize(mode.getPoolSize());
        return this.comboPooledDataSource;
    }

    @Override
    public void close() {
        try {
            comboPooledDataSource.close();
        } catch (Exception ignore) {
        }
    }

    @Override
    public void test() {
        testProxy(comboPooledDataSource);
    }
}
