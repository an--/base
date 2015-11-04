package orther;

import java.util.HashMap;
import java.util.Map;

public class ConvertTest {

	public static void main(String[] args){
		Map<Integer,Object> map = new HashMap<Integer, Object>();
		map.put(1, "1");
		System.out.println(map.get(1).getClass());
	}
	
}
