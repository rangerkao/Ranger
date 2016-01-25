package webReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;





import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class WebReader {

	static Connection conn=null;
	private static  Logger logger ;
	static Properties props=new Properties();
	static String msg=null;
	
	int waitPeriod = 30000;
	int queryPeriod = 3000;
	int maxID;
	List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
	String baseURL;
	String baseURL2;
	
	List<String> cUrl = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		
		/*conn = getConnection();
		
		if(conn==null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			loadProperties();
			new WebReader();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	Data dl = new Data();
	
	
	public String dataProcess(String s){
		String result = s;
		
		if(result==null)
			result="";
		
		return result;		
	}
	
	public String trance(String s){
		String result = s.replace("'", "''").replace("&", "'||'&'||'");
		
		if(result==null)
			result="";
		else
			try {
				result=new String(result.getBytes("BIG5"),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
		return result;
	}
	
	
	public WebReader(){
		baseURL = "http://www.tba-cycling.org/edit_c.aspx?cId=A{{num}}";
		try {
			//basic
			for(int i = 21;i<=66 ;i++){
				String num = String.valueOf(i);
				if(i<10)
					num = "0"+num;
				parseFirst(baseURL.replace("{{num}}", num));
				sleep(3000);
			}
			
			/*for(String s:cUrl){
				parseSecond(s);
				sleep(3000);
			}*/
		} catch (IOException e) {
			prints(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prints(e);
		}
		
		/*Statement st = null;
		String sql = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			for(Map<String,String> m: dataList){
				
				sql = "INSERT INTO MARKETING_DB_COMPANY("
						+ "NAME,PHONE,EMAIL,ADDRESS,CHARGE,FAX,CREATETIME,WEB,COMPANY_ID,SOURCE,CONTACT,REMARK,ENG_NAME) "
						+ "VALUES("
						+ "'"+trance(dataProcess(m.get("name")))+"',"
						+ "'"+dataProcess(m.get("phone"))+"',"
						+ "'"+dataProcess(m.get("mail"))+"',"
						+ "'"+trance(dataProcess(m.get("address")))+"',"
						+ "'"+trance(dataProcess(m.get("charge")))+"',"
						+ "'"+dataProcess(m.get("fax"))+""
						+ "',sysdate,"
						+ "'"+trance(dataProcess(m.get("web")))+"',"
						+ "MARKETING_DB_COMPANY_ID.NEXTVAL,"
						+ "'http://www.lighting.org.tw/Ch_New/about_06.aspx',"
						+ "'"+trance(dataProcess(m.get("contact")))+"',"
						+ "'"+trance(dataProcess(m.get("remark")))+"',"
						+ "'"+trance(dataProcess(m.get("ename")))+"')";
				try {
					prints(sql);
					st.execute(sql);
				} catch (SQLException e) {
					logger.error("Insert error sql: "+sql+".",e);
				}
			}
		} catch (SQLException e1) {
			logger.error("Create Statement Error!");
			e1.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
	}
	
	//doc.getElementsByTag("tag_name");		//以tag名稱
	//doc.getElementById("id_name");		//以id名稱
	//doc.select("div");					//所有dic
	//doc.select("a[href]");				//具有href屬性的 a
	//doc.select("img[src$=.png]");			//具有src 且以.png結尾的img
	//doc.select("div.masthead")			//class=masthead的div
	//doc.select("h3.r > a");				//具有class=r的h3，之後的a

	public void parseFirst(String strURL) throws Exception{
			
	//	String viewK="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";
		
		
		prints("parse web "+strURL);
		
		//Use Jsuop get html
		URL url = new URL(strURL); 
		
		Document source =  Jsoup.parse(url, waitPeriod); 
		Element table = source.getElementById("ctl00_main_grid");
		Elements trs = table.getElementsByTag("tr");
		for(int i = 1;i<trs.size();i++){
			Element tr = trs.get(i);
			Elements tds = tr.getElementsByTag("td");
			logger.info(tds.get(1).text()+":"+tds.get(2).text()+":"+tds.get(3).text()+":"+tds.get(4).text()+":");
		}
		
		
		/*Document source = Jsoup.connect("http://www.tba-cycling.org/edit_c.aspx?cid=A02&memberNo=&company=&address=&page=5")
				.data("__EVENTTARGET", "ctl00$main$grid$ctl02$ctl00")
				.data("__EVENTARGUMENT","")
				.data("__VIEWSTATE", viewK)
				.post();*/
		
		//System.out.println(source.html());
		
		/*Element table = source.getElementById("table2");
		Elements a = table.getElementsByTag("a");

		for(Element e : a){
			
			if(e.text().indexOf("www.lighting.org.tw/Ch_New")!=-1){
				cUrl.add(e.text());
			}
		}
		for(String s : cUrl){
			//System.out.println(s);
		}*/
	}
	
	public void parseSecond(String strURL) throws Exception{

		prints("parse web "+strURL);
		
		//Use Jsuop get html
		URL url = new URL(strURL); 
		
		Document source =  Jsoup.parse(url, waitPeriod); 
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("name",(source.getElementById("ChName1")!=null?source.getElementById("ChName1").text():""));
		map.put("ename",(source.getElementById("EnName1")!=null?source.getElementById("EnName1").text():""));
		map.put("address",(source.getElementById("PostNo")!=null?source.getElementById("PostNo").text():"")+(source.getElementById("ChAdd")!=null?source.getElementById("ChAdd").text():""));
		map.put("charge",(source.getElementById("ChCeo")!=null?source.getElementById("ChCeo").text():""));
		map.put("contact",(source.getElementById("ChPres")!=null?source.getElementById("ChPres").text():""));
		map.put("phone",(source.getElementById("Tel")!=null?source.getElementById("Tel").text():""));
		map.put("fax",(source.getElementById("Fax")!=null?source.getElementById("Fax").text():""));
		map.put("mail",(source.getElementById("Email")!=null?source.getElementById("Email").text():""));
		map.put("web",(source.getElementById("Url")!=null?source.getElementById("Url").text():""));
		map.put("remark",(source.getElementById("Member_Mode")!=null?source.getElementById("Member_Mode").text():""));
		for(String s: map.keySet()){
			System.out.println(s+":"+map.get(s));
		}
		dataList.add(map);
	}
	
	
	
	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	public void prints(Exception e){
		
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		prints(s.toString());
	}
	
	public void prints(String s){
		//System.out.println(s);
		logger.info(s);
	}
	
	public void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static  void loadProperties() throws FileNotFoundException, IOException{
		System.out.println("initial Log4j, property !");
		String path=WebReader.class.getSuperclass().getResource("/").toString().replace("file:", "")+"/webReader.properties";;
				//.getResource("").toString().replace("file:", "")+"/webReader.properties";

		System.out.println(path);
		props.load(new   FileInputStream(path));
		PropertyConfigurator.configure(props);
		logger =Logger.getLogger("webReaderLog");
		logger.info("Logger Load Success!");

	}
	
	static Connection getConnection()
	  {
		
		try
	    {
	      //Class.forName("org.postgresql.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
	    }
	    catch (Exception localException)
	    {
	      System.err.println("ERROR: failed to load Informix JDBC driver.");
	      msg = ("ERROR: failed to load Informix JDBC driver." + localException.getMessage());
	      return null;
	    }
		
	    Connection localConnection = null;
	    try
	    {
	    	
	      DriverManager.setLoginTimeout(10);
	      //System.Environment.SetEnvironmentVariable("NLS_LANG", "AMERICAN_AMERICA.WE8ISO8859P1");
	      //localConnection = DriverManager.getConnection("jdbc:postgresql://192.168.10.197:5432/smppdb", "smpper", "SmIpp3r");
	      localConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.42.1.101:1521:S2TBSDEV", "foyadev", "foyadev");
	      //localConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.42.1.80:1521:s2tbs", "s2tbsadm", "s2tbsadm");
	    }
	    catch (Exception localException)
	    {
	      System.err.println("ERROR DB: failed to connect!");
	      msg = ("ERROR DB: failed to connect!" + localException.getMessage());
	    }
	    return localConnection;
	  }
}
