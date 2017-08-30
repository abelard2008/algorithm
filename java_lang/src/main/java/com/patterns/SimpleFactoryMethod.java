package com.patterns;

interface SimpleFactory{
	void method1();
	void method2();
}

class Implementation1Factory implements SimpleFactory{
	 // Implementation1Factor(){}
	public void method1(){System.out.println("Implementation1Factory method1");}
	public void method2(){System.out.println("Implementation1Factory method2");}
}
class Implementation2Factory implements SimpleFactory{
	 // Implementation2Factor(){}
	public void method1(){System.out.println("Implementation2Factory method1");}
	public void method2(){System.out.println("Implementation2Factory method2");}
}

public class SimpleFactoryMethod {
	
	public static void produceThings(SimpleFactory sf){
		sf.method1();
		sf.method2();
		
	}
	public static void main(String[] args){
		produceThings(new Implementation1Factory());
		produceThings(new Implementation2Factory());
		
	}
}
