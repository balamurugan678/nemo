Feature: Oyster online view order history page

  Background:
    Given I have a customer
    And the customer has an Oyster card
    And the customer has completed an order

  Scenario: Navigate to view order history page
    When I navigate to the view order history page
    Then the view order history page should be displayed