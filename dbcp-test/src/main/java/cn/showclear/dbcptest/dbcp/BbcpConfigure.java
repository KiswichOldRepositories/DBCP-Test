package cn.showclear.dbcptest.dbcp;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import cn.showclear.dbcptest.service.impl.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BbcpConfigure {

    private DataSource unpoolDataSource;
    private DatabaseBean databaseBean;

    @Autowired
    public void setUnpoolDataSource(/*@Qualifier("DS") DataSource unpoolDataSource,*/ DatabaseBean databaseBean) {
//        this.unpoolDataSource = unpoolDataSource;
        this.databaseBean = databaseBean;
    }

    public BbcpConfigure() {
    }


    @Bean("datasourceRunner")
    public List<BaseDatesourceRunner> getDatasourceRunner() {
        ArrayList<BaseDatesourceRunner> datasourceRunners = new ArrayList<>();
        datasourceRunners.add(new HikariCPDatasourceRunner(databaseBean));
        datasourceRunners.add(new DbcpDatasourceRunner(databaseBean));
        datasourceRunners.add(new DruidDatasourceRunner(databaseBean));
        datasourceRunners.add(new C3p0DatasourceRunner(databaseBean));
        datasourceRunners.add(new TomcatJdbcDatasourceRunner(databaseBean));
        return datasourceRunners;
    }
}
