package com.generics;

import java.util.ArrayList;
import java.util.List;

public interface Collection<E> {
	ArrayList<String> n = new ArrayList<String>();
	public boolean containsAll(Collection<?> c);

	public boolean addAll(Collection<? extends E> c);
}

interface Collection1<E> {
	public <T> boolean containsAll(Collection1<T> c);
	public <T extends E> boolean addAll(Collection1<T> c);
	// hey, type variables can have bounds too!
}

class Collections {
	static List<List<? extends String>> history =
		new ArrayList<List<? extends String>>();
	public static <T,X> void copy(List<T> dest, Collection<X> un,List<? extends T> src){

	
	}
	public void drawAll(List<? extends String> shapes) {
//		history.addLast(shapes);
//		for (Shape s: shapes) {
//		s.draw(this);
//		}
		}		
}
