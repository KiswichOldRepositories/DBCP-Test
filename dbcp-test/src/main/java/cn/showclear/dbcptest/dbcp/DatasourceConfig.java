package cn.showclear.dbcptest.dbcp;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.beans.ConstructorProperties;

/**
 * 手动配置datasources
 * <p>在引入多个连接池包后 {@link DataSourceAutoConfiguration}会对其进行多次初始化</p>
 */
@Configuration
public class DatasourceConfig {
    @Value("${spring.datasource.username}")
    public String user;

    @Value("${spring.datasource.password}")
    public String passWd;

    @Value("${spring.datasource.url}")
    public String url;

    @Value("${spring.datasource.driver-class-name}")
    public String driverClass;

    @Bean(name = "DS")
    @Primary
    @Order(1)
    public DataSource getDatasource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driverClass);
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(passWd);
        return basicDataSource;
    }


}
