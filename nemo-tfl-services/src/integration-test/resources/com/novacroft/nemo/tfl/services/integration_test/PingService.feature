Feature: Ping web service

  Scenario: Get ping
    When I call the ping service with a get request
    Then I should receive a ping get response

  Scenario: Post ping
    When I call the ping service with a post request
    Then I should receive a ping post response
