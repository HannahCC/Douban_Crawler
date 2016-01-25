package org.cl.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class SaveInfo {
	
	public static void saveString(String dirname, String filename, String info,boolean isAppend) throws IOException {
		if(null==info||"".equals(info))return;
		mkdir(dirname);
		File f = new File(dirname+"\\"+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		w.write(info+"\r\n");
		w.flush();
		w.close();
	}
	public static void fileCopy(File src,String des_dir, boolean isDelete) throws IOException{
		FileInputStream fi = new FileInputStream(src);
		FileChannel in = fi.getChannel();
		mkdir(des_dir);	
		File t = new File(des_dir+"\\"+src.getName());
		FileOutputStream fo = new FileOutputStream(t);
		FileChannel out = fo.getChannel();
		in.transferTo(0, in.size(), out);
		fi.close();
		in.close();
		fo.close();
		out.close();
		if(isDelete)src.delete();
	}

	public static void mkdir(String dir) {
		File dir_root1 = new File(dir);
		if(!dir_root1.exists())dir_root1.mkdir();
	}
	public static boolean isFileExist(String filename){
		File dir= new File(filename);
		if(dir.exists()){return true;}
		else{return false;}
	}
	public static void deleteFiles(String filename) {
		// TODO Auto-generated method stub
		File file=new File(filename);
		if(file.exists()){file.delete();}
	}
}
