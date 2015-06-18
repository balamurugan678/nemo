Feature: Oyster online Add Existing Card To Account page

  Background:
    Given I have a customer
    And the customer has an Oyster card

  Scenario: Navigate to Add Card page
    When I navigate to the Add Card page
    Then the Add Card page should be displayed