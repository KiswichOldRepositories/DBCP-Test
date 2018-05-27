package cn.showclear.dbcptest.dbcp;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
public class BbcpConfigure {

    private DataSource unpoolDataSource;

    @Autowired
    public void setUnpoolDataSource(@Qualifier("DS") DataSource unpoolDataSource) {
        this.unpoolDataSource = unpoolDataSource;
    }

    public BbcpConfigure() {
    }


    @Bean("c3p0DS")
    @Order
    public DataSource getC3p0Datasource() throws SQLException {
        return DataSources.pooledDataSource(unpoolDataSource);
    }

    @Bean("tomcatDS")
    @Order
    public DataSource getTomcatDatasource() {

    }
}
