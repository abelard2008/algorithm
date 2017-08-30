package com.TrainingJava;

import java.util.ArrayList;
import java.util.List;

public class getFileName {
	private static final List<byte[]> fileObject = new ArrayList<byte[]>();
	private static int fileLen = 0;

	public static void main(String[] args){
		String   FullFileName   = "/opt/eucalyptus/var/lib/eucalyptus/bukkits/test/fcqinqin.part.1PhL7cA...pDOtGAqI0cdDTKYI"; 
		String   FileName   =   FullFileName.substring(FullFileName.lastIndexOf("/")+1, FullFileName.length());

        System.out.println("FileName " + FileName);		
	}
}
