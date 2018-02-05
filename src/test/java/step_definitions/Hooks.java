package step_definitions;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
//3.4.0. import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.Assert;

import helpers.Log;
import helpers.PropertyManager;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

//appium tests
//import helpers.AppiumSrvrApp;
//import io.appium.java_client.android.AndroidDriver;
//import junit.framework.Assert;



public class Hooks{
	final String propertyFile = "portal_automation.properties";

	private static Logger Log = Logger.getLogger(Hooks.class.getName());
	public static boolean dComInitDone=false;
    public static WebDriver driver;
    public static WebDriverWait driverWait;
    public static Scenario scenario;
    static String webdriver_firefox_bin, geckodriver_exe,webdriver_latest_firefox;
    
    static String username;
    static String password;
    static String browser;
    static String domainPortalURL;
    static String websiteCountry;
    
    		
    static int browserRefreshMilSec;
    static int TIME_SHORT_SLEEP, TIME_LONG_SLEEP;
    static char browserCode='F';
    static String downloadPath;
    static String currentWindowHandle;
    
    static String ADBavailable;
    //static AndroidDriver android_driver;
    static boolean AppiumAvailable=false;
    static final int HIGHVALUE_NEGATIVE = -999999;
    static boolean debugMode=false;
    static String errorToReport="";
    static boolean showFKtName=true;
    
    
    // Properties file
    public static Properties domainPortalProperties = null; 
 
	public static String getDevPortalProperty(String propertyKey){
		String propVal=domainPortalProperties.getProperty(propertyKey);
		if (propVal!=null){
			return propVal.trim();
		}
		showLog("PROPERTY_NOT_EXIST: "+propertyKey);
		return "";
	}
	
	
    @BeforeSuite(alwaysRun = true)
    public void setupBeforeSuite() throws InterruptedException, AWTException, MalformedURLException {
    	String msg;
    	debugMode = java.lang.management.ManagementFactory.getRuntimeMXBean().
        	    getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
        	    
        //System.out.println("Debug on? "+debugMode);    	
    	if (!dComInitDone){
    		DOMConfigurator.configure("log4j.xml");
    		dComInitDone=true;
    	}

    	showLog("-------------(START)-------------");
 
    	domainPortalProperties =  PropertyManager.loadProperty(propertyFile);
    	
        //Properties from file

    	webdriver_firefox_bin=getDevPortalProperty("webdriver_firefox_bin");
		webdriver_latest_firefox=getDevPortalProperty("webdriver_latest_firefox");
		geckodriver_exe=getDevPortalProperty("geckodriver_exe");
		domainPortalURL = getDevPortalProperty("domainPortalURL");
		websiteCountry = getDevPortalProperty("websiteCountry");	//AU, NZ, IN, US
				
		TIME_SHORT_SLEEP = Integer.parseInt(getDevPortalProperty("TIME_SHORT_SLEEP")); 			// mil-sec
		TIME_LONG_SLEEP = Integer.parseInt(getDevPortalProperty("TIME_LONG_SLEEP"));			// mil-sec
		browserRefreshMilSec = Integer.parseInt(getDevPortalProperty("browserRefreshMilSec"));	// mil-sec
	    
	    ADBavailable=getDevPortalProperty("ADBavailable");
	    AppiumAvailable=(getDevPortalProperty("AppiumAvailable").toUpperCase()).contains("TRUE");

		downloadPath = getDevPortalProperty("downloadPath");
		username = getDevPortalProperty("username");
		password = getDevPortalProperty("password");
		browser = getDevPortalProperty("browser");

	    msg="properties imported (finish)";
	    Log.info(msg);
	    System.out.println(msg);
	    
		openBrowser();
		
		Log.info("Setup Before Suite -- complete");
    }

    
    public static void showLog(String msg){
    	 
    	if (msg==null) return;
    	String logOrCon;
    	if (msg.startsWith("!") || msg.startsWith("#")){
    		logOrCon=msg.substring(0, 1);
    		msg=msg.substring(1);
    	} else {
    		logOrCon="";
    	}
    	if (logOrCon.isEmpty() || logOrCon.contains("#")){
    		Log.info(msg);
    	}
    	if (logOrCon.isEmpty() || logOrCon.contains("!")){
    		System.err.println(msg);
    	}
    	return;
    }
    

    public void openBrowser() throws MalformedURLException {
    	class Local {};
  	  	Log.info(Local.class.getEnclosingMethod().getName());
  	  	
  	  	boolean geckoDriver=false;	// Available in firefox > 48 and Selenium 3.1
  	  
		if(Hooks.browser.equalsIgnoreCase("Firefox")) {
			
			browserCode='F';

			String seleniumVer="";
			
			if (!geckoDriver){
				// POM must have <version>2.53.1</version> for selenium dependancy
				// works for SELENIUM 2.53.1 and FIREFOX 46 or 47.1
				try {
					Properties p = new Properties();
					if (p!=null){
						p.load(Hooks.class.getResourceAsStream("/META-INF/maven/org.seleniumhq.selenium/selenium-java/pom.properties"));
						// MAVEN POM 
						seleniumVer=p.getProperty("version");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				String mustHaveVer="Must use Firefox47.0.1 with Selenium 2.53.1";
				
				Log.info(mustHaveVer);
				System.out.println(mustHaveVer);
				
				//String mustHaveVer="Must use Firefox46.0 with Selenium 2.53";
				System.out.println("Using Selenium WebDriver:"+seleniumVer+" Firefox Driver:"+webdriver_firefox_bin);
				//System.out.println("All Properties: "+System.getProperties().toString());
	
				
				//Default:(64 bit)
				//System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");

				System.setProperty("webdriver.firefox.bin" ,webdriver_firefox_bin);	//("C:\\Program Files\\MozillaFirefox47.0.1\\firefox.exe"); // must use selenium 2.53.0.1
				try {
					driver = new FirefoxDriver(firefoxProfile());	// Selenium 3.4.0 Firefox 53
				} catch (Exception e) {
					Log.info("Driver instantiation FAIL !! :"+Hooks.browser);
					e.printStackTrace();
					throw new SkipException("TestNG skip after an ERROR...");
				}
			} else {
				// POM must have <version>3.4.0</version> for selenium dependancy
				
				//GeckoDriverService gecko = new GeckoDriverService(new File(geckodriver_exe),4444,ImmutableList.of("--log=fatal"),ImmutableMap.of());
				//gecko.sendOutputTo(new FileOutputStream("gecko_log.txt"));
				//gecko.start();
				//FirefoxOptions opts = new FirefoxOptions().setLogLevel(Level.OFF);
				//DesiredCapabilities capabilities = opts.addTo(DesiredCapabilities.firefox());
				//capabilities.setCapability("marionette", true);
				//driver = new FirefoxDriver(gecko, capabilities);

				
			if (!(getDevPortalProperty("useGeckoDriver")).toUpperCase().contains("TRUE")){
				System.err.println("SELENIUM 3.4.0 is not active, exiting.. Try 2.53.1");
				return;
			}
			
			
				
			}
			
			
		}else if (Hooks.browser.equalsIgnoreCase("IE")) { 
			System.setProperty("webdriver.ie.driver", "C:\\webDriver\\IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			
			capabilities.setCapability("requireWindowFocus", true);
			//capabilities.setCapability("ie.ensureCleanSession", true); // clears browsing history
			
			//capabilities.setCapability("ie.forceCreateProcessApi", true); -- locks up.. donot use
			//capabilities.setCapability("ie.browserCommandLineSwitches", "-private" );
			browserCode='I';
			driver = new InternetExplorerDriver(capabilities);
			//driver = new InternetExplorerDriver();
		}else if (Hooks.browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C://webDriver//chromedriver.exe");
			
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", "C:\\BNZdownloads\\LOGS");

			DesiredCapabilities caps = DesiredCapabilities.chrome();

			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			caps.setCapability(ChromeOptions.CAPABILITY, options);
			
			browserCode='C';
			//driver = new ChromeDriver();
			driver = new ChromeDriver(caps);
			
		}else if (Hooks.browser.equalsIgnoreCase("HtmlUnit")){
			browserCode='H';
			driver = new HtmlUnitDriver();//true enables javascript
    	}else if (Hooks.browser.equalsIgnoreCase("PhantomJS")){
    		browserCode='P';
    		driver = new PhantomJSDriver();
    	} 	  	
				
		Log.info("New driver instantiated :"+Hooks.browser);
		Log.info("Trying Implicit wait 3000 miliseconds");
		System.err.println("Trying Implicit wait 3000 miliseconds");
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS); 
		
		System.err.println("trying driverwait 30");
		Log.info("trying driverwait 30");
		driverWait = new WebDriverWait(driver, 30);	// in seconds
		
		Dimension x = new Dimension(1,1);//width,height
		driver.manage().window().maximize();
    	currentWindowHandle = driver.getWindowHandle();
    	Log.info("Called openBrowser");
    	System.out.println("browser opened");
    }
    
	public static FirefoxProfile firefoxProfile() throws Exception {

		ProfilesIni profile = new ProfilesIni();
		System.out.println("Using FF profile: \"selenium\"");
		Log.info("Using FF profile: \"selenium\"");
		
		FirefoxProfile firefoxProfile = profile.getProfile("selenium");
		//FirefoxProfile firefoxProfile = new FirefoxProfile();
		
		firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
		firefoxProfile.setPreference("browser.download.folderList",2);
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
		firefoxProfile.setPreference("browser.download.dir",downloadPath);
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/csv,"+
						"application/x-msexcel,"+
						"application/excel,"+
						"application/x-excel,"+
						"application/vnd.ms-excel,"+
						"image/png,image/jpeg,"+
						"text/html,"+
						"text/plain,"+
						"application/msword,"+
						"application/xml," + 
						"application/pdf,"+
						"application/octet-stream"); //<== (most important one !!)
		//firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile", "application/octet-stream");
		System.out.println("done with FF profile");
		return firefoxProfile;
	}
    
	//=======================
    // Before each test
    // @Before
	//=======================
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests
     */
    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        int a=0;
        if (a<0){
        
        }
    }

	//=======================
    // After each test
    // @After
	//=======================
    
    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario) {
       
        if(scenario.isFailed()) {
        	
        	if (!dComInitDone){
        		DOMConfigurator.configure("log4j.xml");
        		dComInitDone=true;
        	}
        	
        	Log.error("Fail Case...");
        	System.out.println("Fail Case...");

	        try {
	        	 scenario.write("Current Page URL is " + driver.getCurrentUrl());
	        	 //byte[] screenshot = getScreenshotAs(OutputType.BYTES);
	        	 byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	        	 scenario.embed(screenshot, "image/png");
	        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
	            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
	        }
	        //driver.quit();
        }
       
    }
    
    
    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
    	
        driver.quit();
        Log.info("Browser closed");
        System.out.println("browser closed");
    }

    /***
    @AfterMethod
    public void afterMethod() throws Throwable {
    }    
    ****/
	
    
}

