Feature: Card web service

  Scenario: Get card details for card number
    When The card service is invoked with card number
    Then I should receive card details for the card number
    Then the passenger type should be "Adult" 

 Scenario: Create card for customer with invalid record
    When I call the card service with valid customer Id and empty record
    Then the card should not be created 

  Scenario Outline: Create card for valid customer
    When I call the card service with valid Id <customerId> 
    Then the card should be created successfully
 
 Examples:  
	  |customerId|
	  |36625	 |
	  
  Scenario Outline: Create card for invalid customer
    When I call the card service with invalid Id <customerId>
    Then response should be customer not found  

 Examples:  
	  |customerId|
	  |5451		 |
   