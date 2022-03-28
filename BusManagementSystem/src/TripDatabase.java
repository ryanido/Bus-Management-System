import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TripDatabase {
	private ArrayList<DataPoint> trips;
	private final int CUTOFF = 15;
	private final int ARRIVAL_TIME_INDEX = 1;
	private final int DEPARTURE_TIME_INDEX = 2;
	private final int TRIP_ID_INDEX = 0;

	private class DataPoint {
		private int tripID;
		private String fullDetails;

		public DataPoint(int tripID, String fullDetails) {
			this.tripID = tripID;
			this.fullDetails = fullDetails;
		}

		public String toString() {
			return  fullDetails; //"Trip ID: " + tripID + " Stop ID" + stopID + "\n" + " Arrival Time: " + arrivalTime + " Departure Time: "
//					+ departureTime;
		}
	}

	public TripDatabase(String time, String stopTimes) {
		try {
			String line;
			int tripID;
			String arrivalTime;
			String depatureTime;
			int stopID;
			int arrivalHour;
			int depatureHour;
			ArrayList<DataPoint> trips = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader(stopTimes));
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				arrivalTime = line.split(",")[ARRIVAL_TIME_INDEX].trim();
				if (time.equals(arrivalTime)) {
					tripID = Integer.parseInt(line.split(",")[TRIP_ID_INDEX]);
					depatureTime = line.split(",")[DEPARTURE_TIME_INDEX].trim();
					arrivalHour = Integer.parseInt(arrivalTime.split(":")[0]);
					depatureHour = Integer.parseInt(depatureTime.split(":")[0]);
					if (arrivalHour <= 24 && depatureHour <= 24)
						trips.add(new DataPoint(tripID,line));
				}
			}
			qsort(trips, 0, trips.size() - 1);
			this.trips = trips;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Sorting algorithms
	// Insertion Sort
	private void insertionSort(ArrayList<DataPoint> a, int lo, int hi) {
		int j;
		for (int i = lo; i < hi+ 1; i++) {
			j = i;
			while (j > lo && a.get(j).tripID >= a.get(j - 1).tripID) {
				swap(a, j, j - 1);
				j--;
			}
		}
	}

	public void qsort(ArrayList<DataPoint> a, int lo,int hi)
	{
		if (hi <= lo + CUTOFF - 1) {
			insertionSort(a, lo, hi);
			return;
		}
		if(hi > lo)
		{
			int p = partition(a,lo,hi);
			qsort(a,p+1,hi);
			qsort(a,lo,p-1);
		}
	}
	
	public int partition(ArrayList<DataPoint> a,int lo,int hi)
	{
		int p = hi;
		int l = lo;
		for(int i = lo; i <= hi;i++)
		{
			if(a.get(p).tripID < a.get(i).tripID)
			{
				swap(a,l,i);
				l++;
			}
		}
		swap(a,l,p);
		return l;
	}
	// swaps position in arraylist
	private void swap(ArrayList<DataPoint> a, int x, int y) {
		DataPoint tmp = a.get(x);
		a.set(x, a.get(y));
		a.set(y, tmp);
	}
	
	//checks if database is empty
	public boolean isEmpty()
	{
		return trips.isEmpty();
	}
	//Prints tripDatabase
	public void printTrips() {
		for(DataPoint tmp: trips)
		{
			System.out.println(tmp.toString());
		}
	}
	
}
