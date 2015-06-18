Feature: Import BACS Rejects Financial Services Centre (FSC) file

  Background:
    Given I have a customer
    And the customer has an Oyster card
    And the customer has a refund order
    And the order has a pay as you go item
    And the order has a BACS settlement with a successful status

  Scenario: Import valid record
    And I have a valid BACS rejects file record
    When I load the BACS rejects file record
#  fixme  Then the BACS settlement status should be updated to failed

  Scenario: Reject record with non-matching amount
    And I have a BACS rejects file record with a non-matching amount
    When I load the BACS rejects file record
#  fixme  Then the BACS settlement status should remain as successful
#    And the BACS requests handled file record error should be logged
