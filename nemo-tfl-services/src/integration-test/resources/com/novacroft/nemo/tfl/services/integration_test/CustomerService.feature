Feature: Customer Web Service 

  Scenario Outline: create customer with valid customer details
  
    Given I have these customers
        
    |title	|firstName	|initials |lastName |houseNameNumber |street 			|town			|country |county		  |postcode	| homePhone 	|mobilePhone |emailAddress 			| username|  
    | Mr	| Ran		| T       |Kumar    | 8 			 |Lower Farm Road   |Moulton Park	|GB		 |Nothamptonshire |NN3 6UR	| 01604889500	|07512345678 |ran.fish@novacroft.com| ranmkmr |
    | Mr	| Sub		| C       |Bose     | 15 			 |Edmands Road      |Northampton	|GB		 |Nothamptonshire |NN1 5DY	| 01604881200	|07512976438 |sb.bose@novacroft.com | srbose  |
    
    When the create customer web service is invoked by passing customer with emailaddress <emailAddress> 
    Then a new customer is created and returned with houseNameNumber <houseNameNumber> and street <street> and username <username>
    But customer should not have any errors
    
    Examples:  
	  |houseNameNumber	|street			|username  |emailAddress 		  |
	  |8				|Lower Farm Road|ranmkmr   |ran.fish@novacroft.com|
	  |15				|Edmands Road  	|srbose	   |sb.bose@novacroft.com |
    
  Scenario Outline: create customer webservice with invalid customer details should fail
   
    Given I have these customers
        
    |title	|firstName	|initials |lastName |houseNameNumber |street 			|town			|country |county		  |postcode	| homePhone 	|mobilePhone |emailAddress 			   | username     |  
    | Mr	| 		    | T       |K_u@mar  | 18 			 |Lower Farm Road   |Moulton Park	|GB      |Nothamptonshire |NN3 6UR	| 01601111500	|07712345678 |ran.inv@lid@novacroft.com| ranInvalid   |
    | Mr	| $RK		| C       |         | 15 			 |St Edmands Road   |Northampton	|GB		 |Nothamptonshire |NN3 5DY	| 01622221200	|07572976438 |sb*.bo$e@novacroft.com   | sboseInvalid |
    
    When the create customer web service is invoked by passing customer with emailaddress <emailAddress>
    Then a customer with errors must be created and returned
    But customer should not have valid id
    
     Examples:  
	  |emailAddress 		 |
	  |ran.inv@lid@novacroft.com|
	  |sb*.bo$e@novacroft.com |
	  
   Scenario Outline: get an existing customer
  
    Given I have a customer with emailaddress <emailAddress>
    When the get customer web service is invoked
    Then check and return existing customer with firstName <firstName> and lastName <lastName>
    But customer should not have any errors 
 
    Examples:  
	  |emailAddress						|firstName	|lastName |
	  |billy.fish@example.com   	   	|Billy		|Fish	  |
	  |john.turner@example.com   	   	|John		|Turner	  |
	  
   Scenario Outline: get an existing customer should fail
   
    When the get customer web service is invoked with id <customerId>
    Then a customer with error should be returned
    But customer should not have valid id
 
    Examples:  
	  |customerId |
	  |007	      |
	  |001	      |	    
	  
  Scenario Outline: update customer web service with valid customer details
  
    Given I have a customer with emailaddress <emailAddress>
    When the update customer web service is invoked
        
    |id   |title	|firstName	|initials |lastName   |houseNameNumber |street 			|town			|country |county		  |postcode	| homePhone 	|mobilePhone |emailAddress 			| username|  
    |36688| Mr	| Ran		| T       |Kottapalli | 10 			 |Upper Farm Road   |Moulton Park	|GB		 |Nothamptonshire |NN3 6UR	| 01604889500	|07512345678 |ran.fish@novacroft.com| ranmkmr |
    
    Then customer is updated and returned with houseNameNumber <houseNameNumber> and street <street> and username <username>
    But customer should not have any errors
    
    Examples:  
	  |emailAddress				  |houseNameNumber	|street			|username  |
	  |ran.fish@novacroft.com     |10			    |Upper Farm Road|ranmkmr   |
	  
  Scenario Outline: update customer web service with invalid customer details should fail
   
    Given I have a customer with emailaddress <emailAddress>
    When the update customer web service is invoked
    
    |title	|firstName	|initials |lastName   |houseNameNumber |street 			|town			|country |county		  |postcode	| homePhone 	|mobilePhone |emailAddress 			| username|  
    |   	|    		| T       |           | 10 			   |  Road          |Moulton Park	|GB		 |Nothamptonshire |NN3 6UR	| 01604889500	|07512345678 |                      | ranmkmr |
    
    Then a customer with errors must be created and returned
    
     Examples:  
	  |emailAddress				 |
	  |ran.fish@novacroft.com 	 |
	  
  Scenario Outline: delete customer web service with sucess response
  
    Given I have a customer with emailaddress <emailAddress>
    When the delete customer web service is invoked
        
    |id   |deletedDateTime	|deletedReasonCode	|deletedReferenceNumber |deletedNote   								|  
    |36688| 01-01-2015   	| DEL007		    | DELREF003             | Deleted as customer is cancelled his card |
    
    Then I should receive the success response indicating customer is deleted successfully
    
 	Examples:  
	  |emailAddress				 |
	  |ran.fish@novacroft.com    |
	  |sb.bose@novacroft.com 	 |
	  
   Scenario Outline: delete customer web service with customer not found response
  
    When the delete customer web service is invoked with id <customerId>
        
    |deletedDateTime	|deletedReasonCode	|deletedReferenceNumber |deletedNote   								|  
    | 01-01-2015   		| DEL007		    | DELREF003             | Deleted as customer is cancelled his card |
    
    Then I should receive the customer not found response
    
 	Examples:  
	   |customerId |
	   |007	       |	
	   |001	       |	  
	  
  
	  	  
    
  	  