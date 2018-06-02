package cn.showclear.dbcptest.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SystemInfoEntity {
    private String cpuInfo;
    private String ramInfo;
    private String osName;

    private List<String> dbcpInfoList;
    private String mysqlVersion;
}
