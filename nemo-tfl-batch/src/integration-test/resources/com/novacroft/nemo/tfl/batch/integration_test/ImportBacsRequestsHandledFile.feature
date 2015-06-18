Feature: Import BACS Requests Handled (Settlements) Financial Services Centre (FSC) file

  Background:
    Given I have a customer
    And the customer has an Oyster card
    And the customer has a refund order
    And the order has a pay as you go item
    And the order has a BACS settlement with a requested status

  Scenario: Import valid record
    And I have a valid BACS requests handled file record
    When I load the BACS requests handled file record
    Then the BACS settlement status should be updated to successful

  Scenario: Reject record with non-matching amount
    And I have a BACS requests handled file record with a non-matching amount
    When I load the BACS requests handled file record
    Then the BACS settlement status should remain as requested
    And the BACS requests handled file record error should be logged

  Scenario: Reject record with non-matching payee
    And I have a BACS requests handled file record with a non-matching payee
    When I load the BACS requests handled file record
    Then the BACS settlement status should remain as requested
    And the BACS requests handled file record error should be logged
