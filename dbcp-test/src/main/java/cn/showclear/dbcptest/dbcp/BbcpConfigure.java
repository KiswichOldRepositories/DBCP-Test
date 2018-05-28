package cn.showclear.dbcptest.dbcp;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
public class BbcpConfigure {

    private DataSource unpoolDataSource;
    private DatabaseBean databaseBean;

    @Autowired
    public void setUnpoolDataSource(/*@Qualifier("DS") DataSource unpoolDataSource,*/ DatabaseBean databaseBean) {
//        this.unpoolDataSource = unpoolDataSource;
        this.databaseBean = databaseBean;
    }

    public BbcpConfigure() {
    }


    @Bean("c3p0DS")
    @Order
    public DataSource getC3p0Datasource() throws SQLException, PropertyVetoException {
//        return DataSources.pooledDataSource(unpoolDataSource);
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(databaseBean.getDriverClassName()); //loads the jdbc driver
        comboPooledDataSource.setJdbcUrl(databaseBean.getUrl());
        comboPooledDataSource.setUser(databaseBean.getUsername());
        comboPooledDataSource.setPassword(databaseBean.getPassword());

// the settings below are optional -- c3p0 can work with defaults
        comboPooledDataSource.setMinPoolSize(10);
        comboPooledDataSource.setAcquireIncrement(5);
        comboPooledDataSource.setMaxPoolSize(50);
        comboPooledDataSource.close();
        return comboPooledDataSource;
    }


    @Bean("tomcatDS")
    @Order
    public DataSource getTomcatDatasource() {
        PoolProperties p = new PoolProperties();
        p.setUrl(databaseBean.getUrl());
        p.setDriverClassName(databaseBean.getDriverClassName());
        p.setUsername(databaseBean.getUsername());
        p.setPassword(databaseBean.getPassword());
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
//        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(50);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
//        p.setJdbcInterceptors(
//                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
//                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        datasource.setPoolProperties(p);
        datasource.close();
        return datasource;
    }
}
