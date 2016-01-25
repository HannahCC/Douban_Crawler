package org.cl.dp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cl.conf.Config;

public class cleanIP {

	public static void main(String[] args) throws IOException {//将IP.txt中request_counts小于10的IP去掉
		// TODO Auto-generated method stub	
		int count_max = 0;
		//获取IP地址
		File f = new File(Config.ROOT_PATH+"IP_new.txt");
		File f_IP = new File(Config.ROOT_PATH+"IP.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f_IP,false));
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			String usr = "";
			List<String> PROXY = new ArrayList<String>();
			List<Integer> PROT = new ArrayList<Integer>();
			List<Integer> request_counts = new ArrayList<Integer>();
			while((usr = r.readLine())!= null){
				PROXY.add(usr.split("\t")[0]);
				PROT.add(Integer.parseInt(usr.split("\t")[1]));
				request_counts.add(Integer.parseInt(usr.split("\t")[2]));
				count_max++;
			}
			r.close();
			Set<String> ip_set = new HashSet<String>();
			for(int count = 0;count < count_max;count++){
				if(request_counts.get(count) > 10 && (!ip_set.contains(PROXY.get(count)))){					
					bw.write(PROXY.get(count) + "\t" + PROT.get(count).toString() + "\r\n");
					ip_set.add(PROXY.get(count));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bw.flush();
		bw.close();
	}
}
