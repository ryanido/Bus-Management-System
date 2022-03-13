
public class DataPoint {
	public int tripID;
	public String arrivalTime;
	public String departureTime;
	public int stopID;
	public int arrivalH;
	public int arrivalM;
	public int arrivalS;

	DataPoint(int tripID, String arrivalTime, String depatureTime, int stopID) {
		this.tripID = tripID;
		this.arrivalTime = arrivalTime;
		this.arrivalH = Integer.parseInt(arrivalTime.split(":")[0]);
		this.arrivalM = Integer.parseInt(arrivalTime.split(":")[1]);
		this.arrivalS = Integer.parseInt(arrivalTime.split(":")[2]);
		this.departureTime = depatureTime;
		this.stopID = stopID;
	}

	public String toString() {
		return "Trip ID: " + tripID + " Stop ID" + stopID + "\n" + " Arrival Time: " + arrivalTime + " Departure Time: "
				+ departureTime;
	}
}
