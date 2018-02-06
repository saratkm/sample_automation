Feature: Access Domain Portal website
	Verify portal components

	@viewDashboard
	Scenario: As an user, I verify domain website dashboard
		Given User navigates to Domain Portal website "https://www.domain.com.au/"
		Then User navigates dashboard features
		#Then User closes website

	@selectVariousTabs
	Scenario: As an user, I verify domain''s each TAB content
		Given User navigates to Domain Portal website ""
		And User clicks first tab
		Then verifies contents of that page and goes to next page for all tabs
		#Then User closes website
		
	@userSignInAsMemeber
	Scenario: As an user, I SignIn
		Given User navigates to Domain Portal website ""
		Then As a new user, user signs in as "MEMBER_xxx" and browses available pages
		Then As an existing user "MEMBER_xxx", user logs in to member portal and browses page
		Then User closes website
		