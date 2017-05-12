package program;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.axis2.AxisFault;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

public interface IJatool {

	/************************ Log 控制 ***********************/
	
	//Log4j 系統產生，取得設定檔，
	public Properties getProperties();
	
	//Log4j 讀取Properties，取得設定檔，
	public Properties getProperties(String fileName) throws FileNotFoundException, IOException;
	
	Logger setLogger(Properties prop);
	
	//Log控制
	public void logControl(Logger logger,String level,String message);
	
	//取的Exception詳細資訊
	String getExceptionMsg(Exception e);
	
	/************************ mail 發送  ***********************/
	
	//使用mail Server 發送郵件
	void sendMailwithAuthenticator(String host, int port, String protocol,
			String username, String password, String sender, String receiver,
			String subject, String content) throws AddressException,
			MessagingException;

	void sendMailwithoutAuthenticator(String host, int port, String protocol,
			String username, String password, String sender, String receiver,
			String subject, String content) throws AddressException,
			MessagingException;

	//在Unix系統發送郵件
	void sendMailforUnixLike(String mailfunction, String sender,
			String receiver, String subject, String msg) throws Exception;

	/************************ 時間取得  ***********************/	
	//本月第一天
	Date getMonthFirstDate(Date date);

	//本月最後一天
	Date getMonthLastDate(Date date);
	
	//取得設定好時間的Calendar
	Calendar getCalendar(Integer year, Integer month, Integer day, Integer hour, Integer min, Integer sec);
	

	/************************ DB 連線  ***********************/
	
	Connection connDB(String DriverClass, String DBType, String ip,
			String port, String DBNameorSID, String charset, String UserName,
			String PassWord) throws ClassNotFoundException, SQLException;

	
	/************************ 值處理  ***********************/
	//對Double做格式化
	Double FormatDouble(Double value, String form) throws Exception;
	
	//時間與字串轉換
	Object DateFormat(Object value, String form) throws ParseException, Exception;
	
	//正規測試內容是否符合表示法
	boolean regularMatch(String content, String regex);

	//回傳內容符合表示法的部分
	List<String> regularFind(String content, String regex);
	
	//檢驗純數字
	boolean onlyInteger(String content);
	
	//檢驗純文字
	boolean onlyString(String content);
	
	//數字進位法(四捨五入、上逼近、下逼近等)
	Double roundUpOrDdown(Double value, String method, int digit);
	
	//編碼轉換
	String convertCharset(String content, String sourceCharset,String torgetCharset) throws UnsupportedEncodingException;
	
	//加密文字
	String md5Encode(String source) throws NoSuchAlgorithmException;

	String SHAEncode(String source) throws NoSuchAlgorithmException;

	String RSAEncode(String source, RSAPublicKey publicKey) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,BadPaddingException;

	String RSADecode(String source, RSAPrivateKey privateKey)	throws InvalidKeyException, NoSuchAlgorithmException,
				NoSuchPaddingException, IllegalBlockSizeException,BadPaddingException, DecoderException;
	
	//空值處理
	public Object nvl(Object msg,Object s);
	
	//以反射方式填入Bean
	void reflectSet(Object bean, Map<String, String> valMap)	throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,InvocationTargetException;
	
	/************************ 網路  ***********************/
	
	//呼叫WSDL (web server discriber language) ，以SMPP為例
	String callWSDLServer(String param) throws RemoteException;

	//Post 網路資料
	int HttpPost(String url, String method, String param, String charset)	throws IOException;

	//FTP 相關
	void UpdatToFTP(FTPClient ftp, String  localFileName, String destFileName)	throws IOException;

	void newFTPDir(FTPClient ftp, String folderName) throws IOException;

	FTPClient connectFTP() throws Exception;

	void readFTPFile(FTPClient ftp) throws IOException;

	/************************ 檔案處理  ***********************/
	
	//讀取文字檔
	String readtxt(String filePath,String charset) throws IOException;

	//寫入文字檔
	void writetxt(String filename, String content, boolean append,String charSet) throws UnsupportedEncodingException, FileNotFoundException, IOException;

	//建立新資料夾
	void newFolder(String folderPath);

	//取得類別位置
	String getProgramDir(Class<?> className);

	//移動檔案
	void moveFile(String sourceDir, String DestDir, String fileName);

	//解壓縮
	void unGZIP(String workDir, String fName, String tempDir);
	
	//產生xls或xlsx檔案
	boolean createExcelFile(List<Map<String, String>> head, List<Map<String, Object>> data, String fileName) throws IOException;
	
	InputStream createExcelStream(List<Map<String, String>> head, List<Map<String, Object>> data, String type)throws IOException;
	
	Workbook createExcel(List<Map<String, String>> head, List<Map<String, Object>> data, String type)throws IOException;
	
	//讀取Excel檔案
	void readExcel(String fileName) throws FileNotFoundException, IOException;
	
	/************************ 其他  ***********************/

	//使用Timer 進行，time 傳入Long為Delay時間，傳入Date為首次執行時間，period為null表示單次執行
	void regularExcute(Long periodTime, Object time, TimerTask run);

	

}
