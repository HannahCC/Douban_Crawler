package org.cl.dp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.cl.conf.Config;
import org.cl.service.GetInfo;
import org.cl.service.SaveRecord;

public class Main_getUserID {

	public static void main(String args[]) throws IOException{

		List<String> id_list = new ArrayList<String>();
		GetInfo.getList(Config.ROOT_PATH+"\\MovieId.txt", id_list, true);
		Set<String> userID_set = new HashSet<String>();
		for(String id : id_list){
			userID_set = GetUserID(id,userID_set);//从电影评论文件中获得用户名ID，将多个电影评论文件中的用户名ID存入一个set中
		}
		WriterUserID(userID_set);//将用户名ID写入结果文件中
		SaveRecord.close();
	}

	private static Set<String> GetUserID(String MovieID,Set<String> userID_set) throws IOException{	 
		String CommentFilePath = Config.ROOT_PATH+"\\Comments\\" + MovieID + ".txt";//电影评论文件路径
		File CommentFile = new File(CommentFilePath);
		InputStreamReader read = new InputStreamReader(new FileInputStream(CommentFile));
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		while((lineTxt = bufferedReader.readLine()) != null){
			JSONObject json = JSONObject.fromObject(lineTxt);
			String userID = json.getString("user_id");
			userID_set.add(userID);
		}
		bufferedReader.close();
		return userID_set;
	}

	private static void WriterUserID(Set<String> userID_set) throws IOException{	 		
		File UserIDFile=new File(Config.ROOT_PATH  + "UserID.txt");//用户名ID结果文件
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(UserIDFile,true));
		Iterator<String> iterator = userID_set.iterator();
		do{
			bufferedWriter.write(iterator.next().toString());
			bufferedWriter.newLine();
		}while(iterator.hasNext());	
		bufferedWriter.close();	  
	}
}
