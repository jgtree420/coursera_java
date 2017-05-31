/**
 * 
 */
package util;

import java.awt.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

import geography.GeographicPoint;
import roadgraph.MapSpeed;

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
	private static void SpeedLimitLoadFile(String speedLimitFile) {
		Stream<String> speedLimitStream = null;

		try {
			speedLimitStream = Files.lines(Paths.get(speedLimitFile));
		} catch (IOException e) {
			e.printStackTrace();

		}

		System.out.println("===============Results from File==============");

		speedLimitStream.forEach(System.out::println);
		List<String> sList = speedLimitStream.collect(toList());
	}

	/**
	 * Map lines to MapSpeed POJO
	 */
	
	Function<String, MapSpeed> mapLineToMapSpeed = new Function<String, MapSpeed>(){
		public MapSpeed apply(String line) {
			MapSpeed mapSpeed = new MapSpeed();
			List<String> speedPieces = Splitter.on("\t").trimResults().omitEmptyStrings().splitToList(line);
			mapSpeed.setRoadType(speedPieces.getItem(0));
			mapSpeed.setSpeedLimit(speedPieces.getItem(1));
			mapSpeed.setTrafficPenalty(speedPieces.getItem(2));
			return mapSpeed;

		}
	};
	
	// Split the input string into the line information
	private static MapSpeed splitInputString(String input)
	{	
		
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("\t");
		Matcher m = tokSplitter.matcher(input);
		
		while (m.find()) {		
				tokens.add(m.group());
		}

    	String roadType = tokens.get(0);
		double speedLimit = Double.parseDouble(tokens.get(1));
        double speedLimitPenalty = Double.parseDouble(tokens.get(2));


        return new MapSpeed(roadType, speedLimit, speedLimitPenalty);
        
		
	}

}
