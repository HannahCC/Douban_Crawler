package org.cl.parser;

import java.util.List;

import org.cl.model.UserRelation;
import org.cl.service.SaveRecord;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Parser_UserFriends {
	@SuppressWarnings("unchecked")
	public static void parse(HtmlPage page,UserRelation userRelation){
		
		List<String> friendsID = userRelation.getFriendsID();
		DomElement content = page.getElementById("content");
		//关注人数
		List<HtmlHeading1> friendCount_node = (List<HtmlHeading1>) content.getByXPath(".//h1");
		int friendCount = Integer.parseInt(friendCount_node.get(0).asText().toString().split("\\(")[1].split("\\)")[0]);
		userRelation.setFriendCount(friendCount);
		//关注人的ID列表
		if(friendCount != 0){
			List<HtmlAnchor> friend_nodes = (List<HtmlAnchor>) content.getByXPath(".//dd//a");		
			for(HtmlAnchor friendID : friend_nodes){
				String friId = friendID.getAttribute("href").split("/")[4];
				friendsID.add(friId);
				if(friendID.asText().equals("[已注销]")){
					SaveRecord.saveUserNotExist(friId);
				}			
			}
		}		
	}
}
