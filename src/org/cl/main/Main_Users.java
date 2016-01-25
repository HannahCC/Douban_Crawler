package org.cl.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.cl.conf.Config;
import org.cl.model.User;
import org.cl.service.GetInfo;
import org.cl.service.HttpRequest;
import org.cl.service.SaveRecord;

public class Main_Users {
	public static void main(String args[]) throws Exception{
		HttpRequest hr = new HttpRequest();			    		
		List<String> id_list = new ArrayList<String>();
		GetInfo.getList(Config.ROOT_PATH+"UserID.txt", id_list, true);			
		File resFile=new File(Config.ROOT_PATH+"UserInfo.txt");//用户信息结果文件
		if(resFile.exists()){
			id_list = FromNext(id_list,resFile);//已经爬过的数据不再重新爬，从下一个ID开始
		}		
		BufferedWriter bw = new BufferedWriter(new FileWriter(resFile,true));
		for(String id : id_list){
			String jsonRep = hr.getJSON("https://api.douban.com/v2/user/"+id);
			JSONObject jsonobj=JSONObject.fromObject(jsonRep);//从api获得的用户信息
			User user = (User) JSONObject.toBean(jsonobj, User.class);//用户的基本信息			
			jsonobj=JSONObject.fromObject(user);
			bw.write(jsonobj.toString()+"\r\n");
		}
		bw.flush();
		bw.close();
		SaveRecord.close();
	}	
	
	public static List<String> FromNext(List<String> id_list,File resFile) throws IOException{

		InputStreamReader read = new InputStreamReader(new FileInputStream(resFile));
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		String userID = null;
		String userUID = null;
		while((lineTxt = bufferedReader.readLine()) != null){
			JSONObject json = JSONObject.fromObject(lineTxt);
			userID = json.getString("id");	
			userUID = json.getString("uid");	
			id_list.remove(userID);
			id_list.remove(userUID);
		}
		bufferedReader.close();
		return id_list;
	}

}
