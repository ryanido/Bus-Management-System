import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

//Notes for report:
//Graph Sparse Adjancency list
//Dfs doesnt provide shortest path only a path
public class BusManagementSystem {
	
	public static void main(String[] args) {
		HashMap mappedID = mapStops();
		System.out.print(mappedID.get("8283"));
	}
	
	//Map StopID's to consecutive numbers to optimize space used by Adjacency list 
	public static HashMap mapStops(){
		BufferedReader reader;
		HashMap<String,Integer> mappedIDs = new HashMap<>();
		try {
			reader = new BufferedReader(new FileReader("stops.txt"));
			String line;
			String stopID;
			int index;
			reader.readLine();
			for(index = 0; (line = reader.readLine()) != null; index++)
			{
				stopID= line.split(",")[0];
				mappedIDs.put(stopID, index);
			}
			System.out.print(index);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mappedIDs;
	}

}
