package cn.showclear.dbcptest.service.processor;

import cn.showclear.dbcptest.pojo.DatabaseBean;
import cn.showclear.dbcptest.pojo.SystemInfoEntity;
import cn.showclear.dbcptest.service.BaseDatasourceRunner;
import com.google.common.io.Files;
import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * 读取系统信息和依赖版本
 * 系统信息通过{@link org.hyperic.sigar.Sigar}读取
 * 版本依赖通过 gradle task 写入 version.md，直接读取文件
 */
@Service
public class SystemInfoProcessor {
    private final Logger logger = LoggerFactory.getLogger(SystemInfoProcessor.class);

    private DatabaseBean databaseBean;
    private File temps;

    private Sigar sigar;

    public SystemInfoProcessor(DatabaseBean databaseBean) {
        try {
            this.copyFileToLocal();
            this.databaseBean = databaseBean;
        } catch (IOException e) {
            logger.error("sigar库文件复制失败！");
        }
        sigar = new Sigar();
    }

    public SystemInfoEntity getSystemInfo() {
        SystemInfoEntity systemInfoEntity = new SystemInfoEntity();
        try {
            systemInfoEntity.setCpuInfo(getCpuInfo());
            systemInfoEntity.setRamInfo(getMemoryInfo());
            systemInfoEntity.setMysqlVersion(getMysqlVersion());
            systemInfoEntity.setDbcpInfoList(getDepencyInfo());
            systemInfoEntity.setOsName(getOsInfo());
        } catch (SigarException e) {
            logger.error("sigar出错！");
        } catch (Exception e) {
            logger.error("io出错！");
        }
        //只能提前删除了
        deleteFile();
        return systemInfoEntity;
    }

    private String getCpuInfo() throws SigarException {
        ArrayList<String> cpuInfoList = new ArrayList<>();
        for (CpuInfo cpu : sigar.getCpuInfoList()) {
            cpuInfoList.add(cpu.getModel());
        }
        return Arrays.toString(cpuInfoList.toArray());
    }

    private String getMemoryInfo() throws SigarException {
        Mem mem = sigar.getMem();
        long total = mem.getTotal();
        long free = mem.getFree();
        return String.format("total:%.4fGb,free:%.4fGb", (double) total / 1024 / 1024 / 1024, (double) free / 1024 / 1024 / 1024);
    }

    private String getOsInfo() {
        return System.getProperty("os.name");
    }

    // TODO: 2018/6/2 获取硬盘信息，机械or固态 && 型号
    private String getHddInfo() throws SigarException {
        FileSystem[] fileSystemList = sigar.getFileSystemList();
        for (FileSystem fileSystem : fileSystemList) {
            fileSystem.getDevName();
        }
        return null;
    }

    private List<String> getDepencyInfo() throws IOException {
        InputStream resourceAsStream = SystemInfoProcessor.class.getResourceAsStream("/version.md");
        byte[] bytes = new byte[resourceAsStream.available()];
        resourceAsStream.read(bytes);
        String s = new String(bytes, "UTF-8");
        return Arrays.asList(s.split("\r\n"));
    }


    private String getMysqlVersion() throws Exception {
        Class.forName(databaseBean.getDriverClassName());
        Connection connection = DriverManager.getConnection(databaseBean.getUrl(), databaseBean.getUsername(), databaseBean.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT VERSION()");
        ResultSet resultSet = preparedStatement.executeQuery();
        String version = "";
        while (resultSet.next()) {
            version = resultSet.getString("version()");
        }
        resultSet.close();
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return version;
    }

    private void copyFileToLocal() throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/sigar-amd64-winnt.dll");
        InputStream resourceAsStream1 = this.getClass().getResourceAsStream("/sigar-x86-winnt.dll");
        File tempDir = Files.createTempDir();
        setSigarPath(tempDir);
        this.temps = tempDir;

        File file = new File(tempDir, "sigar-amd64-winnt.dll");
        File file1 = new File(tempDir, "sigar-x86-winnt.dll");
        int len;
        byte[] bytes = new byte[1024];
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            while ((len = resourceAsStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
        }
        bytes = new byte[1024];
        try (FileOutputStream fileOutputStream1 = new FileOutputStream(file1)) {
            while ((len = resourceAsStream1.read(bytes)) != -1) {
                fileOutputStream1.write(bytes, 0, len);
            }
        }
    }

    /**
     * sigar会读取环境变量org.hyperic.sigar.path下的路径作为其动态库的路径
     *
     * @param directory
     */
    private void setSigarPath(File directory) {
        System.setProperty("org.hyperic.sigar.path", directory.getAbsolutePath());
        System.out.println(new File("").getAbsolutePath());
    }

    public void deleteFile() {
        delete(this.temps);
    }

    private void delete(File file) {
        if (file.isFile()) file.delete();
        else {
            if (!file.delete())
                for (File file1 : file.listFiles()) {
                    delete(file1);
                }
            file.delete();
        }
    }

    //gc 时删除lib
    // FIXME: 2018/6/2 发现并不会触发。。
    @Override
    protected void finalize() throws Throwable {
//        super.finalize();
        this.deleteFile();
        logger.info("GC!!!!");
    }
}
