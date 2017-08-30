package com.queue.Main;

import com.queue.api.SinkIF;

public class Main {
	private static BufferElement students[];
	private static FinitQueue stud = new FinitQueue("student");
	private static SinkIF StudIF;
	
	public static void main(String args[]){
		System.out.println("hello world Queue");
		students  = new BufferElement[5];
    	StudIF = (SinkIF)stud;
        for(int i = 0; i < 5; i++){
        	students[i]  = new BufferElement();
        	
        	students[i].SetName(new String("pengcz" + i));
        	students[i].StudNo = 2009100098 + i;
            StudIF.enqueue(students[i]);
             }
        System.out.println("end");
	}
	

}
