
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import step_definitions.Hooks;
import step_definitions.RunCukesTest;


public class Main {

	public static void main(String[] args) {
		org.testng.TestNG.main(args);
	}
	
//	public static void main(String[] args) {
//		TestListenerAdapter tla = new TestListenerAdapter();
//		TestNG testng = new TestNG();
//		testng.setTestClasses(new Class[] { Hooks.class });
//		testng.addListener(tla);
//		testng.run();
//	}
	


}
