package com.effectiveJava;

import java.util.Date;

class Super {
	public Super(){
		System.out.println("Super CTOR");
		overrideMe();
		overrideMe2();

	}
	
	public void overrideMe(){
		System.out.println("Super overrideme");
	}
	public void overrideMe2(){
		System.out.println("Super overrideme 2");
	}
}
public final class Sub extends Super{
	private final Date date;
	
	
	Sub(){
		System.out.println("Sub CTOR");
		date = new Date();
	}
	
	@Override
	public void overrideMe(){
		System.out.println("times " + date);
	}
	
	public static void main(String[] args){
		Sub sub = new Sub();
		System.out.println("Blank");
		sub.overrideMe();
		
		int x = 4;
		int y = 5;
		if(x++>4 & ++y>5){
			x++;
		}
		System.out.println("ooox=" + x + ",y="+y);
		
		float a = (float) 3.5;
		int b = (int)a+2;
		System.out.println(b);
	}
}
