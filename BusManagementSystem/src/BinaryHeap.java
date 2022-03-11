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

	public void decreaseKey(double key, int value) {
		keys[pos.get(value)] = Math.min(key, keys[pos.get(value)]);
		bubbledown(pos.get(value));
		return;
	}

	public int delMin() {
		int min = values[0];
		pos.remove(min);
		swap(0, --n);
		bubbledown(0);
		keys[n] = Double.MAX_VALUE;
		values[n] = Integer.MAX_VALUE;
		return min;
	}

	private int childOf(int p) {
		if (2 * p + 1 > n - 1)
			return -1;
		if (2 * p + 2 > n - 1)
			return 2 * p + 1;
		return keys[2 * p + 1] < keys[2 * p + 2] ? 2 * p + 1 : 2 * p + 2;
	}

	private int parentOf(int c) {
		return (c % 2 == 1) ? c / 2 : c / 2 - 1;
	}

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

	private void bubbledown(int n) {
		int c = childOf(n);
		if (c == -1)
			return;
		if (keys[n] > keys[c]) {
			swap(n, c);
			bubbledown(c);
		}
	}

	private void bubbleup(int n) {
		int p = parentOf(n);
		if (p == -1)
			return;
		if (keys[n] < keys[p]) {
			swap(n, p);
			bubbleup(p);
		}
	}

	public boolean contains(int key) {
		return pos.containsKey(key);
	}

	public boolean isEmpty() {
		return n <= 0;
	}

}
