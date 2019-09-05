Feature: Login

# Background: SSO into GWCC
#    Given I navigate to ClaimCenter cc login page
##    And I enter "su" into input field login.username
##    And I enter "gw" into input field login.password
#   #example of data read from yaml file - also implements fill element method based on yaml input
#    And I fill the login page with super_user data
#    And I click login button
#    Then I land on activities page
#    Then the element general.gwlogo is displayed
    #Then I click on element having id "TabBar:ClaimTab-btnInnerEl"


  Scenario: 1 New claim creation - long form flexible
    Given I enter "NewClaim" into input field general.goto_page
    And I press "TAB" key into input field general.goto_page
    And I press "ENTER" key into input field general.goto_page
    Then I land on fnolsearchorcreatepolicy page
    And I enter "1000000028" into input field fnolsearchorcreatepolicy.policy_number
    And I enter "10/13/2018" into input field fnolsearchorcreatepolicy.enter_loss_date
    And I click on element fnolsearchorcreatepolicy.search_policy
    And the element fnolsearchorcreatepolicy.next is enabled
    When I click on element fnolsearchorcreatepolicy.next
    Then I land on fnolbasicinformation page
    Then I double click on element fnolbasicinformation.reported_by_name
    And  I enter "MARK WILLIAMS" into input field fnolbasicinformation.reported_by_name
    Then I click on element fnolbasicinformation.involved_vehicle_checkbox
    When I click on element fnolbasicinformation.next
    Then I land on fnollossdetails page
    Then I double click on element fnollossdetails.loss_cause
    And I enter "Collision at Uncontrolled Intersection" into input field fnollossdetails.loss_cause
    Then I double click on element fnollossdetails.loss_location_type
    And I enter fnollossdetails.ma_loss_address into input field fnollossdetails.loss_location_type
    When I click on element fnollossdetails.next
    Then I land on fnolservices page
    When I click on element fnolservices.next
    Then I land on fnolassignment page
    When I click on element fnolassignment.finish
    Then I land on fnolclaimsaved page
    And the element fnolclaimsaved.view_created_claim is displayed


  Scenario: short-form BDD compliant - needs Happy path fill methods
    Given I search for "NewClaim" in the information bar
    Then I land on fnolsearchorcreatepolicy page
    And I fill the fnol search or create policy page and continue
    Then I land on fnolbasicinformation page
    And  I fill the fnol basic information page and continue
    Then I land on fnollossdetails page
    And I fill the fnol loss details page and continue
    Then I land on fnolservices page
    Then I fill the fnol services page and continue
    Then I land on fnolassignment page
    Then I fill the fnol assignment page and continue
    Then I land on fnolclaimsaved page
    And the element fnolclaimsaved.view_created_claim is displayed

#UI Test Demo
  @hk
  Scenario: Test with Google
    Given I navigate to "http:\\google.com"
    Then I enter "search abcdefghijklmnopqrstuvwxyz" into input field general.google_search
    Then I run accessibility tests using htmlcs,axe tools


# REST Service Test Demo
  Scenario: Fetch Name Of Country using a REST Service
    Given url 'http://services.groupkt.com/country/get/iso2code/IN'
    When method GET
    Then status 200
    And match response.RestResponse.result.name == 'India'

  # SOAP Service Test Demo

  Scenario: Fetch Holiday List using a SOAP Service
    Given url 'http://www.holidaywebservice.com/HolidayService_v2/HolidayService2.asmx'
    * def requestXML = read('C:\\Gaja\\Automation frameworks\\Cucumber_BDD\\src\\test\\resources\\request.xml')
    Given request requestXML
    And header Content-Type = 'text/xml'
    When method POST
    Then status 200
    And print 'response: ', response
    And match //Holiday[1]/HolidayCode == "VETERANS-DAY-ACTUAL"



    # SOAP Service Test Demo with DataDriven
  Scenario Outline: Fetch Holiday List using a SOAP Service
    Given url 'http://www.holidaywebservice.com/HolidayService_v2/HolidayService2.asmx'
    * def requestXML = read('C:\\Gaja\\Automation frameworks\\Cucumber_BDD\\src\\test\\resources\\requestDataParameterized.xml')
    * def month = '<month>'
    * replace requestXML
    |token          |value|
    |${holidayMonth}|<month>|
    And print 'request: ', requestXMLs
    Given request requestXML
    And header Content-Type = 'text/xml'
    When method POST
    Then status 200
    And print 'response: ', response
    * match response count(//Holiday) == <count>
    Examples:
      | month | count|
      |   1   |    4 |
      |   2   |    7 |
      |   5   |    1 |

  Scenario: Database Validation Access DB
    Given url 'http://services.groupkt.com/country/get/iso2code/IN'
    When method GET
    Then status 200
    And match response.RestResponse.result.name == 'India'
    * def name = 'RAKTIM'
    * def config = { db:'access',url:'C:\\Users\\vs197hg\\Desktop\\Database1.accdb',driverClassName:'net.ucanaccess.jdbc.UcanaccessDriver'}
    * def DbUtil = Java.type('seleniumutils.methods.DbUtil')
    * def db = new DbUtil(config)
    * def dogs = db.readRow("SELECT * FROM CUSTOMER where FirstName='RAKTIM'")
    * match dogs contains { STATE: 'VA'}
#    * def dogs = db.readRow("SELECT * FROM CUSTOMER")
#    * def dogs = db.readRow("SELECT * FROM CUSTOMER where FirstName='RAKTIM'")
#    * def dogs = db.readValue("SELECT STATE FROM CUSTOMER where FirstName='RAKTIM'")
#    * match dogs contains 'VA'


  Scenario: Database Validation SQL DB
    Given url 'http://services.groupkt.com/country/get/iso2code/IN'
    When method GET
    Then status 200
    And match response.RestResponse.result.name == 'India'

    * def name = 'RAKTIM'
    * def config = { username: 'US\\VS197HG', password: 'R@ktim2018',db:'sql', url: 'jdbc:sqlserver://US1232885W2\\QLEXPRESS',instanceName:SQLEXPRESS,integratedSecurity:true,databaseName:'Demo',driverClassName:'com.microsoft.sqlserver.jdbc.SQLServerDriver' }
    * def DbUtil = Java.type('seleniumutils.methods.DbUtil')
    * def db = new DbUtil(config)
    * def dogs = db.readValue("SELECT STATE FROM tblCustomer where FirstName='RAKTIM'")
    #    * def config = { username: 'US\VS197HG', password: 'R@ktim2018', url: 'jdbc:sqlserver://US1232885W2\SQLEXPRESS',instanceName:SQLEXPRESS,integratedSecurity:true,databaseName:'Demo',driverClassName:driverClassName:'com.microsoft.sqlserver.jdbc.SQLServerDriver' }
  #'net.ucanaccess.jdbc.UcanaccessDriver'}




  #Scenario: Multiple codes

  Scenario Outline: Validate Multiple codes
    Given url 'http://services.groupkt.com/country/get/iso2code/'+'<Code>'
    When method GET
    Then status 200
    And match response.RestResponse.result.name == '<ExpCountry>'

    Examples:
      | Code|ExpCountry|
      | IN  |  India   |
      | CA  | Canada   |
      | US  |United States of America|





