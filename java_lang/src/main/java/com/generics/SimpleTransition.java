package com.generics;




public abstract class SimpleTransition<O, T extends Comparable> extends Transition<O, T> {
	
	  public SimpleTransition( String name, T oldState, T newState ) {
			    super( name, oldState, newState );
		  }
}
