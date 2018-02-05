# sample_automation
Sample java, Selenium, Cucumber with TestNG for Domain.com.au 

Perquisite:
Firefox must be installed at:
webdriver_firefox_bin=C:\\Program Files\\MozillaFirefox47.0.1\\firefox.exe

Versions: Use Firefox47.0.1 with Selenium 2.53.1

FF should have profile: selenium
Must have a folder c:\bnzdownloads\log
------------------------------------------
Followings will be tested:
------------------------------------------
Feature: Access Domain Portal website
	Verify portal components

	Scenario: As an user, I verify domain website dashboard
		Given User navigates to Domain Portal website "https://www.domain.com.au/"
		Then User navigates dashboard features
		Then User closes website

	Scenario: As an user, I verify domain''s each TAB content
		Given User navigates to Domain Portal website ""
		And User clicks first tab
		Then verifies contents of that page and goes to next page for all tabs
		Then User closes website
		
	Scenario: As an user, I SignIn
		Given User navigates to Domain Portal website ""
		Then As a new user, user signs in as "MEMBER_xxx" and browses available pages
		Then As an existing user "MEMBER_xxx", user logs in to member portal and browses page
		Then User closes website
------------------------------------------
Dt uploaded - 06-feb-2018 at sydney
Author: S_A_R_T__M_O_H_A_R_A_N_A
------------------------------------------
