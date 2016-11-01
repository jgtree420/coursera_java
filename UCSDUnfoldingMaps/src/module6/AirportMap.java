package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	// hashmap of airport ids to codes
	HashMap<String , String> airportCodes = new HashMap<String , String>();
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		
		
		// create markers from features
		for(PointFeature feature : features) {
			
			
			String airportCode = feature.getStringProperty("code");
			
			// only add airports with CODE
			if (airportCode.length() == 5){
						
			AirportMarker m = new AirportMarker(feature);
				
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			airportCodes.putIfAbsent(feature.getStringProperty("code"),feature.getId());
			//airportCodes.p
			}

		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		//TODO
			//System.out.println(sl.getProperties());
			//System.out.println(sl.getLocations());
			//System.out.println(sl.getStringProperty("destination") + "-" + sl.getStringProperty("source"));
			//System.out.println(sl.getStringProperty("source"));
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
		//selectMarkerIfHover(cityMarkers);
		//loop();
	}

	public void mouseClicked()
	{
		if (lastClicked != null) {
			unhideMarkers();
			lastClicked = null;
		}
		else if (lastClicked == null) 
		{
			checkAirportMarkerForClick();
//			if (lastClicked == null) {
//				checkCitiesForClick();
//			}
		}
	}

	
	// If there is a marker selected 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	// loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : routeList) {
			marker.setHidden(false);
		}
			
		for(Marker marker : airportList) {
			marker.setHidden(false);
		}
	}

	// Helper method that will check if a city marker was clicked on
	// and respond appropriately
	
	
	// Helper method that will check if an airport marker was clicked on
	// and respond appropriately
	private void checkAirportMarkerForClick()
	{
		if (lastClicked != null) return;
		// Loop over the airport markers to see if one of them is selected
		for (Marker m : airportList) {
			AirportMarker marker = (AirportMarker)m;
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = marker;
				String lastClickedAirportID = airportCodes.get(lastClicked.getStringProperty("code"));
				
				//airportCodes.
				//airports.containsKey(source)
				
				// Hide all the other AirportMarkers and hide
				for (Marker mhide : airportList) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}
				}
				for (Marker mhide : routeList) {
					
					if ((mhide.getStringProperty("source").equals(lastClickedAirportID) 
							) 
						)  {
						mhide.setHidden(false);
						System.out.println(mhide.getStringProperty("destination") + "***"+ lastClicked.getStringProperty("code") + "***"+  lastClickedAirportID);
						
						// Show destination markers
						for (Marker m1 : airportList) {
							String airportID = airportCodes.get(m1.getStringProperty("code"));
							if (airportID.equals(mhide.getStringProperty("destination"))){
								m1.setHidden(false);
							}
						}
						
					}
					else {
						mhide.setHidden(true);
					}
				}
				return;
			}
		}
	}
	
}
