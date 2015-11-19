package orther;

/**
 * class init test
 * 
 * @author an
 *
 */
public class ClassInitTest {
    
    public static ClassInitTest o = new ClassInitTest();
    
    static{
        System.out.println( "o.a " + o.a);
        System.out.println( "o.a " + o.b);
    }
    
    public static int a;
    
    public static int b = 1;
    
    
    
    
    public ClassInitTest(){
        System.out.println("constructor " + a);
        System.out.println("constructor " + b);
        a = 2;
    }
    
    public static void main(String[] args) {
        
    }

}
