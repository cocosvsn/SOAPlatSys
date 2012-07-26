package com.cbs.cbsmgr.common;
import java.io.*;

public class RandomAccessFileTest {
	public static  void readFile(){
		try {
			RandomAccessFile rf = new RandomAccessFile("d:/temp/b.txt","r");
			
//			String s = rf.readUTF();
//			System.out.println(s);
			
			long pointer = 0;
			long len = rf.length();
			
			while(pointer<len){
				String s = rf.readLine();
				System.out.println("s=="+s);
				
				pointer = rf.getFilePointer();
			}
			
			
			rf.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void writeFile(){
		try {
			RandomAccessFile rf = new RandomAccessFile("d:/temp/b.txt","rw");
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("11,11,11\n");
			buffer.append("22,22,22");
			
			rf.writeUTF(buffer.toString());
			
			rf.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args) {
		writeFile();
		readFile();
	}
}
