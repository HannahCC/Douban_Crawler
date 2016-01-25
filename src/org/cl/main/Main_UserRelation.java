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
import org.cl.model.UserRelation;
import org.cl.parser.Parser_UserFollower;
import org.cl.parser.Parser_UserFriends;
import org.cl.service.GetInfo;
import org.cl.service.HttpRequest;
import org.cl.service.SaveRecord;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Main_UserRelation {

	public static void main(String[] args) throws IOException{
		HttpRequest hr = new HttpRequest(true);//为True表示，创建wc后都要先登录豆瓣，爬取用户关系必须得登录	
		List<String> id_list = new ArrayList<String>();
		GetInfo.getList(Config.ROOT_PATH+"UserID.txt", id_list, true);			
		File resFile=new File(Config.ROOT_PATH+"UserRealtion.txt");//用户关系结果文件
		if(resFile.exists()){
			id_list = FromNext(id_list,resFile);//已经爬过的数据不再重新爬，从下一个ID开始
		}		
		BufferedWriter bw = new BufferedWriter(new FileWriter(resFile,true));
		for(String id : id_list){
			try{
				UserRelation userRelation = new UserRelation(id);

				//关注人数及关注人列表
				//System.out.println("Getting userFriend info of "+id);
				HtmlPage friendPage = hr.getPage("http://www.douban.com/people/"+id+"/contacts",id);
				if(friendPage==null){continue;}
				Parser_UserFriends.parse(friendPage,userRelation);

				//粉丝人数及粉丝列表
				//System.out.println("Getting userFollower info of "+id);			
				String url = "http://www.douban.com/people/" + id + "/rev_contacts";
				HtmlPage followersPage = hr.getPage(url,id);
				if(followersPage==null){continue;}
				int followerCount = Parser_UserFollower.parse_followerCount(followersPage, userRelation);
				if(followerCount != 0){
					Parser_UserFollower.parse_followersID(followersPage, userRelation);	
				}
				url = Parser_UserFollower.getNextUrl(id, followersPage);

				while(url!=null&&userRelation.getFollowersID().size()<Config.FRIEND_MAX){
					followersPage = hr.getPage(url,id);
					if(followersPage == null){continue;}
					Parser_UserFollower.parse_followersID(followersPage, userRelation);											
					url = Parser_UserFollower.getNextUrl(id, followersPage);
				}

				JSONObject jsonobj = JSONObject.fromObject(userRelation);
				bw.write(jsonobj.toString() + "\r\n");
				bw.flush();	
			} catch (Exception e){
				SaveRecord.saveError(id+"----"+e.getMessage());
			}
		}			
		bw.close();
		SaveRecord.close();
	}	

	public static List<String> FromNext(List<String> id_list,File resFile) throws IOException{

		InputStreamReader read = new InputStreamReader(new FileInputStream(resFile));
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		String userUID = null;
		while((lineTxt = bufferedReader.readLine()) != null){
			JSONObject json = JSONObject.fromObject(lineTxt);	
			userUID = json.getString("uid");	
			id_list.remove(userUID);
		}
		bufferedReader.close();
		return id_list;
	}

}