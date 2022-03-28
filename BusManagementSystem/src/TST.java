import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TST {
	private int n; // size of TST
	private Node root; // root of TST
	private final String[] KEYWORDS = { "FLAGSTOP", "WB", "NB", "SB", "EB" };
	private final String LABELS = " ID , Code , Name , Description , Latitude , Longitude , Zone , URL , Location Type , Parent Station ";
	private final int STOP_NAME_INDEX = 2;
	private Set<String> searchResults;

	private static class Node {
		private char c; // character
		private Node left, mid, right; // left, middle, and right subtries
		private String val; // details of stops associated with string
	}

	public TST(String stops) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(stops));
			reader.readLine();
			String stopName;
			for (String line = reader.readLine().trim(); line != null; line = reader.readLine()) {
				stopName = adjust(line.split(",")[STOP_NAME_INDEX]);
				put(stopName, line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Moves keywords to provide meaningful search functionality
	private String adjust(String stopName) {
		String adjName = stopName;
		String[] stopWords = adjName.split(" ");
		while (Arrays.asList(KEYWORDS).contains(stopWords[0])) {
			adjName = adjName.replace(stopWords[0], "").trim();
			adjName += " " + stopWords[0];
			stopWords = adjName.split(" ");
		}
		return adjName;
	}

	// checks if tree contains key
	public boolean contains(String key) {
		return get(key) != null;
	}

	// Returns list of strings that start with the inputted key
	private Set<String> get(String key) {

		Node subtrieRoot = get(root, key, 0);
		if (subtrieRoot == null)
			return null;
		searchResults = new HashSet<>();
		searchResults.add(subtrieRoot.val);
		getChildValues(subtrieRoot.mid);
		return searchResults;
	}

	// adds values of child nodes to search set
	private void getChildValues(Node parent) {
		if (parent != null) {
			getChildValues(parent.left);
			getChildValues(parent.mid);
			getChildValues(parent.right);
			searchResults.add(parent.val);
		}
	}

	// return subtrie corresponding to an inputted key
	private Node get(Node tmp, String key, int pos) {
		if (tmp == null)
			return null;
		char c = key.charAt(pos);
		if (c < tmp.c)
			return get(tmp.left, key, pos);
		else if (c > tmp.c)
			return get(tmp.right, key, pos);
		else if (pos < key.length() - 1)
			return get(tmp.mid, key, pos + 1);
		else
			return tmp;
	}

	//puts key and value into tree
	private void put(String key, String val) {
		if (key == null) {
			throw new IllegalArgumentException("key is null for put()");
		}
		if (!contains(key))
			n++;
		else if (val == null)
			n--; // delete existing key
		root = put(root, key, val, 0);
	}

	//recursively adds each letter in the key into tree and associates the keys value with that letter
	private Node put(Node tmp, String key, String val, int pos) {
		char c = key.charAt(pos);
		if (tmp == null) {
			tmp = new Node();
			tmp.c = c;
		}
		if (c < tmp.c)
			tmp.left = put(tmp.left, key, val, pos);
		else if (c > tmp.c)
			tmp.right = put(tmp.right, key, val, pos);
		else if (pos < key.length() - 1)
			tmp.mid = put(tmp.mid, key, val, pos + 1);
		else
			tmp.val = val;
		return tmp;
	}

	// Prints Search results
	public void printSearch() {
		System.out.println(LABELS);
		for (String details : searchResults) {
			if (details != null)
				System.out.println(details);
		}
	}
}
