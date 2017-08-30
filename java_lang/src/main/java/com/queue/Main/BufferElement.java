package com.queue.Main;

import com.queue.api.QueueElementIF;

public class BufferElement implements QueueElementIF {
    public String name;
    public int StudNo;
    public void SetName(String a){
    	this.name = a;
    }
}
