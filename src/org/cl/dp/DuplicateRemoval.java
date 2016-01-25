package org.cl.dp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.cl.conf.Config;

import net.sf.json.JSONObject;



public class DuplicateRemoval {
	/**
	 * 对文件去重
	 * 去重后需要手动修改文件名，保证其他程序正常使用
	 */
	public static void main(String args[]) throws IOException{
		userInfo_deduplicate("UserInfo.txt","UserInfo_deduplicated.txt");
	}
	
	private static void userInfo_deduplicate(String src, String res) throws IOException {
		Set<String> id_set = new HashSet<String>();
		File f = new File(Config.ROOT_PATH + src);
		BufferedReader r = new BufferedReader(new FileReader(f));
		File f2 = new File(Config.ROOT_PATH+res);
		BufferedWriter w = new BufferedWriter(new FileWriter(f2));
		String line = null;
		while(null!=(line=r.readLine())){
			if(line.equals(""))continue;
			JSONObject jsonObject = JSONObject.fromObject(line);
			String id = jsonObject.getString("uid");
			if(!id_set.contains(id)){
				id_set.add(id);
				w.write(line+"\r\n");
			}
		}
		r.close();
		w.flush();
		w.close();
	}
}
