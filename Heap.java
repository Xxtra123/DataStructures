package col106.assignment3.Heap;

import java.util.ArrayList;
import java.util.HashMap;



public class Heap<T extends Comparable, E extends Comparable> implements HeapInterface <T, E> {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}
	/*
	 * end code
	 */
	
	// write your code here	
	public class Node {
		T key;
		E value;

		public Node (T key, E value) {
			this.key = key;
			this.value = value;
		}
	}
	public ArrayList<Node> arr = new ArrayList<Node>();
	public Heap() {
		Node a = new Node(null,null);
		this.arr.add(a);
	}
	
	

	public void insert(T key, E value) {

		Node curr = new Node (key, value);
		arr.add(curr);
		swim(arr.size()-1);
		//write your code here
		
	}
	private void swim(int k) {
		while (k > 1 && less(arr.get(k/2),arr.get(k))) {
			exchange(k,k/2);
			k = k/2;
			
		}
		
	}
	
	private boolean less(Node a, Node b) {
		return a.value.compareTo(b.value) < 0;
	}
	private void exchange(int i,int j) {
		Node temp = arr.get(i);
		arr.set (i, arr.get(j));
		arr.set (j, temp);
		
	}
    private boolean isEmpty() {
    	if (arr.size()==1) return true;
    	else return false;
    }
	public E extractMax() {
		if (isEmpty()) return null;
		Node v = arr.get(1);
		exchange(arr.size()-1,1);
		arr.remove (arr.size()-1);
		sink(1);
		return v.value;
		
		
	}
	private void sink(int k) {
		while (2*k <= arr.size()-1)
		 {
		 int j = 2*k;
		 if (j < arr.size()-1 && less(arr.get(j),arr.get(j+1))) j++;
		 if (!less(arr.get(k),arr.get(j))) break;
		 exchange(k, j);
		 k = j;
		 }
	}


	public void delete(T key) {

		int i =0;
		int j = 0;
		for (i=1; i<arr.size(); i++) {
			if (key.compareTo(arr.get(i).key)==0) {
				j++;
				break;
			}
		}
		if (j!=1) return;
		E old = arr.get(i).value;
		E value = arr.get(arr.size()-1).value;
		exchange(arr.size()-1,i);
		arr.remove (arr.size()-1);
		
		if (value.compareTo(old)==1) {
			swim(i);
		}else sink(i);
		
		
	}

	public void increaseKey(T key, E value) {

		int i =0;
		int j = 0;
		for (i=1; i<arr.size(); i++) {
			if (key.compareTo(arr.get(i).key)==0) {
				j++;
				break;
			}
		}
		if (j!=1) return;
		E old = arr.get(i).value;
		arr.get(i).value = value;
		if (value.compareTo(old)==1) {
			swim(i);
		}else sink(i);
	}

	public void printHeap() {
		//write your code here
		int size = arr.size();

		for (int i = 1; i <size; i++) {
			Node temp = arr.get(i);
	        System.out.println(temp.key + ", " + temp.value);
	     }

	}	
}
