package com.generics;

public class Main {

	static Integer ok = LoadConfigs.LoadConfig();
	
	tuples<Integer,Integer> a =new tuples<Integer,Integer>("name", 47,82);
	public static void main(String Args[]) throws Exception, Throwable {
		Transition.anonymous(LoadConfigs.class).transition(State.list());
		System.out.println("peng " + ok);
	}
}
