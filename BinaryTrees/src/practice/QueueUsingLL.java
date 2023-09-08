package practice;

public class QueueUsingLL<T> {

	Node<T> front;
	Node<T> rear;
	int size;
	
	public QueueUsingLL() {
		front=null;
		rear=null;
		size=0;
	}
	
	int size() {
		return size;
	}
	
	boolean isEmpty() {
		return (size==0);
	}
	
	T front() {
		return front.data;
	}
	
	void enqueue(T element) {
		Node<T> newElement= new Node<T>(element);
		
		if(size==0) {
			front= newElement;
			rear=newElement;
			size++;
			return;
		}
		
		rear.next=newElement;
		rear=newElement;
		size++;
		return;
	}
	
	T dequeue() throws QueueEmptyException {
		if(size==0) {
			throw new QueueEmptyException();
		}
		
		T temp =front.data;
		front= front.next;
		
		if(size==1) {
			rear=null;
		}
		size--;
		
		return temp;
	}
}