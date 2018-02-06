package step_definitions;
import java.util.Properties;
import java.util.Random;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DomainSiteNZ extends PortalAutomation {
    public WebDriver driver;
    public Properties domainNZProperties;
    private String baseUrl;
    public WebDriverWait driverWait;
    Random randGen;

    public DomainSiteNZ()
    {
    	if (!Hooks.dComInitDone){
    		DOMConfigurator.configure("log4j.xml");
    		Hooks.dComInitDone=true;
    	}
        driver = Hooks.driver;
        domainNZProperties = Hooks.domainPortalProperties;
    	baseUrl = Hooks.domainPortalURL;
    	driverWait = Hooks.driverWait;
    	randGen = new Random();
    }
    public String domain_Portal_website_is_opened(String webURL) {
    	return baseUrl;
    }
	public int user_sign_in_to_portal(String clientPrefix,String suppliedUserID, String suppliedPasswd) throws InterruptedException {
        return 0;
	}
    public String admin_navigates_to_Dashboard() throws InterruptedException {
    	return "";
    }
	public boolean user_closes_website() throws InterruptedException {
        return true;
	}
}

//==========================(end)===========================//

