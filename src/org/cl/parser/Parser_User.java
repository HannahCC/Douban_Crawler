package org.cl.parser;

import org.cl.model.User;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Parser_User {//无用，可删

	public static User parse(HtmlPage page) {		
		User user = new User();
		return user;
//		DomElement content = page.getElementById("db-usr-profile");
//		List<HtmlHeading1> heading_node = (List<HtmlHeading1>) content.getByXPath(".//h1");
//		//姓名
//		String name = heading_node.get(0).asText().split("\r\n")[0];
//		user.setName(name);
//		//个性签名
//		String signature = heading_node.get(0).asText().split("\r\n")[1];
//		user.setSignature(signature);
//		content = page.getElementById("profile");
//		//常居地
//		List<HtmlAnchor> userInfo_node = (List<HtmlAnchor>) content.getByXPath(".//a");
//		String location = userInfo_node.get(0).asText();
//		user.setLocation(location);
//		//用户ID
//		List<HtmlDivision> joinTime_node = (List<HtmlDivision>) content.getByXPath(".//div[@class='pl']");
//		String ID = joinTime_node.get(0).asText().split("\r\n")[0];
//		user.setID(ID);
//		//加入时间
//		String joinTime = joinTime_node.get(0).asText().split("\r\n")[1];
//		user.setJoinTime(joinTime);
//		//自我介绍
//		content = page.getElementById("intro_display");
//		String intro = content.asText();
//		user.setIntro(intro);
//		//关注人数
//		content = page.getElementById("friend");
//		List<HtmlAnchor> friendCount_node = (List<HtmlAnchor>) content.getByXPath(".//a");
//		int friendCount = Integer.parseInt(friendCount_node.get(0).asText().split("员")[1]);
//		user.setFriendCount(friendCount);
//		//收藏的书的ID
//		// https://api.douban.com/v2/book/user/audreyang/collections
////	    HttpRequest hr = new HttpRequest();				
////		HtmlPage bookCollectionPage = hr.getPage("https://api.douban.com/v2/book/user/"+id+"/collections");	
////		List<String> booksDoID = Parser_UserBook.parse(page);		
//		//粉丝人数
//		content = page.getElementById("content");
//		List<HtmlAnchor> followerCount_node = (List<HtmlAnchor>) content.getByXPath(".//p[@class='rev-link']//a");
//		int followerCount = Integer.parseInt(followerCount_node.get(0).asText().split("被")[1].split("人")[0]);
//		user.setFollowerCount(followerCount);	
	}
}
