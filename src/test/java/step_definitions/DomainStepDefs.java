package step_definitions;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import helpers.Log;


public class DomainStepDefs {
    public WebDriver driver;
    public Properties domainPortalProp;
    private String baseUrl;
    public WebDriverWait driverWait;
    Random randGen;
	private Scenario scenario;
	
	PortalAutomation portalAuto = PortalFactory.getPortalCountry(Hooks.websiteCountry);
	final int HIGHVALUE_NEGATIVE = Hooks.HIGHVALUE_NEGATIVE;
	
    public DomainStepDefs()
    {
    	if (!Hooks.dComInitDone){
    		DOMConfigurator.configure("log4j.xml");
    		Hooks.dComInitDone=true;
    	}
    	
        driver = Hooks.driver;
        domainPortalProp = Hooks.domainPortalProperties;		// Save Local copy
    	baseUrl = Hooks.domainPortalURL;
    	driverWait = Hooks.driverWait;
    	randGen = new Random();
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
    
//***********************************************************************************//    
//                 all test cases follows from here                                  //
//***********************************************************************************//
 
     
    @Given("^User navigates to Domain Portal website \"([^\"]*)\"$")
    public boolean user_navigates_to_Domain_Portal_website(String webSiteURL) throws Throwable {
    	portalAuto.domain_Portal_website_is_opened(webSiteURL);
		return true;
    }

    @Then("^User navigates dashboard features$")
    public boolean user_navigates_dashboard_features() throws Throwable {
    	portalAuto.admin_navigates_to_Dashboard();
        return true;
    }

    @Then("^User closes website$")
    public boolean user_closes_website() throws Throwable {
    	portalAuto.user_closes_website();
        return true;
    }
    
    @Given("^User clicks first tab$")
    public boolean user_clicks_first_tab() throws Throwable {
        return true;
    }

    @Then("^verifies contents of that page and goes to next page for all tabs$")
    public boolean verifies_contents_of_that_page_and_goes_to_next_page_for_all_tabs() throws Throwable {
        return true;
    }

    @Then("^As a new user, user signs in as \"([^\"]*)\" and browses available pages$")
    public boolean as_a_new_user_user_signs_in_as_and_browses_available_pages(String memberID) throws Throwable {
        return true;
    }

    @Then("^As an existing user \"([^\"]*)\", user logs in to member portal and browses page$")
    public boolean as_an_existing_user_user_logs_in_to_member_portal_and_browses_page(String memberID) throws Throwable {
        return true;
    }
}

