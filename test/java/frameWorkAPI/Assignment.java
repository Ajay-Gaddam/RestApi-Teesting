package frameWorkAPI;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Assignment {
	public static List<String> getSymbol(Response response) {
		List<String> symbol=response.jsonPath().get("symbol");
		return symbol;
	}
	public static String getSymbols(Response response,int num) {
		List<String> symbol=response.jsonPath().get("symbol");
		String Symbol=symbol.get(num);
		return Symbol;
	}
	
	public static List<String> readString(String actual) {
		
		RestAssured.baseURI= "https://api.binance.com/";
		
		//Extracting the response as a String Type
		String response=given().queryParam("symbol",actual)
		.when().get("api/v3/exchangeInfo")
		.then()
		.extract().response().asString();
		
		//For parsing  json with the  extracted response
		 JsonPath js=new JsonPath(response);
		 
		 //Getting the BaseAsset value in the Response
		 String baseAsset=js.getString("symbols[0].baseAsset");
		 
		 
		 //Getting the QuoteAsset value in the response
		 String quoteAsset=js.getString("symbols[0].quoteAsset");
		 
		 //Storing the BaseAsset and QuoteAsset in the List and returning it 
		 List<String> list=new ArrayList<String>();
		 list.add(baseAsset);
		 list.add(quoteAsset);
		 return list;
	}
   //"symbols":[{"symbol":"ETHBTC","status":"TRADING","baseAsset":"ETH","baseAssetPrecision":8,"quoteAsset":"BTC",
}
