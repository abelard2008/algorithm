package com.dillon.anonymous;

public class Parcel7 {
    public Contents contents(){
    	return new Contents(){
    		private int i =1;
    		public int value(){return i;}
    	};
    }
    public static void main(String[] args){
    	Parcel7 p = new Parcel7();
    	Contents c = p.contents();
    }
}
