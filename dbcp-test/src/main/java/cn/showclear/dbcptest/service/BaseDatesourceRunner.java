package cn.showclear.dbcptest.service;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.TestModeBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

public abstract class BaseDatesourceRunner implements DatasourceRunner {
    protected String DbcpName;
    protected DatabaseBean databaseBean;
    protected TestModeBean.Mode mode;
    public CountDownLatch countDownLatch;

    public BaseDatesourceRunner(DatabaseBean databaseBean) {
        this.databaseBean = databaseBean;
    }

    public TestModeBean.Mode getMode() {
        return mode;
    }

    public BaseDatesourceRunner setMode(TestModeBean.Mode mode) {
        this.mode = mode;
        return this;
    }

    public String getDbcpName() {
        return DbcpName;
    }

    public BaseDatesourceRunner setDbcpName(String dbcpName) {
        DbcpName = dbcpName;
        return this;
    }

    /**
     * 核心测试过程
     *
     * @param connection
     * @throws SQLException
     */
    protected void testProcess(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(mode.getSql());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    /**
     * 循环测试过程
     *
     * @param dataSource
     */
    protected void testProxy(final DataSource dataSource) {
        this.countDownLatch = new CountDownLatch(mode.getThreadNumber());
        for (int i = 0; i < mode.getThreadNumber(); i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int x = 0; x < mode.getQueryCount(); x++) {
                        try {
                            testProcess(dataSource.getConnection());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    countDownLatch.countDown();
                }
            }) {
            }.start();
        }
        try {
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
