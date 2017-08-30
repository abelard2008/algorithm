package com.basic;

import java.util.Calendar;

class A {
	static { System.out.println("1");}
	
	public A(){
		System.out.println("2");
	}
	
}

class B extends A{
	static {System.out.println("a");}
	
	public B(){
		System.out.println("b");
	}
}
public class PokerTest {
/*
	public static Integer p  = new Integer(1000);
	public static void main(String[] args){
		System.out.println(PokerTest.p);
		Poker poker = new Poker();
		poker.shuffle();
		Poker.Card c1 = poker.deal(0);
		
		Poker.Card c2 = poker.new Card("紅心", 1);
		
		System.out.println(c1);
		System.out.println(c2);
	}*/
	
	public static void main(String[] args){
		
		String a = "abcdef";
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		System.out.println(a.substring(1));
		A ab = new B();
	}
	
}
