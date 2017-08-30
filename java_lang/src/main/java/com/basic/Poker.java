package com.basic;

public class Poker {

	private static String[] suites = {"黑桃","紅桃","草花","方塊"};
	private static int[] faces = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	
	private Card[] cards;
	
	public Poker(){
		
		cards = new Card[52];
		for(int i = 0; i < suites.length; i++){
			for(int j = 0; j < faces.length; j++) {
			cards[i * 13 + j] = new Card(suites[i], faces[j]);
			}
		}
	}
	
	public void shuffle(){
		for(int i = 0, len = cards.length; i < len; i++){
			int index = (int)(Math.random() * len);
			Card temp = cards[index];
			cards[index] = cards[i];
			cards[i] = temp;
		}
	}
	
	public Card deal(int index){
		
		return cards[index];
	}
	public class Card {
		private String suite;
		private int face;
		
		public Card(String suite, int face){
			this.suite = suite;
			this.face = face;
		}
		
		public String toString(){
			
			String faceStr = "";
			switch(face){
			case 1: faceStr = "A"; break;
			case 11: faceStr = "J"; break;
			case 12: faceStr = "Q"; break;
			case 13: faceStr = "K"; break;
			default: faceStr = String.valueOf(face);
			}
			
			return suite + faceStr;
		}
	}
}
