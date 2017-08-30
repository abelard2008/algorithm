package com.concurrency;

public class LiftOff implements Runnable{
 protected int CountDown = 10;
 private static int taskCount = 0;
 private final int id = taskCount++;
 public LiftOff() {}
 public LiftOff(int countDown){
	 this.CountDown = countDown; 
 }
 
 public String status() {
	 return "#" + id + "(" +
	 (CountDown > 0 ? CountDown : "LiftOff!") + "), ";
 }
 public void run(){
	 while(CountDown-- > 0){
		 System.out.println(status());
		// Thread.yield();
	 }
 }
}
