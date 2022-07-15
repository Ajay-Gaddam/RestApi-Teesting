package frameWorkAPI;

import java.util.List;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.HelperClass;

public class GETResponse {
	private SoftAssert sf;
	private String uri;
	private String expected;
	private String actual;
	private String baseAsset;
	private String quoteAsset;
	private String Symbol;
	private int num;
	@BeforeSuite
	public void excelRead() {
	    String path="src/test/resources/DataExcel.xlsx";
	    HelperClass help=new HelperClass();
		uri=help.readFromExcel(path,"Sheet1", 2, 3);
		Symbol=help.readFromExcel(path,"Sheet1", 2, 8);
		expected=help.readFromExcel(path,"Sheet1", 2, 7);
	}
	@BeforeMethod
	public void sfAsssert() {
		sf = new SoftAssert();
	}
	@BeforeTest
	public void prereq() {
		RestAssured.baseURI=uri;
		//creating an instance for requesting to the Server
		RequestSpecification httprequest=RestAssured.given();
		
		//Creating an instance for receving response from the Server
	    Response response=httprequest.request(Method.GET,"/ticker/24hr");
	    List<String> list=Assignment.getSymbol(response);
	   
	    num=list.indexOf(Symbol);
	    actual=Assignment.getSymbols(response, num);
	    
	    List<String> listsymbol=Assignment.readString(actual);
	    baseAsset=listsymbol.get(0);
	    quoteAsset=listsymbol.get(1);
	   
	    
	   // baseAsset= str.replaceAll("\\[", "").replaceAll("\\]","");
       // quoteAsset= str1.replaceAll("\\[", "").replaceAll("\\]","");
	}
	@Test
	public void getResponseFromWebsite() {
		//Performing Assertion Operation b/w the actual Symbol and Expected 
		Reporter.log("The Idex value of a Given Symbol is : "+num);
		
		Reporter.log("The actual Symbol is :"+actual);
		
		sf.assertEquals(actual, expected);
		if(actual.equals(expected))
			Reporter.log("Assertion Passed : "+actual+"=="+expected);
		else
			Reporter.log("Assertion Failed : "+actual+"!="+expected);
		
		Reporter.log("The value of baseAsset is: "+baseAsset);
		Reporter.log("The value of quoteAsset is: "+quoteAsset);
		
		String symbol=baseAsset+quoteAsset;
		Reporter.log("The Value After Performing concat operation b/w baseAsset and quoteAsset is: "+symbol);
		
		sf.assertEquals(symbol, actual);
		if(symbol.equals(actual))
			Reporter.log("Assertion Passed : "+baseAsset+"+"+quoteAsset+"=="+symbol);
		else
			Reporter.log("Assertion Failed : "+baseAsset+"+"+quoteAsset+"!="+symbol);
	}
}
