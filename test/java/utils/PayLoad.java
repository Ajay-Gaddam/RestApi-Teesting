package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PayLoad {

public static String details() throws IOException {
	 String jsonPath="src/test/resources/Data.json";
     String inpValue= new String(Files.readAllBytes(Paths.get(jsonPath)));
	return inpValue;
}
}
