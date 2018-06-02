package cn.showclear.dbcptest.service.processor;

import cn.showclear.dbcptest.pojo.TestModeBean;
import cn.showclear.dbcptest.pojo.TestResultsEntity;
import cn.showclear.dbcptest.service.BaseDatasourceRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TestProcessor {
    private final Logger logger = LoggerFactory.getLogger(TestProcessor.class);
    TestModeBean testModeBean;
    List<BaseDatasourceRunner> datasourceRunners;

    public TestProcessor(TestModeBean testModeBean, List<BaseDatasourceRunner> datasourceRunners) {
        this.testModeBean = testModeBean;
        this.datasourceRunners = datasourceRunners;
    }

    public List<TestResultsEntity.TestResult> test() throws Exception {
        ArrayList<TestResultsEntity.TestResult> testResults = new ArrayList<>();

        preTest();

        for (TestModeBean.Mode mode : testModeBean.getModes()) {
            String modeTitle = String.format("%d*%d in [ %d , %d ],sql : %s ", mode.getThreadNumber(), mode.getQueryCount(), mode.getInitSize(), mode.getPoolSize(), mode.getSql());
            TestResultsEntity.TestResult testResult = new TestResultsEntity.TestResult();
            testResult.setTestMode(modeTitle);

            for (BaseDatasourceRunner datasourceRunner : datasourceRunners) {
                Integer time;
                Integer sum = 0;
                for (int i = 0; i < testModeBean.getAverageCount(); i++) {
                    Date date = new Date();
                    datasourceRunner.open(mode);
                    datasourceRunner.test();
                    datasourceRunner.close();
                    sum = Long.valueOf(new Date().getTime() - date.getTime()).intValue() + sum;
                }
                time = sum / testModeBean.getAverageCount();
                testResult.getTimes().put(datasourceRunner.getDbcpName(), time);
//                chartProcessor.addValue(time, datasourceRunner.getDbcpName(), modeTitle);
                System.out.println(String.format("%s : %d ms | at mode : { poolSize: %d , queryCount : %d , threadNumber: %d ,sql: %s }",
                        datasourceRunner.getDbcpName(), time, mode.getPoolSize(), mode.getQueryCount(), mode.getThreadNumber(), mode.getSql()));
            }

            testResults.add(testResult);
            System.out.println("``````````````````````````````````````````````````````````````````");
        }
        return testResults;

    }

    /**
     * 测试前
     */
    private void preTest() throws Exception {
        datasourceRunners.get(0).open(testModeBean.getModes().get(0));
        datasourceRunners.get(0).test();
        datasourceRunners.get(0).close();
    }
}
