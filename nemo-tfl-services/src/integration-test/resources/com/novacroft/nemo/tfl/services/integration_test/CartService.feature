Feature: Cart service
  
  Cart web service for creating ,retrieving and updating cart.

  Scenario: create a cart for a given  card id
    When I call create cart with card number 030000000515
    Then I should receive a cart with card number 030000000515

  Scenario: retrieve a cart for a given  cart id
    When I retrieve a cart
    Then I should receive a cart

  Scenario: update the cart
    When I have a  cart
    And I have a travel card
      | startDate  | reminderDate | startZone | endZone | duration | productType | prePaidProductReference |
      | 03/15/2015 | 4 days prior | 1         | 3       | 1Month   | Travelcard  | 43597                   |
    And I have a pay  as you go
      | price | productType   |
      | 1000  | Pay as you go |
    Then I should update the cart
    Then I should retrieve a cart  with a travel card
      | startDate  | reminderDate | startZone | endZone | duration | productType | prePaidProductReference |
      | 03/15/2015 | 4 days prior | 1         | 3       | 1Month   | Travelcard  | 43597                   |
    Then I should retrieve a cart  with a pay  as you go
      | price | productType   |
      | 1000  | pay as you go |

  Scenario: Get cartlist by customer id
    When I have customer with customer email "billy.fish@example.com"
    Then I should get the list of carts

  Scenario: Begin checkout
    When I begin checkout with checkoutrequest
      | stationId |
      | 589       |
    Then I should get the checkout result

  Scenario: Authorise checkout payment
    When I authorise cart with checkout request
      | stationId |
      | 589       |
    Then I should get authorise CheckoutResult

  Scenario: Fail checkout payment
    When I fail cart with checkout request
      | stationId |
      | 589       |
    Then I should get  fail CheckoutResult

  Scenario: complete checkout
    When I complete  cart with checkout request
      | stationId |
      | 589       |
    Then I should get  complete CheckoutResult

  Scenario: Delete Cart
    When I delete cart with customer email "billy.fish@example.com"
    Then I should get webServiceResult
