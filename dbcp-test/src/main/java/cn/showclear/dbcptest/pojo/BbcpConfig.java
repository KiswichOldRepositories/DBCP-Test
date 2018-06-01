package cn.showclear.dbcptest.pojo;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.service.BaseDatesourceRunner;
import cn.showclear.dbcptest.service.impl.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局配置
 */
@Configuration
public class BbcpConfig {

    private DatabaseBean databaseBean;

    @Autowired
    public void setUnpoolDataSource(DatabaseBean databaseBean) {
        this.databaseBean = databaseBean;
    }

    public BbcpConfig() {
    }


    @Bean("datasourceRunner")
    public List<BaseDatesourceRunner> getDatasourceRunner() {
        ArrayList<BaseDatesourceRunner> datasourceRunners = new ArrayList<>();
        datasourceRunners.add(new DbcpDatasourceRunner(databaseBean));
        datasourceRunners.add(new DruidDatasourceRunner(databaseBean));
        datasourceRunners.add(new C3p0DatasourceRunner(databaseBean));
        datasourceRunners.add(new TomcatJdbcDatasourceRunner(databaseBean));
        datasourceRunners.add(new HikariCPDatasourceRunner(databaseBean));
        return datasourceRunners;
    }
}
