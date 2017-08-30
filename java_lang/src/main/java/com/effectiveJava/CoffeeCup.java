package com.effectiveJava;


public class CoffeeCup implements Cloneable{
	private int innerCoffee;
	
	public Object clone(){
		try{
			return super.clone();
		}
		catch(CloneNotSupportedException e){
			throw new InternalError(e.toString());
		}
	}
	
	public void add(int amount){
		innerCoffee += amount;
	}
	
	public int releaseOneSip(int sipSize){
		int sip = sipSize;
		if(innerCoffee < sipSize){
			sip = innerCoffee;
		}
		innerCoffee -= sip;
		return sip;
	}
	
	public int spillEntireContents(){
		int all = innerCoffee;
		innerCoffee = 0;
		return all;
	}
	
	public static void main(String[] args){
		CoffeeCup original = new CoffeeCup();
		original.add(75);
		CoffeeCup copy = (CoffeeCup) original.clone();
		copy.releaseOneSip(25);
		
		int origAmount = original.spillEntireContents();
		int copyAmount = copy.spillEntireContents();
		
		System.out.println("Original has " + origAmount + "ml of coffee");
		System.out.println("Copy has " + copyAmount + "ml of coffee");
	}
	
}
