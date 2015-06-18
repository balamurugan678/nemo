Feature: Oyster online home page

  Scenario: Show home page
    When I go to the application root URL
    Then the home page should be displayed

  Scenario: Log on to application
    When I log in
    Then the dash board page should be displayed