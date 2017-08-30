//package com.serializable;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//import org.junit.Test;
//
//public class SerTest
//{
//    @Test public void serializeToDisk()
//    {
//        try
//        {
//            com.serializable.Person ted = new com.serializable.Person("Ted", "Neward", 39);
//            com.serializable.Person charl = new com.serializable.Person("Charlotte",
//                "Neward", 38);
//
//            ted.setSpouse(charl); charl.setSpouse(ted);
//
//            FileOutputStream fos = new FileOutputStream("tempdata.ser");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(ted);
//            oos.close();
//        }
//        catch (Exception ex)
//        {
//            fail("Exception thrown during test: " + ex.toString());
//        }
//
//        try
//        {
//            FileInputStream fis = new FileInputStream("tempdata.ser");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            com.serializable.Person ted = (com.serializable.Person) ois.readObject();
//            ois.close();
//
//            assertEquals(ted.getFirstName(), "Ted");
//            assertEquals(ted.getSpouse().getFirstName(), "Charlotte");
//
//            // Clean up the file
//            new File("tempdata.ser").delete();
//        }
//        catch (Exception ex)
//        {
//            fail("Exception thrown during test: " + ex.toString());
//        }
//    }
//}
