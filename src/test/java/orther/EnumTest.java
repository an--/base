package orther;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumTest {
    
    private static Logger logger = LoggerFactory.getLogger(EnumTest.class);
    
    public static enum format {
        
        bd09ll,
        
        bd09mc,
        
        wgs84ll;
        
    }
    
    public static void main(String[] args){
        logger.info( format.bd09ll.name() );
    }

}
