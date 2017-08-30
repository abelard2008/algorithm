package com.dillon.anonymous;
public class AssingableTest {
	static String x;
	public static void main(String[] args) {
		Integer ok[] = new Integer[0];
		Integer yes[] = new Integer[9];
		
		ok = yes;
		
		ok[4] = 20;
		Class<?> parent = java.io.InputStream.class;
		Class<?> child = java.io.FileInputStream.class;
		x = "pengcz";
		System.out.println("ok's size " + ok.length + ok[4]);
		System.out.println(child.isAssignableFrom(child));
	}
}//output

