package cn.showclear.dbcptest.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试模式
 * n线程 * m次sql 默认（select 1）
 * TODO 对sql进行校验
 */

@ConfigurationProperties(prefix = "mode-test")
@Component
@Getter
@Setter
public class TestModeBean {

    private List<Mode> modes = new ArrayList<>();
    private Integer averageCount;


    @Getter
    @Setter
    public static class Mode{
        private String sql;
        private Integer poolSize;
        private Integer initSize;
        private Integer threadNumber;
        private Integer queryCount;

    }
}
