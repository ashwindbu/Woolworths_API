@SydneyWeather @Regression @All

Feature: Woolworth QA Challenge (API)

  #The response is valid JSON and contains Sydney as a destination day of the weather forecast is
  #Thursday weather returned is > 10 degrees C

  @SydneyWeatherForecast
  Scenario Outline: Validate the response is valid JSON , destination as <City> and the weather forecast for Thursday is not > 10 Celsius
    Given Building the API endpoint with <CountryCode> <Units>
    Then User requests the forecast for Country
    Then User validates the response is valid JSON and Country is <City>
    Then User validates the weather forecast for Thursday is greater than ten degrees celsius

    Examples:
      | City   | CountryCode | Units  |
      | Sydney | 2147714     | metric |
