package orther;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.tool.orther.RuntimeIdentity;

public class RuntimeTest {
    
    private static Logger logger = LoggerFactory.getLogger(RuntimeTest.class);
    
    /**
     * 查看 OperatingSystemBean
     * 
     */
    public static void printOperatingSystemMXBean() {
        OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
        logger.info("name = {}", operatingSystem.getName());
        logger.info("objectName = {}", operatingSystem.getObjectName());
        logger.info("architecture = {}", operatingSystem.getArch());
        logger.info("JVM processors number = {}", operatingSystem.getAvailableProcessors());
        logger.info("version = {}", operatingSystem.getVersion());
        logger.info("load average = {}", operatingSystem.getSystemLoadAverage());
    }
    
    
    /**
     * 输出 RuntimeMXBean 的属性
     * 
     */
    public static void printRuntimeMXBean() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        logger.info("name = {}", runtimeMXBean.getName());
        logger.info("startTime = {}", runtimeMXBean.getStartTime());
        logger.info("uptime = {}", runtimeMXBean.getUptime());
        logger.info("JVM specification name = {}", runtimeMXBean.getSpecName());
        logger.info("JVM specification version = {}", runtimeMXBean.getSpecVersion());
        logger.info("JVM specification vendor = {}", runtimeMXBean.getSpecVendor());
        logger.info("JVM implementation name = {}", runtimeMXBean.getVmName());
        logger.info("JVM implementation vsersion = {}", runtimeMXBean.getVmVersion());
        logger.info("JVM implementation vendor = {}", runtimeMXBean.getVmVendor());
        logger.info("boot class path = {}", runtimeMXBean.getBootClassPath());
        logger.info("class path = {}", runtimeMXBean.getClassPath());
        logger.info("JVM input arguments = {}", runtimeMXBean.getInputArguments());
        logger.info("library path = {}", runtimeMXBean.getLibraryPath());
        logger.info("BootClassPathSupported = {}", runtimeMXBean.isBootClassPathSupported());
        
        
        logger.info("system properties = {}", runtimeMXBean.getSystemProperties());
    }
    
    public static int getRuntimeIdentity() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.hashCode();
    }
    
    public static void main(String[] args) {
        System.out.println(getRuntimeIdentity());
        
        System.out.println(RuntimeIdentity.runtimeHashCode);
        
        System.out.println(RuntimeIdentity.runtimeNameHashCode);
//        printOperatingSystemMXBean();
//        printRuntimeMXBean();
    }
    
}
