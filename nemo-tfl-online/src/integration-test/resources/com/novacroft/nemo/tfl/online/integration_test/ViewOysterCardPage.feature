Feature: Oyster online view oyster card page

  Background:
    Given I have a customer
    And the customer has an Oyster card

  Scenario: Navigate to view card page
    When I navigate to the view oyster card page
    Then the view oyster card page should be displayed