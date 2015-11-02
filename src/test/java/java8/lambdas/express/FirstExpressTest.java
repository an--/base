package java8.lambdas.express;

import java.util.function.BinaryOperator;

public class FirstExpressTest {

	public static void main( String[] args ){

		Runnable noArguments = () -> System.out.println("first lambdas express");
		Thread t1 = new Thread(noArguments);
		t1.start();
		
		BinaryOperator<String> binaryOperator = ( a , b ) -> a + b;
		
		binaryOperatorTest(binaryOperator);

	}
	
	public static void binaryOperatorTest( BinaryOperator<String> binaryOperator ){
		System.out.println( binaryOperator.apply("a", "   b") );
	}
	
	
	
}

