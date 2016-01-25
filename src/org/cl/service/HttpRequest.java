package org.cl.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.cl.conf.Config;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HttpRequest {
	/**
	 * 防止因为网络原因而获取网页失败，每次失败后进行重试，最多重试3次 
	 * @param wc
	 * @param href
	 * @return
	 */
	//private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	private WebClient wc = null;
	private int request_counts = 0;
	private boolean isLogin = false;

	public HttpRequest() throws IOException{
		createWC();
	}

	public HttpRequest(boolean isRelogin) throws IOException{
		this.isLogin = isRelogin;
		createWC();
		if(isLogin){
			login();
		}
	}

	private static WebClient LOGIN_WC = null;
	public synchronized static WebClient getLOGIN_LOGIN_WC() {
		return LOGIN_WC;
	}

	public void login() throws IOException{
		slowdown();
		System.out.println("---do login ");
		int retry = 0;
		boolean isLogin =  false;
		try{
			while(retry<Config.RETRY_MAX && isLogin == false ){
				String account = Config.USERNAME.get(Config.COUNT);
				String pwd = Config.PASSWORD.get(Config.COUNT);
				System.out.println(account + ":" + pwd + " logging! "+retry);
				retry++;
				HtmlPage page = getPage("http://www.douban.com/accounts/login","");
				if(page==null){continue;}
				//SaveRecord.saveError(page.asXml());
				//String str = page.asXml();
				//获取登录表单
				HtmlForm login_form = (HtmlForm) page.getElementsByTagName("form").get(0);
				//判断 是否需要输入验证码，如果需要，手动填写验证码之后登录
				HtmlImage captcha_image = (HtmlImage) page.getElementById("captcha_image");
				if(captcha_image != null){
					File f = new File(Config.ROOT_PATH+"captcha_image.jpg");
					captcha_image.saveAs(f);	
					//设置验证码
					System.out.println("please enter captcha: ");
					Scanner in=new Scanner(System.in);
					String captcha = in.next();//输入验证码
					HtmlTextInput captcha_solution = login_form.getInputByName("captcha-solution");
					captcha_solution.setValueAttribute(captcha);
				}				
				//提交登录请求
				HtmlTextInput username = login_form.getInputByName("form_email");
				username.setValueAttribute(account);
				HtmlPasswordInput password = (HtmlPasswordInput) page.getByXPath("//Input[@type='password']").get(0);
				password.setValueAttribute(pwd);	
				HtmlSubmitInput login = login_form.getInputByName("login");
				HtmlPage index = click(login);
				//判断是否登录成功
				//System.out.println(index.asText());
				if(index==null||!index.getTitleText().equals("豆瓣")){
					System.out.println(account+" : "+pwd+"--login fail!");
					if(index != null && index.getUrl().toString().endsWith("/accounts/safety/locked")){
						System.out.println(account + ":" + pwd + " locked!");
						Config.USERNAME.remove(Config.COUNT);
						Config.PASSWORD.remove(Config.COUNT);
						Config.COUNT_MAX--;
						//因为删除，下次不用换Count值,retry时直接提取下一个账号
					}
				}else{
					System.out.println(Config.USERNAME.get(Config.COUNT)+":"+Config.PASSWORD.get(Config.COUNT)+"--login successfully!");
					isLogin = true;
				}

			}
			changeCount();//下次换一个账号登陆(成功或者连续三次失败)
		}catch (FailingHttpStatusCodeException e){
			e.printStackTrace();
		}	
		if(!isLogin){
			System.exit(-1);
		}
	}

	/** 每次登陆获取一个豆瓣账号，所有账号使用一轮后休眠一次 **/
	public synchronized static void changeCount() {
		if(Config.COUNT<Config.COUNT_MAX-1){
			fallasleep(getUnitSleepTime());
			Config.COUNT++;
		}else {
			fallasleep(getSleepTime());
			Config.COUNT = 0;//重新循环使用账号
			//			Resources.setREQUEST_NUM(0);	//请求数归零
		}
	}

	public HtmlPage click(HtmlSubmitInput button) throws IOException{
		HtmlPage page = null;
		int retry = 0;
		while(retry<Config.RETRY_MAX){
			try {
				page = button.click();
				retry=Config.RETRY_MAX;
			} catch (Exception e) {
				e.printStackTrace();
				retry++;
				System.out.println("click error");
				page = wc.getPage("http://www.douban.com/misc/sorry?original-url=http%3A%2F%2Fwww.douban.com%2F");
				System.out.println(page.asXml());
			}
		}
		return page;
	}

	public String getJSON(String href) throws Exception{
		slowdown();
		String responseText = null;
		int retry = 0;
		while(retry<Config.RETRY_MAX){
			retry++;
			request_counts++;
			WebRequest webRequest = new WebRequest(new URL(href));
			webRequest.setHttpMethod(HttpMethod.GET);
			responseText = sendRequest(webRequest);
			if(responseText!=null){
				retry = Config.RETRY_MAX;
			}else{
				createWC();
				if(isLogin){
					login();
				}
			}
		}
		return responseText;
	}

	//底层请求
	private String sendRequest(WebRequest webRequest) throws Exception{
		String responseContent = null;
		try{
			Page page = wc.getPage(webRequest);
			WebResponse webResponse = page.getWebResponse();
			int status = webResponse.getStatusCode();
			System.out.println("Charset : " + webResponse.getContentCharset());
			System.out.println("ContentType : " + webResponse.getContentType());
			// 读取数据内容
			if (status==200) {
				InputStream bodyStream = webResponse.getContentAsStream();
				responseContent = GetInfo.getStringFromStream(bodyStream);
				bodyStream.close();
			}else if(status==403){
				SaveRecord.saveError(webRequest.getUrl()+"\t403");
			}
			// 关闭响应流
			webResponse.cleanUp();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return responseContent;		
	}

	public HtmlPage getPage(String href, String id) throws IOException{
		//slowdown();
		HtmlPage page = null;
		int retry = 0;
		while(retry<Config.RETRY_MAX){
			try {
				retry++;
				request_counts++;
				page = wc.getPage(href);
				retry = Config.RETRY_MAX;
			} catch (FailingHttpStatusCodeException e){
				if(e.getStatusCode()==404){
					SaveRecord.saveUserNotExist(id);
					return null;
				}else if(e.getStatusCode()==403){
					createWC();
					if(isLogin){
						login();
					}
				}else{
					e.printStackTrace();
				}
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
				createWC();
				if(isLogin){
					login();
				}
			}catch (IOException e) {
				e.printStackTrace();
				//changeIP();
			}
		}
		checkpage(page,href);
		return page;
	}

	private void checkpage(HtmlPage page, String info) {
		if(page==null){SaveRecord.saveError(info);}//获取页面失败
		else if(page.getTitleText().equals("豆瓣电影")){//跳转到主页
			SaveRecord.saveError(page.getUrl()+"\t"+page.getTitleText());
			page = null;
		}else if(page.getTitleText().contains("禁止访问")){//页面爬取失败
			SaveRecord.saveError(page.getUrl()+"\t"+page.getTitleText());
			page = null;
		}
	}

	private void slowdown() throws IOException {
		//降低爬取速度，防止账号被锁
		try {
			Thread.sleep(getUnitSleepTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//当请求次数达到一定数量后，程序休眠30min，降低爬取频数
		if(request_counts>Config.REQUEST_MAX){
			createWC();
			if(isLogin){
				login();
			}
		}
	}

	/** 每次获取一个IP，所有IP使用一轮后休眠一次 
	 * @return 
	 * @throws IOException **/
	private WebClient createNewWC() throws IOException {
		SaveRecord.saveIP(Config.PROXY.get(Config.PROXY_COUNT)+"\t"+Config.PROT.get(Config.PROXY_COUNT)+"\t"+request_counts+"\t"+getCurrentTime());
		if(Config.PROXY_COUNT<Config.PROXY_COUNT_MAX-1){
			fallasleep(getUnitSleepTime());
			Config.PROXY_COUNT++;
		}else {
			fallasleep(getSleepTime());
			Config.PROXY_COUNT = 0;//重新循环使用账号
		}
		String proxy = Config.PROXY.get(Config.PROXY_COUNT);
		if(proxy.equals("")) wc = new WebClient(BrowserVersion.CHROME); //用本机IP
		else wc = new WebClient(BrowserVersion.CHROME,Config.PROXY.get(Config.PROXY_COUNT),Config.PROT.get(Config.PROXY_COUNT));
		return wc;
	}

	/**
	 * 使用代理IP创建一个WC
	 * @return
	 * @throws IOException 
	 */
	private void createWC() throws IOException {
		if(Config.PROXY==null||Config.PROXY.size()==0){wc = new WebClient(BrowserVersion.CHROME);}
		else{
			wc = createNewWC();
		}
		/*HttpClient httpclient = new DefaultHttpClient();
		HttpClientParams.setCookiePolicy(httpclient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);*/
		//WebClient wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setCssEnabled(false);
		wc.getOptions().setActiveXNative(false);
		wc.getOptions().setJavaScriptEnabled(false);
		if(isLogin){
			wc.getOptions().setUseInsecureSSL(true);
		}
		request_counts=0;	//请求数归零
	}

	/** 每次请求，线程休眠随机时间，以此降低爬取速度*/
	private static long getUnitSleepTime(){
		Random r = new Random();
		int sleep_time = r.nextInt(Config.UNIT_SLEEP_TIME);
		return sleep_time;
	}

	/** 达到最大的请求次数或请求被拒绝时程序的休眠时间,单位为毫秒（建议3600000-7200000）*/
	private static long getSleepTime(){
		Random r = new Random();
		int sleep_time = r.nextInt(Config.SLEEP_TIME);
		return sleep_time;
	}

	private static void fallasleep(long sleepTime){
		System.out.println(Thread.currentThread().getName()+"Start to sleep!!!NowTime:"+getCurrentTime()+"I will sleep for "+sleepTime);
		try
		{	//休眠指定时间
			Thread.sleep(sleepTime);
		}catch (InterruptedException ee)
		{
			ee.printStackTrace();
			System.out.println(Thread.currentThread().getName()+"Fail to sleep!!!");
		}
		System.out.println(Thread.currentThread().getName()+"Succeed to sleep!!!NowTime:"+getCurrentTime());
	}

	private static String getCurrentTime() {
		return String.format("%tT", new Date());
		//return df.format(new Date());
	}
}
