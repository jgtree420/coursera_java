/**
 * A class to represent a node in the map
 */
package roadgraph;

import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;

/**
 * @author UCSD MOOC development team
 * 
 * Class representing a vertex (or node) in our MapGraph
 *
 */
class MapNode implements Comparable
{
	/** The list of edges out of this node */
	private HashSet<MapEdge> edges;
		
	/** the latitude and longitude of this node */
	private GeographicPoint location;
	
	/** distance from start */
	private Double distanceFromStart;
	
	/** duration from start */
	private Double durationFromStart;
	
	/** A* Cost from start */
	private Double aStarCost;
	
	/** Dijkstra Cost */
	public static final Double dijkstraEstimatedDistance = 0.0;

	/** Max Kilometer per hour */
	public static final Double MAXKILOPERHOUR = 130.0;
		
	/**
	 * @return the aStarCost
	 */
	public Double getaStarCost() {
		return aStarCost;
	}

//	/** 
//	 * setaStarCost - set A* cost
//	 * @param costFromStart cost from the start, for example, distance from start
//	 * @param estimatedCost estimate cost to goal, for example distance to goal as a bird would fly
//	 */
//	public void setaStarCost(Double costFromStart, Double estimatedCost) {
//		this.aStarCost = costFromStart + estimatedCost;
//	}

	/** 
	 * setaStarCost - set A* cost
	 * @param aStarCost a calculated a* cost
	 */
	public void setaStarCost(Double aStarCost) {
		this.aStarCost = aStarCost;
	}

	
	/**
	 * @return the distanceFromStart
	 */
	public Double getDistanceFromStart() {
		return distanceFromStart;
	}

	/**
	 * @param distanceFromStart the distanceFromStart to set
	 */
	public void setDistanceFromStart(Double distanceFromStart) {
		this.distanceFromStart = distanceFromStart;
	}

	/** 
	 * Create a new MapNode at a given Geographic location
	 * @param loc the location of this node
	 */
	MapNode(GeographicPoint loc)
	{
		location = loc;
		edges = new HashSet<MapEdge>();
		/** initialize distance as positive infinity */
		distanceFromStart = Double.POSITIVE_INFINITY;
		aStarCost = Double.POSITIVE_INFINITY;
	}
		
	/**
	 * Add an edge that is outgoing from this node in the graph
	 * @param edge The edge to be added
	 */
	void addEdge(MapEdge edge)
	{
		edges.add(edge);
	}
	
	/**  
	 * Return the neighbors of this MapNode 
	 * @return a set containing all the neighbors of this node
	 */
	Set<MapNode> getNeighbors()
	{
		Set<MapNode> neighbors = new HashSet<MapNode>();
		for (MapEdge edge : edges) {
			neighbors.add(edge.getOtherNode(this));
		}
		return neighbors;
	}
	
	/**
	 * Get the geographic location that this node represents
	 * @return the geographic location of this node
	 */
	GeographicPoint getLocation()
	{
		return location;
	}
	
	/**
	 * return the edges out of this node
	 * @return a set contianing all the edges out of this node.
	 */
	Set<MapEdge> getEdges()
	{
		return edges;
	}
	
	/**
	 * @return the durationFromStart
	 */
	public Double getDurationFromStart() {
		return durationFromStart;
	}

	/**
	 * @param durationFromStart the durationFromStart to set
	 */
	public void setDurationFromStart(Double durationFromStart) {
		this.durationFromStart = durationFromStart;
	}

	/** Returns whether two nodes are equal.
	 * Nodes are considered equal if their locations are the same, 
	 * even if their street list is different.
	 * @param o the node to compare to
	 * @return true if these nodes are at the same location, false otherwise
	 */
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof MapNode) || (o == null)) {
			return false;
		}
		MapNode node = (MapNode)o;
		return node.location.equals(this.location);
	}
	
	/** Because we compare nodes using their location, we also 
	 * may use their location for HashCode.
	 * @return The HashCode for this node, which is the HashCode for the 
	 * underlying point
	 */
	@Override
	public int hashCode()
	{
		return location.hashCode();
	}
	
	/** ToString to print out a MapNode object
	 *  @return the string representation of a MapNode
	 */
	@Override
	public String toString()
	{
		String toReturn = "[NODE at location (" + location + ")";
		
		toReturn += " intersects streets: ";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + "(" + e.getRoadType()  + "), ";
		}
		toReturn += "]\n";
		toReturn += " distance from Start: " + distanceFromStart.toString();
		toReturn += " duration from Start: " + this.getDurationFromStart();
		toReturn += " a* Cost: " + this.getaStarCost();
		return toReturn;
	}

	// For debugging, output roadNames as a String.
	public String roadNamesAsString()
	{
		String toReturn = "(";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += ")";
		return toReturn;
	}

	/** compareTo used for sorting MapNodes.
	 *  @return 0,-1, 1 depending on distance from the start point
	 */
	@Override
	public int compareTo(Object o) {


		if (this.getaStarCost() < ((MapNode)o).getaStarCost()){
			return -1;
		}
		if (this.getaStarCost() > ((MapNode)o).getaStarCost()){
			return 1;
		}	
		
		// else they are equal
		return 0;
	}

	


}