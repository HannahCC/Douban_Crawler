package org.cl.model;

import java.util.List;

public class Movie {

	private String id;						//id
	private String name;					//电影名
	private String director = "";           //导演
	private String writer = "";				//编剧
	private String stars = "";				//主演
	private String type="";               	//电影类型 
	private String country = "";			//产地
	private String lang = "";				//语言
	private	String releaseDate = "";		//上映日期
	private	String timespan = "";			//时长
	private	String Alias = "";				//别名
	private	String IMDBurl = "";			//IMDB
	private float rating = 0;				//豆瓣评分
	private float _5star = 0;				//5星占比
	private float _4star = 0;				//4星占比
	private float _3star = 0;				//3星占比
	private float _2star = 0;				//2星占比
	private float _1star = 0;				//1星占比
	private int star_comment = 0;			//作出星评的人数
	private int text_comment = 0;			//作出短评的人数
	private int wanted = 0;					//想看的人数
	private int saw = 0;					//看过的人数
	private String description;				//电影简介
	private List<String> sim_movie;			//喜欢这部电影的人也喜欢的电影ID
	private List<String> tags;					//用户对该电影常用的标签
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getTimespan() {
		return timespan;
	}
	public void setTimespan(String timespan) {
		this.timespan = timespan;
	}
	public String getAlias() {
		return Alias;
	}
	public void setAlias(String alias) {
		Alias = alias;
	}
	public String getIMDBurl() {
		return IMDBurl;
	}
	public void setIMDBurl(String iMDBurl) {
		IMDBurl = iMDBurl;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public float get_5star() {
		return _5star;
	}
	public void set_5star(float _5star) {
		this._5star = _5star;
	}
	public float get_4star() {
		return _4star;
	}
	public void set_4star(float _4star) {
		this._4star = _4star;
	}
	public float get_3star() {
		return _3star;
	}
	public void set_3star(float _3star) {
		this._3star = _3star;
	}
	public float get_2star() {
		return _2star;
	}
	public void set_2star(float _2star) {
		this._2star = _2star;
	}
	public float get_1star() {
		return _1star;
	}
	public void set_1star(float _1star) {
		this._1star = _1star;
	}
	public int getStar_comment() {
		return star_comment;
	}
	public void setStar_comment(int star_comment) {
		this.star_comment = star_comment;
	}
	public int getText_comment() {
		return text_comment;
	}
	public void setText_comment(int text_comment) {
		this.text_comment = text_comment;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getSim_movie() {
		return sim_movie;
	}
	public void setSim_movie(List<String> sim_movie) {
		this.sim_movie = sim_movie;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public int getWanted() {
		return wanted;
	}
	public void setWanted(int wanted) {
		this.wanted = wanted;
	}
	public int getSaw() {
		return saw;
	}
	public void setSaw(int saw) {
		this.saw = saw;
	}
	
	
	
}
