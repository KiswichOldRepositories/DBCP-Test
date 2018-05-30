package cn.showclear.dbcptest.service;

import javax.sql.DataSource;

/**
 * 运行datasource的test
 */
public interface DatasourceRunner {
    /**
     * 连接数据库，返回连接池的datasource
     * @return
     */
    public DataSource open();

    /**
     * 关闭连接池
     */
    public void close();

    /**
     * 启动测试
     */
    public void test();
}
