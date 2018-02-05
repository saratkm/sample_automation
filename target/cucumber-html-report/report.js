$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("features/domainSite.feature");
formatter.feature({
  "line": 1,
  "name": "Access Domain Portal website",
  "description": "Verify portal components",
  "id": "access-domain-portal-website",
  "keyword": "Feature"
});
formatter.before({
  "duration": 125446,
  "status": "passed"
});
formatter.scenario({
  "line": 5,
  "name": "As an user, I verify domain website dashboard",
  "description": "",
  "id": "access-domain-portal-website;as-an-user,-i-verify-domain-website-dashboard",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 4,
      "name": "@viewDashboard"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "User navigates to Domain Portal website \"https://www.domain.com.au/\"",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "User navigates dashboard features",
  "keyword": "Then "
});
formatter.step({
  "line": 8,
  "name": "User closes website",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "https://www.domain.com.au/",
      "offset": 41
    }
  ],
  "location": "DomainStepDefs.user_navigates_to_Domain_Portal_website(String)"
});
formatter.result({
  "duration": 13140142472,
  "status": "passed"
});
formatter.match({
  "location": "DomainStepDefs.user_navigates_dashboard_features()"
});
