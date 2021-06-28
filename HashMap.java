package col106.assignment4.HashMap;
import java.util.Vector;
import java.util.ArrayList;

public class HashMap<V> implements HashMapInterface<V> {
	private class kvpair{
		private String key;
		private V value;
		public kvpair(String key,V value) {
			this.key = key;
			this.value = value;
			
		}
	}
	
	private ArrayList<kvpair> arr;
	private int s = 0;

	public HashMap(int size) {
		
		this.arr = new ArrayList<kvpair>(size);
		for (int i = 0; i < size; i++) {
			arr.add(null);
		}
		this.s = size;
		// write your code here
	}
	private int HashCode(String key) {
		int n = key.length();
		long f = 0;
		for (int i =0; i<n;i++) {
			f = 41*f + key.charAt(n-i-1);
		}
		return (int)(f%s)


		;
	}

	public V put(String key, V value){
		int index = HashCode(key);
		kvpair data = new kvpair(key,value);
		while (arr.get(index)!= null) {
			if (arr.get(index).key.equals(key)) {
				V val = arr.get(index).value;
				arr.set(index,data);
				return val;
			}
			index = (index+1)%s;
		}
		arr.set(index, data);	
		return null;
	}
	public boolean delete(String key){
		int index = HashCode(key);
		int count = 0;
		while (arr.get(index)!= null) {
			count++;
			if (count > s) return false;
			if (arr.get(index).key.equals(key)) {
				arr.set(index, null);
				return true;}
				index = (index+1)%s;
			}
			// write your code here
			return false;
			}

	public V get(String key){
		// write your code here
		int index = HashCode(key);
		int count = 0;
		while (arr.get(index)!= null) {
			count++;
			if (count > s) return null;
			if (arr.get(index).key.equals(key)) {
				return arr.get(index).value;
			}
			index = (index+1)%s;
		}
		return null;
	}

	public boolean remove(String key){
		int index = HashCode(key);
		int count = 0;
		while (arr.get(index)!= null) {
			count++;
			if (count > s) return false;
			if (arr.get(index).key.equals(key)) {
				int hole = index;
				int i = index;
				int c = 0;
				while(c<s-1) {
					c++;
					i = (i+1)%s;
					if(arr.get(i)== null) break;
					int nat = HashCode(arr.get(i).key);
					if (i==nat) continue;
					if (i > nat && i < hole) continue;
					if ((hole>=nat)||(hole<nat && i > hole && i < nat)) { 
						arr.set(hole, arr.get(i));
						hole = i;
						
					}
				}
				arr.set(hole, null);
				return true;
				
			}
			index = (index+1)%s;
		}
		// write your code here
		return false;
	}

	public boolean contains(String key){
		// write your code here
		int index = HashCode(key);
		int count = 0;
		while (arr.get(index)!= null) {
			if (count > s) return false;
			if (arr.get(index).key.equals(key)) {
				return true;
			}
			index = (index+1)%s;
		}
		return false;
	}

	public Vector<String> getKeysInOrder(){
		Vector<String> v = new Vector<String>();
		for (int i = 0; i < s; i++) {
			if (arr.get(i)!= null) v.add(arr.get(i).key);
		}
		// write your code here
		return v;
	}
}
