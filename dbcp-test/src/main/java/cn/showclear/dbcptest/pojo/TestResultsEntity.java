package cn.showclear.dbcptest.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * time顺序和datasourceRunner的顺序相同
 */
@Getter
@Setter
public class TestResultsEntity {
    private SystemInfoEntity systemInfoEntity;
    private List<TestResult> testResults = new ArrayList<>();

    @Getter
    @Setter
    public static class TestResult {
        private String testMode;
        private Map<String, Integer> times = new HashMap<>();
    }
}
