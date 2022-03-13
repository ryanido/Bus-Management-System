import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TripDatabase {
	private ArrayList<DataPoint> trips;
	private final int CUTOFF = 15;

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
				arrivalTime = line.split(",")[1].trim();
				if (time.equals(arrivalTime)) {
					tripID = Integer.parseInt(line.split(",")[0]);
					depatureTime = line.split(",")[2].trim();
					stopID = Integer.parseInt(line.split(",")[3]);
					arrivalHour = Integer.parseInt(arrivalTime.split(":")[0]);
					depatureHour = Integer.parseInt(depatureTime.split(":")[0]);
					if (arrivalHour <= 24 && depatureHour <= 24)
						trips.add(new DataPoint(tripID, arrivalTime, depatureTime, stopID));
				}
			}
			mergeSort(trips, 0, trips.size() - 1);
			this.trips = trips;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Sorting algorithms
	// MergeSort
	private void mergeSort(ArrayList<DataPoint> a, int lo, int hi) {
		if (hi <= lo + CUTOFF - 1) {
			insertionSort(a, lo, hi);
			return;
		}
		if(hi > lo)
		{
			int mid = lo + (hi - lo) / 2;
			mergeSort(a, lo, mid);
			mergeSort(a, mid + 1, hi);
			merge(a, lo, mid, hi);
		}
	

	}

	private void merge(ArrayList<DataPoint> a, int lo, int mid, int hi) {
		int nb = mid - lo + 1;
		int nc = hi - mid;
		DataPoint[] b = new DataPoint[nb];
		DataPoint[] c = new DataPoint[nc];
		int j = 0;
		int k = 0;
		for (int i = 0; i < nb; i++) {
			b[i] = a.get(lo + i);
		}
		for (int i = 0; i < nc; i++) {
			c[i] = a.get(mid + 1 + i);
		}
		int i = lo;
		while (j < nb && k < nc) {
			if (b[j].tripID < c[k].tripID) {
				a.set(i++, c[k++]);
			} else {
				a.set(i++, b[j++]);
			}
		}
		while (j < nb) {
			a.set(i++, b[j++]);
		}
		while (k < nc) {
			a.set(i++, c[k++]);
		}
	}

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

	// swaps position in arraylist
	private void swap(ArrayList<DataPoint> a, int x, int y) {
		DataPoint tmp = a.get(x);
		a.set(x, a.get(y));
		a.set(y, tmp);
	}
	
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
