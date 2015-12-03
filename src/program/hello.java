package program;

import java.io.File;
import java.io.IOException;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.jni.Local;
import org.apache.tomcat.util.buf.UDecoder;
import org.apache.tomcat.util.buf.UEncoder;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.MessageType;
import org.jsmpp.util.DeliveryReceiptState;

public class hello {
	private static String msg;
	static IJatool tool =new Jatool();
	public static void main(String[] args) throws UnknownHostException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, DecoderException{
		
		for(Field f : SubscriberExcel.class.getDeclaredFields()){
			System.out.println(f.getName());
		}
		
		System.out.println("2344");
		/*System.out.println(Integer.MAX_VALUE);
		System.out.println(Double.MAX_VALUE);

		File f = new File("C:\\Users\\ranger.kao\\Desktop\\Table.xls");
		long t = f.lastModified();
		System.out.println(t);
		System.out.println(new Date(t));*/
		
		
		
		/*System.out.println(MessageType.SMSC_DEL_RECEIPT.value());
		System.out.println(MessageType.DEFAULT.value());*/
		
		
		//one way encode
		//System.out.println("MD5 encodeing result : "+md5Encode("ranger"));	
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
		query();
		try {
			updateDB(999,param);
			updateDB(999,null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	static void query(){
		Connection conn=getConnection();
		String sql = "select ch_name from MARKETING_DB_COUNTRY A ";
		if(conn==null){
			System.out.println("connection is null");
			
		}else{

			Statement st = null ;
			ResultSet rs = null ;
			PreparedStatement ps = null ;
			try {

				st=conn.createStatement();
				st.executeUpdate(sql);
				/*rs=st.executeQuery(sql);
				
				while(rs.next()){
					String rss=rs.getString("ch_name");
					
					if(rss!=null){
						rss=new String(rss.getBytes("ISO8859-1"),"BIG5");
						System.out.println(rss);
					}
				}*/
				
				Map<String,String> setcountry = new HashMap<String,String>();
	
				sql = "update MARKETING_DB_DATA A set A.country=? where country=? ";
				
				ps = conn.prepareStatement(sql);
				
				for(String s : setcountry.keySet()){
					ps.setString(1, setcountry.get(s));
					ps.setString(2, new String(s.getBytes("BIG5"),"ISO8859-1"));
					System.out.println("effected row "+ps.executeUpdate());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(ps!=null){
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(st!=null){
					try {
						st.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(rs!=null){
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
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
			
			
			
			return tool.HttpPost("http://192.168.10.125:8800/Send%20Text%20Message.htm", param,"");
		}
}
