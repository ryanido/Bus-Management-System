import java.util.HashMap;

public class BinaryHeap {
	private int n;
	private double[] keys;
	private int[] values;
	private int size;
	HashMap<Integer, Integer> pos;

	public BinaryHeap(int size) {
		this.keys = new double[size];
		this.values = new int[size];
		this.size = size;
		this.n = 0;
		this.pos = new HashMap<>();
	}

	//Inserts key -value pair into heap
	public void insert(double key, int value) {
		if (contains(value))
		{
			decreaseKey(key, value);
			return;
		}
		keys[n] = key;
		values[n] = value;
		pos.put(values[n], n);
		bubbleup(n++);
	}

	//reduces the value of a key
	private void decreaseKey(double key, int value) {
		keys[pos.get(value)] = Math.min(key, keys[pos.get(value)]);
		bubbledown(pos.get(value));
		return;
	}

	//returns the minimum key
	public int delMin() {
		int min = values[0];
		pos.remove(min);
		swap(0, --n);
		bubbledown(0);
		keys[n] = Double.MAX_VALUE;
		values[n] = Integer.MAX_VALUE;
		return min;
	}

	//returns the smallest child of p
	private int childOf(int p) {
		if (2 * p + 1 > n - 1)
			return -1;
		if (2 * p + 2 > n - 1)
			return 2 * p + 1;
		return keys[2 * p + 1] < keys[2 * p + 2] ? 2 * p + 1 : 2 * p + 2;
	}

	//returns the parent of c
	private int parentOf(int c) {
		return (c % 2 == 1) ? c / 2 : c / 2 - 1;
	}

	//swaps the position of 2 key-value pairs
	private void swap(int x, int y) {
		double k = keys[x];
		keys[x] = keys[y];
		keys[y] = k;
		int v = values[x];
		values[x] = values[y];
		values[y] = v;
		pos.put(values[x], x);
		pos.put(values[y], x);
	}

	//recursively decreases the position of a key value pair
	private void bubbledown(int n) {
		int c = childOf(n);
		if (c == -1)
			return;
		if (keys[n] > keys[c]) {
			swap(n, c);
			bubbledown(c);
		}
	}

	//recursively increases the position of a key value pair
	private void bubbleup(int n) {
		int p = parentOf(n);
		if (p == -1)
			return;
		if (keys[n] < keys[p]) {
			swap(n, p);
			bubbleup(p);
		}
	}

	//checks if the heap contains a key
	public boolean contains(int key) {
		return pos.containsKey(key);
	}

	//checks if a heap is empty
	public boolean isEmpty() {
		return n <= 0;
	}

}
