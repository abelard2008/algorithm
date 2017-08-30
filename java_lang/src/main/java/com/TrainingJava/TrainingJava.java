package com.TrainingJava;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class TrainingJava {
	private static final List<byte[]> fileObject = new ArrayList<byte[]>();
	private static int fileLen = 0;
	private static Logger LOG = Logger.getLogger(TrainingJava.class);
	
	public TrainingJava(){
		LOG.info("1111 hello this is a log");
		//LOG.error("this is a error");
		
	}
	private static void test(byte[] bytes)
	{
		fileObject.add(bytes);
		System.out.println("fileObject len  " + fileObject.size());
		//System.out.println("contents is " + fileObject. .toString());
	}
	public static void main(String [] args)throws Exception,BufferUnderflowException{
		// Write a file:
		FileChannel fc =
		new FileOutputStream("dataxx.txt").getChannel();
        fc.write(ByteBuffer.wrap("some testes is right".getBytes()));
		fc.close();

		LOG.info("ddddd hello this is a log");
		
		FileChannel fc2 = new FileInputStream("dataxx.txt").getChannel();
		byte[] res = new byte[(int)fc2.size()];
		ByteBuffer dst = ByteBuffer.allocate((int)fc2.size());  
		fc2.read(dst);
		//dst.flip();
		dst.clear();
		System.out.println("dont know 1111 " + dst.remaining());
		
		
		byte[] bytes = new byte[dst.capacity()];
		//fc.position(0);
		System.out.println("dont know 2222  " + dst.remaining());
		try{
		dst.get(bytes, 0, bytes.length);}
		catch(BufferUnderflowException e){ }
		dst.flip();
		while(dst.hasRemaining())
		System.out.print((char)dst.get());
		fc2.close();
		System.out.println(" res  "+ bytes.length);
/*		
		bytes = "Some text I know it".getBytes();
		System.out.println("string "+ bytes.toString());
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		fc =
			new FileOutputStream("dataxx.txt").getChannel();
		System.out.println(" res  333 "+ fc.size());
		fc.write(buf);
		//fc.write(ByteBuffer.wrap("Some text ".getBytes()));
		fc.close(); */
		/*
		fc = new FileOutputStream("data.txt").getChannel();
		//fc.write(ByteBuffer.wrap("Some text ".getBytes()));
		fc.write(dst);
		fc.close();
fc =	new FileInputStream("data.txt").getChannel();
System.out.println("fc  "+ (int)fc.size());
//byte[] res1 = new byte[(int)fc.size()];
ByteBuffer dst1 = ByteBuffer.allocate((int)fc.size());  

//fc = new FileInputStream("data.txt").getChannel();
fc.read(dst1);
dst1.flip();
while(dst1.hasRemaining())
System.out.print((char)dst1.get());
		//dst.flip();
		//while(dst.hasRemaining())
		//System.out.print((char)dst.get());
		System.out.println();
		String a = new String("hello world");
		String b = new String("how are you doing!");
		byte[] bytes = a.getBytes();
		
		System.out.println("hello world  " + bytes.length);
		System.out.println("hello world  " + b.getBytes().length);
		test(a.getBytes());
		test(b.getBytes());
		*/
		
	}

}
