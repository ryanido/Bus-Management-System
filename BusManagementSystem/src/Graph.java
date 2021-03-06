import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Graph {
	private int nvertices, nedges; // number of vertices number of edges
	private HashMap<Integer, Boolean> processed; // array for vertices processed
	private HashMap<Integer, Integer> parents;// HashMap for parents(vertex preceding) of each vertex
	private HashMap<Integer, EdgeNode> edges;// HashMap for each edge(Adjacency List)
	private HashMap<Integer, Double> distTo;// HashMap for cost to each edge
	private HashMap<Integer, String> stopNames;// HashMap for stopNames
	private HashMap<Integer, Double> stopLats;// HashMap for stop latitude
	private HashMap<Integer, Double> stopLons;// HashMap for stop longitude
	private BinaryHeap pq;
	private final int STOP_ID_INDEX = 0;
	private final int STOP_LAT_INDEX = 4;
	private final int STOP_LON_INDEX = 5;
	private final int STOP_NAME_INDEX = 2;
	private final int TRIP_ID_INDEX = 0;
	private final int FROM_INDEX = 0;
	private final int TO_INDEX = 1;
	private final int TYPE_INDEX = 2;
	private final int COST_INDEX = 3;
	private final int TIME_INDEX = 1;
	private final int STOP_TIMES_ID_INDEX = 3;
	
	private class EdgeNode {
		private int stopID;
		private double cost; 
		private EdgeNode next;
		
		public EdgeNode(int stopID, double cost)
		{
			this.stopID = stopID;
			this.cost = cost;
			this.next = null;			
		}
	}

	public Graph(String stops, String transfers, String stopTimes) {
		BufferedReader reader;
		this.nvertices = 0;
		this.nedges = 0;
		try {
			reader = new BufferedReader(new FileReader(stops));
			String line;
			String lineB;
			String stopName;
			int stopID;
			double stopLat;
			double stopLon;
			// Initialize all HashMaps
			this.edges = new HashMap<>();
			this.stopNames = new HashMap<>();
			this.stopLats = new HashMap<>();
			this.stopLons = new HashMap<>();
			this.processed = new HashMap<>();
			this.parents = new HashMap<>();
			this.distTo = new HashMap<>();
			reader.readLine();
			// Adding stopnames and longitude and latitude of each stop
			while ((line = reader.readLine()) != null) {
				stopID = Integer.parseInt(line.split(",")[STOP_ID_INDEX]);
				stopLat = Double.parseDouble(line.split(",")[STOP_LAT_INDEX]);
				stopLon = Double.parseDouble(line.split(",")[STOP_LON_INDEX]);
				stopName = line.split(",")[STOP_NAME_INDEX];
				stopNames.put(stopID, stopName);
				stopLats.put(stopID, stopLat);
				stopLons.put(stopID, stopLon);
				nvertices++;
			}
			// Adding edges from transfers.txt
			reader.close();
			reader = new BufferedReader(new FileReader(transfers));
			reader.readLine();
			int from;
			int to;
			int type;
			double cost;
			int timeA;
			int timeB;
			while ((line = reader.readLine()) != null) {
				from = Integer.parseInt(line.split(",")[FROM_INDEX]);
				to = Integer.parseInt(line.split(",")[TO_INDEX]);
				type = Integer.parseInt(line.split(",")[TYPE_INDEX]);
				cost = type == 0 ? 2 : (double) Integer.parseInt(line.split(",")[COST_INDEX]) / 100;
				addEdge(from, to, cost);
			}
			// Adding edges from stop_times.txt
			reader.close();
			reader = new BufferedReader(new FileReader(stopTimes));
			reader.readLine();
			line = reader.readLine();
			int tripIDA;
			int tripIDB;
			while ((lineB = reader.readLine()) != null) {
				from = Integer.parseInt(line.split(",")[STOP_TIMES_ID_INDEX]);
				to = Integer.parseInt(lineB.split(",")[STOP_TIMES_ID_INDEX]);
				tripIDA = Integer.parseInt(line.split(",")[TRIP_ID_INDEX]);
				tripIDB = Integer.parseInt(lineB.split(",")[TRIP_ID_INDEX]);
				//checking for invalid inputs
				timeA = Integer.parseInt(line.split(",")[TIME_INDEX].split(":")[0].trim());
				timeB = Integer.parseInt(lineB.split(",")[TIME_INDEX].split(":")[0].trim());
				if (tripIDA == tripIDB && timeA < 25 && timeB < 25)
					addEdge(from, to, 1);
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

	// add Edge to graph
	public void addEdge(int start, int end, double weight) {
		String edge = "(" + start + "," + end + ")";
		EdgeNode tmp = new EdgeNode(end, weight);
		tmp.next = edges.get(start);
		edges.put(start, tmp);
		nedges++;
	}

	// Initialising Arrays for searching
	private void initSearch() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("stops.txt"));
			String line;
			int stopID;
			reader.readLine();
			this.pq = new BinaryHeap(nedges);
			while ((line = reader.readLine()) != null) {
				stopID = Integer.parseInt(line.split(",")[STOP_ID_INDEX]);
				this.processed.put(stopID, false);
				this.parents.put(stopID, -1);
				this.distTo.put(stopID, Double.POSITIVE_INFINITY);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean contains(int id) {
		return edges.containsKey(id);
	}

//	// Djikstra shortest path
//	public boolean Djikstra(int start, int end) {
//		initSearch();
//		int v = -1; // vertex
//		distTo.put(start, 0.0);
//		pq.insert(distTo.get(start), start);
//		while (!pq.isEmpty() && v != end) {
//			v = pq.delMin();
//			relax(v);
//		}
//		if (processed.get(end)) {
//			System.out.printf("\nHere is the route \nFrom:" + start + ":" + stopNames.get(start) + "\n" + "To" + ":"
//					+ end + ":" + stopNames.get(end) + "\n" + "Cost: %2d",distTo.get(end));
//			printPath(start, end);
//			return true;
//		}
//		return false;
//	}
//
//	private void relax(int v) {
//		for (EdgeNode tmp = edges.get(v); tmp != null; tmp = tmp.next) {
//			int w = tmp.stopID;
//			if (distTo.get(w) > distTo.get(v) + tmp.cost) {
//				distTo.put(w, distTo.get(v) + tmp.cost);
//				parents.put(w, v);
//				pq.insert(distTo.get(w), w);
//			}
//		}
//		processed.put(v, true);
//	}

	// A* shortest path
	public boolean shortestPath(int start, int end) {
		initSearch();
		int v = -1;// starting vertex
		double g; // term for heuristic
		double h; // term for heuristic
		double f; // heuristic
		int w;
		distTo.put(start, 0.0);
		pq.insert(distHeuristic(start, end), start);
		while (!pq.isEmpty() && v != end) {
			v = pq.delMin();
			for (EdgeNode tmp = edges.get(v); tmp != null; tmp = tmp.next) {
				w = tmp.stopID;
				if (!processed.get(w)) {
					h = distHeuristic(w, end);
					g = distTo.get(v) + tmp.cost;
					f = h + g;
					if (distTo.get(w) > g) {
						parents.put(w, v);
						distTo.put(w, g);
						pq.insert(f, w);
					}
				}

			}
			processed.put(v, true);
		}

		if (processed.get(end)) {
			System.out.println("Here is the route \nFrom:" + start + ":" + stopNames.get(start) + "\n" + "To" + ":"
					+ end + ":" + stopNames.get(end) + "\n" + "Cost:" + distTo.get(end));
			printPath(start, end);
			return true;
		}
		return false;
	}

	// calculating distance from stops using Haversine Formula(uses longitude and
	// latitude to calculate distance)
	private double distHeuristic(int point, int end) {
		double lon1 = Math.toRadians(stopLons.get(point));
		double lat1 = Math.toRadians(stopLats.get(point));
		double lon2 = Math.toRadians(stopLons.get(end));
		double lat2 = Math.toRadians(stopLats.get(point));
		double r = 6371; // radius of earth
		double result = (2 * r) * Math.asin(Math.sqrt(Math.sin((lat2 - lat1) / 2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon2 - lon1) / 2), 2)));
		return result;
	}

	//prints path of shortest path
	private void printPath(int start, int end) {
		if (start != end) {
			printPath(start, parents.get(end));
		}
		System.out.printf("Stop ID: " + end + "\n" + stopNames.get(end) + "\n");
	}
}
