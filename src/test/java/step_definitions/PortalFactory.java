package step_definitions;

public class PortalFactory {
	public static PortalAutomation getPortalCountry(String websiteCountry){
		if (websiteCountry.contains("AU")){
			return new DomainSiteAU();
		}
		if (websiteCountry.contains("NZ")){
			//return new DomainSiteNZ();
			return new DomainSiteAU();
		}
		if (websiteCountry.contains("CN")){
			//return new DomainSiteCN();
			return new DomainSiteAU();
		}
		return null;
	}
}
