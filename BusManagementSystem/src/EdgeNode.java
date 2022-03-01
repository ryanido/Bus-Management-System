
public class EdgeNode {
	public String stopID;
	public int mappedID;
	public double weight;
	public EdgeNode next;
	
	public EdgeNode(String stopID, double weight, int mappedID)
	{
		this.stopID = stopID;
		this.mappedID = mappedID;
		this.weight = weight;
		this.next = null;			
	}
	public EdgeNode()
	{
		this.stopID = null;
		this.mappedID = -1;
		this.weight = -1;
		this.next = null;	
	}
}
