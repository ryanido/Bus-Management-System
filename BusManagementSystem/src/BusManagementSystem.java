
import java.util.InputMismatchException;
import java.util.Scanner;

//Notes for report:
//Graph Sparse Adjancency list
//Dfs doesnt provide shortest path only a path
public class BusManagementSystem {
	public static boolean quit = false;

	public static void main(String[] args) {
		System.out.println("Welcome to the the Bus Management System");
		while (!quit) {
			home();
		}
		System.out.println("Thanks for using the system!!!");
	}

	public static void home() {
		Scanner input = new Scanner(System.in);
		System.out.printf("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"
				+ "\nEnter 1 to search for bus stops by name \n" + "Enter 2 to search for trips by arrival time \n"
				+ "Enter 3 to find the shortest path between 2 stops \n" + "Enter 'quit' to quit \n"
				+ "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
		if (input.hasNext("1"))
			stopSearch();
		else if (input.hasNext("2"))
			tripSearch();
		else if (input.hasNext("3"))
			shortestPath();
		else if (input.hasNext("quit"))
			quit = true;
		else
			System.out.println("Please enter a valid input");
		return;

	}

	// Finds the shortest path between 2 stops
	public static void shortestPath() {
		Scanner input = new Scanner(System.in);
		Graph g = new Graph(); // Graph of bus stops
		boolean valid = false; // Boolean for valid stops
		boolean done = false; // Boolean for if user is done
		int start; // start stop
		int end; // end stop
		while (!done) {
			try {
				System.out.println("Enter the start end stop IDs separated by a ' ':");
				start = input.nextInt();
				end = input.nextInt();
				if (!(g.contains(start) && g.contains(end))) {
					System.out.println("Error:" + ((!g.contains(start) && !g.contains(end))
							? (start + "and" + end + "are not valid stop IDs")
							: !g.contains(start) ? (start + " is not a valid stop ID") : (end + "is not a valid stop ID")));
					System.out.printf("Enter 'r' if you would like to try again" + "\n"
							+ "Enter 'h' to return to the homepage" + "\n" + "Enter 'quit' to quit");

					if (input.hasNext("quit")) {
						quit = true;
						return;
					} else if (input.hasNext("r")) {

					} else
						System.out.println("Please enter a valid input");
				} else {

					if (!g.shortestPath(start, end)) {
						System.out.println("No Route Found");
					}
					System.out.printf(
							"Enter 'r' if you would like to enter different inputs \n" + "Enter 'h' to return to the home page");
					while (!input.hasNext("r")) {
						if (input.hasNext("h")) {
							return;
						} else {
							System.out.println("Please enter a valid input\n");
							input.nextLine();
						}
					}
					input.nextLine();
				}
			} catch(InputMismatchException e)
			{
				System.out.println("Please enter a valid input");
				input.nextLine();
			}
			
		}
		
		
	}

	// Searches for trips by arrival time
	public static void tripSearch() {
		// TODO Auto-generated method stub

	}

	// Searches for stops by name
	public static void stopSearch() {
		// TODO Auto-generated method stub

	}

}
