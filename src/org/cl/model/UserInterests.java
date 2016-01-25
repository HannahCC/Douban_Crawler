package org.cl.model;

import java.util.List;

public class UserInterests {

	private int bookDoCount;     //在读的书的数量（如何判断是否有这一项）
	private int bookWishCount;   //想读的书的数量
	private int bookCollectCount;//读过的书的数量
	private int movieDoCount;    //在看的电影的数量
	private int movieWishCount;  //想看的电影的数量
	private int movieCollectCount;//看过的电影的数量
	private int musicDoCount;    //在听的音乐的数量
	private int musicWishCount;  //想听的音乐的数量
	private int musicCollectCount;//听过的音乐的数量
	private int groupCount;      //常去的小组个数
	private List<String> booksDoID;            //在读的书的ID
	private List<String> booksWishID;          //想读的书的ID
	private List<String> booksCollectID;       //读过的书的ID
	private List<String> moviesDoID;            //在看的电影的ID
	private List<String> moviesWishID;          //想看的电影的ID
	private List<String> moviesCollectID;       //看过的电影的ID
	private List<String> musicsDoID;            //在听的音乐的ID
	private List<String> musicsWishID;          //想听的音乐的ID
	private List<String> musicsCollectID;       //听过的音乐的ID
	private List<String> groupsID;     //常去小组的ID列表
	private List<String> sitesID;          //关注的小站的ID列表
	private List<String> magazinesID;      //看过的杂志的ID列表(大部分用户没有这一项)
	
	public int getBookDoCount() {
		return bookDoCount;
	}
	public void setBookDoCount(int bookDoCount) {
		this.bookDoCount = bookDoCount;
	}
	public int getBookWishCount() {
		return bookWishCount;
	}
	public void setBookWishCount(int bookWishCount) {
		this.bookWishCount = bookWishCount;
	}
	public int getBookCollectCount() {
		return bookCollectCount;
	}
	public void setBookCollectCount(int bookCollectCount) {
		this.bookCollectCount = bookCollectCount;
	}
	public int getMovieDoCount() {
		return movieDoCount;
	}
	public void setMovieDoCount(int movieDoCount) {
		this.movieDoCount = movieDoCount;
	}
	public int getMovieWishCount() {
		return movieWishCount;
	}
	public void setMovieWishCount(int movieWishCount) {
		this.movieWishCount = movieWishCount;
	}
	public int getMovieCollectCount() {
		return movieCollectCount;
	}
	public void setMovieCollectCount(int movieCollectCount) {
		this.movieCollectCount = movieCollectCount;
	}
	public int getMusicDoCount() {
		return musicDoCount;
	}
	public void setMusicDoCount(int musicDoCount) {
		this.musicDoCount = musicDoCount;
	}
	public int getMusicWishCount() {
		return musicWishCount;
	}
	public void setMusicWishCount(int musicWishCount) {
		this.musicWishCount = musicWishCount;
	}
	public int getMusicCollectCount() {
		return musicCollectCount;
	}
	public void setMusicCollectCount(int musicCollectCount) {
		this.musicCollectCount = musicCollectCount;
	}
	public int getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}
	public List<String> getBooksDoID() {
		return booksDoID;
	}
	public void setBooksDoID(List<String> booksDoID) {
		this.booksDoID = booksDoID;
	}
	public List<String> getBooksWishID() {
		return booksWishID;
	}
	public void setBooksWishID(List<String> booksWishID) {
		this.booksWishID = booksWishID;
	}
	public List<String> getBooksCollectID() {
		return booksCollectID;
	}
	public void setBooksCollectID(List<String> booksCollectID) {
		this.booksCollectID = booksCollectID;
	}
	public List<String> getMoviesDoID() {
		return moviesDoID;
	}
	public void setMoviesDoID(List<String> moviesDoID) {
		this.moviesDoID = moviesDoID;
	}
	public List<String> getMoviesWishID() {
		return moviesWishID;
	}
	public void setMoviesWishID(List<String> moviesWishID) {
		this.moviesWishID = moviesWishID;
	}
	public List<String> getMoviesCollectID() {
		return moviesCollectID;
	}
	public void setMoviesCollectID(List<String> moviesCollectID) {
		this.moviesCollectID = moviesCollectID;
	}
	public List<String> getMusicsDoID() {
		return musicsDoID;
	}
	public void setMusicsDoID(List<String> musicsDoID) {
		this.musicsDoID = musicsDoID;
	}
	public List<String> getMusicsWishID() {
		return musicsWishID;
	}
	public void setMusicsWishID(List<String> musicsWishID) {
		this.musicsWishID = musicsWishID;
	}
	public List<String> getMusicsCollectID() {
		return musicsCollectID;
	}
	public void setMusicsCollectID(List<String> musicsCollectID) {
		this.musicsCollectID = musicsCollectID;
	}
	public List<String> getGroupsID() {
		return groupsID;
	}
	public void setGroupsID(List<String> groupsID) {
		this.groupsID = groupsID;
	}
	public List<String> getSitesID() {
		return sitesID;
	}
	public void setSitesID(List<String> sitesID) {
		this.sitesID = sitesID;
	}
	public List<String> getMagazinesID() {
		return magazinesID;
	}
	public void setMagazinesID(List<String> magazinesID) {
		this.magazinesID = magazinesID;
	}

}
