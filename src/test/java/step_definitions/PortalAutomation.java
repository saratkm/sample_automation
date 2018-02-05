package step_definitions;

public abstract class PortalAutomation {
	abstract String domain_Portal_website_is_opened(String client);
	abstract String admin_navigates_to_Dashboard() throws InterruptedException;
	abstract int user_sign_in_to_portal(String clientPrefix,String suppliedUserID, String suppliedPasswd) throws InterruptedException;
	abstract boolean user_closes_website() throws InterruptedException;
}
