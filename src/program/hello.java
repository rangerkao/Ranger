package program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.naming.java.javaURLContextFactory;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.MessageType;
import org.jsmpp.util.DeliveryReceiptState;

import program.Jatool.connectionControl.taskClass;

public class hello {
	private static String msg;
	static IJatool tool =new Jatool();
	static Logger logger;
	


	
	public static void main(String[] args) throws UnknownHostException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, DecoderException{
		
		Properties prop = getProperties();
		PropertyConfigurator.configure(prop);
		logger =Logger.getLogger(hello.class);
		logger.info("Logger Load Success!");

		//System.out.println("1d23".matches("^\\d+$"));
		
		/*try {
			tool.sendMailwithoutAuthenticator("202.133.250.242",25,"smtp",
					"ranger.kao@sim2travel.com","kkk770204",
					"mailSample","ranger.kao@sim2travel.com,k1988242001@gmail.com",
					"mail test","mail for test.");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*List<Double> list = new ArrayList<Double>();
		
		for(int i = 0 ;i<10;i++){
			list.add(Math.random()*1000);
		}

		
		mergeSort(list,0,list.size()-1);		*/
		
		
		/*Calendar endTime = Calendar.getInstance();
		
		endTime.setTime(new Date());
		endTime.set(Calendar.DAY_OF_YEAR, endTime.get(Calendar.DAY_OF_YEAR)+1);
		endTime.set(Calendar.HOUR_OF_DAY, 0);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		System.out.println(endTime.getTime());*/
		
		/*
		String ipaddr = "221.177.201.14";		
		
		if(ipaddr.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$")){
			String [] ips = ipaddr.split("\\.");
			long ipNumber =0L;
			for(int j=0;j<ips.length;j++){
				ipNumber+=Integer.parseInt(ips[j])*Math.pow(256, 3-j);
			}
			System.out.println("ipNumber="+ipNumber);
		}*/
		
		/*long st = System.currentTimeMillis();
		System.out.println(st);
		readTxt();
		System.out.println("ended pass time="+(System.currentTimeMillis()-st));*/
	
		
		//DBTest();
		
		/*Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR)-59);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		System.out.println(c.getTime());*/
		
		/*System.out.println(Integer.MAX_VALUE);
		System.out.println(Double.MAX_VALUE);
		*/

		/*File f = new File("C:\\Users\\ranger.kao\\Desktop\\1104Addon\\20151223\\AddServer.002.454120260258715.20151221.txt");
		System.out.println(f.length());*/
		
		
		
		/*System.out.println(MessageType.SMSC_DEL_RECEIPT.value());
		System.out.println(MessageType.DEFAULT.value());*/
		
		
		/*try {
			System.out.println(java.net.URLEncoder.encode("UNUSED=on&UDH=&Data=B30200F1&PID=7F&DCS=F6&Submit=Submit&Binary=1","Big5"));
			System.out.println(java.net.URLEncoder.encode("家庭","big5"));
			System.out.println(java.net.URLEncoder.encode("家庭","UTF-16BE"));
			System.out.println(java.net.URLEncoder.encode("UNUSED=on&UDH=&Data=b00c2b3835323536303935383332&PID=7F&DCS=F6&Submit=Submit&Binary=1","UTF-16BE"));
			System.out.println(java.net.URLEncoder.encode("D=","UTF-16BE"));
			System.out.println(java.net.URLDecoder.decode("%90%19%66%2F%54%2B%51%69%52%47%7C%21%8A%0A%4E%4B%95%77%7C%21%8A%0A%7B%C4%4F%8B%FF%1A%60%A8%53%EF%7D%93%75%31%00%20%00%55%00%44%00%48%00%49%00%20%53%CA%00%20%00%44","UTF-16BE"));
			System.out.println(java.net.URLDecoder.decode("%90%19%00%49%00%4D","UTF-16BE"));

			System.out.println(java.net.URLEncoder.encode("簡訊測試","UTF-16BE"));
			
			String s = "UNUSED=on&UDH=&Data=B30200F1&PID=7F&DCS=F6&Submit=Submit&Binary=1";
			for(byte b : s.getBytes("UTF-16BE")){
				System.out.print(b+"\t");
			}
			System.out.println();
			byte e = -118;
			for(byte b : s.getBytes("UTF-16BE")){
				String c = Integer.toHexString(b);
				
				System.out.print((c.length()==1?"0"+c:(c.length()>2?c.substring(c.length()-2):c))+"");
			}
			System.out.println();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	*/
		
		//one way encode
		/*System.out.println("MD5 encodeing result : "+md5Encode("panda"));	
		System.out.println("MD5 encodeing result : "+md5Encode("yvonne"));	
		System.out.println("MD5 encodeing result : "+md5Encode("zora"));	
		System.out.println("MD5 encodeing result : "+md5Encode("helen"));	
		System.out.println("MD5 encodeing result : "+md5Encode("wendy"));	*/
		//System.out.println("SHA encodeing result : "+SHAEncode("Sim217Life"));	
		
		//非對稱加密(解密必須保留當初建立的key)
		/*//KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        //初始化密钥对生成器，密钥大小为1024位  
        keyPairGen.initialize(1024);  
        //生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        //得到私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();   
        System.out.println("private Key:"+privateKey.toString());
        //得到公钥  
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        System.out.println("publicKey Key:"+publicKey.toString());
        
        String s = RSAEncode("source",publicKey);
        System.out.println("RSA encodeing result : "+RSAEncode("source",publicKey));
        System.out.println("RSA Decodeing result : "+RSADecode(s,privateKey));*/

		/*
		System.out.println("abc"+"\t"+"abc".getBytes().length);
		System.out.println("中華門號"+"\t"+"中華門號".getBytes().length);
		System.out.println("主新德科技股份有限公司"+"\t"+"主新德科技股份有限公司".getBytes().length);
		String report = "";
		int[] sf = new int[]{25,20,20,20,30,14,45,45,60,25,10};
		String [] v = new String[]{
				"中華門號","起始時間","結束時間","身份證字號","Email","已警示","建立時間","取消時間","客戶姓名","進線者姓名","手機型號"						
				};
		report += pfString(sf,v);
		
		sf = new int[]{13,10,10,12,30,7,20,20,60,30,10};
		Connection conn=getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql=
				"SELECT A.PID,B.FOLLOWMENUMBER CHTMSISDN,A.SERVICEID,A.MCC,A.ALERTED,A.ID,A.CALLER_NAME,A.CUSTOMER_NAME,A.PHONE_TYPE,A.EMAIL,A.CANCEL_REASON, "
				+ "A.START_DATE,A.END_DATE,"
				+ "TO_CHAR(A.CREATE_TIME,'yyyy/MM/dd hh24:mi:ss') CREATE_TIME,TO_CHAR(A.CANCEL_TIME,'yyyy/MM/dd hh24:mi:ss') CANCEL_TIME "
				+ "from HUR_VOLUME_POCKET A,FOLLOWMEDATA B "
				+ "WHERE A.SERVICEID = B.SERVICEID AND A.TYPE=0 AND B.FOLLOWMENUMBER like '886%' "
				+ "ORDER BY A.START_DATE DESC ";
		try {
			st = conn.createStatement();
			rs=st.executeQuery(sql);
			
			while(rs.next()){
				report += pfString(sf,new String[]{
						rs.getString("CHTMSISDN"),
						rs.getString("START_DATE"),
						rs.getString("END_DATE"),
						nvl(rs.getString("ID")," "),
						nvl(rs.getString("EMAIL")," "),
						rs.getString("ALERTED"),
						rs.getString("CREATE_TIME"),
						nvl(rs.getString("CANCEL_TIME")," "),
						convertString(rs.getString("CUSTOMER_NAME"),"ISO-8859-1","Big5"),
						convertString(rs.getString("CALLER_NAME"),"ISO-8859-1","Big5"),
						convertString(rs.getString("PHONE_TYPE"),"ISO-8859-1","Big5"),
						});
			}
			
			System.out.println(report);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(st != null)
					st.close();
				if(rs!=null)
					rs.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		*/
		
		/*Connection conn=getConnection();
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("time 1");
		
		try {
			st.execute("select 'A' from dual");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("time 2");*/
		
		
	}
	public static String pfString(int[] f,String[] value){
		String r ="";
		/*int sum = 0;
		for(int i = 0 ; i<f.length ; i++){
			
			int j = 0;
			String v = "";
			if(i<value.length){
				j = value[i].getBytes().length;
				v = value[i];
			}
			r +=v;
			for(;j<f[i];j++)
				r+=" ";
			
			sum+=f[i];
		}
		r+="\n";*/
		if(value.length>0)
			r+=value[0];
		if(value.length>1){
			for(int i = 1;i<value.length;i++){
				r+="|"+value[i];
			}
		}
		r+="\n";
		return r;
	}
	
	public static String convertString(String msg,String sCharset,String dCharset) throws UnsupportedEncodingException{
		
		if(msg==null)
			msg=" ";
		
		return sCharset==null? new String(msg.getBytes(),dCharset):new String(msg.getBytes(sCharset),dCharset);
	}
	public static String nvl(String msg,String s){
		if(msg==null)
			msg = s;
		return msg;
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
	
	
	
	static Map<String,String> excludeTWNLDNUMBER = new HashMap<String,String>();
	public static void readTxt(){
		BufferedReader reader = null;
		String str = null;
		try {
			String path = hello.class.getClassLoader().getResource("").getPath();
			System.out.println("path:"+path);
			String filePath = path+"excludeNumbers.txt";
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			excludeTWNLDNUMBER.clear();
			while ((str = reader.readLine()) != null) {
				String s=str.trim();
				String[] numbers = s.split("\t");
				
				if(numbers.length>=2){
					System.out.println(numbers[0]+","+numbers[1]);
					excludeTWNLDNUMBER.put(numbers[0], numbers[1]);	
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void CalendarTest(){
		Calendar calendar =Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+1,0,0,0);
		System.out.println("calendar:"+calendar.getTime());
	}
	
	//簡訊測試   分段
	public static void breakSMSTest(){
		
		String msg = "*環球卡高用量提醒*提醒您本月數據用量上網金額統計至前{{bp}}一小時加部份推估，已約達NT65,000.00，"
				+ "實際用量及使用金額以帳單為準。另您已{{bp}}約定上網金額不設限，感謝您的留意。如需諮詢請電客服*123#。";
		
		int number = 100;
		int length = msg.length();
		byte[] b = msg.getBytes();
		length = b.length;
		int msgN = length/number;
		if(length%number>0)
			msgN += 1;
		String [] sub =new String[msgN];
		
		for(int i=0;i<msgN;i++){
			int last = (i+1)*number;
			if(last>length)
				last=length;
			
			byte[] c=new byte[last-i*number];
			
			System.arraycopy(b, i*number , c, 0, last-i*number);

				sub[i]=new String(c);
	
			sub[i]=msg.substring(i*number,last);
		}
		//String [] sub =msg.split("\\{\\{bp\\}\\}");
		for(String s: sub){
			System.out.println(s);
		}
	}
	
	public static void DBTest(){
		
		String param="測試123.33215測次側側側";
		try {
			query();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			updateDB(999,param);
			updateDB(999,null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static void jsmppTest(){
		//狀態測試
		DeliveryReceipt delReceipt = new DeliveryReceipt();
		delReceipt.setFinalStatus(DeliveryReceiptState.ACCEPTD);
		System.out.println(delReceipt.getFinalStatus().value()+1);

	}
	//簡訊發送測試
	public static void sendSMSTest(){
		
		String phone=null;
		String msg=null;
	
		try {
			
			phone="886989235253";	msg="測試";
			
			
			setSMSPostParam(new String(msg.getBytes("big5"),"ISO-8859-1"),phone);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public static String byteToString2(byte[] source){
        return new String(Hex.encodeHex(source));
	}
	
	//RSA EncodeTest
	public static String RSAEncode(String source,RSAPublicKey publicKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		 //Cipher负责完成加密或解密工作，基于RSA  
        Cipher cipher = Cipher.getInstance("RSA");  
        //根据公钥，对Cipher对象进行初始化  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        byte[] resultBytes = cipher.doFinal(source.getBytes());  
        
        return byteToString2(resultBytes);  
	}
	//RSA DecodeTest
	public static String RSADecode(String source,RSAPrivateKey  privateKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, DecoderException{
		//Cipher负责完成加密或解密工作，基于RSA  
        Cipher cipher = Cipher.getInstance("RSA");  
        //根据公钥，对Cipher对象进行初始化  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        byte[] resultBytes = cipher.doFinal(Hex.decodeHex(source.toCharArray()));  
        return new String(resultBytes);  
	}
	
	//md5 EncodeTest
	public static String md5Encode(String source) throws NoSuchAlgorithmException{
		String input=source;
		 MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        return byteToString1(messageDigest);
	}
	
	//SHA EncodeTest
	public static String SHAEncode(String source) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA");  
        byte[] srcBytes = source.getBytes();  
        md.update(srcBytes);  
        byte[] resultBytes = md.digest();
        return byteToString1(resultBytes);  
	}
	
	static Connection getConnection()
	  {
		
		try
	    {
			//Class.forName("org.postgresql.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("com.mysql.jdbc.Driver");
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
	      //localConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.42.1.101:1521:S2TBSDEV", "foyadev", "foyadev");
	      localConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.42.1.80:1521:s2tbs", "s2tbsadm", "s2tbsadm");
	      //localConnection = DriverManager.getConnection("jdbc:mysql://192.168.10.199:3306/CRM_DB?characterEncoding=utf8", "crmuser", "crm");
	    }
	    catch (Exception localException)
	    {
	      System.err.println("ERROR DB: failed to connect!");
	      msg = ("ERROR DB: failed to connect!" + localException.getMessage());
	    }
	    return localConnection;
	  }
	
	static void updateDB(int num,String param) throws UnsupportedEncodingException{
		Connection conn=getConnection();
		if(conn==null){
			System.out.println("connection is null");
			
		}else{

			try {
				
				if(param!=null && !"".equals(param)){
					PreparedStatement pst = conn.prepareStatement("UPDATE HUR_SMS_COMTENT A SET A.COMTENT =? WHERE A.ID=?");
					String pm=new String(param.getBytes("BIG5"),"ISO8859-1");
					pst.setString(1, pm);
					pst.setInt(2, num);
					pst.executeUpdate();
					
					pst.close();
				}

				PreparedStatement pst2 = conn.prepareStatement("select A.comtent from HUR_SMS_COMTENT A WHERE A.ID=?");
				pst2.setInt(1, num);
				ResultSet rs=pst2.executeQuery();
			
				while(rs.next()){
					String rss=rs.getString("comtent");
					rss=new String(rss.getBytes("ISO8859-1"),"BIG5");
					System.out.println(rss);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
	
	@SuppressWarnings("unchecked")
	static void query() throws UnsupportedEncodingException{
		Connection conn=getConnection();

		
		//String sql = "SELECT SUBS_NAME FROM CRM_SUBSCRIBERS A WHERE A.SUBS_NAME like to_nchar('%"+name+"%') ";
		String sql=
				"select A.COMPANY_ID,A.NAME,B.CH_NAME,C.HSCODE "
				+ "from MARKETING_DB_COMPANY A,MARKETING_DB_COUNTRY B,MARKETING_DB_DATA C,MARKETING_DB_HSCODE D "
				+ "where A.COMPANY_ID = C.COMPANY_ID and B.CODE = C.COUNTRY and C.HSCODE = D.CODE and rownum=1 ";

		
		if(conn==null){
			System.out.println("connection is null");
			
		}else{

			Statement st = null ;
			ResultSet rs = null ;
			PreparedStatement ps = null ;
			try {

				
				st=conn.createStatement();
				System.out.println("SQL:"+sql);
				rs = st.executeQuery(sql);

				Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
				
				while(rs.next()){
					String COMPANY_ID = rs.getString("COMPANY_ID");
					Map<String,Object> map2 = null;
					Set<String> setC = null;
					Set<String> setH = null;
					if(map.containsKey(COMPANY_ID)){
						map2 = map.get(COMPANY_ID);
						setC = (Set<String>) map2.get("setC");
						setH = (Set<String>) map2.get("setH");
					}else{
						map2 = new HashMap<String,Object>();
						setC=new HashSet<String>();
						setH=new HashSet<String>();
					}

					setC.add(rs.getString("CH_NAME"));
					setH.add(rs.getString("HSCODE"));
					map2.put("setC", setC);
					map2.put("setH", setH);
					map2.put("NAME", rs.getString("NAME"));
					map.put(COMPANY_ID, map2);
				}
				
				
				for(String s : map.keySet()){
					String ss = "";
					Set<String> setC = (Set<String>) map.get(s).get("setC");
					Set<String> setH = (Set<String>) map.get(s).get("setH");
					String Name = (String) map.get(s).get("NAME");
					
					ss += s+"\t";
					ss += Name+"\t";
					for(String s2:setC){
						ss += s2+",";
					}
					ss += "\t";
					for(String s2:setH){
						ss += s2+",";
					}

					logger.info(ss);
				}
				
			/*	
				System.out.println(ip.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$"));
				String [] ips = ip.split("\\.");
				long ipNumber =0L;
				for(int j=0;j<ips.length;j++){
					ipNumber+=Integer.parseInt(ips[j])*Math.pow(256, 3-j);
				}
				System.out.println("ipNumber="+ipNumber);
				
				System.out.println("end");
				
				for(Map<String,Object> m : IPtoMccmncList){
					long startNum = (Long) m.get("START_NUM");
					long EndNum = (Long) m.get("END_NUM");
					
					if(startNum <= ipNumber && ipNumber <= EndNum){
						break;
					}
				}*/

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if (ps != null)
						ps.close();

					if (st != null)
						st.close();

					if (rs != null)
						rs.close();

					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
	
		}
	}
	
	 private static String setSMSPostParam(String msg,String phone) throws IOException{
			StringBuffer sb=new StringBuffer ();

			String PhoneNumber=phone,Text=msg,charset="big5",InfoCharCounter=null,PID=null,DCS=null;
			String param =
					"PhoneNumber=+{{PhoneNumber}}&"
					+ "Text={{Text}}&"
					+ "charset={{charset}}&"
					+ "InfoCharCounter={{InfoCharCounter}}&"
					+ "PID={{PID}}&"
					+ "DCS={{DCS}}&"
					+ "Submit=Submit";
			
			if(PhoneNumber==null)PhoneNumber="";
			if(Text==null)Text="";
			if(charset==null)charset="";
			if(InfoCharCounter==null)InfoCharCounter="";
			if(PID==null)PID="";
			if(DCS==null)DCS="";
			param=param.replace("{{PhoneNumber}}",PhoneNumber );
			param=param.replace("{{Text}}",Text );
			param=param.replace("{{charset}}",charset );
			param=param.replace("{{InfoCharCounter}}",InfoCharCounter );
			param=param.replace("{{PID}}",PID );
			param=param.replace("{{DCS}}",DCS );
			
			
			
			return null;
			/*return tool.HttpPost("http://192.168.10.125:8800/Send%20Text%20Message.htm", param,"");*/
		}
	 
	 public static Properties getProperties(){
			Properties result=new Properties();
			
			result.put("log4j.rootCategory", "DEBUG, stdout, FileOutput");
			
			result.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
			result.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
			result.put("log4j.appender.stdout.layout.ConversionPattern", "%d [%5p] (%F:%L) - %m%n");
			
			result.put("log4j.appender.FileOutput", "org.apache.log4j.DailyRollingFileAppender");
			result.put("log4j.appender.FileOutput.layout", "org.apache.log4j.PatternLayout");
			result.put("log4j.appender.FileOutput.layout.ConversionPattern", "%d [%5p] (%F:%L) - %m%n");
			result.put("log4j.appender.FileOutput.DatePattern", "'.'yyyyMMdd");
			result.put("log4j.appender.FileOutput.File", "Ranger.log");
			
			return result;
	}
}
