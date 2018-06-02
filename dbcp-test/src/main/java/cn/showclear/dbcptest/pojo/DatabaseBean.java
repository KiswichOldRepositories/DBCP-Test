package cn.showclear.dbcptest.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
@Getter
@Setter
public class DatabaseBean {
    private String username;
    private String password;
    private String url;
    private String driverClassName;
}
