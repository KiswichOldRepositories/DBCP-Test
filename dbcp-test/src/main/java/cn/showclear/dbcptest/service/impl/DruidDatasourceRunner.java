package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import cn.showclear.dbcptest.service.DatasourceRunner;

import javax.sql.DataSource;

public class DruidDatasourceRunner extends BaseDatesourceRunner {
    public DruidDatasourceRunner(DatabaseBean databaseBean) {
        super(databaseBean);
    }

    @Override
    public DataSource open() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void test() {

    }
}
