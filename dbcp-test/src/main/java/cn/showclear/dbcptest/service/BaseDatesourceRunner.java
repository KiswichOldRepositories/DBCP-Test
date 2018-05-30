package cn.showclear.dbcptest.service;

import cn.showclear.dbcptest.pojo.DatabaseBean;

import javax.sql.DataSource;

public abstract class BaseDatesourceRunner implements DatasourceRunner {

    protected DatabaseBean databaseBean;

    public BaseDatesourceRunner( DatabaseBean databaseBean) {
        this.databaseBean = databaseBean;
    }



}
