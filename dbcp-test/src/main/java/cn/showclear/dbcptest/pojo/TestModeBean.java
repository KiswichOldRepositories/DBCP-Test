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
public class TestModeBean {

    private List<Mode> modes = new ArrayList<>();

    private Integer averageCount;

    public List<Mode> getModes() {
        return modes;
    }

    public TestModeBean setModes(List<Mode> modes) {
        this.modes = modes;
        return this;
    }

    public Integer getAverageCount() {
        return averageCount;
    }

    public TestModeBean setAverageCount(Integer averageCount) {
        this.averageCount = averageCount;
        return this;
    }

    public static class Mode{
        private String sql;
        private Integer poolSize;
        private Integer threadNumber;
        private Integer queryCount;

        public String getSql() {
            return sql;
        }

        public Mode setSql(String sql) {
            this.sql = sql;
            return this;
        }

        public Integer getPoolSize() {
            return poolSize;
        }

        public Mode setPoolSize(Integer poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public Integer getThreadNumber() {
            return threadNumber;
        }

        public Mode setThreadNumber(Integer threadNumber) {
            this.threadNumber = threadNumber;
            return this;
        }

        public Integer getQueryCount() {
            return queryCount;
        }

        public Mode setQueryCount(Integer queryCount) {
            this.queryCount = queryCount;
            return this;
        }
    }
}
