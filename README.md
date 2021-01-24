# Automated test example in Java Rest assured with cucumber

This project is to Open Weather Map is free to use the website (and JSON API service) that can be used to query the weather forecast of major cities around the
world.

Endpoint : http://openweathermap.org/forecast16

Test scenarios are described in the feature file located here .cucumber-test-sample/features/WoolworthsQAChallenge.feature.

The Mission (Should you choose to accept it)
Is to implement a test to automate the following acceptance criteria (in the form of a BDD) in your tool of choice/language
Scenario: A happy holidaymaker
Given I like to holiday in Sydney
And I only like to holiday on Thursdays
When I look up the weather forecast
Then I receive the weather forecast
And the temperature is warmer than 10 degrees

Assertions
The response is valid JSON and contains Sydney as a destination day of the weather forecast is
Thursday weather returned is > 10 degrees C

## Installation ##

This test runs in Chrome browser.

src/test/java/stepDefs/WoolworthsQAChallenge.java


To install all dependencies, run

```console
$ mvn clean install
```

## Running tests ##

```console
$ mvn test
```

After tests are run, reports are generated at : /target/cucumber-reports/
