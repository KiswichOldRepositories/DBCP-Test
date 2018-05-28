package cn.showclear.dbcptest.service;

import javax.sql.DataSource;

/**
 * 运行datasource的test
 */
public interface DatasourceRunner {
    public DataSource open();

    public void test();
}
