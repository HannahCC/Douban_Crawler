package org.cl.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cl.conf.Config;
import org.cl.model.Comment;
import org.cl.parser.Parser_Comment;
import org.cl.service.GetInfo;
import org.cl.service.HttpRequest;
import org.cl.service.SaveInfo;
import org.cl.service.SaveRecord;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Main_Comments {
	public static void main(String args[]) throws IOException{
		HttpRequest hr = new HttpRequest();
		List<String> id_list = new ArrayList<String>();
		GetInfo.getList(Config.ROOT_PATH+"\\MovieId.txt", id_list, true);
		SaveInfo.mkdir(Config.ROOT_PATH+"\\Comments");
		//GetInfo.idfilter_dirId(id_list, "\\Comments");

		for(String id : id_list){
			if(id.equals("25710912"))continue;
			int number = 0;
			int max = 0;
			System.out.println("Getting comment info of "+id);
			String url = "http://movie.douban.com/subject/"+id+"/comments?sort=new_score";
			do{
				HtmlPage page = hr.getPage(url,id);
				max = Parser_Comment.getTrueCommentMax(page);
				if(page==null){continue;}
				try{
					List<Comment> comment_list = Parser_Comment.parse(page);
					save(id,comment_list);
					number+=comment_list.size();
					url = Parser_Comment.getNextUrl(id, page);
				}catch (Exception e){
					SaveRecord.saveError(page.asXml());
					page=null;
				}
			}while(number<Config.COMMENT_MAX&&Parser_Comment.hasMore(url,max));			
		}
		SaveRecord.close();
	}

	private static void save(String id, List<Comment> comment_list) throws IOException {
		File resFile=new File(Config.ROOT_PATH+"\\Comments\\"+id+".txt");//结果文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(resFile,true));
		JSONArray comments_json=JSONArray.fromObject(comment_list);
		for(int i=0;i<comments_json.size();i++){
			JSONObject  comment = comments_json.getJSONObject(i);
			bw.write(comment.toString()+"\r\n");
		}
		bw.flush();
		bw.close();
	}
	
	
}
