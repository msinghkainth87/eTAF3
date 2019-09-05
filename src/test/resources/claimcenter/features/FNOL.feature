Feature: zzFNOL Claim

  Background: Login
    Given I navigate to ClaimCenter
    And I fill super_user data from login yaml onto the page
    And I click on element login.login
    Then I land on activities page
@google
  @fnol
  Scenario: zz New claim creation - Basic way
    Given I enter "NewClaim" into input field general.goto_page
    And   I press "ENTER" key into input field general.goto_page
    Then   the element fnolsearchorcreatepolicy.enter_loss_date is enabled
    And   I fill personal_auto_policy data from fnolsearchorcreatepolicy yaml onto the page
    Then   the element fnolselectvehicleinvolved.involved_vehicle_0 is enabled
    When  I click on element fnolselectvehicleinvolved.involved_vehicle_0
    And  I click on element fnolselectvehicleinvolved.next
    Then   the element fnolbasicinformation.relation_to_insured is enabled
    And  I select "2" option by index from dropdown fnolbasicinformation.reported_by_name
    And  I select "2" option by index from dropdown fnolbasicinformation.relation_to_insured
    And  I click on element fnolbasicinformation.involved_vehicle_0
    And  I click on element fnolbasicinformation.next
    Then   the element fnollossdetails.loss_cause is enabled
    And  I select "3" option by index from dropdown fnollossdetails.loss_cause
    And  I select "2" option by index from dropdown fnollossdetails.loss_location
    And  I select "3" option by index from dropdown fnollossdetails.loss_jurisdiction
    And  I select "3" option by index from dropdown fnollossdetails.fault_rating
    And  I click on element fnollossdetails.next
    Then   the element fnolservices.claim_level_services is enabled
    When  I click on element fnolservices.next
    Then   the element fnolassignment.claim_assign is enabled
    When  I click on element fnolassignment.finish
    Then   the element fnolclaimsaved.view_created_claim is displayed



  Scenario: short-form BDD compliant - GenericFill
    Given I navigate to ClaimCenter
#    And I fill super_user data from login yaml onto the page
#    And I click on element login.login
#    Then I land on activities page
#    Given I land on activities page
    When I search for "NewClaim" in the "general.goto_page" text box
#    And I fill personal_auto_policy data from fnolsearchorcreatepolicy yaml onto the page
#    Then I land on fnolbasicinformation page
#    And I fill personal_auto_policy data from fnolbasicinformation yaml onto the page
#    Then I land on fnollossdetails page
#    And I fill personal_auto_policy data from fnollossdetails yaml onto the page
#    Then I land on fnolvehicledetails page
#    And I fill personal_auto_policy data from fnolvehicledetails yaml onto the page
#    Then I land on fnollossdetails page
#    When I click on element fnollossdetails.next
#    Then I land on fnolservices page
#    When I click on element fnolservices.next
#    Then I land on fnolassignment page
#    When I click on element fnolassignment.finish
#    Then I land on fnolclaimsaved page
#    Then the element fnolclaimsaved.view_created_claim is displayed


  Scenario: short-form BDD compliant - Custom Step definition way
    Given I create a claim using personal_auto_policy data


#http://eypc.eastus.cloudapp.azure.com:8180/pc/PolicyCenter.do

