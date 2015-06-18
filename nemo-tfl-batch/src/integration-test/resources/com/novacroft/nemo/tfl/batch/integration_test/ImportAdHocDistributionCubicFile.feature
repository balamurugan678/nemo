Feature: Import AdHoc Distribution Cubic file

  Background:
    Given I have a customer
    And the customer has an Oyster card
    And the customer has a order with Paid status
    And the order has a pay as you go item
    And the order has a AdHoc settlement with a requested status

  Scenario: Import valid adhoc load pickedup record  
    Given I have a valid AdHoc Load PickedUp file record
    When  I load the AdHoc Load PickedUp file record
    Then  the AdHoc settlement status should be updated to Picked Up 
    And   the Order status should be updated to complete
    
  Scenario: Import invalid adhoc load pickedup record with empty request sequence number 
    Given I have an invalid AdHoc Load PickedUp file record
    When  I load the AdHoc Load PickedUp file record
    Then  the AdHoc settlement status should remain as requested
    And   the order status should remain as paid
    

