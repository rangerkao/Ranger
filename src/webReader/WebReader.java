package webReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;
import org.jsoup.Connection.Method;
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

	public static void main(String[] args) {
		
		try {
			loadProperties();
			new WebReader();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
	public String transfer(String s){
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
		String baseUrl = "http://www.cisanet.org.tw/eBook/eBook_more?id=";
		
		String ids = "5234,5233,5231,5229,5228,5227,5225,5224,5222,5221,5219,5217,5216,5215,5214,5213,5212,5211,"
				+ "5210,5209,5206,5202,5201,5189,5188,5187,5180,5179,5177,5176,5175,5174,5169,5168,5166,5165,"
				+ "5160,5159,5157,5156,5152,5150,5149,5146,5143,5142,5141,5140,5139,5138,5137,5136,5135,5133,"
				+ "5131,5129,5127,5124,5123,5122,5121,5118,5117,5115,5114,5113,5111,5110,5109,5107,5105,5102,"
				+ "5101,5100,5099,5095,5092,5090,5086,5084,5083,5080,5079,5078,5076,5071,5064,5062,5059,5058,"
				+ "5051,5049,5048,5047,5046,5044,5041,5040,5036,5032,5031,5029,5028,5023,5022,5021,5013,5003,"
				+ "5002,5000,4997,4995,4987,945,942,941,940,938,935,936,934,967,966,965,964,896,917,895,892,"
				+ "891,890,888,887,885,886,883,881,882,877,879,871,872,865,867,864,868,863,861,858,857,852,"
				+ "851,850,846,849,847,843,840,841,839,838,836,834,833,829,828,824,810,816,814,812,798,794,"
				+ "797,787,800,784,781,774,775,772,770,767,766,761,755,752,912,748,911,746,747,745,744,743,"
				+ "910,742,741,739,738,737,735,728,727,732,724,909,721,720,719,718,717,908,907,714,715,713,"
				+ "712,710,709,707,706,696,697,695,898,693,498,729,860,777,613,740,640,786,602,600,483,482,"
				+ "579,576,550,480,584,571,566,503,478,477,583,541,476,475,580,575,474,619,562,545,473,471,"
				+ "543,523,513,520,495,479,472,470,469,630,599,796,597,593,591,590,589,587,586,585,512,647,"
				+ "578,577,573,570,468,565,564,561,560,467,620,559,558,557,556,466,555,554,553,551,549,548,"
				+ "547,546,497,607,544,542,494,618,592,540,539,536,535,534,533,532,530,529,526,524,522,634,"
				+ "567,521,519,604,518,517,516,515,511,510,687,689,688,508,507,681,680,679,678,677,676,675,"
				+ "674,673,671,906,904,903,902,901,900,669,505,899,668,667,666,665,664,663,504,659,658,656,"
				+ "655,652,641,502,501,646,645,500,499,644,643,635,633,632,631,629,628,626,650,649,636,627,"
				+ "624,622,642,615,496,493,611,605,603,638,601,491,490,489,487,905,690,661,653,637,610,606,"
				+ "581,552,527,514,509,506,488,486,485,484";
		
		
		
		try {
			for(String id : ids.split(",")) {
				Document document = Jsoup.parse(new URL(baseUrl+id).openStream(), "utf-8", baseUrl);
				
				logger.info("name"+document.getElementsByClass("txt_13").first().html());
				
				Elements datas = document.getElementsByClass("memb1Table");
				
				for(Element e : datas) {
					Elements details = e.getElementsByTag("td");
					logger.info(details.get(0).html()+":"+details.get(2).html());
				}			
				sleep(2000);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public static void trustEveryone() {  
        try {  
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {  
                public boolean verify(String hostname, SSLSession session) {  
                    return true;  
                }  
            });  
  
            SSLContext context = SSLContext.getInstance("TLS");  
            context.init(null, new X509TrustManager[] { new X509TrustManager() {  
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                }  
  
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                }  
  
                public X509Certificate[] getAcceptedIssuers() {  
                    return new X509Certificate[0];  
                }  
            } }, new SecureRandom());  
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());  
        } catch (Exception e) {  
            // e.printStackTrace();  
        }  
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
