package base.tool.orther;

/**
 * 数字字符串相关工具
 * 
 * @author an
 *
 */
public class NumberStrings {
    
    /**
     * 求数字字符串去掉符号之后各个数字的和
     * sumLongChar("1234") == 10
     * sumLongChar("-1234") == 10 
     * 
     * @param numberStr
     * @return
     */
    public static int sumUnsignedNumberString(String numberStr) {
        numberStr = numberStr.trim();
        int strLen = numberStr.length();
        int sum = 0;
        for (int i = 0; i < strLen; i++) {
            if ('-' == numberStr.charAt(0)) {
                continue;
            }
            sum += numberStr.charAt(i) - 48;
        }
        return sum;
    }
    
    /**
     * 求数字去掉符号之后的每一位相加的和；
     * sumLongChar(1234) == 10 
     * 
     * @param number
     * @return
     */
    public static int sumLongChar(long number) {
        return sumUnsignedNumberString("" + Math.abs(number));
        
    }
    
    /**
     * 求数字去掉符号之后的每一位相加的和；
     * sumLongChar(123.4) == 10 
     * 
     * @param number
     * @return
     */
    public static int sumDoubleChar(Double number) {
        return sumUnsignedNumberString("" + Math.abs(number));
        
    }

}
