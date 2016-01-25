package org.cl.conf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
PRO_NAME = Facebook_Crawler
RES_NAME = Facebook_res
REQUEST_INTERVAL = 2000
REQUEST_MAX = 450
FRIENDS_MAX = 1000
TIMELINE_MAX = 300
COMMENT_MAX = 50
RETRY_MAX = 2
 * @author Chenli
 *
 */
public class Config {
	public static String ROOT_PATH = "D:\\Project_DataMinning\\Data\\Douban_res\\";
	public static int UNIT_SLEEP_TIME = 10000;//millisecond
	public static int SLEEP_TIME = 3600000;//millisecond
	public static int REQUEST_MAX = 5000;//times
	public static int RETRY_MAX = 3;
	public static int COMMENT_MAX = 50000;
	public static int FRIEND_MAX = 500;
	public static int FOLLOWER_MAX = 500;
	public static int INTEREST_MAX = 100;

	public static List<String> PROXY;
	public static List<Integer> PROT;
	public static int PROXY_COUNT = -1;
	public static int PROXY_COUNT_MAX = 0;

	/** 账号设置 **/
	public static List<String> USERNAME;
	public static List<String> PASSWORD;
	public static int COUNT = 0;
	public static int COUNT_MAX = 0;


	static {
		//获取配置
		File f = new File(ROOT_PATH+"Config.txt");
		Map<String,String> confmap = new HashMap<String,String>();
		BufferedReader r;
		try {
			r = new BufferedReader(new FileReader(f));
			String conf = "";
			while((conf = r.readLine())!= null){
				String conf_name = conf.split(" = ")[0];
				String conf_value =  conf.split(" = ")[1];
				confmap.put(conf_name, conf_value);
			}
			if(confmap.containsKey("UNIT_SLEEP_TIME")){UNIT_SLEEP_TIME = Integer.parseInt(confmap.get("UNIT_SLEEP_TIME"));}
			if(confmap.containsKey("SLEEP_TIME")){SLEEP_TIME = Integer.parseInt(confmap.get("SLEEP_TIME"));}
			if(confmap.containsKey("REQUEST_MAX")){REQUEST_MAX = Integer.parseInt(confmap.get("REQUEST_MAX"));}
			if(confmap.containsKey("COMMENT_MAX")){COMMENT_MAX = Integer.parseInt(confmap.get("COMMENT_MAX"));}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getIP();
		getAccount();

	}

	private static void getIP() {

		//获取IP地址
		File f = new File(ROOT_PATH+"IP.txt");
		try {	
			if(f.exists()){
				BufferedReader r = new BufferedReader(new FileReader(f));
				String usr = "";
				PROXY = new ArrayList<String>();
				PROT = new ArrayList<Integer>();
				PROXY.add(""); //添加本机IP
				PROT.add(0);
				PROXY_COUNT_MAX++;
				while((usr = r.readLine())!= null){
					PROXY.add(usr.split("\t")[0]);
					PROT.add(Integer.parseInt(usr.split("\t")[1]));
					PROXY_COUNT_MAX++;
				}
				r.close();
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getAccount(){
		//获取豆瓣账号和密码
		File f=new File(ROOT_PATH+"DoubanAccount.txt");
		try {
			BufferedReader rAccount = new BufferedReader(new FileReader(f));
			String usr = "";
			USERNAME = new ArrayList<String>();
			PASSWORD = new ArrayList<String>();
			while((usr = rAccount.readLine())!= null){
				if (usr.equals("")) {
					break;
				}
				USERNAME.add(usr.split("\t")[0]);
				PASSWORD.add(usr.split("\t")[1]);
				COUNT_MAX++;
			}
			rAccount.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
