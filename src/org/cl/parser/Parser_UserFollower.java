package org.cl.parser;

import java.util.List;

import org.cl.model.UserRelation;
import org.cl.service.SaveRecord;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Parser_UserFollower {

	public static int parse_followerCount(HtmlPage page,UserRelation userRelation){		
		DomElement content = page.getElementById("content");
		//粉丝人数
		@SuppressWarnings("unchecked")
		List<HtmlHeading1> followerCount_node = (List<HtmlHeading1>) content.getByXPath(".//h1");
		int followerCount = Integer.parseInt(followerCount_node.get(0).asText().split("\\(")[1].split("\\)")[0]);
		userRelation.setFollowerCount(followerCount);
		return followerCount;
	}

	public static void parse_followersID(HtmlPage page,UserRelation userRelation){
		List<String> followersID = userRelation.getFollowersID();	
		DomElement content = page.getElementById("content");	
		//粉丝ID列表
		@SuppressWarnings("unchecked")
		List<HtmlAnchor> follower_nodes = (List<HtmlAnchor>) content.getByXPath(".//dd//a");
		for(HtmlAnchor followerID : follower_nodes){
			String folId = followerID.getAttribute("href").split("/")[4];
			followersID.add(folId);
			if(followerID.asText().equals("[已注销]")){
				SaveRecord.saveUserNotExist(folId);
			}
		}
	}

	public static String getNextUrl(String id,HtmlPage page) {
		DomElement content = page.getElementById("content");
		String href = null;
		@SuppressWarnings("unchecked")
		List<HtmlAnchor> next = (List<HtmlAnchor>) content.getByXPath(".//span[@class='next']//a");		
		if(next.size() != 0){
			href = "http://www.douban.com/" + next.get(0).getAttribute("href");
		}		
		return href;
	}

}
