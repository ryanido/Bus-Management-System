import java.util.LinkedList;
import java.util.Queue;


public class Graph {
	int nvertices; //number of vertices
	boolean[] discovered; 
	boolean[] processed;
	int[] parents;
	EdgeNode[] edges;
	
	public Graph(int nvertices)
	{
		
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
		}
	}
}
 