Feature: Oyster online dashboard page

  Background:
    Given I have a customer
    And the customer has an Oyster card

  Scenario: Navigate to dashboard page
    When I navigate to the dashboard page
    Then the dashboard page should be displayed