package com.queue.Main;

import com.queue.api.QueueElementIF;
import com.queue.api.QueueIF;

public class FinitQueue implements QueueIF {
	 private ssLinkedList qlist;
	 private int queueSize;
	 private String name;
	  /** 
	   * Create a FiniteQueue with the given enqueue predicate.
	   */
	public FinitQueue(String name) {
        this.name = name;
	    qlist = new ssLinkedList();
	    queueSize = 0;
	  }
	public void enqueue(QueueElementIF enqueueMe){
		queueSize++;
		qlist.add_to_tail(enqueueMe);
	}
	public void x()
	{}
}
