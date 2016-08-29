package program;


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
import org.json.JSONObject;

public interface IJatool {

	void sendMailwithAuthenticator(String host, int port, String protocol,
			String username, String password, String sender, String receiver,
			String subject, String content) throws AddressException,
			MessagingException;

	void sendMailwithoutAuthenticator(String host, int port, String protocol,
			String username, String password, String sender, String receiver,
			String subject, String content) throws AddressException,
			MessagingException;

	void sendMailforUnixLike(String mailfunction, String sender,
			String receiver, String subject, String msg) throws Exception;

	Properties getProperties();

	Connection connDB(String DriverClass, String DBType, String ip,
			String port, String DBNameorSID, String charset, String UserName,
			String PassWord) throws ClassNotFoundException, SQLException;

	String callWSDLServer(String param) throws RemoteException;

	Date getMonthFirstDate(Date date);

	Date getMonthLastDate(Date date);

	java.sql.Date convertJaveUtilDate_To_JavaSqlDate(Date date);

	Date convertJaveSqlDate_To_JavaUtilDate(java.sql.Date date);

	Date DateFormat(String dateString, String form) throws ParseException, Exception;

	String DateFormat(Date date, String form);

	String[] HttpPost(String url, String method, String param, String charset)
			throws IOException;

	Double FormatDouble(Double value, String form);

	String FormatNumString(Double value, String form);

	void readtxt(String filePath);

	void writetxt(String filename, String content, boolean append,
			String charSet);

	void newFolder(String folderPath);

	boolean regularMatch(String content, String regex);

	List<String> regularFind(String content, String regex);

	Double roundUpOrDdown(Double value, String method, int digit);

	Calendar getCalendarByCalculate(Integer year, Integer month, Integer day,
			Integer hour, Integer min, Integer sec);

	String convertCharset(String content, String sourceCharset,
			String torgetCharset) throws UnsupportedEncodingException;

	String getProgramDir(Class<?> className);
	
	void regularExcute(long periodTime, long delay, TimerTask run);

	void regularExcute(long periodTime, Date FirstExecuteTime, TimerTask run);

	String getExceptionMsg(Exception e);

	void UpdatToFTP(FTPClient ftp, InputStream input, String destFileName)
			throws IOException;

	void newFTPDir(FTPClient ftp, String folderName) throws IOException;

	FTPClient connectFTP() throws Exception;

	void readFTPFile(FTPClient ftp) throws IOException;

	void moveFile(String sourceDir, String DestDir, String fileName);

	void unGZIP(String workDir, String fName, String tempDir);

	String nullProccess(String s);

	String getLocalIP() throws UnknownHostException;

	InputStream getExcelInputStream(List<Map<String, String>> head,
			List<Map<String, Object>> data) throws IOException;

	void createExcelFile(List<Map<String, String>> head,
			List<Map<String, Object>> data, String path) throws IOException;

	String md5Encode(String source) throws NoSuchAlgorithmException;

	String SHAEncode(String source) throws NoSuchAlgorithmException;

	String RSAEncode(String source, RSAPublicKey publicKey)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException;

	String RSADecode(String source, RSAPrivateKey privateKey)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, DecoderException;

	void reflectSet(Object bean, Map<String, String> valMap)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException;

	
	
}
