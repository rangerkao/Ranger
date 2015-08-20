package webReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;





import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

		Set<String> taxIdSet=dl.getTaxIdSet();
		//taxIdSet.clear();
		baseURL = "http://cpkang.taiwantrade.com.tw/CH/companydetail/{{taxnumber}}/CRM/";
		int i=0;
		for(String taxId : taxIdSet){
			System.out.println("Proccess :"+(++i));
			try {
				parseFirst(baseURL,taxId);
				sleep(queryPeriod);
			} catch (IOException e) {
				logger.error("error occure at taxId="+taxId+".",e);
			}
		}
		
		
		Statement st = null;
		String sql = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			for(Map<String,String> m: dataList){
				sql = "INSERT INTO MARKETING_DB_COMPANY("
						+ "NAME,"
						+ "PHONE,"
						+ "EMAIL,"
						+ "ADDRESS,"
						+ "FAX,"
						+ "WEB,"
						+ "CHARGE,"
						+ "VAT_NUMBER,"
						+ "REMARK,"
						+ "CREATETIME,COMPANY_ID,SOURCE) "
						+ "VALUES("
						+ "'"+dataProcess(m.get("公司名稱"))+"',"
						+ "'"+dataProcess(m.get("公司電話號碼"))+"',"
						+ "'"+dataProcess(m.get("公司電子郵件"))+"',"
						+ "'"+dataProcess(m.get("通訊地址"))+"',"
						+ "'"+dataProcess(m.get("公司傳真號碼"))+"',"
						+ "'"+dataProcess(m.get("公司網址"))+"',"
						+ "'"+dataProcess(m.get("負責人"))+"',"
						+ "'"+dataProcess(m.get("VAT_NUMBER"))+"',"
						+ "'"+dataProcess(m.get("REMARK"))+"',"
						+ "SYSDATE,MARKETING_DB_COMPANY_ID.NEXTVAL,'cpkang')";
				try {
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
		}
	}
	
	//doc.getElementsByTag("tag_name");		//以tag名稱
	//doc.getElementById("id_name");		//以id名稱
	//doc.select("div");					//所有dic
	//doc.select("a[href]");				//具有href屬性的 a
	//doc.select("img[src$=.png]");			//具有src 且以.png結尾的img
	//doc.select("div.masthead")			//class=masthead的div
	//doc.select("h3.r > a");				//具有class=r的h3，之後的a
	
	public void parseFirst(String strURL,String number) throws IOException{
		strURL=strURL.replace("{{taxnumber}}", number);
		System.out.println(strURL);
		URL url = new URL(strURL); 
		
		Map<String,String> map = new HashMap<String,String>();
		String remark = "";
		Document source =  Jsoup.parse(url, waitPeriod); 
		Elements company_detail = source.getElementsByAttributeValue("class", "company_detail");
		
		if(!company_detail.isEmpty()){
			Elements th = company_detail.get(0).getElementsByTag("th");
			Elements td = company_detail.get(0).getElementsByTag("td");
			
			for(int i =0;i<th.size();i++){
				String name = th.get(i).text();
				String value = td.get(i).text();
				System.out.println(name+" = "+value);
				
				if(	"公司名稱".equalsIgnoreCase(name)||
					"公司電子郵件".equalsIgnoreCase(name)||
					"公司電話號碼".equalsIgnoreCase(name)||
					"公司傳真號碼".equalsIgnoreCase(name)||
					"通訊地址".equalsIgnoreCase(name)||
					"負責人".equalsIgnoreCase(name)||
					"公司網址".equalsIgnoreCase(name)){
					
					map.put(name, trance(value));
				}else 
					if(	"郵遞區號".equalsIgnoreCase(name)||
						"公司形態".equalsIgnoreCase(name)||
						"主要出口產品".equalsIgnoreCase(name)||
						"主要外銷市場".equalsIgnoreCase(name)){
						
						remark+=name+"="+value+",";
				}else 
					if(	"公協會網站會員".equalsIgnoreCase(name)||
						"鄧白氏認證".equalsIgnoreCase(name)||
						"公協會網站會員".equalsIgnoreCase(name)){
						
						//do nothing
				}else{
					logger.error("read url="+strURL+" found not set parameter "+name+".");
				}
			}
			if(!"".equals(remark)){
				map.put("REMARK", trance(remark));
			}
		}
		
		if(map.isEmpty()){
			logger.error("read url="+strURL+" found not data!");
		}else{
			map.put("VAT_NUMBER", number);
			dataList.add(map);
		}
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
