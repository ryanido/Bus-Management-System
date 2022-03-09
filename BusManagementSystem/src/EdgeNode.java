
public class EdgeNode {
	public int stopID;
	public double cost; 
	public EdgeNode next;
	
	public EdgeNode(int stopID, double cost)
	{
		this.stopID = stopID;
		this.cost = cost;
		this.next = null;			
	}
//	public EdgeNode()
//	{
//		this.stopID = null;
//		this.cost = -1;
//		this.next = null;	
//	}
}
