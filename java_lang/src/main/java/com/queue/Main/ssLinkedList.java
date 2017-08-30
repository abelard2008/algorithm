package com.queue.Main;

public class ssLinkedList {

	private int num_in_list;
	private ssLinkedListElement first;
	private ssLinkedListElement last;

	// some private variables to maintain a heap of elements for fast
	// allocation
	private ssLinkedListElement lle_heap[];
	private int num_in_lle_heap;
	private static final int HEAP_ALLOC_NUM = 5;

	private class ssLinkedListElement {
		public Object obj;
		protected ssLinkedListElement prev, next;

		public ssLinkedListElement(Object o) {
			prev = next = null;
			obj = o;
		}

	}

	/**
	 * Allocates a brand new ssLinkedList
	 */
	public ssLinkedList() {
		num_in_list = 0;
		first = last = null;
		lle_heap = new ssLinkedListElement[HEAP_ALLOC_NUM];
		num_in_lle_heap = 0;
	}

	// heap o' elements - we'll try to keep no more than 25 cached

	private ssLinkedListElement alloc_lle(Object o) {
		ssLinkedListElement retel;

		if (num_in_lle_heap == 0) {
			for (int i = 0; i < HEAP_ALLOC_NUM; i++) {
				lle_heap[i] = new ssLinkedListElement(null);
				num_in_lle_heap++;
			}
		}

		num_in_lle_heap--;
		retel = lle_heap[num_in_lle_heap];
		retel.obj = o;
		return retel;
	}

	/**
	 * Adds an object to the tail (end) of the linked list.
	 * 
	 * @param o
	 *            the object to add
	 */
	public void add_to_tail(Object o) {
		ssLinkedListElement lle = alloc_lle(o);

		if (first == null) {
			first = last = lle;
		} else {
			last.next = lle;
			lle.prev = last;
			last = lle;
		}
		num_in_list++;
	}
}
