package cn.showclear.dbcptest;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbcpTestApplicationTests {


    @Test
    public void contextLoads() {
        Sigar sigar = new Sigar();
        try {
            CpuInfo cpuInfo = sigar.getCpuInfoList()[0];
            cpuInfo.getVendor();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

}
