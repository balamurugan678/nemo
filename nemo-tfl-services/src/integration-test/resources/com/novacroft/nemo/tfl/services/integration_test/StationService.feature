Feature: Station web service

  Scenario: Get AllStations
    When I call the Station service with a get request of AllStations
    Then I should receive a AllStations get response

  Scenario: Get ActiveStations
    When I call the Station service with a get request of ActiveStations
    Then I should receive a ActiveStations get response
