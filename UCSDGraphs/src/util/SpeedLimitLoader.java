/**
 * 
 */
package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
import static java.util.stream.Collectors.*;

import geography.GeographicPoint;
import roadgraph.MapGraph;
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
		roadgraph.MapGraph map = new roadgraph.MapGraph();
		SpeedLimitLoadFile(speedLimitFile, map);

	}

	// Read File using lines() and Stream approach.
	private static void SpeedLimitLoadFile(String speedLimitFile, roadgraph.MapGraph map) {
		Stream<String> speedLimitStream = null;
		List<MapSpeed> mapSpeeds = new ArrayList<MapSpeed>();

		try {
			speedLimitStream = Files.lines(Paths.get(speedLimitFile));

			mapSpeeds = (List<MapSpeed>) Files.lines(Paths.get(speedLimitFile)).map(line -> line.split("\t")) // transform
																												// each
																												// line
																												// to
																												// an
																												// array
					.map(snippets -> new MapSpeed(snippets[0], Double.parseDouble(snippets[1]),
							Double.parseDouble(snippets[2])))
					.collect(Collectors.toList()); // transform to mapSpeeds

		} catch (IOException e) {
			e.printStackTrace();

		}

		// speedLimitStream.forEach(System.out::println);
		// mapSpeeds.forEach(System.out::println);

		for (MapSpeed n : mapSpeeds) {
			map.addMapSpeed(n.getRoadType(), n);
		}

	}

}
