/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;
import util.SpeedLimitLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 *         A class which represents a graph of geographic locations Nodes in the
 *         graph are intersections between
 *
 */
public class MapGraph {
	// Maintain both nodes and edges as you will need to
	// be able to look up nodes by lat/lon or by roads
	// that contain those nodes.
	private HashMap<GeographicPoint, MapNode> pointNodeMap;
	private HashSet<MapEdge> edges;
	private HashMap<String, MapSpeed> speedMap;
	private SearchCost searchCost = SearchCost.DISTANCE;

	/**
	 * @return the searchCost
	 */
	public SearchCost getSearchCost() {
		return searchCost;
	}

	/**
	 * @param searchCost
	 *            the searchCost to set
	 */
	public void setSearchCost(SearchCost searchCost) {
		this.searchCost = searchCost;
	}

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph() {
		pointNodeMap = new HashMap<GeographicPoint, MapNode>();
		edges = new HashSet<MapEdge>();
		speedMap = new HashMap<String, MapSpeed>();
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return pointNodeMap.values().size();
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * 
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		return pointNodeMap.keySet();
	}

	/**
	 * Get the number of road segments in the graph
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		return edges.size();
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
		if (location == null) {
			return false;
		}
		MapNode n = pointNodeMap.get(location);
		if (n == null) {
			n = new MapNode(location);
			pointNodeMap.put(location, n);
			return true;
		} else {
			return false;
		}
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

		MapNode n1 = pointNodeMap.get(from);
		MapNode n2 = pointNodeMap.get(to);

		// check nodes are valid
		if (n1 == null)
			throw new NullPointerException("addEdge: pt1:" + from + "is not in graph");
		if (n2 == null)
			throw new NullPointerException("addEdge: pt2:" + to + "is not in graph");

		MapEdge edge = new MapEdge(roadName, roadType, n1, n2, length);
		edges.add(edge);
		n1.addEdge(edge);

	}

	/***
	 * addMapSpeed
	 */

	public void addMapSpeed(String roadType, MapSpeed mapSpeed) {
		speedMap.put(roadType, mapSpeed);
	}

	/***
	 * getMapSpeed
	 */

	public MapSpeed getMapSpeed(String roadType) {
		return speedMap.get(roadType);
	}

	/***
	 * getAllMapSpeed
	 */

	public Collection<MapSpeed> getAllMapSpeed() {
		return speedMap.values();
	}

	/**
	 * Get a set of neighbor nodes from a mapNode
	 * 
	 * @param node
	 *            The node to get the neighbors from
	 * @return A set containing the MapNode objects that are the neighbors of
	 *         node
	 */
	private Set<MapNode> getNeighbors(MapNode node) {
		return node.getNeighbors();
	}

	/**
	 * resetMapNodeCosts reset each Map Nodes Distance From Start and A* Cost
	 */
	private void resetMapNodeCosts() {

		pointNodeMap.forEach((k, v) -> {
			v.setDistanceFromStart(Double.POSITIVE_INFINITY);
			v.setDurationFromStart(Double.POSITIVE_INFINITY);
			v.setaStarCost(Double.POSITIVE_INFINITY);
		});

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
		/*
		 * Note that this method is a little long and we might think about
		 * refactoring it to break it into shorter methods as we did in the Maze
		 * search code in week 2
		 */

		// Setup - check validity of inputs
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		MapNode startNode = pointNodeMap.get(start);
		MapNode endNode = pointNodeMap.get(goal);
		if (startNode == null) {
			System.err.println("Start node " + start + " does not exist");
			return null;
		}
		if (endNode == null) {
			System.err.println("End node " + goal + " does not exist");
			return null;
		}

		// setup to begin BFS

		resetMapNodeCosts();

		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		toExplore.add(startNode);
		MapNode next = null;

		while (!toExplore.isEmpty()) {
			next = toExplore.remove();

			// hook for visualization
			nodeSearched.accept(next.getLocation());

			if (next.equals(endNode))
				break;
			Set<MapNode> neighbors = getNeighbors(next);
			for (MapNode neighbor : neighbors) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, next);
					toExplore.add(neighbor);
				}
			}
		}
		if (!next.equals(endNode)) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		// Reconstruct the parent path
		List<GeographicPoint> path = reconstructPath(parentMap, startNode, endNode);

		return path;

	}

	/**
	 * Reconstruct a path from start to goal using the parentMap
	 *
	 * @param parentMap
	 *            the HashNode map of children and their parents
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	private List<GeographicPoint> reconstructPath(HashMap<MapNode, MapNode> parentMap, MapNode start, MapNode goal) {
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode current = goal;

		while (!current.equals(start)) {
			path.addFirst(current.getLocation());
			current = parentMap.get(current);
		}

		// add start
		path.addFirst(start.getLocation());
		return path;
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
		// TODO: Implement this method in WEEK 3

		// Setup - check validity of inputs
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		MapNode startNode = pointNodeMap.get(start);
		MapNode endNode = pointNodeMap.get(goal);
		if (startNode == null) {
			System.err.println("Start node " + start + " does not exist");
			return null;
		}
		if (endNode == null) {
			System.err.println("End node " + goal + " does not exist");
			return null;
		}

		// setup to begin dijkstra
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		Queue<MapNode> toExplore = new PriorityQueue<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		startNode.setDistanceFromStart(0.0);
		startNode.setDurationFromStart(0.0);
		toExplore.add(startNode);
		MapNode next = null;
		int nodesVisited = 0;

		while (!toExplore.isEmpty()) {
			next = toExplore.remove();
			nodesVisited++;
			System.out.println("DIJKSTRA visiting " + next);

			// hook for visualization
			nodeSearched.accept(next.getLocation());
			if (!visited.contains(next)) {
				visited.add(next);

				// if we found goal break
				if (next.equals(endNode))
					break;

				Set<MapNode> neighbors = getNeighbors(next);
				for (MapNode neighbor : neighbors) {
					if (!visited.contains(neighbor)) {
						for (MapEdge e : next.getEdges()) {

							// find the Edge that routes to the Neighbor Node
							if (e.getOtherNode(next).equals(neighbor)) {

								// calculate distance from the start
								Double tempDist = next.getDistanceFromStart() + e.getLength();
								Double tempEstimatedDistance = MapNode.dijkstraEstimatedDistance;

								// calculate a * cost
								MapNode tempMapNode = calculateCost(tempDist, next.getDurationFromStart(),
										tempEstimatedDistance, e);

								// Update Neighbor's costs if calculated cost is
								// shorter
								// update parent map with new mapping
								if (neighbor.getaStarCost() > tempMapNode.getaStarCost()) {
									neighbor.setDistanceFromStart(tempMapNode.getDistanceFromStart());
									neighbor.setDurationFromStart(tempMapNode.getDurationFromStart());
									neighbor.setaStarCost(tempMapNode.getaStarCost());
									parentMap.put(neighbor, next);
									// enQueue into the Priority Queue
									toExplore.add(neighbor);
								}
							}
						}

					}
				}
			}

		}

		if (!next.equals(endNode)) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		// Reconstruct the parent path
		List<GeographicPoint> path = reconstructPath(parentMap, startNode, endNode);

		System.out.println("Nodes visited in search:" + nodesVisited);

		return path;

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
		// Setup - check validity of inputs
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		MapNode startNode = pointNodeMap.get(start);
		MapNode endNode = pointNodeMap.get(goal);
		if (startNode == null) {
			System.err.println("Start node " + start + " does not exist");
			return null;
		}
		if (endNode == null) {
			System.err.println("End node " + goal + " does not exist");
			return null;
		}

		// setup to begin a*
		resetMapNodeCosts();
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		Queue<MapNode> toExplore = new PriorityQueue<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		startNode.setDistanceFromStart(0.0);
		startNode.setDurationFromStart(0.0);
		toExplore.add(startNode);
		MapNode next = null;
		int nodesVisited = 0;

		while (!toExplore.isEmpty()) {
			next = toExplore.remove();
			nodesVisited++;
			System.out.println("A* visiting " + next);

			// hook for visualization
			nodeSearched.accept(next.getLocation());
			if (!visited.contains(next)) {
				visited.add(next);

				// if we found goal break
				if (next.equals(endNode))
					break;

				Set<MapNode> neighbors = getNeighbors(next);
				for (MapNode neighbor : neighbors) {
					if (!visited.contains(neighbor)) {
						for (MapEdge e : next.getEdges()) {

							// find the Edge that routes to the Neighbor Node
							if (e.getOtherNode(next).equals(neighbor)) {

								// calculate distance from the start
								Double tempDist = next.getDistanceFromStart() + e.getLength();
								Double tempEstimatedDistance = neighbor.getLocation().distance(goal);

								// calculate a * cost
								MapNode tempMapNode = calculateCost(tempDist, next.getDurationFromStart(),
										tempEstimatedDistance, e);

								// Update Neighbor's costs if calculated cost is
								// shorter
								// update parent map with new mapping
								if (neighbor.getaStarCost() > tempMapNode.getaStarCost()) {
									neighbor.setDistanceFromStart(tempMapNode.getDistanceFromStart());
									neighbor.setDurationFromStart(tempMapNode.getDurationFromStart());
									neighbor.setaStarCost(tempMapNode.getaStarCost());
									parentMap.put(neighbor, next);
									// enQueue into the Priority Queue
									toExplore.add(neighbor);

								}
							}
						}

					}
				}
			}

		}

		if (!next.equals(endNode)) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		// Reconstruct the parent path
		List<GeographicPoint> path = reconstructPath(parentMap, startNode, endNode);

		System.out.println("Nodes visited in search:" + nodesVisited);

		return path;
	}

	private MapNode calculateCost(Double distanceFromStart, Double durationFromStart, Double estimatedDistance,
			MapEdge edge) {

		Double tempAStarCost = 0.0;
		MapNode tempMapNode = new MapNode(null);

		if (searchCost == SearchCost.DISTANCE) {
			// compute cost using distance
			tempAStarCost = distanceFromStart + estimatedDistance;
		} else {
			// initialize at min speed of 20KMH
			Double tempSpeed = 20.0;
			Double tempSpeedTraffic = 20.0;
			MapSpeed tempMapSpeed;

			tempMapSpeed = getMapSpeed(edge.getRoadType());
			if (tempMapSpeed != null) {
				tempSpeed = tempMapSpeed.getSpeedLimit();
				tempSpeedTraffic = tempMapSpeed.getSpeedLimitTraffic();
			}

			if (searchCost == SearchCost.DURATION_TRAFFIC) {
				tempSpeed = tempSpeedTraffic;
			}

			// compute cost using duration

			Double tempDistDuration = durationFromStart + (edge.getLength() * tempSpeed);

			// For the algorithm to find the actual shortest path,
			// the heuristic function must be admissible, meaning that it never
			// overestimates
			// the actual cost to get to the nearest goal node.
			// therefore if i take the tempEstimatedDistance and multiply it by
			// a
			// constant Max Legal Speed, this
			// should always under estimate
			Double tempEstimatedDistanceDuration = estimatedDistance * MapNode.MAXKILOPERHOUR;

			tempAStarCost = tempDistDuration + tempEstimatedDistanceDuration;
			tempMapNode.setDurationFromStart(tempDistDuration);

		}

		tempMapNode.setDistanceFromStart(distanceFromStart);

		tempMapNode.setaStarCost(tempAStarCost);

		return tempMapNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MapGraph [getSearchCost()=" + getSearchCost() + ", getNumVertices()=" + getNumVertices()
				+ ", getVertices()=" + getVertices() + ", getNumEdges()=" + getNumEdges() + ", getAllMapSpeeds() = "
				+ getAllMapSpeed() + "]";
	}

	public static void main(String[] args) {

		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		SpeedLimitLoader.loadSpeedLimitFile("data/speedlimits/defaults.txt", simpleTestMap);
		//
		// set searchCost
		 simpleTestMap.setSearchCost(SearchCost.DURATION_TRAFFIC);

		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		System.out.println(simpleTestMap);
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart, testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart, testEnd);

		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		SpeedLimitLoader.loadSpeedLimitFile("data/speedlimits/defaults.txt", testMap);

		// A very simple test using real data testStart = new
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		 testroute = testMap.dijkstra(testStart,testEnd);
		 testroute2 = testMap.aStarSearch(testStart,testEnd);

		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart, testEnd);
		testroute2 = testMap.aStarSearch(testStart, testEnd);

		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);

		// List<GeographicPoint> route = theMap.dijkstra(start, end);
		// 81 Dijkstra

		// List<GeographicPoint> route2 = theMap.aStarSearch(start, end);
		// 19 a*

	}

}