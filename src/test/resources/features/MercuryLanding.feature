Feature: Mercury Landing Page


  Scenario: Login to Mercury Expenses
    Given I navigate to mercury.onegate page
    And I click on element mercury.my_timesheets
    Then I switch to new window
    Then I should see page title as "My Timesheet"
    Then I click on element time.next_period

