
public class EdgeNode {
	public String stopID;
	public int mappedID;
	public int weight;
	public EdgeNode next;
	
	public EdgeNode(String stopID, int weight, int mappedID)
	{
		this.stopID = stopID;
		this.mappedID = mappedID;
		this.weight = weight;
		this.next = null;			
	}
}
