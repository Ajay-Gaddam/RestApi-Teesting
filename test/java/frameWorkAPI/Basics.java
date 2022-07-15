package frameWorkAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.AddBody;
import utils.ReUsableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
public static void main(String[] args) {
	// TODO Auto-generated method stub
	// validate if Add Place API is workimg as expected 
			//Add place-> Update Place with New Address -> Get Place to validate if New address is present in response
			
			//given - all input details 
			//when - Submit the API -resource,http method
			//Then - validate the response
			RestAssured.baseURI= "https://rahulshettyacademy.com";
			String response=given().queryParam("key", "qaclick123").header("Content-Type","application/json")
			.body(AddBody.AddPlace()).when().post("maps/api/place/add/json")
			.then().statusCode(200).body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)")
			.extract().response().asString();
			System.out.println(response);	
			
			//creating an instance for JsonPath
			
			JsonPath js=new JsonPath(response); //for parsing Json
			String placeId=js.getString("place_id");
			
			System.out.println(placeId);
			//Update Place
			String newAddress = "Summer Walk, Africa";
			
			String putUpdate=given().queryParam("key", "qaclick123").header("Content-Type","application/json")
			.body("{\r\n" + 
					"\"place_id\":\""+placeId+"\",\r\n" + 
					"\"address\":\""+newAddress+"\",\r\n" + 
					"\"key\":\"qaclick123\"\r\n" + 
					"}").
			when().put("maps/api/place/update/json")
			.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"))
			.extract().response().asString();
			System.out.println(putUpdate);
			
			// Get Body
			String getPlaceResponse=	given().queryParam("key", "qaclick123")
					.queryParam("place_id",placeId)
					.when().get("maps/api/place/get/json")
					.then().assertThat().statusCode(200).extract().response().asString();
				JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse);
				String actualAddress =js1.getString("address");
				System.out.println(actualAddress);				
}
}
