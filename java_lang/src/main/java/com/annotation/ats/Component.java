package com.annotation.ats;

import org.apache.log4j.Logger;

public enum Component {
  bootstrap( false, true, false ),
  component( false, true, false ),
  eucalyptus( true, false, true ),
  walrus( true, false, false ),
  dns( true, false, true ),
  storage( true, false, false ),
  db( false, false, true ),
  jettyxy( true, false, true ),
  configuration( true, false, true ),
  cluster( false, false, false ),
  any( false, true, false );

  /**
   * @note is a sub-service of {@link Component.eucalyptus}
   */
  private final Boolean cloudLocal;
  /**
   * @note should have a dispatcher built
   */
  private final Boolean hasDispatcher;
  /**
   * @note always load the service locally
   */
  private final Boolean alwaysLocal;
  
  private Component( Boolean hasDispatcher, Boolean alwaysLocal, Boolean cloudLocal ) {
	    this.alwaysLocal = alwaysLocal;
	    this.hasDispatcher = hasDispatcher;
	    this.cloudLocal = cloudLocal;
	  }  
}