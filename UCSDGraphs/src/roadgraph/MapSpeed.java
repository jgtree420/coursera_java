/**
 * 
 */
package roadgraph;

/**
 * @author jgordon
 * POJO Class to capture Speed Limit and Traffic Penalty 
 *
 */
public class MapSpeed {

	private String roadType;
	private Double speedLimit;
	private Double trafficPenalty;
	/**
	 * @return the roadType
	 */
	public String getRoadType() {
		return roadType;
	}
	public MapSpeed(String roadType, Double speedLimit, Double trafficPenalty) {
		super();
		this.roadType = roadType;
		this.speedLimit = speedLimit;
		this.trafficPenalty = trafficPenalty;
	}
	/**
	 * @param roadType the roadType to set
	 */
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}
	/**
	 * @return the speedLimit
	 */
	public Double getSpeedLimit() {
		return speedLimit;
	}
	/**
	 * @param speedLimit the speedLimit to set
	 */
	public void setSpeedLimit(Double speedLimit) {
		this.speedLimit = speedLimit;
	}
	/**
	 * @return the trafficPenalty
	 */
	public Double getTrafficPenalty() {
		return trafficPenalty;
	}
	/**
	 * @param trafficPenalty the trafficPenalty to set
	 */
	public void setTrafficPenalty(Double trafficPenalty) {
		this.trafficPenalty = trafficPenalty;
	}
	
	/**
	 * @return the getSpeedLimitTraffic
	 */
	public Double getSpeedLimitTraffic() {
		return getSpeedLimit() / getTrafficPenalty();
	}
	
	

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MapSpeed [getRoadType()=" + getRoadType() + ", getSpeedLimit()=" + getSpeedLimit()
				+ ", getTrafficPenalty()=" + getTrafficPenalty() + ", getSpeedLimitTraffic()="
				+ getSpeedLimitTraffic() +"]";
	}
	
}
