package com.TrainingJava;

import java.net.*;
import java.io.*;

public class readFromURL{
public static void readFromU()
{
    try
    {
        URL url = new URL("http://www.go4expert.com");
        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        InputStream inStream = connection.getInputStream();
        BufferedReader input =
        new BufferedReader(new InputStreamReader(inStream));

        String line = "";
        while ((line = input.readLine()) != null)
        System.out.println(line);
    }
    catch (Exception e)
    {
        System.out.println(e.toString());
    }
}

public static void main(String [] args)
{
	readFromU();
	}

}