package step_definitions;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@RunWith(Cucumber.class)
@CucumberOptions(
		
		//features = {"classpath:features/domainSite.feature"},
		features = "classpath:features",
		plugin = {"pretty", "html:target/cucumber-html-report"},
		//dryRun = true,
		//monochrome = true,

		//tags = {"@viewDashboard"}
		tags = {"~@adminCreates"}
		)


public class RunCukesTest extends AbstractTestNGCucumberTests {
}

//You can use the cucumber.options environmental variable to specify the tags at runtime
//mvn -D"cucumber.options=--tags @Other,@Now" test
//This supercedes the tags already contained in the test code.

//mvn test -Dcucumber.options="--tags @your_tag"

//You can run all tags with
//mvn clean test 

//or one or more tags with
//mvn clean test -Dtags="@foo,@bar"

// for printing ANSI color text use ansifilter utility and export to html 
//https://sourceforge.net/projects/ansifilter/?source=typ_redirect
