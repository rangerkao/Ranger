package program;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//190
/*import com.infotech.smpp.SMPPServicesStub;
import com.infotech.smpp.SMPPServicesStub.SendSMPP;
import com.infotech.smpp.SMPPServicesStub.SendSMPPResponse;*/

//199
/*import com.iglomo.SMPPServicesStub;
import com.iglomo.SMPPServicesStub.SendSMPP;
import com.iglomo.SMPPServicesStub.SendSMPPResponse;*/

public class Jatool implements IJatool{

	/**針對Jtool做log紀錄
	 * 
	 * @param logger 
	 * @param type
	 * @param message
	 * 
	 * 參數為 logger、log層級、訊息
	 */
	public void logControl(Logger logger,String level,String message){
		if(logger==null){
			System.out.println(message);
		}else{
			if("info".equalsIgnoreCase(level)){
				logger.info(message);
			}else if("debug".equalsIgnoreCase(level)){
				logger.debug(message);
			}else if("error".equalsIgnoreCase(level)){
				logger.error(message);
			}
		}
	}
	
	public void mailSample() throws Exception{
		sendMailwithAuthenticator("202.133.250.242",25,"smtp",
				"ranger.kao@sim2travel.com","kkk770204",
				"mailSample","ranger.kao@sim2travel.com,k1988242001@gmail.com",
				"sendMailwithAuthenticator","sendMailwithAuthenticator sample");
		sendMailwithoutAuthenticator("202.133.250.242",25,"smtp",
				"ranger.kao@sim2travel.com","kkk770204",
				"mailSample","ranger.kao@sim2travel.com,k1988242001@gmail.com",
				"sendMailwithAuthenticator","sendMailwithAuthenticator sample");
		
		//Linux
		sendMailforUnixLike("mail","mailSample","ranger.kao@sim2travel.com,k1988242001@gmail.com","sendMailforUnixLike","sendMailforUnixLike smaple");
		//Solaris
		sendMailforUnixLike("mailx","mailSample","ranger.kao@sim2travel.com,k1988242001@gmail.com","sendMailforUnixLike","sendMailforUnixLike smaple");
		
	}
	
	/** 
	 * 有驗證器的mail功能
	 */
	@Override
	public void sendMailwithAuthenticator(String host,int port,String protocol,final String username,final String password,
			String sender,String receiver,String subject,String content) throws AddressException, MessagingException{

		  Properties props = new Properties();
		  props.put("mail.smtp.host", host);
		  props.put("mail.smtp.auth", "true");
		  props.put("mail.smtp.starttls.enable", "true");
		  props.put("mail.smtp.port", port);
		  Session session = Session.getInstance(props, new Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(username, password);
		   }
		  });

		 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject(subject);
			message.setText(content);
			
			Transport transport = session.getTransport(protocol);
			transport.connect(host, port, username, password);
			
			Transport.send(message);
			
			System.out.println("Send mail finished.");

		  
	}
	/** 
	 * 無驗證器的mail功能 
	 */
	@Override
	public void sendMailwithoutAuthenticator(String host,int port,String protocol,final String username,final String password,
			String sender, String receiver, String subject,String content) throws AddressException, MessagingException {

		Properties props = new Properties();
		  props.put("mail.smtp.host", host);
		  props.put("mail.smtp.auth", "true");
		  props.put("mail.smtp.starttls.enable", "true");
		  props.put("mail.smtp.port", port);
		
		Session session = javax.mail.Session.getInstance(props);

		
		Message message = new MimeMessage(session);
		message.setHeader("Disposition-Notification-To", "ranger.kao@sim2travel.com");//回條參數
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(receiver));
		message.setSubject(subject);
		message.setText(content);

		Transport transport = session.getTransport(protocol);
		transport.connect(host, port, username, password);
	    transport.sendMessage(message, message.getAllRecipients());
	    System.out.println("Send mail finished.");
		
	}	
	/** 
	 * 利用unix內建的mail功能
	 */
	@Override
	public void sendMailforUnixLike(String mailfunction,String sender,String receiver,String subject,String msg) throws Exception{
		String ip ="";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
		}
		
		if(receiver==null ||"".equals(receiver)){
			throw new Exception("No receiver and No UserName Set!");
		}
		
		msg=msg+"\n from location "+ip;			
		
		String [] cmd=new String[3];
		cmd[0]="/bin/bash";
		cmd[1]="-c";
		cmd[2]= "/bin/echo \""+msg+"\" | /bin/"+mailfunction+" -s \""+subject+"\" -r "+sender+" "+receiver+"." ; ;

		try{
			Process p = Runtime.getRuntime().exec (cmd);
			p.waitFor();
			System.out.println("send mail cmd:"+cmd[0]+" "+cmd[1]+" "+cmd[2]);
		}catch (Exception e){
			System.out.println("send mail fail:"+cmd[2]);
		}
	}
	
	/**
	 * 設定Properties
	 * @return
	 */
	@Override
	public Properties getProperties(){
		Properties result=new Properties();
		
		result.put("log4j.rootCategory", "DEBUG, stdout, FileOutput");
		
		result.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		result.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		result.put("log4j.appender.stdout.layout.ConversionPattern", "%d [%5p] (%F:%L) - %m%n");
		
		result.put("log4j.appender.FileOutput", "org.apache.log4j.DailyRollingFileAppender");
		result.put("log4j.appender.FileOutput.layout", "org.apache.log4j.PatternLayout");
		result.put("log4j.appender.FileOutput.layout.ConversionPattern", "%d [%5p] (%F:%L) - %m%n");
		result.put("log4j.appender.FileOutput.DatePattern", "'.'yyyyMMdd");
		result.put("log4j.appender.FileOutput.File", "AddonService.log");

		//use Logger.getLogger(StressClientTest.class);
		result.put("log4j.logger.StressClient", "INFO, StressClient");
		result.put("log4j.appender.StressClient", "org.apache.log4j.DailyRollingFileAppender");
		result.put("log4j.appender.StressClient.File", "StressClient.log");
		result.put("log4j.appender.StressClient.DatePattern", "'.'yyyyMMdd");
		result.put("log4j.appender.StressClient.layout", "org.apache.log4j.PatternLayout");
		result.put("log4j.appender.StressClient.layout.ConversionPattern", "%-d{yyyy-MM-dd HH:mm:ss} [%c:%L]-[%p] %m%n");
		
		
		return result;
	}
	
	public Properties getmailProperties(){
		Properties result=new Properties();

		//mail setting
		result.put("mail.smtp.auth", "true");
		result.put("mail.smtp.host", "202.133.250.242");
		result.put("mail.smtp.port", "25");
		result.put("mail.transport.protocol", "smtp");
		result.put("mail.username", "ranger.kao@sim2travel.com");
		result.put("mail.password", "kkk770204");
		result.put("mail.Sender", "Send Program");
		result.put("mail.Receiver", "ranger.kao@sim2travel.com,k1988242001@gmail.com");

		return result;
	}

	
	
	/**
	 * 資料庫連線
	 * @param DriverClass
	 * @param DBType
	 * @param ip
	 * @param port
	 * @param DBNameorSID
	 * @param charset
	 * @param UserName
	 * @param PassWord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public Connection connDB(String DriverClass,
			String DBType,String ip,String port,String DBNameorSID,String charset,
			String UserName,String PassWord) throws ClassNotFoundException, SQLException{

			Class.forName(DriverClass);
			String url="jdbc:{{DBType}}:thin:@{{Host}}:{{Port}}:{{ServiceName}}{{charset}}"
					.replace("{{DBType}}", DBType)
					.replace("{{Host}}", ip)
					.replace("{{Port}}", port)
					.replace("{{ServiceName}}", DBNameorSID)
					.replace("{{charset}}", (charset!=null?"?charset="+charset:""));
			
		return connDB(DriverClass, url, UserName, PassWord);
	}

	public Connection connDB(String DriverClass, String URL,
			String UserName, String PassWord) throws ClassNotFoundException, SQLException {
		Connection conn = null;

			Class.forName(DriverClass);
			conn = DriverManager.getConnection(URL, UserName, PassWord);
		return conn;
	}

	/**
	 * Web Server Discriber Language smaple
	 * @param param
	 * @return
	 * @throws RemoteException
	 */
	@Override
	public String callWSDLServer(String param) throws RemoteException {

		String result = null;
		/*SMPPServicesStub stub = new SMPPServicesStub();

		SendSMPP smpp = new SendSMPP();
		smpp.setArgs0(param);
		SendSMPPResponse res = stub.sendSMPP(smpp);

		result = res.get_return();*/

		return result;

	}

	/**
	 * 取得當月第一天
	 * @param date
	 * @return
	 */
	@Override
	public Date getMonthFirstDate(Date date) {
		
		Calendar calendar = Calendar.getInstance();
		Date monthFirstDate=null;

		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		monthFirstDate=calendar.getTime();
		calendar.clear();

		return monthFirstDate;
	}
	/**
	 * 取得當月最後一天
	 * @param date
	 * @return
	 */
	@Override
	public Date getMonthLastDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		Date monthLastDate=null;
		
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		monthLastDate= calendar.getTime();
		calendar.clear();
		return monthLastDate;
	}
	
	/**
	 * 預設 格式 yyyy/MM/dd HH:mm:ss，將String 與 Date 相互轉換
	 * @param value(String or Date)
	 * @param form
	 * @return
	 */
	@Override
	public Object DateFormat(Object value, String form) throws Exception {
		String iniform="yyyy/MM/dd HH:mm:ss";
		
		if(value==null) 
			throw new Exception("Value couldn't be Null.");
		
		DateFormat dateFormat = new SimpleDateFormat(form);
		
		//如果值為Date，可使用Simple Name Date
		if(value instanceof  java.util.Date){
			return dateFormat.format(value);
		}
		
		//如果值為String，可使用Simple Name String
		if(value instanceof  java.lang.String){
			return dateFormat.parse((String) value);
		}

		return null;
	}
	/**
	 * 對Http做 Post
	 * @param url
	 * @param method [POST or GET]
	 * @param param
	 * @param charset 是否編碼
	 * @return [結果代碼，對方回傳內容]
	 */
	@Override
	public int HttpPost(String url,String method,String param,String charset) throws IOException{
		URL obj = new URL(url);
		
		String urlParam = param;
		
		if(charset!=null && !"".equals(charset))
			urlParam=URLEncoder.encode(urlParam, charset);

		
		HttpURLConnection con =  (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod(method);
		//con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");*/
		con.setDoOutput(true); //如要從連線輸出回應資料，需設定為true
		
		// Send post request
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(param);
		wr.flush();//將暫存資料寫至目的地
		wr.close();
 
		//回應碼
		int responseCode = con.getResponseCode();
		System.out.println("post data("+param+") to "+ url+". Result : "+responseCode);
 
		//讀取回傳資料
		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("get response:"+response.toString());
		
		return responseCode;
	}
	
	/**
	 * 對浮點數做標準化
	 * @param value
	 * @param form 預設為 小數後兩位，3位撇節法格式為"#,##0.00";
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Double FormatDouble(Double value,String form) throws Exception{
		
		if(value == null)
			throw new Exception("Input could't be null.");
		
		if(form==null || "".equals(form)){
			form="0.00";
		}
			
		/*DecimalFormat df = new DecimalFormat(form);   
		String str=df.format(value);*/
		
		return Double.valueOf(new DecimalFormat(form).format(value));
	}


	/**
	 * 讀取文字檔
	 * @param filePath
	 * @throws IOException 
	 */
	@Override
	public String readtxt(String filePath,String charset) throws IOException {
		//String filePath =BillReport.class.getClassLoader().getResource("").toString().replace("file:", "")+ "source/";

		BufferedReader reader = null;
		String str = null;
		StringBuffer content = new StringBuffer();
		
		if(charset == null)
			charset = "UTF-8";
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset)); 
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
				content.append(str);
			}
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}

	/**
	 * 寫入文字檔
	 * @param filename
	 * @param content
	 * @param append (延伸true或是覆寫false)
	 * @param charSet
	 * @throws IOException 
	 */
	@Override
	public void writetxt(String filename,String content,boolean append ,String charSet) throws IOException {
		BufferedWriter fw = null;
		if(charSet==null)
			charSet = "UTF-8";
		try {
			//建立新檔，已存在的話會刪除重建
			File file = new File(filename);
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), charSet)); 
			fw.append(content);
			//flush 後才會在文件顯示內容，可忽略讓程式自動flush
			//fw.flush(); 

		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
						e.printStackTrace();
				}
			}
		}		
	}
	/**
	 * 建立文件夾
	 */
	@Override
	public void newFolder(String folderPath) {
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		if (!myFilePath.exists()) {
			myFilePath.mkdir();
		}
	
	}

	/**
	 * 比對字串
	 * @param content
	 * @param regex
	 * @return
	 */
	@Override
	public boolean regularMatch(String content, String regex) {
		
		return content.matches(regex);
	}

	/**
	 * 回傳字串中符合的部分
	 * @param content
	 * @param regex
	 * @return
	 */
	@Override
	public List<String> regularFind(String content, String regex) {
		
		Pattern p =Pattern.compile(regex);
		Matcher m = p.matcher(content);
		
		List<String> list = new ArrayList<String>();
		
		while(m.find()){
			list.add(m.group());
		}
		
		return list;
	}


	/**
	 * 小數點4捨5入
	 * @param value
	 * @param method
	 * 
	 * @param digit
	 * @return
	 */
	@Override
	public Double roundUpOrDdown(Double value,String method,int digit){
		
		Double result = 0D;
		//取捨位>=0.5->ROUND_UP，反之->ROUND_DOWN
		if("ROUND_HALF_UP".equals(method))		result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
		//取捨位>0.5->ROUND_UP，反之->ROUND_DOWN
		if("ROUND_HALF_DOWN".equals(method))	result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_HALF_DOWN).doubleValue();
		
		if("ROUND_HALF_EVEN".equals(method))	result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		//value 為正->ROUND_UP，如果為負->ROUND_DOWN
		if("ROUND_CEILING".equals(method))		result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_CEILING).doubleValue();
		//value 為負->ROUND_UP，如果為正->ROUND_DOWN
		if("ROUND_FLOOR".equals(method))		result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_FLOOR).doubleValue();
		//遠離zero
		if("ROUND_UP".equals(method))			result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_UP).doubleValue();
		//朝向zero
		if("ROUND_DOWN".equals(method))			result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_DOWN).doubleValue();
		
		return result;
	}


	/**
	 * 取的指定時間的calendar
	 */
	@Override
	public Calendar getCalendar(Integer year, Integer month,	Integer day, Integer hour, Integer min, Integer sec) {
		
		Calendar calendar=Calendar.getInstance();
		
		if(year!=null)
			calendar.set(Calendar.YEAR, year);
		if(month!=null)
			calendar.set(Calendar.MONTH, month);
		if(day!=null)
			calendar.set(Calendar.DATE, day);
		if(hour!=null)
			calendar.set(Calendar.HOUR, hour);
		if(min!=null)
			calendar.set(Calendar.MINUTE, min);
		if(sec!=null)
			calendar.set(Calendar.SECOND, sec);
		
		return calendar;
	}


	/**
	 * 
	 * @param content
	 * @param sourceCharset
	 * @param torgetCharset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public String convertCharset(String content, String sourceCharset,
			String torgetCharset) throws UnsupportedEncodingException {
		if(sourceCharset==null)
			return new String(content.getBytes(),torgetCharset);
		if(torgetCharset==null)
			return new String(content.getBytes(sourceCharset));
		return new String(content.getBytes(sourceCharset),torgetCharset);
	}

	/**
	 * 取得程式絕對路徑
	 * @param className
	 * @return
	 */
	@Override
	public String getProgramDir(Class<?> className) {
		return className.getClassLoader().getResource("").toString().replace("file:", "").replace("%20", " ");
	}


	/**
	 * 間隔時間
	 * @param periodTime
	 * 多久後執行第一次
	 * @param delay
	 * 實做TimerTask 的任務class
	 * @param run
	 */
	 

	@Override
	public void regularExcute(Long periodTime, Object time, TimerTask run) {
		Timer timer =new Timer();
		
		
		if(periodTime ==null){
			if(time instanceof Long)
				timer.schedule(run,(Long) time);
			
			if(time instanceof Date)
				timer.schedule(run,(Date) time);
		}else{
			if(time instanceof Long)
				timer.schedule(run,(Long) time, periodTime);
			
			if(time instanceof Date)
				timer.schedule(run,(Date) time, periodTime);
		}
		
		

	}

	@Override
	public String getExceptionMsg(Exception e) {
		StringWriter errors = new StringWriter(); 
		e.printStackTrace(new PrintWriter(errors)); 
		return errors.toString();
	}
	@Override
	public void UpdatToFTP(FTPClient ftp,InputStream input,String destFileName) throws IOException{

		ftp.setBufferSize(1024);  
        ftp.setControlEncoding("GBK");
        // 设置文件类型（二进制）  
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);  

		if(ftp.storeFile(destFileName, input)){
			System.out.println("Update Success!");
		}else{
			System.out.println("Update fail!");
		}

		input.close();
		
	}
	@Override
	public void newFTPDir(FTPClient ftp,String folderName) throws IOException{
		if(ftp.makeDirectory(folderName)){
        	System.out.println("Create folder Success!");
        }else{
        	System.out.println("Create folder fail!");
        }
	}
	@Override
	public FTPClient connectFTP() throws Exception{
		

		FTPClient ftp = new FTPClient();
		//建立連線
		ftp.connect("10.42.1.110");
   	

		//登入
		if (!ftp.login("wacos", "wacos")) {
			ftp.logout();
			throw new Exception("登入失敗");
		}
		//取得回應碼
		int reply = ftp.getReplyCode();

		System.out.println("reply:"+reply);
		//登入狀態
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new Exception("無回應");
		}           
  
		//FTP改為被動模式
		ftp.enterLocalPassiveMode();
   
		//改路徑
		ftp.changeWorkingDirectory("/CDR/script/tool/GPRS_flatrate/inputfile/test");   
   
		System.out.println("connectFtp Success!");
		
		return ftp;
	}
	
	@Override
	public void readFTPFile(FTPClient ftp) throws IOException{
		FTPFile[] fList = ftp.listFiles();
		
		 System.out.println("File List:");
		 
		for(FTPFile f : fList){
			 System.out.println((f.isDirectory()?"folder:":"file")+f.getName());
		}
		
	}

	@Override
	public void moveFile(String sourceDir,String DestDir,String fileName){
		File f = new File(sourceDir+"\\"+fileName);
		File f2 = new File(DestDir+"\\"+fileName);
		f.renameTo(f2);
	}
	@Override
	public void unGZIP(String workDir,String fName,String tempDir){
		String fileName = fName;
		GZIPInputStream gzi = null;
		BufferedOutputStream bos = null;
		try {
			gzi = new GZIPInputStream(new FileInputStream(new File(workDir,fName)));
			int to = fileName.lastIndexOf('.');

	        String toFileName = fileName.substring(0, to);
	        System.out.println(tempDir);
	        File targetFile = new File(tempDir,toFileName);
	        bos = new BufferedOutputStream(new FileOutputStream(targetFile));
	        int b;
	        byte[] d = new byte[1024];
	        while ((b = gzi.read(d)) > 0) {
                bos.write(d, 0, b);
            }
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			try {
				if(gzi!=null){
					gzi.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
			}
		}
	}

	@Override
	public Object nvl(Object msg,Object s){
		if(msg==null)
			msg = s;
		return msg;
	}
	
	
	@Override
	public Workbook createExcel(List<Map<String,String>> head,List<Map<String,Object>> data,String type) throws IOException{
		Workbook wb = null;
		int rowN = 0;
		int sheetN = 0;
		//建立xls檔案
		if(type.matches("^xls$")){
			wb = new HSSFWorkbook();  
			HSSFSheet sheet = (HSSFSheet) wb.createSheet("sheet"+sheetN++);  
			sheet.setColumnWidth(0, 20*256);
			sheet.setColumnWidth(1, 15*256);
			sheet.setColumnWidth(2, 20*256);
			HSSFRow row = sheet.createRow(rowN++);
			HSSFCell cell ;
			//欄位樣式
			HSSFCellStyle style = (HSSFCellStyle) wb.createCellStyle(); 

			//字型大小
			HSSFFont font = (HSSFFont) wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //粗體

			style.setFont(font);
			
			//寫入Header
			for(int col = 0 ;col < head.size() ;col++){
				cell = row.createCell(col);
				cell.setCellStyle(style);
				cell.setCellValue(head.get(col).get("name"));
			}
			
			for(int r = 0 ; r<data.size() ;r++){
				row = sheet.createRow(rowN++);
				for(int col = 0; col < head.size() ;col++){
					row.createCell(col).setCellValue(nvl(data.get(r).get(head.get(col).get("col")),"").toString());;
				}
				//滿頁換Sheet
				if(rowN==65534){
					sheet = (HSSFSheet) wb.createSheet("sheet"+sheetN++);
					rowN = 0;
				}
			}
			
		
		}
		
		//建立xlsx檔案
		if(type.matches("^xlsx$")){
			wb = new XSSFWorkbook();  
		}
		
		return wb;
	}
	
	@Override
	public void readExcel(String fileName) throws FileNotFoundException, IOException{
		
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		
		if(fileName.matches("^xlsx$")){
			wb = new XSSFWorkbook(new FileInputStream(new File(fileName)));
		}
		if(fileName.matches("^xls$")){
			wb = new HSSFWorkbook(new FileInputStream(new File(fileName)));
		}
		
		int sheetN = wb.getNumberOfSheets();//回傳總數
		
		for(int i = 0 ; i < sheetN ; i ++){
			sheet = wb.getSheet(wb.getSheetName(i));
			if(sheet == null ) continue;
			int rowN = sheet.getLastRowNum();//回傳最後一列的數字，回傳N-1
			for(int j = 0 ; j <= rowN ; j++){
				row = sheet.getRow(j);
				if(row == null ) continue;
				int colN = row.getLastCellNum();//回傳最後一行的數字，回傳N-1
				for(int k = 0 ; k <= colN ; k++){
					cell = row.getCell(k);
					if(cell == null ) continue;
					System.out.println(cell.toString());//直接印出
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){//判斷格式，格式錯誤會丟Exception
						System.out.println(cell.getStringCellValue());//以文字方式印出
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						System.out.println(cell.getNumericCellValue());//以文字方式印出
					}
					
				}
			}
		}
		
		
	}
	
	@Override
	public InputStream createExcelStream(List<Map<String,String>> head,List<Map<String,Object>> data,String type) throws IOException{
		if(type == null){
			System.out.println("type is null.");
			return null;
		}
		
		Workbook wb = null;
		if(type.matches("^xls$")){
			wb = createExcel(head, data, "xls");
		}
		//建立xlsx檔案
		if(type.matches("^xlsx$")){
			wb = createExcel(head, data, "xlsx");
		}
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        wb.write(os);  
        byte[] fileContent = os.toByteArray();  
        ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
        return is;
	}
	
	@Override
	public boolean createExcelFile(List<Map<String,String>> head,List<Map<String,Object>> data,String fileName) throws IOException{
		boolean result = false;
		if(fileName == null){
			System.out.println("File name is null.");
			return false;
		}
		
		Workbook wb = null;
		if(fileName.matches(".+\\.xls")){
			wb = createExcel(head, data, "xls");
		}
		//建立xlsx檔案
		if(fileName.matches(".+\\.xlsx")){
			wb = createExcel(head, data, "xlsx");
		}
		
		File f = new File(fileName);
		FileOutputStream os = new FileOutputStream(f);
		try {
			wb.write(os);
			result = true;
		} finally{
			os.close();
		}
		
		return result;
	}
	
	/**
	 * 指標性Merge sort
	 * 
	 * start 起點
	 * 
	 * end 終點(包含)，list.size()-1
	 */
	public static void mergeSort(List<Double> list,int start,int end){
		
		//如果只剩下一個單元，直接丟回
		if(start>=end)
			return;
		
		int middle = (int) Math.floor((end-start)/2);
		middle+=start;
		
		//merge left
		mergeSort(list,start,middle);
		//merge right
		mergeSort(list,middle+1,end);
		
		int i = start;
		int j = middle+1;
		
		//規則：
		//當第i的數字<=第j的數，i 已經是最前面，i比較數字改成第i+1位
		//當第i的數字   >第j的數，將第j的數字插入第i數字之前，比較數字改成第i+1位，j改成j+1位
		//j的前面永遠是i數列的比對終點
		//當i==j或是j>end時，比對結束，剩下的也已經在之前排序好
		
		while(i<j && j<=end){
			
			if(list.get(i)<=list.get(j)){
				i++;
			}else{
				double t = list.get(j);
				list.remove(j);
				list.add(i, t);
				i++;j++;
			}
			
		}
		System.out.println(start+":"+end);
		for(double d : list){
			System.out.print(d+" ");
		}
		System.out.println();
	}
	
	public void creatValidateImage(){
		
		char[] CHARS = {'2','3','4','5','6','7','8','9'
				,'A','B','C','D','E','F','G','H','J','K','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'
				,'a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z'
		};
		
		Random random = new Random();
		
		
		StringBuffer buffer = new StringBuffer();
		
		for(int i = 0 ; i < 6 ; i++)
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		
		String randomString = buffer.toString();
		
		
		//圖形寬高
		int width = 100;
		int height = 30;
		
		//隨機顏色
		Color color = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
		//找出對比色
		Color reverse = new Color(255-color.getRed(),255-color.getGreen(),255-color.getBlue());
		//建立彩色圖片
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		//繪圖元件
		Graphics2D g = bi.createGraphics();
		//字型
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
		//顏色
		g.setColor(color);
		//背景(填滿)
		g.fillRect(0, 0, width, height);
		//設定前景
		g.setColor(reverse);
		g.drawString(randomString, 18, 20);
		//隨機建立最多100個噪點，隨機位置
		for(int i = 0 ,n = random.nextInt(100) ; i < n ; i++)
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
		
		try {
			File outputfile = new File("C:/Users/ranger.kao/Desktop/valide.png");
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void parseXmlByDOM(String filePath){
		
		File xmlFile = new File(filePath);//讀取檔案
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); //以實例方式建立Document Builder Factory
		
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();//利用Factory建立 Document Builder
			Document document = builder.parse(xmlFile);//利用builder建立Document
			Element root = document.getDocumentElement();
			NodeList childNodes = root.getChildNodes();
			
			for(int i = 0; i<childNodes.getLength() ; i++){
				
				String nodeName = childNodes.item(i).getNodeName();
				NodeList nextChildNodes = childNodes.item(i).getChildNodes(); //下一層
				//...
				
			}
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	public void parseXmlBySAX(String filePath){
		File xmlFile = new File(filePath);//讀取檔案
		SAXParserFactory factory = SAXParserFactory.newInstance(); //以實例建立Parse Factory
		
		try {
			SAXParser parser = factory.newSAXParser(); //以Factory建立parser
			parser.parse(xmlFile, new MySaxHandler());//以parse解析檔案，並使用觸發器
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	class MySaxHandler extends DefaultHandler {
		
		private String content;
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			content = new String(ch,start,length);//遇到字元集時轉換為文字
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if("title".equals(qName)){
				System.out.println("標題："+content);
			}
			//....
		}
		
		@Override
		public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
			
		}
	}
	
	public void createXmlByJavaBean(String filePath){
		File xmlFile = new File(filePath);//儲存位置
		
		try {
			JAXBContext context = JAXBContext.newInstance(Article.class); //指定Bean建立上下文
			Marshaller m = context.createMarshaller();
			
			Article article = new Article();
			article.setTitle("title");
			article.setEmail("mail");
			article.setAuthor("author");
			
			m.marshal(article, xmlFile); //轉換
			
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
	}
	public void createJavaBeanByXml(String filePath){
		File xmlFile = new File(filePath);//儲存位置
		
		try {
			JAXBContext context = JAXBContext.newInstance(Article.class); //指定Bean建立上下文
			Unmarshaller u = context.createUnmarshaller();
			Article article =(Article) u.unmarshal(xmlFile);
		
			
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
	}
	
	@XmlRootElement//可使用@XmlRootElement(name = "article") 方式命名
	class Article{
		
		//@XmlElementWrapper(name = "DATA") 外面再包一層
		
		//@XmlElement(name = "ITEM") 元素命名
		private String title;
		private String author;
		private String email;
		
		
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
	
	/***加密編碼***/
	//單向
	//md5 EncodeTest
	@Override
	public String md5Encode(String source) throws NoSuchAlgorithmException{
		String input=source;
		 MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        return byteToString1(messageDigest);
	}
	
	//SHA EncodeTest
	@Override
	public String SHAEncode(String source) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA");  
        byte[] srcBytes = source.getBytes();  
        md.update(srcBytes);  
        byte[] resultBytes = md.digest();
        return byteToString1(resultBytes);  
	}
	public static String byteToString1(byte[] source){
		BigInteger number = new BigInteger(1, source);
        String hashtext = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
	}
	
	//雙向
	//RSA EncodeTest
	@Override
	public String RSAEncode(String source,RSAPublicKey publicKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		 //Cipher负责完成加密或解密工作，基于RSA  
        Cipher cipher = Cipher.getInstance("RSA");  
        //根据公钥，对Cipher对象进行初始化  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        byte[] resultBytes = cipher.doFinal(source.getBytes());  
        
        return byteToString2(resultBytes);  
	}
	//RSA DecodeTest
	@Override
	public String RSADecode(String source,RSAPrivateKey  privateKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, DecoderException{
		//Cipher负责完成加密或解密工作，基于RSA  
        Cipher cipher = Cipher.getInstance("RSA");  
        //根据公钥，对Cipher对象进行初始化  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        byte[] resultBytes = cipher.doFinal(Hex.decodeHex(source.toCharArray()));  
        return new String(resultBytes);  
	}
	
	/**
	 * 傳入Bean與Map，再利用反射中的Set去給值
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Override
	public void reflectSet(Object bean, Map<String, String> valMap) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> cls = bean.getClass();  
		for (Field field : cls.getDeclaredFields()) {
			String fieldSetName = parSetName(field.getName());  
            if (!checkSetMet(cls.getDeclaredMethods(), fieldSetName)) {  
                continue;  
            }  
            Method fieldSetMet = cls.getMethod(fieldSetName, field  
                    .getType());  
			String value = valMap.get(field.getName());  
			
			if (null != value && !"".equals(value)) {  
                  String fieldType = field.getType().getSimpleName();  
                  if ("String".equals(fieldType)) {  
                      fieldSetMet.invoke(bean, value);  
                  } else if ("Date".equals(fieldType)) {  
                     /* Date temp = parseDate(value);  
                      fieldSetMet.invoke(bean, temp); */ 
                  } else if ("Integer".equals(fieldType)|| "int".equals(fieldType)) {  
                      Integer intval = Integer.parseInt(value);  
                      fieldSetMet.invoke(bean, intval);  
                  } else if ("Long".equalsIgnoreCase(fieldType)) {  
                      Long temp = Long.parseLong(value);  
                      fieldSetMet.invoke(bean, temp);  
                  } else if ("Double".equalsIgnoreCase(fieldType)) {  
                      Double temp = Double.parseDouble(value);  
                      fieldSetMet.invoke(bean, temp);  
                  } else if ("Boolean".equalsIgnoreCase(fieldType)) {  
                      Boolean temp = Boolean.parseBoolean(value);  
                      fieldSetMet.invoke(bean, temp);  
                  } else {  
                      System.out.println("not supper type" + fieldType);  
                  }  
              }  
		}
		
		
	}
	 public static boolean checkSetMet(Method[] methods, String fieldSetMet) {		 
        for (Method met : methods) {  
            if (fieldSetMet.equals(met.getName())) {  
                return true;  
            }  
        }  
        return false;  
    }  
	 public static String parSetName(String fieldName) {  
	        if (null == fieldName || "".equals(fieldName)) {  
	            return null;  
	        }  
	        return "set" + fieldName.substring(0, 1).toUpperCase()  
	                + fieldName.substring(1);  
	    } 
	 
	 
	public static String byteToString2(byte[] source){
        return new String(Hex.encodeHex(source));
	}
	
	/***
	  * 當連線超過一定時間無人使用後進行關閉
	  * @author ranger.kao
	  *
	  */
	 public class connectionControl{
		 
		 Connection conn;
		 long lastUsingTime = 0;
		 long maintainTime = 1000*60*5;
		 long checkStartTime = new Date().getTime();
		 
		 String DriverClass,URL,UserName,PassWord;
		 
		 public connectionControl(String DriverClass,String URL,String UserName,String PassWord){
			 System.out.println("Initialize.");
			 this.DriverClass = DriverClass;
			 this.URL = URL;
			 this.UserName = UserName;
			 this.PassWord = PassWord;

			 new Timer().schedule(new taskClass(),checkStartTime,1000*60*5);
		 }
		 
		 public Connection getConnection() throws SQLException, ClassNotFoundException{
			 System.out.println("get connction.");
			 lastUsingTime = System.currentTimeMillis();
			 if(conn == null)
				 createConnect();
			 return conn;
		 }
		 
		 private void createConnect() throws SQLException, ClassNotFoundException{
			System.out.println("create connction.");
			Class.forName(DriverClass);
			conn = DriverManager.getConnection(URL, UserName, PassWord); 
		 }
		 private void closeConnect(){
			 try {
				 System.out.println("close connction.");
				conn.close();
			} catch (SQLException e) {}
		 }
		 
		 public class taskClass extends TimerTask{
			@Override
			public void run() {
				if(System.currentTimeMillis() - lastUsingTime > maintainTime){
					closeConnect();
					System.out.println("cling connction.");
					conn = null;
				}
			}
		 }
	 }
	 /***
	  * 連接池設計，可設定最大連線數，等待極限值，
	  * 當等待過久允許開新連線
	  * 定時檢查連線是否正常
	  * @author ranger.kao
	  *
	  */
	 public static class connectionPooling{
		 
			static int min = 0;
			static int max = 5;
			static int existed = 0;
			static int waitingLimit = 100; 
			long checkStartTime = new Date().getTime();
			static List<Connection> connections = new ArrayList<Connection>();
			 
			static String DriverClass;
			static String URL;
			static String UserName;
			static String PassWord;
			 
			public connectionPooling(String DriverClass,String URL,String UserName,String PassWord){
				 System.out.println("Initialize.");
				 connectionPooling.DriverClass = DriverClass;
				 connectionPooling.URL = URL;
				 connectionPooling.UserName = UserName;
				 connectionPooling.PassWord = PassWord;

				 //確認Connection連線狀態
				 new Timer().schedule(new taskClass(),checkStartTime,1000*60*60);
			 }
			
			static public Connection getConnection() throws ClassNotFoundException, SQLException{
				return getConnection(0);
			}
			 static public Connection getConnection(int i) throws ClassNotFoundException, SQLException{
				 
				if(connections.size()==min){
					if(existed >= max && i<waitingLimit){
						System.out.println("wait connction.");
						try {
							Thread.sleep(1000*10);
						} catch (InterruptedException e) {}
						getConnection(i+10);
					}
					return cerateConnection();
				}else{
					System.out.println("get connction.");
					int index = connections.size()-1;
					return connections.remove(index);
				}
			 }
			 
			static public void releaseConnection(Connection conn){
				 System.out.println("release connction.");
				 if(existed > max){
					try {
						conn.close();
					} catch (SQLException e) {}
				 }else{
					 connections.add(conn);
				 }
				 System.out.println("remain "+connections.size()+" connction.");
			 }
			 
			 private static Connection cerateConnection() throws ClassNotFoundException, SQLException{
				 System.out.println("create connction.");
				 existed++;
				 Class.forName(DriverClass);
				 return DriverManager.getConnection(URL, UserName, PassWord); 
			 }
			 
			 public class taskClass extends TimerTask{
				@Override
				public void run() {
					System.out.println("check connction.");
					synchronized(connections){
						Iterator<Connection> it = connections.iterator();
						Statement st = null;
						while(it.hasNext()){
							Connection c = it.next();
							try {
								st = c.createStatement();
								st.execute("select 'OK' from dual.");
							} catch (SQLException e) {
								System.out.println("close and remove connction.");
								try {
									c.close();
								} catch (SQLException e1) {}
								it.remove();
								existed--;
							}finally{
								try {
									st.close();
								} catch (SQLException e) {}
							}
						}

						System.out.println("check result . remain "+connections.size()+" connction.");
					}
				}
			}
		 }
	
	
	
	public static void main(String[] args){
		Jatool j = new Jatool();
		//j.creatValidateImage();
	}

	@Override
	public Properties getProperties(String fileName) throws FileNotFoundException, IOException {
		Properties props = new Properties();  
		props.load(new FileInputStream(fileName));
		return props;
	}
	@Override
	public Logger setLogger(Properties props){
		PropertyConfigurator.configure(props);
		Logger logger =Logger.getLogger(this.getClass());
		logger.info("Logger Load Success!");
		return logger;
	}

	@Override
	public boolean onlyInteger(String content) {
		return regularMatch(content, "^\\d+$");
	}

	@Override
	public boolean onlyString(String content) {
		return regularMatch(content, "^\\D+$");
	}

	
}
