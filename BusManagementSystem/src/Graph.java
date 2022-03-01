import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class Graph {
	private int nvertices; //number of vertices
	private boolean[] discovered; // array for vertices discovered but not processed
	private boolean[] processed; // array for vertices processed
	private int[] parents; // array for parents(vertex preceding) of each vertex
	private EdgeNode[] edges;// array for each edge
	private double[] distTo;//array for distance to each edge 
	private HashMap<String,Integer> mappedIDs; // hashmap of stopID to position in array
	
	public Graph()
	{
		BufferedReader reader;
		this.nvertices = 0;
		try {
			//Map StopID's to consecutive numbers to optimize space used by Adjacency list 
			reader = new BufferedReader(new FileReader("stops.txt"));
			String line;
			String stopID;
			reader.readLine();
			for(int index = 0; (line = reader.readLine()) != null; index++)
			{
				stopID= line.split(",")[0];
				(this.mappedIDs).put(stopID, index);
				this.nvertices++;
			}
			//Initialize all arrays to appropriate length
			this.edges = new EdgeNode[nvertices];
			this.discovered = new boolean[nvertices];
			this.processed = new boolean[nvertices];
			this.parents = new int[nvertices];
			this.distTo = new double[nvertices];
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//add Edge to graph
	public void addEdge(String start, String end,int weight)
	{
		EdgeNode tmp = new EdgeNode(end,weight,this.mappedIDs.get(end));
		int startIndex = this.mappedIDs.get(start);
		tmp.next = this.edges[startIndex];
		this.edges[startIndex] = tmp;
	}
	//Depth First Search Method
	public void dfs(int start)
	{
		initSearch();
		int parent, child; // parent and child points on graph
		parent = start;
		discovered[parent] = true;
		EdgeNode tmp = edges[parent]; // temporary edgeNode
		while(tmp != null)
		{
			child = tmp.mappedID;
			if(discovered[child] != true)
			{
				dfs(child);
				parents[child] = parent;
			}
			tmp = tmp.next;
		}
		processed[parent] = true;
	}
	
	//Breadth First Search Method
	public void bfs(int start)
	{
		initSearch();
		int parent,child; // parent and child points on graph
		Queue<Integer> queue = new LinkedList<>();
		discovered[start] = true;
		queue.add(start);
		EdgeNode tmp; // temporary edgeNode
		while(!queue.isEmpty())
		{
			parent = queue.remove();
			tmp = edges[parent];
			while(tmp != null)
			{
				child = tmp.mappedID;
				if(discovered[child] != true)
				{
					queue.add(child);
					parents[child] = parent;
					discovered[child] = true;
				}
				tmp = tmp.next;
			}
			processed[parent] = true;
		}
	}
	
	//Initialising Arrays for searching
	public void initSearch()
	{
		for(int i = 0; i < nvertices; i++)
		{
			discovered[i] = false;
			processed[i] = false;
			parents[i] = -1;
			distTo[i] = Double.POSITIVE_INFINITY;
		}
	}
	
	//Djikstra shortest path
	public void Djikstra(int start,int end)
	{
		initSearch();
		BinaryHeap pq = new BinaryHeap(nvertices);
		int v,y; // parent and child points on graph
		v = start;
		distTo[v] = 0;
		relax(v,pq);
		y = pq.deleteMax();
		while(!pq.isEmpty() && y != end) //
		{
			relax(y,pq);
			y = pq.deleteMax();
		}
	}
	
	public void relax(int v, BinaryHeap pq) // pq
	{
		for(EdgeNode tmp = this.edges[v]; tmp != null; tmp = tmp.next)
		{
			int w = tmp.mappedID;
			if(distTo[w] > distTo[v] + tmp.weight) {
				distTo[w] = distTo[v] + tmp.weight;
				parents[w] = v;
			}
			pq.insert(tmp.weight, tmp.mappedID);
		}
	}
}
 