package org.cl.model;

import java.util.ArrayList;
import java.util.List;

public class UserRelation {

	private String uid = "";		
	private int friendCount = 0;     //关注人数
	private int followerCount = 0;   //粉丝人数	
	private List<String> friendsID;    //关注人的ID列表
	private List<String> followersID;  //粉丝ID列表

	public UserRelation(String uid) {
		this.uid = uid;
		friendsID = new ArrayList<String>();
		followersID = new ArrayList<String>(); 
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getFriendCount() {
		return friendCount;
	}
	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}
	public int getFollowerCount() {
		return followerCount;
	}
	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	public List<String> getFriendsID() {
		return friendsID;
	}
	public void setFriendsID(List<String> friendsID) {
		this.friendsID = friendsID;
	}
	public List<String> getFollowersID() {
		return followersID;
	}
	public void setFollowersID(List<String> followersID) {
		this.followersID = followersID;
	}

}
