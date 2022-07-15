package frameWorkAPI;

import static io.restassured.RestAssured.given;
import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.HelperClass;
import utils.PayLoad;
import utils.ReUsableMethods;


public class PostReq {
	private String uri;
	@BeforeTest
	public void readFromExcel() {
		 String path="src/test/resources/DataExcel.xlsx";
		 HelperClass help=new HelperClass();
	     uri=help.readFromExcel(path,"Sheet1", 4, 3);
	}
@Test
public void postReq() throws IOException {
	String token="189f1d7ff3752b9dcfebb7e84b8efca89065e7922face6f8a0cb244a1211b823";
	RestAssured.baseURI=uri;
	String response=given().header("Authorization","Bearer "+token)
			.header("Content-Type","application/json")
			.body(PayLoad.details())
			.when().post("/users").then().log().all().statusCode(201).extract().response().asString();
    Reporter.log(response);
	
	//Capturing the Id 
	JsonPath js=ReUsableMethods.rawToJson(response); 
	String ID=js.getString("id");
	System.out.println(ID);
	Reporter.log(ID);
	// Getting the Details with the help of Generated ID
	
	String bodyresponse=given().header("Authorization","Bearer "+token)
			.header("Content-Type","application/json")
			.queryParam("id", ID)
			.when().get("/users").then().log().all().statusCode(200).extract().response().asString();
	System.out.println(bodyresponse);
	Reporter.log(bodyresponse);
	
	// Performing Delete Operation
	
	String deleteResponse=given().header("Authorization","Bearer "+token)
			.header("Content-Type","application/json")
			.when().delete("/users/"+ID).then().log().all().statusCode(204).extract().response().asString();
	System.out.println(deleteResponse);
}
}