package stepdefs;

import commonutils.AppConstants;
import io.restassured.internal.RestAssuredResponseImpl;
import org.json.JSONArray;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import static io.restassured.RestAssured.given;
import static stepdefs.Hooks.cucumberReportInfoLog;


public class WoolworthsQAChallenge {

    private Response response;
    private RequestSpecification request;
    public static String customerId;
    public static int StatusCode = 200;

    @Given("^Building the API endpoint with (.*) (.*)$")
    public void buildingTheAPIEndpointWithCountryCodeUnits(String CountryCode, String Units) {

        cucumberReportInfoLog("Forming API Endpoint");
        RestAssured.baseURI = AppConstants.APIEndPoint;
        request = given().params("id", CountryCode).params("appid", AppConstants.APP_KEY)
                .params("units", Units);
    }

    @Then("^User requests the forecast for Country$")
    public void userRequestsTheForecastForCountry() {

        response = request.get("data/2.5/forecast");
        cucumberReportInfoLog('\n' + "_________Post Response START__________" + '\n');
        cucumberReportInfoLog(response.getBody().prettyPrint());
        cucumberReportInfoLog('\n' + "_________Post Response END__________" + '\n');
    }

    @Then("^User validates the response is valid JSON and Country is (.*)$")
    public void userValidatesTheResponseIsValidJSONAndCountryIsSydney(String ExpectedCity) {

        JsonPath jp = new JsonPath(response.asString());
        response.then().assertThat().statusCode(StatusCode).and().contentType(ContentType.JSON);
        cucumberReportInfoLog("StatusCode: " + StatusCode);

        String ActualCity = jp.get("city.name");
        Assert.assertEquals(ActualCity, ExpectedCity);
        cucumberReportInfoLog("Target City: " + ActualCity);
    }

    //This method will fetch the api response , read the date and if the date converted falls in Thursday it will read the temperature is greater than 10 degrees C
    @Then("^User validates the weather forecast for Thursday is greater than ten degrees celsius$")
    public void userValidatesTheWeatherForecastForThursdayIsGreaterThanTenDegreesCelsius() {

        String responstring = ((RestAssuredResponseImpl) response).getContent().toString();
        JSONObject jsonObj = new JSONObject(responstring.toString());
        JSONArray ReadItem = jsonObj.getJSONArray("list");

        for (int i = 0; i < ReadItem.length(); i++) {
            JSONObject ChildJsonObj = ReadItem.getJSONObject(i);
            String ChildString = ChildJsonObj.getString("dt_txt");
            String DayofForecast = GetDayFromDate(ChildString.replaceAll("\\s.*", ""));
            if (DayofForecast.contentEquals("THURSDAY")) {
                String test = DayofForecast;
                JSONObject UnitsJsonObj = ChildJsonObj;
                int Temperature = UnitsJsonObj.getJSONObject("main").getInt("temp");
                if (Temperature > 10) {
                    Assert.assertTrue("Temperature is Greater than 10 degrees C", true);
                    Assert.assertTrue("Forecast Temperature on Thursday "+ChildString+": "+Temperature+"", true);
                    cucumberReportInfoLog('\n' + "Forecast Temperature on Thursday "+ChildString+": "+Temperature+"" + '\n');

                } else {
                    Assert.assertFalse("Temperature is not Greater than 10 degrees C", true);
                    cucumberReportInfoLog('\n' + "Forecast Temperature: "+Temperature+"" + '\n');
                }
            }
        }
    }

    //method to get the day from date fetched from API
    public String GetDayFromDate(String inputdate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String ActualDate = null;

        try {
            LocalDate date = LocalDate.parse(inputdate);
            DayOfWeek day = date.getDayOfWeek();
            ActualDate = day.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActualDate;
    }
}