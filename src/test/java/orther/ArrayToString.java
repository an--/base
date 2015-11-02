package orther;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayToString {

	public static void main(String[] args){
		String[] strArray = {"qerwer" , "asdfasdf","asdfasdfqwer"};
		String[] a2 = (String[]) Array.newInstance(String.class, 3);
		System.out.println(strArray.toString());
		System.out.println(Arrays.toString(strArray));
		System.out.println(Arrays.toString(a2));
	}
	
	
}

