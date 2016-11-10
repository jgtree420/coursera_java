package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(11);
		pg.ellipse(x, y, 5, 5);
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		String title = getName();
		String code = getCode();
		String code_title = code + "-" + title;
		pg.pushStyle();
		
		pg.rectMode(PConstants.CORNER);
		
		pg.stroke(110);
		pg.fill(255,255,255);
		pg.rect(x, y + 15, pg.textWidth(code_title) +6, 18, 5);
		
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(255,0,0);
		pg.text(code_title, x + 3 , y +18);
		
		
		pg.popStyle();
		// show routes
		
		
	}
	
	public String getName() {
		return (String) getProperty("name");	
		
	}
	
	public String getCity() {
		return (String) getProperty("city");	
		
	}
	
	public String getCoutry() {
		return (String) getProperty("country");	
		
	}

	public String getCode() {
		return (String) getProperty("code");	
		
	}

	public float getaltitude() {
		return Float.parseFloat(getProperty("altitude").toString());
	}
	
//	public int compareTo(AirportMarker m)
//	{
//	    if (m.getCountRoutes() < this.getCountRoutes())
//	        return -1;
//	    else if (this.getCountRoutes() < m.getCountRoutes())
//	        return 1;
//	    else
//	        return 0;
//	}
	
}
