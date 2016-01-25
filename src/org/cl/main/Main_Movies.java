package org.cl.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.cl.conf.Config;
import org.cl.model.Movie;
import org.cl.parser.Parser_Movie;
import org.cl.service.GetInfo;
import org.cl.service.HttpRequest;
import org.cl.service.SaveRecord;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Main_Movies {
	public static void main(String args[]) throws IOException{
		HttpRequest hr = new HttpRequest();
		List<String> id_list = new ArrayList<String>();
		GetInfo.getList(Config.ROOT_PATH+"\\MovieId.txt", id_list, true);
		
		File resFile=new File(Config.ROOT_PATH+"\\MovieInfo.txt");//结果文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(resFile,true));
		for(String id : id_list){
			HtmlPage page = hr.getPage("http://movie.douban.com/subject/"+id+"/?from=showing",id);
			if(page==null){continue;}
			System.out.println("Getting movie info of "+id);
			Movie movie = Parser_Movie.parse(page);
			movie.setId(id);
			JSONObject jsonobj=JSONObject.fromObject(movie);
			bw.write(jsonobj.toString()+"\r\n");
		}
		bw.flush();
		bw.close();
		SaveRecord.close();
	}
}
