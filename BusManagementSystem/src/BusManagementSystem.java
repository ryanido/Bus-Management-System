
import java.util.InputMismatchException;
import java.util.Scanner;

//Notes for report:
//Graph Sparse Adjancency list
public class BusManagementSystem {
	public static boolean quit = false;
	public static final String STOPS = "stops.txt";
	public static final String TRANSFERS = "transfers.txt";
	public static final String STOP_TIMES = "stop_times.txt";
	public static final Graph CITY_GRAPH = new Graph(STOPS, TRANSFERS, STOP_TIMES);
	public static final TST STOPS_TST = new TST(STOPS);

	public static void main(String[] args) {
		System.out.println("Welcome to the the Bus Management System");

		while (!quit) {
			home();
		}
		System.out.println("Thanks for using the system!!!");
	}

	//Homepage of application
	public static void home() {
		Scanner input = new Scanner(System.in);
		System.out.printf("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"
				+ "\nEnter 1 to search for bus stops by name \n" + "Enter 2 to search for trips by arrival time \n"
				+ "Enter 3 to find the shortest path between 2 stops \n" + "Enter 'quit' to quit \n"
				+ "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
		String path = input.nextLine();
		if (path.equals("1"))
			stopSearch();
		else if (path.equals("2"))
			tripSearch();
		else if (path.equals("3"))
			shortestPath();
		else if (path.equals("quit"))
			quit = true;
		else
			System.out.println("Please enter a valid input");
		return;

	}

	// Finds the shortest path between 2 stops
	public static void shortestPath() {
		boolean done = false; // Boolean for if user is done
		int start; // start stop
		int end; // end stop
		while (!done) {
			try {
				Scanner input = new Scanner(System.in);
				System.out.println("Enter the start and end stop IDs separated by a ' ':");
				start = input.nextInt();
				end = input.nextInt();
				if (!(CITY_GRAPH.contains(start) && CITY_GRAPH.contains(end))) {
					System.out.println("Error: " + ((!CITY_GRAPH.contains(start) && !CITY_GRAPH.contains(end))
							? (start + " and " + end + " are not valid stop IDs")
							: !CITY_GRAPH.contains(start) ? (start + " is not a valid stop ID")
									: (end + " is not a valid stop ID")));
					if (!tryAgain())
						return;

				} else {

					if (!CITY_GRAPH.shortestPath(start, end)) {
						System.out.println("No Route Found");
					}
					if (!tryAgain())
						return;
				}
			} catch (InputMismatchException e) {
				if (!tryAgain())
					return;
			}

		}

	}

	// Searches for trips by arrival time
	public static void tripSearch() {
		boolean done = false; // Boolean for if user is done
		String time; // Arrival time
		int h; // Arrival hour
		int m; // Arrival minute
		int s; // Arrival second
		TripDatabase trips;
		while (!done) {
			try {
				Scanner input = new Scanner(System.in);
				System.out.println("Please enter the arrival time for the trip in the format HH:MM:SS");
				time = input.next();
				// if input is not in format or does not have all of hh mm and ss display error
				// message
				h = time.split(":").length == 3 ? Integer.parseInt(time.split(":")[0]) : 24;
				m = time.split(":").length == 3 ? Integer.parseInt(time.split(":")[1]) : 60;
				s = time.split(":").length == 3 ? Integer.parseInt(time.split(":")[2]) : 60;
				if (h < 24 && m < 60 && s < 60) {
					trips = new TripDatabase(time.trim(), STOP_TIMES);
					if (!trips.isEmpty()) {
						System.out.println("Here are the trips with time " + time + ":");
						System.out.println("Trip ID, Arrival Time, Depature Time, Stop ID, Stop Sequence, Stop Headsign, Pick Up Type, Drop Off Type, Shape Dist Travelled" );
						trips.printTrips();
					} else {
						System.out.println("There are no arrival times with time " + time);
					}
					if (!tryAgain())
						return;
				} else {
					System.out.println("Please enter a valid input in the format HH:MM:SS");
					if (!tryAgain())
						return;
				}
			} catch (NumberFormatException e) {

				System.out.println("Please enter a valid input in the format HH:MM:SS");
				if (!tryAgain())
					return;
			}
		}

	}

	// Searches for stops by name
	public static void stopSearch() {
		String stopToSearch; // Stop to search for
		boolean done = false;
		Scanner input = new Scanner(System.in);
		while (!done) {
			System.out.println("Enter the name of a stop you would like to search for");
			stopToSearch = input.nextLine().trim().toUpperCase();
			if (STOPS_TST.contains(stopToSearch)) {
				System.out.println("Here are your results");
				STOPS_TST.printSearch();
			} else
				System.out.println("Error: There's no stops that fit the search criteria");
			if (!tryAgain())
				return;
		}

	}

	// Function called when there is an input error or the user gets a successful
	// search returns true if user chooses to return home or quit
	public static boolean tryAgain() {
		System.out.printf("Enter 'r' if you would like to try again" + "\n" + "Enter 'h' to return to the homepage"
				+ "\n" + "Enter 'quit' to quit \n");
		String path;
		Scanner input = new Scanner(System.in);
		path = input.nextLine();
		while (!path.equals("r")) {
			if (path.equals("quit")) {
				quit = true;
				return false;
			} else if (path.equals("h")) {
				return false;
			}
			System.out.println("Please enter a valid input");
			path = input.nextLine();
		}
		return true;
	}

}
