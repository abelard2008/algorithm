package com.effectiveJava;


public class CoffeeCup2 implements Cloneable{
	private Coffee innerCoffee = new Coffee(0);
	
	public Object clone(){
		CoffeeCup2 copyCup = null;
		try{
			copyCup = (CoffeeCup2)super.clone();
		}
		catch(CloneNotSupportedException e){
			throw new InternalError(e.toString());
		}
		copyCup.innerCoffee = (Coffee)innerCoffee.clone();
		return copyCup;
	}
	
	public void add(int amount){
		innerCoffee.add(amount);
	}
	
	public int releaseOneSip(int sipSize){
		return innerCoffee.remove(sipSize);
}
	
	public int spillEntireContents(){
		return innerCoffee.removeAll();
	}
	
	public static void main(String[] args){
		CoffeeCup2 original = new CoffeeCup2();
		original.add(75);
		System.out.println("xoxo");
		CoffeeCup2 copy = (CoffeeCup2) original.clone();

		copy.releaseOneSip(25);

		int origAmount = original.spillEntireContents();
		int copyAmount = copy.spillEntireContents();
		
		System.out.println("Original has " + origAmount + "ml of coffee");
		System.out.println("Copy has " + copyAmount + "ml of coffee");
	}
	
}
