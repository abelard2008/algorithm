package com.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
//@Deprecated
class resourcesTest{
public static void main(String arg[]){
	
    Enumeration<URL> p1;
    URI u = null;

  try {
	p1 = Thread.currentThread( ).getContextClassLoader( ).getResources( "com.eucalyptus.CloudServiceProvider" );
      if ( !p1.hasMoreElements( ) ) System.out.println("gggg nothing");
      else System.out.println("gggg anything");
      while ( p1.hasMoreElements( ) ){
      u = p1.nextElement( ).toURI( );
      System.out.println("gggg pengcz " + u.toURL( ).openStream( ));	
      //  LOG.debug("jialq " + stage.name( ).toString());
        Properties props = new Properties( );
        props.load( u.toURL( ).openStream( ) );
        String name = props.getProperty( "euca.url.local" );
        System.out.println("gggg pengcz " + name);}		
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	  	
	System.out.println("Just for testing resouces");
}
}