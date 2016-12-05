package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		
		LLNode<E> n = new LLNode<E>(null);
		n.next = tail;
		n.prev = n.next.prev;
		n.next.prev = n;
		n.prev.next= n;
		n.data = element;
		size += 1;
		return true;		
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if (index >= size || index < 0){
			throw new IndexOutOfBoundsException();
		}
				
		LLNode<E> currNode = new LLNode<E>(null);
		for (int i = 0; i <= index ; i++){
			if (i == 0){
			currNode = head.next;
			}
			else currNode = currNode.next;
			
		}
		return currNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (index > size || index < 0){
			throw new IndexOutOfBoundsException();
		}
		
		if (element == null){
			throw new NullPointerException();
		}
				
		LLNode<E> currNode = new LLNode<E>(null);
		for (int i = 0; i <= index ; i++){
			if (i == 0){
			currNode = head.next;
			}
			else currNode = currNode.next;
			
		}
		
		LLNode<E> n = new LLNode<E>(null);
		n.next = currNode;
		n.prev = n.next.prev;
		n.next.prev = n;
		n.prev.next= n;
		n.data = element;
		size += 1;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index > size || index < 0){
			throw new IndexOutOfBoundsException();
		}
		
				
		LLNode<E> currNode = new LLNode<E>(null);
		LLNode<E> prevNode = new LLNode<E>(null);
		for (int i = 0; i <= index ; i++){
			if (i == 0){
			currNode = head.next;
			}
			else currNode = currNode.next;
			
		}
		
		prevNode = currNode.prev;
		
		prevNode.next = currNode.next;
		//prevNode.prev = prevNode.next.prev;
		prevNode.next.prev = prevNode;
		//prevNode.prev.next= prevNode;
		size -= 1;
		
		return currNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (index > size || index < 0){
			throw new IndexOutOfBoundsException();
		}
		
		if (element == null){
			throw new NullPointerException();
		}
				
		LLNode<E> currNode = new LLNode<E>(null);
		for (int i = 0; i <= index ; i++){
			if (i == 0){
			currNode = head.next;
			}
			else currNode = currNode.next;
			
		}
		
		E olddata = currNode.data;
		currNode.data = element;
		
		return olddata;
	}   
	
	
	public void printList (){
        String result = "";
        LLNode<E>  current = head;
        while(current.next != null){
            current = current.next;
            result += current.data + ", ";
        }
        System.out.println("List: " + result); 
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	public LLNode() 
	{
		this.data = null;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
		

	
	public String toString(){
		return data.toString();
		
	}

	
}
