package com.generics;

public class LoadConfigs extends BootstrapTransition<State>{

	  public LoadConfigs( ) {
		    super( "load-configurations", State.DISABLED, State.PRIMORDIAL );
		  }	
	  
	  public static <T> T LoadConfig( ) {
		   return (T)(new Integer(100));
		  }	
	
}
