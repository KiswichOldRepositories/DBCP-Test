package cn.showclear.dbcptest.service.processor;

/**
 * 读取系统信息和依赖版本
 * 系统信息通过{@link org.hyperic.sigar.Sigar}读取
 * 版本依赖通过 gradle task 写入 version.md，直接读取文件 ,因此需要先执行
 */
public class SystemInfoProcessor {


}
