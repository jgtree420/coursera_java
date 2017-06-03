/**
 * 
 */
package roadgraph;

/**
 * @author jgordon 
 * used to define different types of costs used by Dijkstra and A*
 */
public enum SearchCost {

	DISTANCE, 
	DURATION, 
	DURATION_TRAFFIC;
}
