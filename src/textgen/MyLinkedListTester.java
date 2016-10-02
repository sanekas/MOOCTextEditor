/**
 * 
 */
package textgen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> testRemoveList;
	MyLinkedList<Integer> testAddEndList;
	MyLinkedList<Integer> testSizeList;
	MyLinkedList<Integer> testAddAtIndexList;
	MyLinkedList<Integer> testSetList;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		testRemoveList = new MyLinkedList<Integer>();
		testRemoveList.add(65);
		testRemoveList.add(21);
		testRemoveList.add(42);
		testAddEndList = new MyLinkedList<>();
		testAddEndList.add(65);
		testAddEndList.add(21);
		testAddEndList.add(42);
		testSizeList = new MyLinkedList<>();
		testSizeList.add(65);
		testSizeList.add(21);
		testSizeList.add(42);
		testAddAtIndexList = new MyLinkedList<>();
		testAddAtIndexList.add(65);
		testAddAtIndexList.add(21);
		testAddAtIndexList.add(42);
		testSetList = new MyLinkedList<>();
		testSetList.add(65);
		testSetList.add(21);
		testSetList.add(42);
		
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
		int a = testRemoveList.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, testRemoveList.get(0));
		assertEquals("Remove: check size is correct ", 2, testRemoveList.size());
		
		int b = testRemoveList.remove(1);
		assertEquals("Remove chek b is correct ", 42, b);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, testRemoveList.get(0));
		assertEquals("Remove: check size is correct ", 1, testRemoveList.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveIOBExcLow() {
		testRemoveList.remove(-5);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveIOBExcHigh() {
		testRemoveList.remove(1000);
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd() {
		testAddEndList.add(25);
		Assert.assertTrue(testAddEndList.get(3).equals(25));
	}

	@Test(expected = NullPointerException.class)
	public void testAddEndNullElement() {
		testAddEndList.add(null);
	}
	
	/** Test the size of the list */
	@Test
	public void testSize() {
		testSizeList.remove(0);
		testSizeList.add(25);
		Assert.assertTrue(testSizeList.size() == 3);
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex() {
		testAddAtIndexList.add(3, 25);
        Assert.assertTrue(testAddAtIndexList.get(3) == 25);
		
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddAtIndexMethodOnIOBExcLow() {
		testAddAtIndexList.add(-1, 3);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddAtIndexMethodOnIOBExcHigh() {
		testAddAtIndexList.add(1000, 3);
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet() {
		testSetList.set(1, 2016);
		Assert.assertTrue(testSetList.get(1) == 2016);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetMethotOnIOBExcLow() {
		testSetList.set(-2, 3);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetMethotOnIOBExcHigh() {
		testSetList.set(1000, 3);
	}

}
