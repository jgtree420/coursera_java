package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {
	private GeographicPoint location;
	private List<MapEdge> neighbors;
	
	public MapNode (){
		location = null;
		neighbors = new ArrayList<MapEdge>();
		
	}
	
	
	public void setGeographicPoint(GeographicPoint location){
	 this.location = location;
	}
	
	public void addNeighbor(MapEdge e){
		this.neighbors.add(e);
	}
	
	
	/**
	 * Get location of MapNode
	 * 
	 * @return The list of Neighbors
	 */
	public GeographicPoint getlocation(){
		return this.location;
	}

	
	/**
	 * Get a list of Neighbors
	 * 
	 * @return The list of Neighbors
	 */
	public List<MapEdge> getNeighbors(){
		return this.neighbors;
	}


	
}
