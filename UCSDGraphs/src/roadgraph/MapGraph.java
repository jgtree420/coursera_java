/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.Queue;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 *         A class which represents a graph of geographic locations Nodes in the
 *         graph are intersections between
 *
 */
public class MapGraph {

	private int numVertices;
	private int numEdges;
	private Map<GeographicPoint, MapNode> vertices;

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph() {
		// TODO: Implement in this constructor in WEEK 3
		vertices = new HashMap<GeographicPoint, MapNode>();
		numVertices = 0;
		numEdges = 0;
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		// TODO: Implement this method in WEEK 3
		return numVertices;
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * 
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		// TODO: Implement this method in WEEK 3

		return vertices.keySet();
	}

	/**
	 * Get the number of road segments in the graph
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		// TODO: Implement this method in WEEK 3
		return numEdges;
	}

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

		// if location is not null and does not already exist in the adjListMap
		// add and then return true
		MapNode mNode = new MapNode();

		if (location != null && !vertices.containsKey(location)) {
			vertices.put(location, mNode);
			numVertices++;
			return true;
		}

		return false;
	}

	/**
	 * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
	 * GeographicPoints have already been added to the graph
	 * 
	 * @param from
	 *            The starting point of the edge
	 * @param to
	 *            The ending point of the edge
	 * @param roadName
	 *            The name of the road
	 * @param roadType
	 *            The type of the road
	 * @param length
	 *            The length of the road, in km
	 * @throws IllegalArgumentException
	 *             If the points have not already been added as nodes to the
	 *             graph, if any of the arguments is null, or if the length is
	 *             less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length)
			throws IllegalArgumentException {

		// TODO: Implement this method in WEEK 3

		// if any of the arguments is null or if the length is less than 0
		if (from == null || to == null || roadName == null || roadType == null || length < 0) {

			throw new IllegalArgumentException();
		}

		// add data to the MapEdge Object
		MapEdge edge = new MapEdge(from, to, roadName, roadType, length);

		// If the points have not already been added as nodes to the graph
		if (vertices.get(from).getNeighbors().contains(edge)) {
			throw new IllegalArgumentException();
		}

		// Add the edge to the vertices neighbor list
		(vertices.get(from)).addNeighbor(edge);
		// increment the numEdges
		numEdges++;

	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *         path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return bfs(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *         path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 3

		// Hook for visualization. See writeup.
			//	nodeSearched.accept(next.getLocation());

		// check if start or goal is null. If true return empty list
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}

		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = bfsSearch(start, goal, parentMap, nodeSearched);

		if (!found) {
			System.out.println("No path exists");
			return null;
		}
		// reconstruct the path
		return reconstructPath(start, goal, parentMap);
	}

	private boolean bfsSearch(GeographicPoint start, GeographicPoint goal,
			HashMap<GeographicPoint, GeographicPoint> parentMap, Consumer<GeographicPoint> nodeSearched) {
		// store visited points in a hashset
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		// use a Queue for breadth first search
		Queue<GeographicPoint> toExplore = new LinkedList<GeographicPoint>();

		// queue start location
		toExplore.add(start);

		// use boolean found to store if we found goal or not
		boolean found = false;

		// explore while queue is not empty
		while (!toExplore.isEmpty()) {

			// set point curr to head of queue
			GeographicPoint curr = toExplore.remove();

			// check if curr equal goal
			if (curr.equals(goal)) {
				found = true;
				break;
			}

			// get Neighbors of curr
			List<GeographicPoint> neighbors = getNeighbors(curr);

			// use a list iterator to traverse backward
			ListIterator<GeographicPoint> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				GeographicPoint next = it.previous();

				// if the next point is not part of our visited set
				// then add next to visited
				// and also add point next to parent hash map. this allows us to
				// easily find parent point curr
				// finally add point next to the Queue (toExplore)
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.add(next);
					nodeSearched.accept(next);
				}
			}
		}

		// return true if we found goal else return false
		return found;
	}

	/**
	 * Construct the Path from start to goal
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The ending location
	 * @param parentMap
	 *            the HashMap that stores the a point mapped to its parent point
	 * @return A list of points representing the path from start to ending goal
	 */
	private List<GeographicPoint> reconstructPath(GeographicPoint start, GeographicPoint goal,
			HashMap<GeographicPoint, GeographicPoint> parentMap) {

		// Use a linked list to store the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();

		// initialze the curr point as the goal
		GeographicPoint curr = goal;

		// Working from the ending point curr
		// add to the linked list's front if we have not reached the starting
		// point
		// set next point curr equal to the parent of the current curr point
		while (curr != start) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}

		// finally add the starting point to the head of the linked list
		path.addFirst(start);

		// return an ordered linked list
		return path;
	}

	/**
	 * Get Neighbors of point v
	 * 
	 * @param v
	 *            The location we want neighbors of
	 * @return A list of point v's neighbors
	 */
	public List<GeographicPoint> getNeighbors(GeographicPoint v) {
		// Store MapNode associated to point v in tempNode
		MapNode tempNode = vertices.get(v);
		
		// store list of map edges in neighborEdgeList
		List<MapEdge> neighborEdgeList = tempNode.getNeighbors();
		
		// Add point end from each edge to neighbors list
		List<GeographicPoint> neighborList = new ArrayList<GeographicPoint>();
		for (MapEdge i : neighborEdgeList) {
			neighborList.add(i.getEnd());
		}

		return neighborList;

	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return dijkstra(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 4

		// Hook for visualization. See writeup.
		// nodeSearched.accept(next.getLocation());

		return null;
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return aStarSearch(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 4

		// Hook for visualization. See writeup.
		// nodeSearched.accept(next.getLocation());

		return null;
	}

	/**
	 * Return a String representation of the graph
	 * 
	 * @return A string representation of the graph
	 */
	// printMaze
	public String toString() {
		String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
		if (numVertices <= 20)
			s += printMap();
		return s;
	}

	/**
	 * Generate string representation of adjacency list
	 * 
	 * @return the String
	 */
	public String printMap() {
		String s = "Adjacency list";
		s += " (size " + getNumVertices() + "+" + getNumEdges() + " integers):";

		for (GeographicPoint v : vertices.keySet()) {
			s += "\n\t" + v + ": ";
			MapNode m = vertices.get(v);
			for (MapEdge w : m.getNeighbors()) {
				s += w + ", ";
			}
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println(firstMap);
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

		System.out.println("Test 1 using firstMap:");
		List<GeographicPoint> testroute = firstMap.bfs(testStart, testEnd);

		for (GeographicPoint i : testroute) {
			System.out.println(i + " >>> ");
		}

		System.out.println("DONE.");

		System.out.print("Making a new map...");
		MapGraph secondMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/graders/mod2/map2.txt", secondMap);
		System.out.println(secondMap);
		// 0 0 1 1
		GeographicPoint testStart2 = new GeographicPoint(6.0, 6.0);
		GeographicPoint testEnd2 = new GeographicPoint(0.0, 0.0);

		System.out.println("Test 1 using secondMap:");
		List<GeographicPoint> testroute2 = secondMap.bfs(testStart2, testEnd2);

		for (GeographicPoint i : testroute2) {
			System.out.println(i + " >>> ");
		}

		System.out.println("DONE.");
		// You can use this method for testing.

		/*
		 * Here are some test cases you should try before you attempt the Week 3
		 * End of Week Quiz, EVEN IF you score 100% on the programming
		 * assignment.
		 */
		/*
		 * MapGraph simpleTestMap = new MapGraph();
		 * GraphLoader.loadRoadMap("data/testdata/simpletest.map",
		 * simpleTestMap);
		 * 
		 * GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		 * GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		 * 
		 * System.out.
		 * println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5"
		 * ); List<GeographicPoint> testroute =
		 * simpleTestMap.dijkstra(testStart,testEnd); List<GeographicPoint>
		 * testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		 * 
		 * 
		 * MapGraph testMap = new MapGraph();
		 * GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		 * 
		 * // A very simple test using real data testStart = new
		 * GeographicPoint(32.869423, -117.220917); testEnd = new
		 * GeographicPoint(32.869255, -117.216927); System.out.
		 * println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5"
		 * ); testroute = testMap.dijkstra(testStart,testEnd); testroute2 =
		 * testMap.aStarSearch(testStart,testEnd);
		 * 
		 * 
		 * // A slightly more complex test using real data testStart = new
		 * GeographicPoint(32.8674388, -117.2190213); testEnd = new
		 * GeographicPoint(32.8697828, -117.2244506); System.out.
		 * println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10"
		 * ); testroute = testMap.dijkstra(testStart,testEnd); testroute2 =
		 * testMap.aStarSearch(testStart,testEnd);
		 */

		/* Use this code in Week 3 End of Week Quiz */
		/*
		 * MapGraph theMap = new MapGraph();
		 * System.out.print("DONE. \nLoading the map...");
		 * GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		 * System.out.println("DONE.");
		 * 
		 * GeographicPoint start = new GeographicPoint(32.8648772,
		 * -117.2254046); GeographicPoint end = new GeographicPoint(32.8660691,
		 * -117.217393);
		 * 
		 * 
		 * List<GeographicPoint> route = theMap.dijkstra(start,end);
		 * List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		 * 
		 */

	}

}
