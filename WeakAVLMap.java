package col106.assignment4.WeakAVLMap;
import java.util.LinkedList;

import java.util.Queue;
import java.util.Vector;
import java.util.ArrayList;
@SuppressWarnings("unchecked")

public class WeakAVLMap<K extends Comparable,V> implements WeakAVLMapInterface<K,V>{
	
	public int numRotations = 0;
	public node root;
	public class node{
		K key;
		V value;
		int rank = 1;
		int height = 1;
		node right= null,left = null,parent = null;
		public node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
	
	public WeakAVLMap(){
		// write your code here
	}
	private void heightup(node node) {
		int leftNodeHeight = 0;
		int rightNodeHeight = 0;
		if (node.left != null) leftNodeHeight = node.left.height;
		if (node.right != null) rightNodeHeight = node.right.height;
	    node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
	}

	public void insert(K key, V value) {
		root = insert(root,key,value);
	}
	private node insert(node curr,K key, V value) {
		if (curr == null) {
			node n = new node(key,value);
			return n;
		}
		else {
			int c = curr.key.compareTo(key);
			if (c > 0) {
				curr.left = insert(curr.left,key,value);
				curr.left.parent = curr;
			}
			else {
				curr.right = insert(curr.right,key,value);
				curr.right.parent = curr;
				}
			
		}
		heightup(curr);
		return curr;
		
	}
	private void update(K key, V value) {
		node a = getit(key);
		a.value = value;
	}

	public V put(K key, V value){
		node a = getit(key);
		if (a!=null) {
			V val = a.value;
			update(key,value);
			return val;}
		insert(key,value);
		node x = getit(key);
		node parent = x.parent;
		while (parent != null &&  parent.rank-x.rank!= 1) {
			node sib = (parent.left == x)?parent.right:parent.left;
			int sdiff = (sib == null)?parent.rank:parent.rank - sib.rank;
			 if (sdiff >= 2) {
				 int l = (x.left == null)?x.rank:x.rank - x.left.rank;
				 int r = (x.right == null)?x.rank:x.rank - x.right.rank;
				 if (parent.left == x && l == 1) {
					 parent.rank--;
				     rotateRight(parent);
				     break;
				 }
				 else if (parent.left == x && l != 1) {				 
					 x.rank--;
			         x.right.rank++;
			         parent.rank--;
			         rotateLeft(x);
				     rotateRight(parent);
				     break;
				 }
				 else if (parent.right == x && r == 1) {
					 parent.rank--;
				     rotateLeft(parent);
				     break;
				 }
				 else if (parent.right == x && r != 1) {
					 x.rank--;
			         x.left.rank++;
			         parent.rank--;
			         rotateRight(x);
				     rotateLeft(parent);
				     break;
				 }
				}
			else parent.rank++;
			x = parent;
		    parent = x.parent;			
			}
		return null;
	}

	
	public void rotateRight(node A) {
		node P = A.parent;
		node B = A.left;
		node a = B.right;
		B.right = A;
		A.parent = B;
		B.parent = P;
		A.left = a;
		if (a!=null) a.parent = A;
		if (P!=null) {
			if (A == P.left) P.left = B;
			else P.right = B;
		}
		if (A==root) root = B;
		heightup(A);
		heightup(B);
		
		
		while (P!=null) {
			heightup(P);
			P = P.parent;
		}
		numRotations++;
	}
	public void rotateLeft(node A) {
		node P = A.parent;
		node B = A.right;
		node a = B.left;
		B.left = A;
		A.parent = B;
		B.parent = P;
		A.right = a;
		if (a!=null) a.parent = A;
		if (P!=null) {
			if (A == P.left) P.left = B;
			else P.right = B;
		}
		if (A==root) root = B;
		heightup(A);
		heightup(B);
		while (P!=null) {
			heightup(P);
			P = P.parent;
		}
		
		numRotations++;
	}
	public V remove(K key){
		node p = getit(key);
		if (p==null) return null;
		V value = p.value;
		node rep = null; node sib = null; node par = null;
		if (p.right == null && p.left == null) {
			par = p.parent;
			if (par==null) {
				root = null;
				return value;
			}
			if (p==par.left)  sib = par.right;
			else sib = par.left;
			rep = p;
			rep.rank--;
		}
		else if (p.left != null && p.right != null) {
			node a = findMin(p.right);
			par = a.parent;
			rep = (a.left==null)?a.right:a.left;
			if (a.left == null && a.right == null) {
				rep = a;
				rep.rank--;
			}
			if (a==par.left)  sib = par.right;
			else sib = par.left;
		}
		else {
			rep = (p.left==null)?p.right:p.left;
			par = p.parent;
			if(par == null){
			root = rep; return value;}
			if (p==par.left)  sib = par.right;
			else sib = par.left;}
		delete(key,value);
		int delta = par.rank - rep.rank;
		while (delta==3||par.rank == 2 && par.right == null && par.left ==null) {
			int i = 0;
			if (sib == null) i = par.rank;
			else i = par.rank-sib.rank;
			if (i == 2) par.rank--;
			else {
				int l = (sib.left==null)?sib.rank:sib.rank - sib.left.rank;
				int r = (sib.right==null)?sib.rank:sib.rank - sib.right.rank;
				boolean caseOne = false, singleRotate = false, doubleRotate = false;
				if (l == 2 && r == 2) caseOne = true;
				if ((l==1 && par.left == sib) || (r==1 && par.right == sib)) singleRotate = true;
				if ((l!=1 && par.left == sib) || (r!=1 && par.right == sib)) doubleRotate = true;
				if (caseOne == true) {par.rank--;
				sib.rank--;
				}
				else if (singleRotate == true && par.left == sib) {
					sib.rank++;
					if (sib.right == null) par.rank = par.rank - 2;
					else par.rank = par.rank - 1;
					rotateRight(par);
					break;
				}
				else if (doubleRotate == true && par.left == sib) {
					par.rank = par.rank - 2;
					sib.right.rank += 2;
					sib.rank--;
					rotateLeft(sib);
					rotateRight(par);
					break;
				}
				else if (singleRotate == true && par.right == sib) {
					sib.rank++;
					if (sib.left == null) par.rank = par.rank - 2;
					else par.rank = par.rank - 1;
					rotateLeft(par);
					break;
				}
				else if (doubleRotate == true && par.right == sib) {
					par.rank = par.rank - 2;
					sib.left.rank += 2;
					sib.rank--;
					rotateRight(sib);
					rotateLeft(par);					
					break;
				}
		}
			if (par.parent == null) break;
			rep = par;
			par = par.parent;
			if (par.left == rep) sib = par.right;
			else sib = par.left;
			delta = par.rank - rep.rank;
	}
		return value;
	}


	public node getit(K key) {
		node curr = root;
		while (curr!=null) {
		int c = curr.key.compareTo(key);
		if (c > 0) curr = curr.left;
		else if (c < 0) curr = curr.right;
		else return curr;
		// write your code her 
		}	
		return null;
	}

	public V get(K key){
		node curr = root;
		while (curr!=null) {
		int c = curr.key.compareTo(key);
		if (c > 0) curr = curr.left;
		else if (c < 0) curr = curr.right;
		else return curr.value;
		// write your code her 
		}	
		return null;
	}
	
	public ArrayList Inorder() {
		ArrayList<K> arr = new ArrayList<K>();
		return inorder(root,arr);
	}
	public  ArrayList<K> inorder(node root,ArrayList<K> arr) {
		if (root == null) {
			return arr;
		}
		inorder(root.left,arr);
		arr.add(root.key);
		inorder(root.right,arr);
		return arr;
	}
	public Vector<V> searchRange(K key1, K key2){
		ArrayList<K> arr = Inorder();
		Vector<V> vec = new Vector<V>();
		for(K i : arr) {
			if (i.compareTo(key1)>=0 && i.compareTo(key2)<=0) {
				K key = i;
				V value = get(key);
				vec.add(value);
			}
		}	
		return vec;
	}

	public int rotateCount(){
		return numRotations;
	}

	public int getHeight(){
		if (root == null) return 0;
		return root.height;
	}

	public Vector<K> BFS(){
		// write your code her 
		Vector<K> v = new Vector<K>();
		Queue<node> q = new LinkedList<node>();
		if (root == null) { 
		return v;
		}
		q.add(root);
		while (q.size() != 0) {
			node temp = q.remove();
			if (temp.left != null)
				q.add(temp.left);
			if (temp.right != null)
				q.add(temp.right);
			v.add(temp.key);
		}
		
		return v;
		
	}
	private node findMin(node x) {
		node curr = x;
		while(curr.left!=null) {
			curr = curr.left;
		}
		return curr;
	}
	
	public void delete( K key, V value) {
		root = delete(root,key,value);
	}
	private node delete(node node, K key, V value) {
		    if (node == null) return null;
		    int c = key.compareTo(node.key);
		    if (c < 0) {
		      node.left = delete(node.left, key,value);
		    } else if (c > 0) {
		      node.right = delete(node.right, key,value);
		    } else {
		      if (node.left == null) {
		      if (node.right!=null) node.right.parent = node.parent; 
		    	 
		        return node.right;
		      } else if (node.right == null) {
		    	  if (node.left!=null) node.left.parent = node.parent; 
		        return node.left;
		      } else {
		          node successorNode = findMin(node.right);
		          K successorKey = successorNode.key;
		          V successorValue = successorNode.value;
		          node.value = successorValue;
		          node.key = successorKey;
		          node.right = delete(node.right,successorKey, successorValue);
		        
		      }
		    }
		    heightup(node);
		    return node;
		  }

	
	
	  

}
