package frameWorkAPI;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import utils.AddBody;

public class ComplexJson {
public static void main(String[] args) {
	JsonPath js=new JsonPath(AddBody.CoursePrice());
	int count=	js.getInt("courses.size()");
	System.out.println(count);
	//Print Purchase Amount
	int totalAmount= js.getInt("dashboard.purchaseAmount");
	System.out.println(totalAmount);
	
	//Title of the First Course 
	String Firsttitle=js.getString("courses[0].title");
	System.out.println("The Title of First Course is: "+Firsttitle);
	
	// Printing all Courses Title and respective prices
	int n=js.getInt("courses.size()");
	
	for(int i=0;i<n;i++) {
		System.out.println("The Title of  Course is: "+js.getString("courses["+i+"].title"));
		System.out.println(js.getString("courses["+i+"].price").toString());
	}
	// Printing The copies sold by RPA

	for(int i=0;i<n;i++) {
		String title=js.getString("courses["+i+"].title");
		if(title.equalsIgnoreCase("RPA")) {
			System.out.println(js.getString("courses["+i+"].copies"));
			break;
		}
	}
	
	// Validating The sum of All Courses equals to the Purchase amount
	int sum=0;
	for(int i=0;i<n;i++) {
		String price=js.getString("courses["+i+"].price");
		String copies=js.getString("courses["+i+"].copies");
		sum=sum+Integer.parseInt(price)*Integer.parseInt(copies);
	}
	 System.out.println(sum);
	 
	 // Performing Assertion 
	 Assert.assertEquals(sum,totalAmount);
}
}