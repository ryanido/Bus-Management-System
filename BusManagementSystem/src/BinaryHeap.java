
public class BinaryHeap {
	private int size;
	private double[] values;
	private int n;
	private int[] keys;
	public BinaryHeap(int size)
	{
		this.size = size;
		this.values = new double[size];
		this.n = 0;
		this.keys = new int[size];
	}
	
	public boolean isEmpty() {
		return this.n == 0;
	}
	public void insert(double v, int id) {
		if(n  <= size)
		{
			this.values[n] = v;
			this.keys[n] = id;
			bubbleup(++n);
		}
		else
		{
			System.out.println("Too big");
		}
	}
	
	public int deleteMax() {
		int max = keys[0];
		values[0] = values[n -1];
		keys[0] = keys[n - 1];
		values[--n] = 0;
		keys[--n] = -1;
		bubbledown(1);
		return max;
	}
	
	public int parentOf(int index) {
		if(index == 1)
		{
			return -1;
		}
		return index/2;
	}
	
	public int childOf(int index) {
		if(2*index > n) 
		{
			return -1;
		}
		else if(2*index + 1 > n)
		{
			return 2*index;
		}
		return values[2 * index -1] > values[2 * index] ? 2 * index  : 2 * index + 1;
	}
	
	public void bubbleup(int c) {
		if(parentOf(c) != -1 && values[c - 1] > values[parentOf(c) - 1])
		{
			int x = parentOf(c);
			swap(c,parentOf(c));
			bubbleup(x);
		}
	}
	
	public void swap(int x, int y)
	{
		x--;
		y--;
		double tmp = values[x];
		values[x] = values[y];
		values[y] = tmp;
		int key = keys[x];
		keys[x] = keys[y];
		keys[y] = key;
	}
	
	public void bubbledown(int p) {
		if(childOf(p) != -1 && values[p - 1] < values[childOf(p) - 1])
		{
			int x = childOf(p);
			swap(p,childOf(p));
			bubbledown(x);
		}
	}


}
