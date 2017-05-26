/**
 * 
 */
package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author jgordon
 *
 */
public class SpeedLimitLoader {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	String speedLimitFile = "data/speedlimits/defaults.txt";
	SpeedLimitLoadFile(speedLimitFile);

	}
	
	// Read File using lines() and Stream approach.
	private static void SpeedLimitLoadFile(String speedLimitFile){
		Stream<String> speedLimitStream = null;
		
		try {
			speedLimitStream = Files.lines(Paths.get(speedLimitFile));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		System.out.println("===============Results from File==============");
		
		speedLimitStream.forEach(System.out::println);
	}
	
	
	

}
