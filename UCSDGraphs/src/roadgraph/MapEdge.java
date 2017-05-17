package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	private GeographicPoint start;
	private GeographicPoint end;
	private String streetName;
	private String streetType;
	private double distance;

	public MapEdge() {
		this.start = null;
		this.end = null;
		this.streetName = null;
		this.distance = 0.0;
		this.streetType = null;

	}

	/**
	 * Constructor used to initialize object with parameters start, end, streetName, StreetType, distance
	 * @param start - starting location
	 * @param end - ending location
	 * @param streetName - street name of edge
	 * @param streetType - street type of edge
	 * @param distance - distance of street edge
	 */
	public MapEdge(GeographicPoint start, GeographicPoint end, String streetName, String streetType,  double distance) {
		this.start = start;
		this.end = end;
		this.streetName = streetName;
		this.streetType = streetType;
		this.distance = distance;

	}

	/**
	 * @return the start point
	 */
	public GeographicPoint getStart() {
		return start;
	}

	/**
	 * @param start set starting location
	 */
	public void setStart(GeographicPoint start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public GeographicPoint getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(GeographicPoint end) {
		this.end = end;
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the streetType
	 */
	public String getStreetType() {
		return streetType;
	}

	/**
	 * @param streetType the streetType to set
	 */
	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}


	@Override
	public String toString() {
		return "MapEdge [getStart()=" + getStart() + ", getEnd()=" + getEnd() + ", getStreetName()=" + getStreetName()
				+ ", getDistance()=" + getDistance() + ", getStreetType()=" + getStreetType() + "]";
	}
	
	

}
