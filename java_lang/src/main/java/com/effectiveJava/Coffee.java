package com.effectiveJava;

public class Coffee implements Cloneable{
	private int volume;
	Coffee(int volume){
		System.out.println("Coffee ctor called");
		this.volume = volume;
	}
	
	public Object clone(){
		try {
			System.out.println("Coffee clone called");
			return super.clone();
		}catch(CloneNotSupportedException e){
			throw new InternalError(e.toString());
		}
	}
	public void add(int amount){
		volume += amount;
	}
	
	public int remove(int amount){
		System.out.println("1 remove called " + volume + " " + amount);
		int v = amount;
		if(volume < amount)
			v = volume;
		volume -= v;
		System.out.println("2 remove called " + volume + " " + amount);

		return v;
	}
	
	public int removeAll(){
		int all = volume;
		volume = 0;
		return all;
	}
}
