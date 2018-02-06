package step_definitions;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import helpers.Log;


public class DomainSiteAU extends PortalAutomation {
    public WebDriver driver;
    public Properties adminPortalProperties;
    private String baseUrl;
    public WebDriverWait driverWait;
    
    Random randGen;
    
	// Sleep time (in mil-sec)
	final int SHORT_SEC  = 600;
	final int MEDIUM_SEC = 1200;
	final int SECONDS2   = 2000;
    final int LARGE_SEC  = 3000;
    
    final int HIGHVALUE_NEGATIVE = Hooks.HIGHVALUE_NEGATIVE;
    
    public DomainSiteAU()
    {
    	if (!Hooks.dComInitDone){
    		DOMConfigurator.configure("log4j.xml");
    		Hooks.dComInitDone=true;
    	}
        driver = Hooks.driver;
    	adminPortalProperties = Hooks.domainPortalProperties;
    	baseUrl = Hooks.domainPortalURL;
    	driverWait = Hooks.driverWait;
    	randGen = new Random();
    }
    
    
    public String domain_Portal_website_is_opened(String webURL) {
    	
		if (Hooks.showFKtName){
			class Local {};
			Log.info(Local.class.getEnclosingMethod().getName());
		}
		
		if ((!webURL.isEmpty()) && (webURL.startsWith("http") && webURL.contains("://www."))){
			//Save URL value for rest of the calls
			baseUrl = webURL;
			Hooks.domainPortalURL=baseUrl;
		}
    	driver.get(baseUrl);
    	return baseUrl;
    }
	
	public int user_sign_in_to_portal(String clientPrefix,String suppliedUserID, String suppliedPasswd) throws InterruptedException {

		if (Hooks.showFKtName){
			class Local {};
			Log.info(Local.class.getEnclosingMethod().getName());
		}
		
       	driver.findElement(By.xpath(".//*[@id='homepage']/div/div/div[1]/header/div[2]/div/nav/div/div[3]/div/a[1]")).click();
       	Thread.sleep(SHORT_SEC);
       	
        return 0;
	}


    public String admin_navigates_to_Dashboard() throws InterruptedException {
		if (Hooks.showFKtName){
			class Local {};
			Log.info(Local.class.getEnclosingMethod().getName());
		}
    	boolean pass=true;
    	String rslt="";
    
    	class TabAction{
    		String tabName;
    		boolean newPg;
    		By byType;
    		String subItems;
    		String subBy;
    		public TabAction(String name, boolean np, By by, String itm, String subBy) {
                this.tabName=name;
                this.newPg=np;
                this.byType=by;
                this.subItems=itm;
                this.subBy=subBy;
            }
    	};
    	
    	TabAction[] ta =   new TabAction[] { 	
    			new TabAction("Buy",			false, By.linkText("Buy"),				"",null), 
    			new TabAction("Rent",			false, By.linkText("Rent"),				"",null),
    			new TabAction("New homes",		false, By.linkText("New homes"),		"",null),
    			new TabAction("Sold",			false, By.linkText("Sold"),				"",null),
    			new TabAction("Commercial",		true,  By.linkText("Commercial"),		"Property,Business for Sale+,Franchise",		"linkText"),
    			new TabAction("News",			true,  By.linkText("News"),				"Advice,Living,Money & Markets,Video",			"linkText"),
    			new TabAction("Agents",			false, By.linkText("Agents"),			"",null),
    			new TabAction("More",			false, By.linkText("More"),			"Share,Home Price Guide,Auction Results,Suburb Profiles,Home Loans,Place an ad",	"linkText"),
    			//new TabAction("Sign in",		false, By.xpath(".//*[@id='homepage']/div/div/div[1]/header/div[2]/div/nav/div/div[3]/div/a[1]"),			"",null),
    			//new TabAction("Sign up",		false, By.xpath(".//*[@id='homepage']/div/div/div[1]/header/div[2]/div/nav/div/div[3]/div/a[2]"),			"",null),
    			new TabAction("savedsearch",	false, By.cssSelector("button.member-dropdown__toggle-savedsearch"),										"",null),
    			new TabAction("shortlist",		false, By.cssSelector("button.member-dropdown__toggle-shortlist"),											"",null)
    	};
    	
    
    	int j=0;
    	for (int i=0;i<ta.length; i++){
    		LogToReport(1,"-- "+ta[i].tabName);
    		
    		driver.findElement(ta[i].byType).click();
    		Thread.sleep(LARGE_SEC);
    		j=ta[i].subItems.length();
    		rslt += ta[i].tabName + ",";
    		
			if (j<=0){
				if (ta[i].newPg){
					driver.navigate().back();
				}
				continue;
			}
			
			By byObj=null; 
			String zSub[] =ta[i].subItems.split(",");
			
			for (int n=0;n<zSub.length;n++ ){
				boolean skip2 = zSub[n].endsWith("+");
				String subItem = skip2?zSub[n].substring(0, zSub[n].length()-1):zSub[n];
				byObj = ta[i].subBy.startsWith("linkText") 	? By.linkText(subItem)		: byObj;
				byObj = ta[i].subBy.startsWith("xpath")    	? By.xpath(subItem)			: byObj;
				byObj = ta[i].subBy.startsWith("css")    	? By.cssSelector(subItem)	: byObj;
				
				if (byObj!=null){	
					LogToReport(1,"---- "+subItem);
					if (driver.findElements(byObj).size()>0){
						driver.findElement(byObj).click();
						Thread.sleep(LARGE_SEC);
					}
					
					rslt += "+"+subItem + ",";
					driver.navigate().back();
					
					if (skip2){
						driver.navigate().back();
					}
					
					Thread.sleep(LARGE_SEC);
					if (driver.findElements(ta[i].byType).size()>0){
						driver.findElement(ta[i].byType).click();
					} else {
						driver.navigate().to(baseUrl);
						Thread.sleep(SHORT_SEC);
					}
				}
			}//for n=0
			
			//driver.get(baseUrl);
			//Thread.sleep(SHORT_SEC);

    	}//for i=0
    	
    	LogToReport(1,"End of TAB selections..");
    	return rslt;
    }
    
    
	public boolean user_closes_website() throws InterruptedException {
		if (Hooks.showFKtName){
			class Local {};
			Log.info(Local.class.getEnclosingMethod().getName());
		} 
		
		driver.close();
    	Thread.sleep(SHORT_SEC);
        return true;
	}
    
    public static void LogToReport(int one, String msg){
    	// parameters one: -2, -1, 0, 1 
    	if (one>-3){
    		Log.info(msg);
    	}
    	
    	if (one>=0){
    		Hooks.errorToReport += (msg+(msg.isEmpty()?"":"~"));
    	}
    	
    	if (Math.abs(one)==1){
    		System.err.println(msg);
    	}
    }
}

//==========================(end)===========================//

