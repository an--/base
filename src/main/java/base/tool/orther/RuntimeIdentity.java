package base.tool.orther;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

import com.google.common.base.Strings;
import com.sun.corba.se.spi.orb.StringPair;

/**
 * jvm 实例标识相关
 * @author an
 *
 */
public class RuntimeIdentity {

    /** runtimeMXBean 对象的 hashCode **/
    public static final int runtimeHashCode;
    
    /** runtimeMXBean 对象 name 的 hashCode **/
    public static final int runtimeNameHashCode;
    
    public static final int runtimeId;
    
    private static long sequence = 0L;
    
    private static long lastSecondsOfTime = 0L;
    
    private static final int SEQ_MAX = 99999;
    
    /** 流水ID {seconds(10)}{channelId (3)}{jvm id (2)}{seq{3}}  **/
    
    static {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        runtimeHashCode = runtimeMXBean.hashCode();
        runtimeNameHashCode = runtimeMXBean.getName().hashCode();
        runtimeId = NumberStrings.sumLongChar(runtimeNameHashCode);        
    }
    
    public static Long getIdentityNumber() {
        long timestamp = System.currentTimeMillis();
        return timestamp;
    }
    
    public static String nextSecondId() {
        long timestamp = System.currentTimeMillis();
        long secondsOfTime = timestamp/1000;
        
        if (secondsOfTime != lastSecondsOfTime) {
            lastSecondsOfTime = secondsOfTime;
            sequence = 0;
        } else {
            if (++sequence > SEQ_MAX) {
                lastSecondsOfTime = tilNextSecond(lastSecondsOfTime)/1000;
                sequence = 0;
            }
        }
        return "" + lastSecondsOfTime + Strings.padStart("" + runtimeId, 2, '0') + Strings.padStart("" + sequence, 5, '0');
    }
    
    private static long tilNextSecond(final long lastSceondsOfTime) {
        long timestamp = System.currentTimeMillis();
        long lastSceondMills = lastSceondsOfTime*1000;
        while (1000 > timestamp - lastSceondMills) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
    
    public static void main(String[] args){
        System.out.println(runtimeId);
        
        int i = 0;
        
        while ( i++ < 200000 ) {
            String id = nextSecondId();
            if (i%100 == 0) {
                System.out.println(id);
            }
        }
        
//        System.out.println(runtimeHashCode);
//        System.out.println(Long.toBinaryString(runtimeHashCode));
//        System.out.println(Long.toBinaryString(runtimeHashCode).length());
//        
//        System.out.println();
//        System.out.println();
//        
//        System.out.println(runtimeNameHashCode);
//        System.out.println(Integer.toBinaryString(runtimeNameHashCode));
//        System.out.println(Integer.toBinaryString(runtimeNameHashCode).length());
    }
    
}
