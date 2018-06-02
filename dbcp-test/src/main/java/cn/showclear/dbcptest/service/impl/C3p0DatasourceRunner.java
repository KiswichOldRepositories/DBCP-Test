package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.service.BaseDatasourceRunner;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


public class C3p0DatasourceRunner extends BaseDatasourceRunner {

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

        this.comboPooledDataSource.setInitialPoolSize(mode.getInitSize());
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
