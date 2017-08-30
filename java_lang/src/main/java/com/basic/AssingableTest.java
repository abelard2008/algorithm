package com.basic;
public class AssingableTest {
	static String x;
	public static void main(String[] args) {
		Integer f1 = 100, f2 = 100, f3 = 100, f4 = 100;
		
		Integer ok[] = new Integer[0];
		Integer yes[] = new Integer[9];
		
		ok = yes;
		System.out.println(f3 == f4);
		System.out.println(f1 == f3);
		ok[4] = 20;
		
		System.out.println("question 17:");
		String s1 = "Programming";
		String s2 = new String("Programming");
		String s3 = "Program" + "ming";
		
		System.out.println(s1 == s2);
		System.out.println(s1 == s3 );
		System.out.println(s1 == s1.intern());
		Class<?> parent = java.io.InputStream.class;
		Class<?> child = java.io.FileInputStream.class;
		x = "pengcz";
		System.out.println("ok's size " + ok.length + ok[4]);
		System.out.println(child.isAssignableFrom(child));
	}
}//output

