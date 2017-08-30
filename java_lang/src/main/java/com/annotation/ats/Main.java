package com.annotation.ats;

public class Main {
    static Bootstrap bootstrap = new HttpBootstrap();
    static WalrusBootstrap bs1 =new WalrusBootstrap();
    
    public static void trackUseCases(Class<?> cl)
    
    {
    	
   		System.out.println("name is 1111 " + cl.getAnnotation(Provides.class).value().name() );

    }
	public static void main(String args[])
	{
		trackUseCases(HttpBootstrap.class);

		Provides p = Ats.from( bootstrap ).get( Provides.class );
		System.out.println("name is " + p.value().name() );
	}
}
