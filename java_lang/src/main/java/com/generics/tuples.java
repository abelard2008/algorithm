package com.generics;

import org.apache.log4j.Logger;

public class tuples<O, T> implements Comparable<tuples>{
	  private static Logger LOG = Logger.getLogger( Transition.class );
	  private final T       oldState;
	  private final T       newState;
	  private final String  name;
	  
	  /**
	   * @param oldState
	   * @param newState
	   */
	  public tuples( String name, T oldState, T newState ) {
	    this.name = name;
	    this.oldState = oldState;
	    this.newState = newState;
	  }

	@Override
	public int compareTo(tuples o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
