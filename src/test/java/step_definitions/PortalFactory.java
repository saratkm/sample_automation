package step_definitions;

public class PortalFactory {
	public static PortalAutomation getPortalCountry(String websiteCountry){
		if (websiteCountry.contains("AU")){
			return new DomainSiteAU();
		}
		if (websiteCountry.contains("NZ")){
			return new DomainSiteNZ();
		}
		if (websiteCountry.contains("US")){
			return new DomainSiteUS();
		}
		return null;
	}
}
