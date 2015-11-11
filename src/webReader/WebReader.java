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
	
	public static void main(String[] args) {
		
		
		conn = getConnection();
		
		if(conn==null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		Set<String> stkIdSet=new HashSet<String>();
		stkIdSet = dl.getSTKIdSet();
		//taxIdSet.clear();
		baseURL = "http://ic.tpex.org.tw/company_basic.php?stk_code={{stkId}}";
		baseURL2 = "http://ic.tpex.org.tw/company_contact.php?stk_code={{stkId}}";
		for(String stkId : stkIdSet){
			Map<String,String> map = new HashMap<String,String>();
			try {
				//basic
				parseFirst(baseURL,stkId,map);
				parseSecond(baseURL2,stkId,map);
			} catch (IOException e) {
				prints(e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				prints(e);
			}
			
			if(map.size()!=0)
				dataList.add(map);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*Statement st = null;
		String sql = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			for(Map<String,String> m: dataList){
				
				sql = "INSERT INTO MARKETING_DB_COMPANY("
						+ "NAME,PHONE,EMAIL,ADDRESS,CHARGE,FAX,CREATETIME,WEB,COMPANY_ID,SOURCE,CONTACT,INDUSTRY) "
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
						+ "'http://ic.tpex.org.tw/',"
						+ "'"+trance(dataProcess(m.get("contact")))+"',"
						+ "'"+trance(dataProcess(m.get("industry")))+"')";
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
	
	public void parseFirst(String strURL,String number,Map<String,String> map) throws Exception{
		
		strURL=strURL.replace("{{stkId}}", number);
		
		prints("parse web "+strURL);
		
		//Use Jsuop get html
		URL url = new URL(strURL); 
		
		Document source =  Jsoup.parse(url, waitPeriod); 
		Element e = source.getElementsByTag("H3").first();
		String name = e.text().replace("基本資料", "");
		prints("公司名稱:"+name);
		map.put("name", name);
		String html = source.html();

		String reg = "http://www.tpex.org.tw/storage/company_basic/company_basic.php\\?s=\\d{4}&m=\\d{2}";
		Matcher m = Pattern.compile(reg).matcher(html);
		m.find();
		
		String jsonAddr = m.group();
		
		if(jsonAddr == null){
			throw new Exception("For stkId:"+number+" found no data!");
		}
		
		prints("jsonAddr:"+jsonAddr);
		

		//Use JsonPaser to get return json data
		String jsonS = readUrl(jsonAddr);
		
		prints(jsonS);
		
		jsonS = jsonS.substring(0,jsonS.length()-1).replace("getCompanyBasic(", "");
		
		JSONObject json = new JSONObject(jsonS);

		String MAIN_BUSINESS = "";
		for(Object s:json.keySet()){
			String tag = s.toString();
			String value = (String) json.get(s.toString());
			if("CHAIRMAN_NAME".equalsIgnoreCase(tag)){
				prints("董事長:"+value);
				map.put("charge", value);
			}else if("PRESIDENT_NAME".equalsIgnoreCase(tag)){
				//prints("總經理:"+json.get(s.toString()));
			}else if("SPOKENMAN".equalsIgnoreCase(tag)){
				//prints("發言人:"+json.get(s.toString()));
			}else if("COMPANY_TEL".equalsIgnoreCase(tag)){
				prints("電話:"+value);
				map.put("phone", value);
			}else if("COMPANY_ADDRESS".equalsIgnoreCase(tag)){
				prints("地址:"+value);
				map.put("address", value);
			}else if("INTERNET_ADDRESS".equalsIgnoreCase(tag)){
				prints("網址:"+value);
				map.put("web", value);
			}else if("NAME".equalsIgnoreCase(tag)){
				prints("產業類別:"+value);
				map.put("industry", value);
			}else if("MAIN_BUSINESS1".equalsIgnoreCase(tag)||"MAIN_BUSINESS2".equalsIgnoreCase(tag)||"MAIN_BUSINESS3".equalsIgnoreCase(tag)){
				if(!"".equals(value))
					MAIN_BUSINESS+=value+"<BR>\n";
			}
		}
		if(!"".equals(MAIN_BUSINESS)){
			prints("主要經營業務:"+MAIN_BUSINESS);	
			map.put("remark", MAIN_BUSINESS);
		}

	}
	
	public void parseSecond(String strURL,String number,Map<String,String> map) throws Exception{
		
		strURL=strURL.replace("{{stkId}}", number);
		
		prints("parse web "+strURL);
		
		//Use Jsuop get html
		URL url = new URL(strURL); 
		
		Document source =  Jsoup.parse(url, waitPeriod); 
		Elements h3 = source.getElementsByTag("H3");
		
		for(Element e : h3){
			String text = e.html();
			Elements es = e.getElementsByTag("small");
			if(es.size()==0)
				continue;
			
			Element small =  es.get(0);
			
			if(text.indexOf("聯絡人")!=-1){
				prints("聯絡人:"+small.text());
				map.put("contact", small.text());
			}else if(text.indexOf("公務電話")!=-1){
				//prints("公務電話:"+small.text());
				
			}else if(text.indexOf("電子郵件")!=-1){
				prints("電子郵件:"+small.text());
				map.put("mail", small.text());
			}else if(text.indexOf("公司網址")!=-1){
				prints("公司網址:"+small.text());
				map.put("web", small.text());
			}else if(text.indexOf("公司電話")!=-1){
				prints("公司電話:"+small.text());
				map.put("phone", small.text());
			}else if(text.indexOf("公司傳真")!=-1){
				prints("公司傳真:"+small.text());
				map.put("fax", small.text());
			}else if(text.indexOf("公司地址")!=-1){
				prints("公司地址:"+small.text());
				map.put("address", small.text());
			}
		}
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
