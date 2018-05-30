package cn.showclear.dbcptest.service.impl;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import cn.showclear.dbcptest.service.DatasourceRunner;

import javax.sql.DataSource;

public class C3p0DatasourceRunner extends BaseDatesourceRunner {

    public C3p0DatasourceRunner(DatabaseBean databaseBean) {
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
