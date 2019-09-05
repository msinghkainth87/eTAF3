@boc
Feature: Become a Seller Servicer

  Scenario: Test to verify Pre-app submission
    Given I navigate to singlefamily.landing page
    Then I should see page title as "Resources for Sellers/Servicers - Freddie Mac"
    #Then I run comparative accessibility tests using htmlcs,aXe tools
    And I click on element singlefamily.become_a_seller_servicer
    Then the element singlefamily.apply_to_be_seller_servicer is displayed
    And I click on element singlefamily.apply_to_be_seller_servicer
    Then the element singlefamily.submit_preapp is displayed
    And I click on element singlefamily.submit_preapp
    Then I should see page title as "Pre-Application Questionnaire"
    Then I run comparative accessibility tests using htmlcs,aXe tools
    Then I fill anchor_example data from preapp yaml onto the page

  @freddie
  Scenario: Test to verify Pre-app submission
    Given I navigate to singlefamily.landing page
    Then I should see page title as "Resources for Sellers/Servicers - Freddie Mac"
    And I fill sf_input data from singlefamily yaml onto the page
    Then I should see page title as "Pre-Application Questionnaire"
    Then I fill preapp_input data from preapp yaml onto the page
#    Then I run comparative accessibility tests using htmlcs,aXe tools