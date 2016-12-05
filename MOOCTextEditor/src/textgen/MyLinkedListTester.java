/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		//shortList.printList();
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		//list1.printList();
		int a = list1.remove(0);
		//list1.printList();
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		
//		list1.add(65);
//		list1.add(21);
//		list1.add(42);
		
		int b = list1.remove(1);
		//list1.printList();
		assertEquals("Remove: check b is correct ", 42, b);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 1, list1.size());
		
	
		
		// TODO: Add more tests here
		
		// TODO: Add index out of
		//you will want to thrown an IndexOutOfBoundsException if the index provided is out of bounds. 
		try {
			longerList.remove(99);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		try {
			longerList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}	
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		
		//shortList.printList();
		shortList.add("Jonathan");
		//shortList.printList();
		int shortListSize = shortList.size();
		assertEquals("Check second", "Jonathan", shortList.get(shortListSize - 1));
		
		
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("Check Size",10,longerList.size());
		
		// add a test to add one more item and check size
		longerList.add(30);
		assertEquals("Check Size",11,longerList.size());
		// add a test to remove an item and then check the size
		longerList.remove(2);
		assertEquals("Check Size",10,longerList.size());
		
		
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		
		longerList.add(3,300);
		assertEquals("Check 3",(Integer)300, longerList.get(3));
		assertEquals("Check Size",11,longerList.size());
		
		longerList.add(0,99);
		assertEquals("Check 0",(Integer)99, longerList.get(0));
		assertEquals("Check Size",12,longerList.size());
		
		// try to add to an out of bound index
		try {
			longerList.add(99,1000);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		try {
			longerList.add(-1,1000);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		//  ADD test . Null elements are not allowed in the list so 
		//  if someone tries to insert one you should throw a NullPointerException.
		try {
			longerList.add(2,null);
			fail("Check NullPointerException");
		}
		catch (NullPointerException e) {
		
		}

	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		// TODO: Test the old value is returned
		
//		list1.add(65);
//		list1.add(21);
//		list1.add(42);
		//list1.printList();
		int a = list1.set(2, 100);
		//list1.printList();
		assertEquals("Set: check a is correct ", 42, a);
		assertEquals("Set: check element 0 is correct ", (Integer)65, list1.get(0));
		assertEquals("Set: check size is correct ", 3, list1.size());
		
		
		
		// TODO: Test replace worked
		
		//you will want to thrown an IndexOutOfBoundsException if the index provided is out of bounds. 
				// In addition, if someone tries to set a node with a null element, you should throw a NullPointerException.
		// try to add to an out of bound index
		try {
			longerList.set(99,1000);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		//  ADD test . Null elements are not allowed in the list so 
		//  if someone tries to insert one you should throw a NullPointerException.
		try {
			longerList.set(3,null);
			fail("Check NullPointerException");
		}
		catch (NullPointerException e) {
		
		}
	    
	}
	
	
	// TODO: Optionally add more test methods.
	
}
