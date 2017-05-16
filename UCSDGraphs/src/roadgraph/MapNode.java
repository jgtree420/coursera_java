package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {
	GeographicPoint location;
	List<MapEdge> neighbors;
	
	/**
	 * Add a node corresponding to an intersection at a Geographic Point If the
	 * location is already in the graph or null, this method does not change the
	 * graph.
	 * 
	 * @param location
	 *            The location of the intersection
	 * @return true if a node was added, false if it was not (the node was
	 *         already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		// TODO: Implement this method in WEEK 3
		// int v = getNumVertices();
		ArrayList<GeographicPoint> neighbors = new ArrayList<GeographicPoint>();
		// if location is not null and does not already exist in the adjListMap
		// add and then return true
		if (location != null && !adjListsMap.containsKey(location)) {
			adjListsMap.put(location, neighbors);
			numVertices++;
			return true;
		}

		// TODO: Add logic for True False
		return false;
	}
}
