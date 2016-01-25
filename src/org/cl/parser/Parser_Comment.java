package org.cl.parser;

import java.util.ArrayList;
import java.util.List;

import org.cl.model.Comment;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class Parser_Comment {

	@SuppressWarnings("unchecked")
	public static List<Comment> parse(HtmlPage page) {
		List<Comment> comment_list = new ArrayList<Comment>();
		DomElement comments = page.getElementById("comments");
		List<HtmlDivision> comment_node_list = (List<HtmlDivision>) comments.getByXPath(".//div[@class='comment']");
		for(HtmlDivision comment_node : comment_node_list){
			Comment cm = new Comment();
			//投票
			List<HtmlSpan> span_nodes = (List<HtmlSpan>) comment_node.getByXPath(".//span[@class='votes pr5']");
			int votes = Integer.parseInt(span_nodes.get(0).asText());
			cm.setVotes(votes);
			//评论者
			span_nodes = (List<HtmlSpan>) comment_node.getByXPath(".//span[@class='comment-info']");
			HtmlAnchor user_node = (HtmlAnchor) span_nodes.get(0).getByXPath("./a[1]").get(0);
			String user_id = user_node.getAttribute("href").split("/")[4];
			cm.setUser_id(user_id);
			String user_name = user_node.asText();
			cm.setUser_name(user_name);
			//评分
			List<HtmlSpan> span_nodes2 = (List<HtmlSpan>) span_nodes.get(0).getByXPath("./span");
			int index = 0;
			if(span_nodes2.size()>1){
				int rating = Integer.parseInt(span_nodes2.get(index).getAttribute("class").split("star|0 ")[1]);
				cm.setRating(rating );
				index++;
			}
			//评价时间
			String created_at = span_nodes2.get(index).asText();
			cm.setCreated_at(created_at);
			//评论内容
			List<HtmlParagraph> p_nodes = (List<HtmlParagraph>) comment_node.getByXPath(".//p");
			String text = p_nodes.get(0).asText();
			cm.setText(text);
			comment_list.add(cm);
		}
		return comment_list;
	}
	public static boolean hasMore(String url, int max) {
		int start = Integer.parseInt(url.split("start=|&")[1]);
		if(start>max)return false;
		else return true;
	}
	public static String getNextUrl(String id,HtmlPage page) {
		DomElement paginator = page.getElementById("paginator");
		@SuppressWarnings("unchecked")
		List<HtmlAnchor> next_url = (List<HtmlAnchor>) paginator.getByXPath(".//a[@class='next']");
		String href = "http://movie.douban.com/subject/"+id+"/comments"+next_url.get(0).getAttribute("href");
		System.out.println(href);
		return href;
	}

	public static int getTrueCommentMax(HtmlPage page) {
		DomElement paginator = page.getElementById("paginator");
		@SuppressWarnings("unchecked")
		List<HtmlSpan> span_nodes = (List<HtmlSpan>) paginator.getByXPath(".//span[@class='total']");
		int number = Integer.parseInt(span_nodes.get(0).asText().split("\\s+")[1]);
		return number;
	}
}
