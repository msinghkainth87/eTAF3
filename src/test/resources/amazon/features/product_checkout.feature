Feature: Ordering a product in Amazon

  @FUNCTIONAL
  Scenario: Product Check out - Mixed with elementary fills and YAML fills
    Given I navigate to amazon.home page
    Then I enter "samsung galaxy s10 plus case" into input field amazon.search_bar
    Then I click on element amazon.search_button
    Then I should see page title as "Amazon.com: samsung galaxy s10 plus case"
    And I click on element searchpage.search_result_2
    Then I should see page title having partial text as "Samsung Galaxy S10 Plus"
    And I click on element productpage.add_to_cart
    And I click on element productpage.close_side_pane
    And I click on element productpage.view_cart
    Then I should see page title as "Amazon.com Shopping Cart"
    And I click on element ordercartpage.proceed_to_checkout
    Then I should see page title as "Amazon Sign In"
    And I fill gajendra_mahadevan_personal data from loginpage yaml onto the page
    Then I should see page title as "Select a shipping address"
    And I fill gaja_personal_details data from checkoutpage yaml onto the page
    And I sign out of amazon

  @uat
  Scenario: Product ordering and check out
    Given I navigate directly to checkout page for samsung_galaxy_s10_plus_case data
    And I fill dynamic_data data from checkoutpage yaml onto the page
    And I sign out of amazon