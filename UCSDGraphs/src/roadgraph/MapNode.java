package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/**
 * Object to represent a MapNode which stores a location with a list of
 * Neighbors
 * 
 * @author jgordon
 *
 */
public class MapNode {
	private GeographicPoint location;
	private List<MapEdge> neighbors;

	/**
	 * Default constructor
	 */
	public MapNode() {
		location = null;
		neighbors = new ArrayList<MapEdge>();

	}

	/**
	 * constructor to initialize MapNode with passed in location and neighbor
	 * list
	 * 
	 * @param location
	 *            Location point
	 * @param neighbors
	 *            list of the location points neighbors
	 */
	public MapNode(GeographicPoint location, List<MapEdge> neighbors) {
		this.location = location;
		this.neighbors = neighbors;
	}

	/**
	 * Set the location
	 * 
	 * @param location
	 */
	public void setLocation(GeographicPoint location) {
		this.location = location;
	}

	/**
	 * Add MapEdge to neighbor list
	 * 
	 * @param e
	 */
	public void addNeighbor(MapEdge e) {
		this.neighbors.add(e);
	}

	/**
	 * Get location of MapNode
	 * 
	 * @return The list of Neighbors
	 */
	public GeographicPoint getlocation() {
		return this.location;
	}

	/**
	 * Get a list of Neighbors
	 * 
	 * @return The list of Neighbors
	 */
	public List<MapEdge> getNeighbors() {
		return this.neighbors;
	}

}
