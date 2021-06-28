package col106.assignment3.BST;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import java.util.HashMap;
// BST
public class BST<T extends Comparable, E extends Comparable> implements BSTInterface<T, E> {
	/*
	 * Do not touch the code inside the upcoming block If anything tempered your
	 * marks will be directly cut to zero
	 */
	@SuppressWarnings("unchecked")
	public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}

	public class BSTNode {
		int v;
		T key;
		E value;
		BSTNode left;
		BSTNode right;

		public BSTNode(T key, E value) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
		}
	}

	public BSTNode root = null;

	/*
	 * end code start writing your code from here
	 */

	// write your code here
	private ArrayList<BSTNode> arr = new ArrayList<BSTNode>();
	private HashMap<T, BSTNode> hm = new HashMap<T, BSTNode>();

	// write your code here
	@SuppressWarnings("unchecked")
	public void insert(T key, E value) {
		if (root == null) {
			root = new BSTNode(key, value);
			BSTNode n = new BSTNode(key, value);
			hm.put(key, n);
		} else {
			BSTNode curr = root;
			BSTNode par = null;
			while (curr != null) {
				par = curr;
				int c = value.compareTo(curr.value);
				if (c > 0)
					curr = curr.right;
				else
					curr = curr.left;
			}
			int c = value.compareTo(par.value);
			if (c > 0)
				par.right = new BSTNode(key, value);
			else
				par.left = new BSTNode(key, value);
			BSTNode n = new BSTNode(key, value);
			hm.put(key, n);
		}
		// write your code here
	}

	public void update(T key, E value) {
		// write your code here
		delete(key);
		insert(key, value);
	}

	@SuppressWarnings("unchecked")
	private BSTNode find(BSTNode root, T key) {

		BSTNode temp = hm.get(key);
		return temp;
	}

	@SuppressWarnings("unchecked")

	public void printBST() {
		Queue<BSTNode> q = new LinkedList<BSTNode>();
		q.add(root);
		while (q.size() != 0) {
			BSTNode temp = q.remove();
			if (temp.left != null)
				q.add(temp.left);
			if (temp.right != null)
				q.add(temp.right);
			System.out.println(temp.key + ", " + temp.value);
		}
	}

	public void delete(T key) {
		BSTNode n = hm.get(key);
		E val = n.value;

		BSTNode curr = root;
		BSTNode par = null;
		while (curr != null && curr.value != val) {
			par = curr;
			int c = val.compareTo(curr.value);
			if (c > 0)
				curr = curr.right;
			else
				curr = curr.left;
		}
		if (curr == null)
			return;
		if (curr.left == null && curr.right == null) {
			if (curr == root)
				root = null;
			else {
				if (par.left == curr)
					par.left = null;
				else
					par.right = null;
			}
		} else if (curr.left != null && curr.right != null) {
			BSTNode suc = minval(curr.right);
			E value = suc.value;
			T k = suc.key;
			delete(k);
			curr.key = k;
			curr.value = value;

		} else {
			BSTNode child = curr.left == null ? curr.right : curr.left;
			if (curr == root)
				root = child;
			else {
				if (par.left == curr)
					par.left = child;
				else
					par.right = child;
			}

		}

		// write your code here
	}

	private BSTNode minval(BSTNode a) {
		BSTNode curr = a;

		while (curr.left != null) {

			curr = curr.left;
		}
		return curr;
	}

}
