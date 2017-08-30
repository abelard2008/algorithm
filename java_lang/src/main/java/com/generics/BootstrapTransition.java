package com.generics;


public class BootstrapTransition<O> extends SimpleTransition<O, State>{
	  public BootstrapTransition( String name, State oldState, State newState ) {
		    super( name, oldState, newState );
		  }
	public void commit(O Object){}
	@Override
	protected void prepare(O component) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void post(O component) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void rollback(O component) {
		// TODO Auto-generated method stub
		
	}
 }
