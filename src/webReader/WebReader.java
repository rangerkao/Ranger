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
		
		//篩選器
		//doc.getElementsByTag("tag_name");		//以tag名稱
		//doc.getElementById("id_name");		//以id名稱
		//doc.select("div");					//所有dic
		//doc.select("a[href]");				//具有href屬性的 a
		//doc.select("img[src$=.png]");			//具有src 且以.png結尾的img
		//doc.select("div.masthead")			//class=masthead的div
		//doc.select("h3.r > a");				//具有class=r的h3，之後的a
		
		
		//**************************登入驗證***************//
		/*Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        });*/
		
		//******************* HTTPS 連線驗證 ****************//
		trustEveryone();  
		
		/*String [] URLList = new String[] {
				"https://www.518.com.tw/優強機械有限公司-company-26258.html",
				"https://www.518.com.tw/綠舟科技股份有限公司-company-2780765.html",
				"https://www.518.com.tw/佐登妮絲國際股份有限公司-company-208196.html",
				"https://www.518.com.tw/飛得是企業有限公司-company-159445.html",
				"https://www.518.com.tw/驚嘆號藝能有限公司-company-2652168.html",
				"https://www.518.com.tw/首創人力管理顧問有限公司-company-2867723.html",
				"https://www.518.com.tw/金儀國際科技股份有限公司-company-672423.html",
				"https://www.518.com.tw/傳仕精密機械股份有限公司-company-4016386.html",
				"https://www.518.com.tw/御手企業管理社-company-1976116.html",
				"https://www.518.com.tw/偉霸工業股份有限公司-company-572924.html",
				"https://www.518.com.tw/中部雷射股份有限公司-company-2268575.html",
				"https://www.518.com.tw/儷寶化粧品有限公司-company-3009659.html",
				"https://www.518.com.tw/榮笠企業股份有限公司-company-3652492.html",
				"https://www.518.com.tw/宇澄科技股份有限公司-company-3784327.html",
				"https://www.518.com.tw/快樂城企業有限公司-company-3411698.html",
				"https://www.518.com.tw/鈺晟管理顧問有限公司-company-3755943.html",
				"https://www.518.com.tw/精英人力資源股份有限公司-company-32579.html",
				"https://www.518.com.tw/展瑞工業有限公司-company-897275.html",
				"https://www.518.com.tw/馬路科技顧問股份有限公司-company-2644547.html",
				"https://www.518.com.tw/揚運國際有限公司-company-44246.html",
				"https://www.518.com.tw/博賢人力資源有限公司-company-3853489.html",
				"https://www.518.com.tw/廷御企劃有限公司-company-226679.html",
				"https://www.518.com.tw/捷德科技股份有限公司-company-550324.html",
				"https://www.518.com.tw/盈佳數位有限公司-company-3824977.html",
				"https://www.518.com.tw/天力企業股份有限公司-company-129995.html",
				"https://www.518.com.tw/巨泓彩色印刷有限公司-company-2529491.html",
				"https://www.518.com.tw/赤天使貿易股份有限公司-company-3567342.html",
				"https://www.518.com.tw/海峰機械工業股份有限公司-company-880451.html",
				"https://www.518.com.tw/天申茗茶(萬益茶舍國際有限公司)-company-3017133.html",
				"https://www.518.com.tw/貿緯商事有限公司-company-920866.html",
				"https://www.518.com.tw/歐利生技有限公司-company-3332111.html",
				"https://www.518.com.tw/滿漢清知識行銷有限公司-company-3744100.html",
				"https://www.518.com.tw/琦鼎國際股份有限公司-company-4004451.html",
				"https://www.518.com.tw/登洋五金製品有限公司-company-1437534.html",
				"https://www.518.com.tw/駿騰國際有限公司-company-3839335.html",
				"https://www.518.com.tw/銀榮實業股份有限公司(北海東紅高雄辦事處)-company-649105.html",
				"https://www.518.com.tw/佳燁科技股份有限公司-company-654764.html",
				"https://www.518.com.tw/貝里斯商群策創意有限公司台灣分公司-company-3598610.html",
				"https://www.518.com.tw/嘉義縣私立名人幼兒園-company-811935.html",
				"https://www.518.com.tw/英利生股份有限公司-company-156856.html",
				"https://www.518.com.tw/六喬實業股份有限公司-company-2104118.html",
				"https://www.518.com.tw/貝思奇國際文教事業有限公司-company-3937075.html",
				"https://www.518.com.tw/三巨國際電機有限公司-company-2951952.html",
				"https://www.518.com.tw/同文同種股份有限公司-company-1607724.html",
				"https://www.518.com.tw/歐騎企業有限公司-company-3437003.html",
				"https://www.518.com.tw/福立旺精密機電有限公司-company-4033010.html",
				"https://www.518.com.tw/頂鈞塑膠模具股份有限公司-company-798889.html",
				"https://www.518.com.tw/勁威貿易有限公司-company-3714041.html",
				"https://www.518.com.tw/鼎欣音樂美術珠算文理補習班-company-3704790.html",
				"https://www.518.com.tw/飛上枝頭文化創意產業有限公司-company-3174933.html",
				"https://www.518.com.tw/領志科技有限公司-company-3323194.html",
				"https://www.518.com.tw/鼎縊有限公司-company-935355.html",
				"https://www.518.com.tw/國銓科技有限公司-company-1910060.html",
				"https://www.518.com.tw/冠鑫興業股分有限公司-company-750389.html",
				"https://www.518.com.tw/展艷國際有限公司-company-1051340.html",
				"https://www.518.com.tw/三德國際股份有限公司-company-2666546.html",
				"https://www.518.com.tw/私立新寶幼兒園-company-3016458.html",
				"https://www.518.com.tw/川尚股份有限公司-company-3184401.html",
				"https://www.518.com.tw/英屬維京群島商樂透遊戲股份有限公司-company-2812121.html",
				"https://www.518.com.tw/愛麗絲世界有限公司-company-865440.html",
				"https://www.518.com.tw/承泓管理顧問有限公司-company-1558907.html",
				"https://www.518.com.tw/成美商標織造股份有限公司-company-2840891.html",
				"https://www.518.com.tw/瑞昌針織廠股份有限公司-company-988595.html",
				"https://www.518.com.tw/優達創意教育股份有限公司-company-386255.html",
				"https://www.518.com.tw/哈克廚房-company-2448861.html",
				"https://www.518.com.tw/信義房屋仲介股份有限公司-company-553798.html",
				"https://www.518.com.tw/美訊實業有限公司-company-1558304.html",
				"https://www.518.com.tw/頂尖餐飲行銷股份有限公司-company-2739723.html",
				"https://www.518.com.tw/成宇工業股份有限公司-company-878436.html",
				"https://www.518.com.tw/科大光學企業有限公司-company-855864.html",
				"https://www.518.com.tw/宗佳企業有限公司-company-637758.html",
				"https://www.518.com.tw/德興動物醫院-company-447919.html",
				"https://www.518.com.tw/展新生化科技有限公司-company-1465913.html",
				"https://www.518.com.tw/中興凡而工業有限公司-company-2291461.html",
				"https://www.518.com.tw/談話頭小吃店-company-3201487.html",
				"https://www.518.com.tw/第五大道文理音樂補習班-company-3682540.html",
				"https://www.518.com.tw/勳風企業有限公司-company-175783.html",
				"https://www.518.com.tw/巨喡企業有限公司-company-44428.html",
				"https://www.518.com.tw/台灣英科特國際股份有限公司-company-2433376.html",
				"https://www.518.com.tw/高權工業股份有限公司-company-3157468.html",
				"https://www.518.com.tw/萬綺國際股份有限公司-company-1971003.html"
		};*/
		
		String baseUrl = "https://www.yes123.com.tw/admin/job_refer_list.asp";
		try {
			
			
				Document document = Jsoup.parse(new URL(baseUrl).openStream(), "utf-8", baseUrl);
				
				for(Element e : document.getElementsByClass("bsname")) {

					String comapnyURL = "https://www.yes123.com.tw/admin/"+e.attr("href");
					System.out.println(comapnyURL);
					//Document company = Jsoup.parse(new URL(comapnyURL).openStream(), "utf-8", comapnyURL);
					Document company = Jsoup.parse(comapnyURL);
					
					System.out.println(company.html());
					logger.info(company.getElementsByClass("company_title").get(0).html());
					logger.info(company.getElementsByClass("comp_detail_2").get(0).html());
					
					
					return;
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
