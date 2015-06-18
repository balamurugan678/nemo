Feature: Product web service 

Scenario: Get ReferenceData 
	When I call the Product service with a get request of ReferenceData 
	Then I should receive a ReferenceData get response 
	
Scenario: Get Product web service with valid travel card details 

	When I call the Product service with a get request of TravelCard 
		|duration	|startZone	|endZone	|startDate	|endDate	|emailReminder	|passengerType	|discountType		 |	
		|1Month		|1			|2			|27/12/2014	|10/01/2015	|4				|Adult			|No Discount		 |
		
	Then I should receive a TravelCard get response 
	But travelcard should not have any errors 
	
Scenario: Get Product web service with valid pay as you go details 

	When I call the Product service with a get request of PayAsYouGo 
		|amount|		
		|2000  |
		
	Then I should receive a PayAsYouGo get response 
	But payAsYouGo should not have any errors 
	
