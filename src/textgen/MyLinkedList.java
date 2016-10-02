package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	private static final String INDEX_OUT_OF_BOUNDS_EXCEPTION_INFO = "Size of MyLinkedList: %d Index: %d";
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {

	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) {
		if (element == null) {
			throw new NullPointerException("You aren't able to add null to MyLinkedList!");
		}
		LLNode<E> newNode = new LLNode<>(element);
		if (head == null) {
			head = newNode;
			size++;
			return true;
		}
		if (tail == null) {
			tail = newNode;
			head.setNext(tail);
			tail.setPrev(head);
			size++;
			return true;
		}
		tail.setNext(newNode);
		newNode.setPrev(tail);
		tail = newNode;
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_EXCEPTION_INFO, size, index));
		}
		int counter = 0;
		LLNode<E> temp = head;
		while (temp != null) {
			if (counter == index) {
				return temp.getData();
			} else {
				temp = temp.getNext();
				counter++;
			}
		}
		return null;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param index The index where the element should be added
	 * @param element The element to add
	 * @throws IndexOutOfBoundsException
	 */
	public void add(int index, E element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_EXCEPTION_INFO, size, index));
		}
		if (element == null) {
			throw new NullPointerException("You aren't able to add null to MyLinkedList!");
		}
		if (index == size) {
			add(element);
			return;
		}
		if (index == 0) {
			LLNode<E> newNode = new LLNode<>(element);
			newNode.setNext(head);
			head.setPrev(newNode);
			head = newNode;
			size++;
			return;
		}
		int counter = 0;
		LLNode<E> temp = head;
		while (temp != null) {
			if (counter == index) {
				LLNode<E> newNode = new LLNode<E>(element);
				newNode.setNext(temp);
				newNode.setPrev(temp.getPrev());
				if (temp.getPrev() != null) {
					temp.getPrev().setNext(newNode);
				}
				temp.setPrev(newNode);
				size++;
				break;
			} else {
				temp = temp.getNext();
				counter++;
			}
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		if (index < 0 || index >= size || size == 0) {
			throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_EXCEPTION_INFO, size, index));
		}

		LLNode<E> temp = head;
		int counter = 0;
		while (temp != null) {
			if (counter == index) {
				if (temp.getPrev() != null) {
					temp.getPrev().setNext(temp.getNext());
				}
				if (temp.getNext() != null) {
					temp.getNext().setPrev(temp.getPrev());
				}
				if (index == 0) {
					head = temp.getNext();
				}
				size--;
				return temp.getData();
			}
			temp = temp.getNext();
			counter++;
		}
		return null;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size || size == 0) {
			throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_EXCEPTION_INFO, size, index));
		}
		if (element == null) {
			throw new NullPointerException("You aren't able to set null");
		}

		LLNode<E> temp = head;
		int counter = 0;
		while (temp != null) {
			if (counter == index) {
				E data = temp.getData();
				temp.setData(element);
				return data;
			}
			temp = temp.getNext();
			counter++;
		}
		return null;
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode<E> getPrev() {
		return prev;
	}

	public void setPrev(LLNode<E> prev) {
		this.prev = prev;
	}

	public LLNode<E> getNext() {
		return next;
	}

	public void setNext(LLNode<E> next) {
		this.next = next;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}
}
