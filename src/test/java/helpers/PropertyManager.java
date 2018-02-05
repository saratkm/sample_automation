package helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
	private static final Properties PROPERTY = new Properties();
	private static final String FRAMEWORKPROPERTIESPATH ="\\src\\test\\resources\\properties\\";
			
	public static Properties loadProperty(String propertyFileToLoad) {
		FileInputStream fis = null;
		
	    try {
	    	String filePath = System.getProperty("user.dir")+ FRAMEWORKPROPERTIESPATH + propertyFileToLoad;
	    	System.out.println("properties from:"+filePath);
	    	fis = new FileInputStream(filePath);
	        PROPERTY.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Runtime.getRuntime().halt(0);
	    } catch (IOException io) {
	    	io.printStackTrace();
	        Runtime.getRuntime().halt(0);
	    } finally {
			if (fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					Runtime.getRuntime().halt(0);
				}
			}
		}
	    
	    return PROPERTY;
	}
}
