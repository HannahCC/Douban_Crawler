package org.cl.parser;

import java.util.ArrayList;
import java.util.List;
import org.cl.model.Movie;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlStrong;

public class Parser_Movie {

	@SuppressWarnings("unchecked")
	public static Movie parse(HtmlPage page) {
		Movie movie = new Movie();
		
		DomElement content = page.getElementById("content");
		List<HtmlSpan> name_node = (List<HtmlSpan>) content.getByXPath(".//span[@property='v:itemreviewed']");
		String name = name_node.get(0).asText();
		movie.setName(name);
		//基本信息
		DomElement info = page.getElementById("info");
		List<HtmlSpan> info_nodes = (List<HtmlSpan>) info.getByXPath(".//span[@class='pl']");
		String director = info_nodes.get(0).getNextSibling().getNextSibling().asText();
		movie.setDirector(director);
		String writer = info_nodes.get(1).getNextSibling().getNextSibling().asText();
		movie.setWriter(writer);
		String stars = info_nodes.get(2).getNextSibling().getNextSibling().asText();
		movie.setStars(stars);
		String country = info_nodes.get(4).getNextSibling().asText();
		movie.setCountry(country);
		String lang = info_nodes.get(5).getNextSibling().asText();
		movie.setLang(lang);
		String releaseDate = info_nodes.get(6).getNextSibling().getNextSibling().asText();
		movie.setReleaseDate(releaseDate);
		String timespan = info_nodes.get(7).getNextSibling().getNextSibling().asText();
		movie.setTimespan(timespan);
		String alias = info_nodes.get(8).getNextSibling().asText();
		movie.setAlias(alias);
		HtmlAnchor IMDB_node = (HtmlAnchor) info_nodes.get(9).getNextSibling().getNextSibling();
		String IMDBurl = IMDB_node.getAttribute("href");
		movie.setIMDBurl(IMDBurl);
		List<HtmlSpan> type_nodes = (List<HtmlSpan>) info.getByXPath(".//span[@property='v:genre']");
		String type = "";
		for(HtmlSpan type_node:type_nodes){
			type+=type_node.asText()+" / ";
		}
		movie.setType(type.substring(0, type.length()-3));
		//评分信息
		DomElement interest = page.getElementById("interest_sectl");
		List<HtmlStrong> rating_nodes = (List<HtmlStrong>) interest.getByXPath(".//strong[@property='v:average']");
		float rating = Float.parseFloat(rating_nodes.get(0).asText());
		movie.setRating(rating);
		List<HtmlDivision> star_nodes = (List<HtmlDivision>) interest.getByXPath(".//div[@class='power']");
		float _5star = Float.parseFloat(star_nodes.get(0).getNextSibling().asText().replace("%", ""));
		movie.set_5star(_5star);
		float _4star = Float.parseFloat(star_nodes.get(1).getNextSibling().asText().replace("%", ""));
		movie.set_4star(_4star);
		float _3star = Float.parseFloat(star_nodes.get(2).getNextSibling().asText().replace("%", ""));
		movie.set_3star(_3star);
		float _2star = Float.parseFloat(star_nodes.get(3).getNextSibling().asText().replace("%", ""));
		movie.set_2star(_2star);
		float _1star = Float.parseFloat(star_nodes.get(4).getNextSibling().asText().replace("%", ""));
		movie.set_1star(_1star);
		List<HtmlSpan> start_comment_nodes = (List<HtmlSpan>) interest.getByXPath(".//span[@property='v:votes']");
		int star_comment = Integer.parseInt(start_comment_nodes.get(0).asText());
		movie.setStar_comment(star_comment);
		//内容简介
		String description= page.getElementById("link-report").asText();
		movie.setDescription(description);
		//评论
		DomElement comment_node = page.getElementById("comments-section");
		info_nodes = (List<HtmlSpan>) comment_node.getByXPath(".//span[@class='pl']");
		int text_comment = Integer.parseInt(info_nodes.get(0).asText().split("\\s+")[2]);
		movie.setText_comment(text_comment);
		//推荐同类电影
		DomElement recomment_node = page.getElementById("recommendations");
		List<HtmlAnchor> movie_nodes = (List<HtmlAnchor>) recomment_node.getByXPath(".//dt//a");
		List<String> sim_movies = new ArrayList<String>();
		for(HtmlAnchor sim_movie : movie_nodes){
			sim_movies.add(sim_movie.getAttribute("href").split("/")[4]);
		}
		movie.setSim_movie(sim_movies);
		//标签
		List<HtmlDivision> tag_body_nodes = (List<HtmlDivision>) content.getByXPath(".//div[@class='tags-body']");
		List<HtmlAnchor> tag_nodes = (List<HtmlAnchor>) tag_body_nodes.get(0).getByXPath(".//a");
		List<String> tags = new ArrayList<String>();
		for(HtmlAnchor tag_node : tag_nodes){
			tags.add(tag_node.asText());
		}
		movie.setTags(tags);
		//想看和看过的人数
		List<HtmlDivision> interest_nodes = (List<HtmlDivision>) content.getByXPath(".//div[@class='subject-others-interests-ft']");
		List<HtmlAnchor> anchor_nodes = (List<HtmlAnchor>) interest_nodes.get(0).getByXPath(".//a");
		int wanted = Integer.parseInt(anchor_nodes.get(0).asText().split("人")[0]);
		movie.setWanted(wanted);
		int saw = Integer.parseInt(anchor_nodes.get(1).asText().split("人")[0]);
		movie.setSaw(saw);
		return movie;
	}

}
