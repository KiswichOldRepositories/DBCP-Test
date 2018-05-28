package cn.showclear.dbcptest.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
public class DatabaseBean {
    private String username;
    private String password;
    private String url;
    private String driverClassName;

    public String getUsername() {
        return username;
    }

    public DatabaseBean setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DatabaseBean setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DatabaseBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public DatabaseBean setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }
}
