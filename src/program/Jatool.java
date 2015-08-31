package program;


import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//190
/*import com.infotech.smpp.SMPPServicesStub;
import com.infotech.smpp.SMPPServicesStub.SendSMPP;
import com.infotech.smpp.SMPPServicesStub.SendSMPPResponse;*/

//199
/*import com.iglomo.SMPPServicesStub;
import com.iglomo.SMPPServicesStub.SendSMPP;
import com.iglomo.SMPPServicesStub.SendSMPPResponse;*/

public class Jatool implements IJatool{

	
	private void logControl(Logger logger,String type,String message){
		if(logger==null){
			System.out.println(message);
		}else{
			if("info".equalsIgnoreCase(type)){
				logger.info(message);
			}else if("debug".equalsIgnoreCase(type)){
				logger.debug(message);
			}else if("error".equalsIgnoreCase(type)){
				logger.error(message);
			}
		}
	}
	
	@Override
	public void sendMail(Logger logger,String sender, String receiver, String subject,String content) throws Exception {
		sendMail(logger,null,sender,receiver,subject,content);
	}
	

	@Override
	public void sendMail(Logger logger,Properties props,String sender,String receiver,String subject,String content) throws Exception {

		if(props==null){
			props=getProperties1();
		}
		logControl(logger,"info","get Properites!");			
		
		final String host=props.getProperty("mail.smtp.host");
		logControl(logger,"info","Connect to Host : "+ host);
		
		String p=props.getProperty("mail.smtp.port");
		final Integer port=((p==null||"".equals(p))?null:Integer.valueOf(p));
		logControl(logger,"info","port : "+port);
		
		final String username=props.getProperty("mail.username");
		final String passwd=props.getProperty("mail.password");		
		
		String auth = props.getProperty("mail.smtp.auth");
		boolean authFlag = true;
		if(auth==null||"".equals(auth)||"false".equals(auth)){
			authFlag=false;
		}
		logControl(logger,"info","use authority : "+authFlag);
		
		boolean sessionDebug = false; 
		boolean singleBody=true;
		
		/*if(sender==null || "".equals(sender)){
			if(username==null){
				logControl(logger,"error","No sender and No UserName Set!");
				throw new Exception("No sender and No UserName Set!");
			}
			sender=username;			
		}else{
			if(username!=null && !"".equals(username) &&!sender.equalsIgnoreCase(username)){
				logControl(logger,"error","sender is not equals to UserName !");
				return;
			}
		}*/
		
		InternetAddress[] address = null; 
		String ccList="";
		
		
		StringBuilder messageText = new StringBuilder(); 
		messageText.append("<html><body>"); 
		messageText.append(content); 
		messageText.append("</body></html>"); 
		
		javax.mail.Session mailSession=null;
		logControl(logger,"debug","Creat mail Session!");
		if(authFlag){
			// construct a mail session 
			mailSession = javax.mail.Session.getInstance(props,new javax.mail.Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(username, passwd);
			    }
			}); 
		}else{
			mailSession = javax.mail.Session.getDefaultInstance(props);
		}
		
		mailSession.setDebug(sessionDebug); 
		
			Message msg = new MimeMessage(mailSession); 
			
			try {
				msg.setFrom(new InternetAddress(sender));			// mail sender 
			} catch (Exception e) {
				msg.setFrom();
			}
			
			address = InternetAddress.parse(receiver, false); // mail recievers 
			msg.setRecipients(Message.RecipientType.TO, address); 
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccList)); // mail cc 
			
			msg.setSubject(subject); // mail's subject 
			msg.setSentDate(new Date());// mail's sending time 
			logControl(logger,"debug","set mail content!");
			if(singleBody){
				//msg.setText(messageText.toString());
			    msg.setContent(messageText.toString(), "text/html;charset=UTF-8");
			}else{
				MimeBodyPart mbp = new MimeBodyPart();// mail's charset
				mbp.setContent(messageText.toString(), "text/html; charset=utf8"); 
				Multipart mp = new MimeMultipart(); 
				mp.addBodyPart(mbp); 
				msg.setContent(mp); 
			}

			if(receiver==null ||"".equals(receiver)){
				System.out.println("Can't send email without receiver!");
			}else{
				Transport.send(msg);
			}
			logControl(logger,"info","sending mail from "+sender+" to "+receiver+"\n<br>"+
										"Subject : "+msg.getSubject()+"\n<br>"+
										"Content : "+msg.getContent()+"\n<br>"+
										"SendDate: "+msg.getSentDate());			
	}
	@Override
	public void sendMailforLinux(String msg) throws Exception{
		sendMailforLinux(msg,null);
	}
	@Override
	public void sendMailforLinux(String msg,String receiver) throws Exception{
		String ip ="";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		if(receiver==null ||"".equals(receiver)){
			throw new Exception("No receiver and No UserName Set!");
		}
			
		String Receiver =receiver;
		
		msg=msg+" from location "+ip;			
		
		String [] cmd=new String[3];
		cmd[0]="/bin/bash";
		cmd[1]="-c";
		cmd[2]= "/bin/echo \""+msg+"\" | /bin/mailx -s \"SMPP System alert\" "+Receiver ;

		try{
			Process p = Runtime.getRuntime().exec (cmd);
			p.waitFor();
			System.out.println("send mail cmd:"+cmd);
		}catch (Exception e){
			System.out.println("send mail fail:"+msg);
		}
	}
	
	//mail
	@Override
	public Properties getProperties1(){
		Properties result=new Properties();
		
		result.setProperty("mail.smtp.host", "202.133.250.242");
		result.setProperty("mail.transport.protocol", "smtp");
		//result.setProperty("mail.smtp.port", "");//���]�w�w�]��25
		
		result.setProperty("mail.smtp.auth", "true");
		
		//TLS authentication 
		//result.setProperty("mail.smtp.starttls.enable", "true");

		//SSL authentication 
		//result.setProperty("mail.smtp.socketFactory.port", "465");
		//result.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");		
		
		
		//�ۭq�Ѽ�
		result.setProperty("mail.username", "ranger.kao@sim2Travel.com");
		result.setProperty("mail.password", "kkk770204");
		
		return result;
	}
	//Log
	@Override
	public Properties getProperties2(){
		Properties result=new Properties();
		
		result.setProperty("log4j.rootCategory", "DEBUG, stdout, FileOutput");
		
		result.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		result.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		result.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%d [%5p] (%F:%L) - %m%n");
		
		result.setProperty("log4j.appender.FileOutput", "org.apache.log4j.DailyRollingFileAppender");
		result.setProperty("log4j.appender.FileOutput.layout", "org.apache.log4j.PatternLayout");
		result.setProperty("log4j.appender.FileOutput.layout.ConversionPattern", "%d [%5p] (%F:%L) - %m%n");
		result.setProperty("log4j.appender.FileOutput.DatePattern", "'.'yyyyMMdd");
		result.setProperty("log4j.appender.FileOutput.File", "AddonService.log");

		return result;
	}

	
	@Override
	public Connection connDB(String DriverClass, String URL,
			String UserName, String PassWord) throws ClassNotFoundException, SQLException {
		Connection conn = null;

			Class.forName(DriverClass);
			conn = DriverManager.getConnection(URL, UserName, PassWord);
		return conn;
	}
	
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

	@Override
	public Date getMonthFirstDate(Date date) {
		
		Calendar calendar = Calendar.getInstance();//�q�{����e�ɶ�
		Date monthFirstDate=null;

		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		monthFirstDate=calendar.getTime();
		calendar.clear();

		return monthFirstDate;
	}

	@Override
	public Date getMonthLastDate(Date date) {
		Calendar calendar = Calendar.getInstance();//�q�{����e�ɶ�
		Date monthLastDate=null;
		
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		monthLastDate= calendar.getTime();
		calendar.clear();
		return monthLastDate;
	}
	
	@Override
	public Date getDayFirstDate(Date date) {
		
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));//�q�{����e�ɶ�
		Date monthFirstDate=null;

		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		monthFirstDate=calendar.getTime();
		calendar.clear();

		return monthFirstDate;
	}

	@Override
	public Date getDayLastDate(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));//�q�{����e�ɶ�
		Date monthLastDate=null;
		
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		monthLastDate= calendar.getTime();
		calendar.clear();
		return monthLastDate;
	}

	@Override
	public java.sql.Date convertJaveUtilDate_To_JavaSqlDate(java.util.Date date) {
		
		return new java.sql.Date(date.getTime());
	}

	@Override
	public java.util.Date convertJaveSqlDate_To_JavaUtilDate(java.sql.Date date) {
		return new java.util.Date(date.getTime());
	}

	String iniform="yyyy/MM/dd HH:mm:ss";
	@Override
	public String DateFormat(){
		DateFormat dateFormat = new SimpleDateFormat(iniform);
		return dateFormat.format(new Date());
	}
	
	@Override
	public String DateFormat(Date date, String form) {
		
		if(date==null) date=new Date();
		if(form==null ||"".equals(form)) form=iniform;
		
		DateFormat dateFormat = new SimpleDateFormat(form);
		return dateFormat.format(date);
	}

	@Override
	public Date DateFormat(String dateString, String form) throws ParseException {
		Date result=new Date();
		
		if(dateString==null) return result;
		
		if(form==null ||"".equals(form)) form=iniform;
		DateFormat dateFormat = new SimpleDateFormat(form);
		result=dateFormat.parse(dateString);
		
		return result;
	}
	
	@Override
	public String HttpPost(String url,String param,String charset) throws IOException{
		URL obj = new URL(url);
		
		if(charset!=null && !"".equals(charset))
			param=URLEncoder.encode(param, charset);
		
		
		HttpURLConnection con =  (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		/*con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");*/
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(param);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + new String(param.getBytes("ISO8859-1")));
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		return(response.toString());
	}
	
	@Override
	public Double FormatDouble(Double value,String form){
		
		if(form==null || "".equals(form)){
			form="0.00";
		}
			
		/*DecimalFormat df = new DecimalFormat(form);   
		String str=df.format(value);*/
		
		return Double.valueOf(new DecimalFormat(form).format(value));
	}
	
	@Override
	public String FormatNumString(Double value){
		return FormatNumString(value,null);
	}
	
	@Override
	public String FormatNumString(Double value,String form){
		if(form==null || "".equals(form)){
			form="#,##0.00";
		}
			
		DecimalFormat df = new DecimalFormat(form);   
		String str=df.format(value);
		
		return str;
	}

	@Override
	public void readtxt(String filePath) {
		//String filePath =BillReport.class.getClassLoader().getResource("").toString().replace("file:", "")+ "source/";

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8")); 
			// ��wŪ���󪺽s�X�榡�A�H�K�X�{����ýX
			
			String str = null;
			
			while ((str = reader.readLine()) != null) {
				
				System.out.println(str);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writetxt(String content) {
		BufferedWriter fw = null;
		
		try {
			File file = new File("c://text.txt");
			if(!file.exists())
				file.createNewFile();
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // ���I�s�X�榡�A�H�KŪ��ɤ���r�Ų��`
			fw.append("�ڼg�J�����e");
			fw.newLine();
			fw.append("�ڤS�g�J�����e");
			fw.flush(); // �����g�J�w�s�������e
		} catch (Exception e) {
			e.printStackTrace();
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
	
	@Override
	public void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		}catch(Exception e) {
			System.out.println("新建目錄操作出錯");
			e.printStackTrace();
		}
	}

	@Override
	public boolean regularMatch(String content, String regex) {
		
		return content.matches(regex);
	}

	@Override
	public Pattern regularMatch(String regex) {

		Pattern p =Pattern.compile(regex);
		return p;
	}

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


	@Override
	public String parseDBURL(String DBType, String ip, String port, String DB,
			String charSet) {
		String URL="jdbc:"+DBType+":@"+ip+":"+port+":"+DB+"";
		
		if(charSet!=null &&!"".equals(charSet))
			URL+="?characterEncoding="+charSet;
		
		return URL;
	}
	
	@Override
	public Double roundUpOrDdown(Double value,String method,int digit){
		
		Double result = 0D;
		
		if("ROUND_HALF_UP".equals(method))		result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
		if("ROUND_HALF_DOWN".equals(method))	result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_HALF_DOWN).doubleValue();
		if("ROUND_HALF_EVEN".equals(method))	result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		if("ROUND_CEILING".equals(method))		result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_CEILING).doubleValue();
		if("ROUND_FLOOR".equals(method))		result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_FLOOR).doubleValue();
		if("ROUND_UP".equals(method))			result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_UP).doubleValue();
		if("ROUND_DOWN".equals(method))			result =new BigDecimal(value).setScale(digit, BigDecimal.ROUND_DOWN).doubleValue();
		
		return result;
	}


	@Override
	public Calendar getCalendar(){
		return Calendar.getInstance();
	}
	
	
	@Override
	public Calendar getCalendar(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}


	@Override
	public Calendar getCalendar(String date, String formate) throws ParseException {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat(formate).parse(date));
		return calendar;
	}


	@Override
	public Calendar getCalendarByCalculate(Integer year, Integer month,
			Integer day, Integer hour, Integer min, Integer sec) {
		
		Calendar calendar=Calendar.getInstance();
		
		if(year!=null)
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+year);
		if(month!=null)
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+month);
		if(day!=null)
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+day);
		if(hour!=null)
			calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+hour);
		if(min!=null)
			calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)+min);
		if(sec!=null)
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)+sec);
		
		return calendar;
	}


	@Override
	public String convertCharset(String content, String sourceCharset,
			String torgetCharset) throws UnsupportedEncodingException {
		if(sourceCharset==null)
			return new String(content.getBytes(),torgetCharset);
		if(torgetCharset==null)
			return new String(content.getBytes(sourceCharset));
		return new String(content.getBytes(sourceCharset),torgetCharset);
	}


	@Override
	public String getLocalIP() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress()+"";
	}


	@Override
	public String getProgramDir(Class<?> className) {
		return className.getClassLoader().getResource("").toString();
	}


	@Override
	public String [] splitIP(String ip) {
		return ip.split("\\.");
	}


	@Override
	public void regularExcute(long periodTime, TimerTask run) {
		Timer timer =new Timer();
		timer.schedule(run,0, periodTime);
	}


	@Override
	public void regularExcute(long periodTime, long delay, TimerTask run) {
		Timer timer =new Timer();
		timer.schedule(run,delay, periodTime);
	}


	@Override
	public void regularExcute(long periodTime, Date FirstExecuteTime,
			TimerTask run) {
		Timer timer =new Timer();
		timer.schedule(run,FirstExecuteTime, periodTime);
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
	
}
