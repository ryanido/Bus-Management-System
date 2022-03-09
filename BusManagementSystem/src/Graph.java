import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class Graph {
	private int nvertices,nedges; //number of vertices number of edges
	private HashMap<Integer,Boolean> discovered;  // array for vertices discovered
	private HashMap<Integer,Boolean> processed;  // array for vertices processed
	private HashMap<Integer,Integer> parents;// HashMap for parents(vertex preceding) of each vertex
	private HashMap<Integer,EdgeNode> edges;// HashMap for each edge(Adjacency List)
	private HashMap<Integer,Double> distTo;//HashMap for cost to each edge 
	private HashMap<Integer,String> stopNames;//HashMap for stopNames
	private BinaryHeap pq;
	
	public Graph()
	{
		BufferedReader reader;
		this.nvertices = 0;
		this.nedges = 0;
		try { 
			reader = new BufferedReader(new FileReader("stops.txt"));
			String line;
			String lineB;
			String stopName;
			int stopID;
			//Initialize all HashMaps
			this.edges = new HashMap<>();
			this.stopNames = new HashMap<>();
			this.processed = new HashMap<>();
			this.discovered = new HashMap<>();
			this.parents = new HashMap<>();
			this.distTo = new HashMap<>();
			reader.readLine();
			//Adding stopnames
			while((line = reader.readLine()) != null)
			{
				stopID = Integer.parseInt(line.split(",")[0]);
				stopName = line.split(",")[2];
				stopNames.put(stopID, stopName);
				nvertices++;
			}
			// Adding edges from transfers.txt
			reader.close();
			reader = new BufferedReader(new FileReader("transfers.txt"));
			reader.readLine();
			int from;
			int to;
			int type;
			double cost;
			while((line = reader.readLine()) != null)
			{
				from = Integer.parseInt(line.split(",")[0]);
				to = Integer.parseInt(line.split(",")[1]);
				type = Integer.parseInt(line.split(",")[2]);
				cost = type == 0 ? 2 : (double)Integer.parseInt(line.split(",")[3])/100 ;
				addEdge(from,to,cost);
			}
			// Adding edges from stop_times.txt
			reader.close();
			reader = new BufferedReader(new FileReader("stop_times.txt"));
			reader.readLine();
			line = reader.readLine();
			int tripIDA;
			int tripIDB;
			while((lineB = reader.readLine()) != null)
			{
				from = Integer.parseInt(line.split(",")[3]);
				to = Integer.parseInt(lineB.split(",")[3]);
				tripIDA = Integer.parseInt(line.split(",")[0]);
				tripIDB = Integer.parseInt(lineB.split(",")[0]);
				if (tripIDA == tripIDB) addEdge(from,to,1);
				line = lineB;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//add Edge to graph
	public void addEdge(int start, int end,double weight)
	{
		EdgeNode tmp = new EdgeNode(end,weight);
		tmp.next = edges.get(start);
		edges.put(start, tmp);
		nedges++;
	}

	//Initialising Arrays for searching
	private void initSearch()
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader("stops.txt"));
			String line;
			int stopID;
			reader.readLine();
			for(int index = 0; (line = reader.readLine()) != null; index++)
			{
				stopID = Integer.parseInt(line.split(",")[0]);
				this.processed.put(stopID, false);
				this.discovered.put(stopID, false);
				this.parents.put(stopID, -1);
				this.distTo.put(stopID, Double.POSITIVE_INFINITY);
				this.pq = new BinaryHeap(nvertices);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public boolean contains(int id)
	{
		return edges.containsKey(id);
	}
	
	//Djikstra shortest path
	public boolean Djikstra(int start,int end)
	{
		initSearch();
		int v,y; // parent and child points on graph
		v = start;
		distTo.put(v, 0.0);
		relax(v);
		processed.put(v, true);
		y = pq.delMin();
		while(!pq.isEmpty() && y != end) //
		{
			processed.put(y, true);
			relax(y);
			y = pq.delMin();
		}
		if(processed.get(end))
		{
			printPath(start,end);
			return true;
		}
		return false;
	}
	
	private void relax(int v) 
	{
		for(EdgeNode tmp = edges.get(v); tmp != null; tmp = tmp.next)
		{
			int w = tmp.stopID;
			if(!processed.get(w)) 
			{
				if(distTo.get(w) > distTo.get(v) + tmp.cost) {
					distTo.put(w,distTo.get(v) + tmp.cost);
					parents.put(w,v);
				}
				pq.insert(distTo.get(w), w);
			}
		}
	}
	
	private void printPath(int start,int end)
	{
		while(start != end)
		{
			printPath(start,parents.get(end));
		}
		System.out.println("Stop ID: " + end + "/n" + stopNames.get(end));
	}
}
 