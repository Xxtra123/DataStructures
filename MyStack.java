
// Interface for MyStack Class
interface MyStackInterface<T>
{ 
    void push(T value);
    T pop() throws  EmptyStackException;
    T top() throws  EmptyStackException;
    boolean isEmpty();

} 
  
// Implement all the functions
class MyStack<T> implements MyStackInterface<T> 
{ 
    
   
public int size = 0;
	private int capacity = 1;
	private T [] arr;
	
	public MyStack() {
		arr = (T[]) new Object[capacity];
	}
	
	public void push(T value) {
		if (size + 1 > capacity) {
			 	capacity = 2*capacity;
				T [] temparr = (T[]) new Object[capacity];
				for (int i = 0;i<size;i++) {
					temparr[i] = arr[i];
				}
				arr = temparr;	
		}arr[size++] = value;
	}
	
	public T pop()  throws  EmptyStackException  {
		 if (isEmpty()) {
				throw new EmptyStackException("Stack is Empty");
			}
			else {
		return arr[--size];
	}}
	public T top()  throws  EmptyStackException{
	      if (isEmpty()) {
			throw new EmptyStackException("Stack is Empty");
		}
		else {
		return arr[size-1];
	}}
	public boolean isEmpty() {
		return (size==0);
	} 



}
class  EmptyStackException extends Exception{
	public EmptyStackException(String s) {
		super(s);
	}
}