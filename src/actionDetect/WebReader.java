package actionDetect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebReader {

	static Connection conn=null;
	
	static Set<String> pHSCodList = new HashSet<String>();
	static Set<String> pCompanyList = new HashSet<String>();
	static Map<String,Map<String,String>> compData = new HashMap<String,Map<String,String>>();

	String baseURL;
	
	public static void main(String[] args) {
		
		conn = getConnection();
		
		if(conn==null)
			return;
		
		new WebReader();
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	static String msg=null;
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
	
	
	void setpList(){
		
		/*pList.add("product4.php?prod_sn=06-004");
		pList.add("product4.php?prod_sn=06-005");
		pList.add("product4.php?prod_sn=06-006");
		pList.add("product4.php?prod_sn=06-015");
		pList.add("product4.php?prod_sn=06-016");
		pList.add("product4.php?prod_sn=06-017");
		pList.add("product4.php?prod_sn=06-018");
		pList.add("product4.php?prod_sn=06-019");
		pList.add("product4.php?prod_sn=06-020");
		pList.add("product4.php?prod_sn=06-021");
		pList.add("product4.php?prod_sn=06-022");
		pList.add("product4.php?prod_sn=06-031");
		pList.add("product4.php?prod_sn=06-038");
		pList.add("product4.php?prod_sn=06-040");
		pList.add("product4.php?prod_sn=06-041");
		pList.add("product4.php?prod_sn=06-051");
		pList.add("product4.php?prod_sn=06-101");
		pList.add("product4.php?prod_sn=06-111");
		pList.add("product4.php?prod_sn=06-112");
		pList.add("product4.php?prod_sn=06-113");
		pList.add("product4.php?prod_sn=06-114");
		pList.add("product4.php?prod_sn=06-200");
		pList.add("product4.php?prod_sn=06-201");
		pList.add("product4.php?prod_sn=06-202");
		pList.add("product4.php?prod_sn=06-203");
		pList.add("product4.php?prod_sn=06-204");
		pList.add("product4.php?prod_sn=06-205");
		pList.add("product4.php?prod_sn=06-206");
		pList.add("product4.php?prod_sn=06-207");
		pList.add("product4.php?prod_sn=06-208");
		pList.add("product4.php?prod_sn=06-211");
		pList.add("product4.php?prod_sn=06-212");
		pList.add("product4.php?prod_sn=06-213");
		pList.add("product4.php?prod_sn=06-214");
		pList.add("product4.php?prod_sn=06-241");
		pList.add("product4.php?prod_sn=06-250");
		pList.add("product4.php?prod_sn=06-251");
		pList.add("product4.php?prod_sn=06-252");
		pList.add("product4.php?prod_sn=06-253");
		pList.add("product4.php?prod_sn=06-409");
		pList.add("product4.php?prod_sn=06-410");
		pList.add("product4.php?prod_sn=06-411");
		pList.add("product4.php?prod_sn=06-412");
		pList.add("product4.php?prod_sn=06-413");
		pList.add("product4.php?prod_sn=06-414");
		pList.add("product4.php?prod_sn=06-415");
		pList.add("product4.php?prod_sn=06-416");
		pList.add("product4.php?prod_sn=06-495");
		pList.add("product4.php?prod_sn=06-496");
		pList.add("product4.php?prod_sn=06-497");
		pList.add("product4.php?prod_sn=06-499");
		pList.add("product4.php?prod_sn=06-500");
		pList.add("product4.php?prod_sn=06-501");
		pList.add("product4.php?prod_sn=06-502");
		pList.add("product4.php?prod_sn=06-503");
		pList.add("product4.php?prod_sn=06-504");
		pList.add("product4.php?prod_sn=06-505");
		pList.add("product4.php?prod_sn=06-506");
		pList.add("product4.php?prod_sn=06-507");
		pList.add("product4.php?prod_sn=06-508");
		pList.add("product4.php?prod_sn=06-509");
		pList.add("product4.php?prod_sn=06-510");
		pList.add("product4.php?prod_sn=06-511");
		pList.add("product4.php?prod_sn=06-512");
		pList.add("product4.php?prod_sn=06-513");
		pList.add("product4.php?prod_sn=06-514");
		pList.add("product4.php?prod_sn=06-515");
		pList.add("product4.php?prod_sn=06-516");
		pList.add("product4.php?prod_sn=06-517");
		pList.add("product4.php?prod_sn=06-518");
		pList.add("product4.php?prod_sn=06-519");
		pList.add("product4.php?prod_sn=06-520");
		pList.add("product4.php?prod_sn=06-521");
		pList.add("product4.php?prod_sn=06-522");
		pList.add("product4.php?prod_sn=06-523");
		pList.add("product4.php?prod_sn=06-525");
		pList.add("product4.php?prod_sn=06-529");
		pList.add("product4.php?prod_sn=06-530");
		pList.add("product4.php?prod_sn=06-531");
		pList.add("product4.php?prod_sn=06-532");
		pList.add("product4.php?prod_sn=06-533");
		pList.add("product4.php?prod_sn=06-534");
		pList.add("product4.php?prod_sn=06-535");
		pList.add("product4.php?prod_sn=06-536");
		pList.add("product4.php?prod_sn=06-537");
		pList.add("product4.php?prod_sn=06-538");
		pList.add("product4.php?prod_sn=06-539");
		pList.add("product4.php?prod_sn=06-540");
		pList.add("product4.php?prod_sn=06-541");
		pList.add("product4.php?prod_sn=06-542");
		pList.add("product4.php?prod_sn=06-543");
		pList.add("product4.php?prod_sn=06-544");
		pList.add("product4.php?prod_sn=06-545");
		pList.add("product4.php?prod_sn=06-551");
		pList.add("product4.php?prod_sn=06-552");
		pList.add("product4.php?prod_sn=06-553");
		pList.add("product4.php?prod_sn=06-554");
		pList.add("product4.php?prod_sn=06-555");
		pList.add("product4.php?prod_sn=06-556");
		pList.add("product4.php?prod_sn=06-557");
		pList.add("product4.php?prod_sn=06-560");
		pList.add("product4.php?prod_sn=06-561");
		pList.add("product4.php?prod_sn=06-562");
		pList.add("product4.php?prod_sn=06-563");
		pList.add("product4.php?prod_sn=06-570");
		pList.add("product4.php?prod_sn=06-571");
		pList.add("product4.php?prod_sn=06-572");
		pList.add("product4.php?prod_sn=06-573");
		pList.add("product4.php?prod_sn=06-580");
		pList.add("product4.php?prod_sn=06-581");
		pList.add("product4.php?prod_sn=06-582");
		pList.add("product4.php?prod_sn=06-583");
		pList.add("product4.php?prod_sn=06-590");
		pList.add("product4.php?prod_sn=06-591");
		pList.add("product4.php?prod_sn=06-592");
		pList.add("product4.php?prod_sn=06-593");
		pList.add("product4.php?prod_sn=06-596");
		pList.add("product4.php?prod_sn=06-597");
		pList.add("product4.php?prod_sn=06-601");
		pList.add("product4.php?prod_sn=06-602");
		pList.add("product4.php?prod_sn=06-603");
		pList.add("product4.php?prod_sn=06-611");
		pList.add("product4.php?prod_sn=06-621");
		pList.add("product4.php?prod_sn=06-901");
		pList.add("product4.php?prod_sn=06-902");
		pList.add("product4.php?prod_sn=06-903");
		pList.add("product4.php?prod_sn=06-904");
		pList.add("product4.php?prod_sn=06-905");
		pList.add("product4.php?prod_sn=06-906");
		pList.add("product4.php?prod_sn=06-911");
		pList.add("product4.php?prod_sn=06-920");
		pList.add("product4.php?prod_sn=06-921");
		pList.add("product4.php?prod_sn=06-931");
		pList.add("product4.php?prod_sn=06-950");
		pList.add("product4.php?prod_sn=06-951");
		pList.add("product4.php?prod_sn=06-952");
		pList.add("product4.php?prod_sn=07-001");
		pList.add("product4.php?prod_sn=07-002");
		pList.add("product4.php?prod_sn=07-003");
		pList.add("product4.php?prod_sn=07-004");
		pList.add("product4.php?prod_sn=07-005");
		pList.add("product4.php?prod_sn=07-006");
		pList.add("product4.php?prod_sn=07-007");
		pList.add("product4.php?prod_sn=07-008");
		pList.add("product4.php?prod_sn=07-009");
		pList.add("product4.php?prod_sn=07-010");
		pList.add("product4.php?prod_sn=07-011");
		pList.add("product4.php?prod_sn=07-012");
		pList.add("product4.php?prod_sn=07-013");
		pList.add("product4.php?prod_sn=07-015");
		pList.add("product4.php?prod_sn=07-018");
		pList.add("product4.php?prod_sn=07-019");
		pList.add("product4.php?prod_sn=07-020");
		pList.add("product4.php?prod_sn=07-021");
		pList.add("product4.php?prod_sn=07-022");
		pList.add("product4.php?prod_sn=07-023");
		pList.add("product4.php?prod_sn=07-024");
		pList.add("product4.php?prod_sn=07-025");
		pList.add("product4.php?prod_sn=07-026");
		pList.add("product4.php?prod_sn=07-027");
		pList.add("product4.php?prod_sn=07-028");
		pList.add("product4.php?prod_sn=07-029");
		pList.add("product4.php?prod_sn=07-030");
		pList.add("product4.php?prod_sn=07-031");
		pList.add("product4.php?prod_sn=07-032");
		pList.add("product4.php?prod_sn=07-033");
		pList.add("product4.php?prod_sn=07-034");
		pList.add("product4.php?prod_sn=07-035");
		pList.add("product4.php?prod_sn=07-036");
		pList.add("product4.php?prod_sn=07-037");
		pList.add("product4.php?prod_sn=07-038");
		pList.add("product4.php?prod_sn=07-039");
		pList.add("product4.php?prod_sn=07-040");
		pList.add("product4.php?prod_sn=07-041");
		pList.add("product4.php?prod_sn=07-042");
		pList.add("product4.php?prod_sn=07-043");
		pList.add("product4.php?prod_sn=07-044");
		pList.add("product4.php?prod_sn=07-045");
		pList.add("product4.php?prod_sn=07-051");
		pList.add("product4.php?prod_sn=07-052");
		pList.add("product4.php?prod_sn=07-053");
		pList.add("product4.php?prod_sn=07-054");
		pList.add("product4.php?prod_sn=07-055");
		pList.add("product4.php?prod_sn=07-056");
		pList.add("product4.php?prod_sn=07-081");
		pList.add("product4.php?prod_sn=07-082");
		pList.add("product4.php?prod_sn=07-100");
		pList.add("product4.php?prod_sn=07-101");
		pList.add("product4.php?prod_sn=07-102");
		pList.add("product4.php?prod_sn=07-103");
		pList.add("product4.php?prod_sn=07-104");
		pList.add("product4.php?prod_sn=07-105");
		pList.add("product4.php?prod_sn=07-106");
		pList.add("product4.php?prod_sn=07-107");
		pList.add("product4.php?prod_sn=07-109");
		pList.add("product4.php?prod_sn=07-110");
		pList.add("product4.php?prod_sn=07-111");
		pList.add("product4.php?prod_sn=07-112");
		pList.add("product4.php?prod_sn=07-113");
		pList.add("product4.php?prod_sn=07-121");
		pList.add("product4.php?prod_sn=07-122");
		pList.add("product4.php?prod_sn=07-129");
		pList.add("product4.php?prod_sn=07-130");
		pList.add("product4.php?prod_sn=07-131");
		pList.add("product4.php?prod_sn=07-132");
		pList.add("product4.php?prod_sn=07-133");
		pList.add("product4.php?prod_sn=07-134");
		pList.add("product4.php?prod_sn=07-135");
		pList.add("product4.php?prod_sn=07-136");
		pList.add("product4.php?prod_sn=07-137");
		pList.add("product4.php?prod_sn=07-140");
		pList.add("product4.php?prod_sn=07-141");
		pList.add("product4.php?prod_sn=07-142");
		pList.add("product4.php?prod_sn=07-143");
		pList.add("product4.php?prod_sn=07-151");
		pList.add("product4.php?prod_sn=07-152");
		pList.add("product4.php?prod_sn=07-201");
		pList.add("product4.php?prod_sn=07-202");
		pList.add("product4.php?prod_sn=07-203");
		pList.add("product4.php?prod_sn=07-204");
		pList.add("product4.php?prod_sn=07-205");
		pList.add("product4.php?prod_sn=07-206");
		pList.add("product4.php?prod_sn=07-211");
		pList.add("product4.php?prod_sn=07-213");
		pList.add("product4.php?prod_sn=07-214");
		pList.add("product4.php?prod_sn=07-281");
		pList.add("product4.php?prod_sn=07-282");
		pList.add("product4.php?prod_sn=07-283");
		pList.add("product4.php?prod_sn=07-284");
		pList.add("product4.php?prod_sn=07-301");
		pList.add("product4.php?prod_sn=07-310");
		pList.add("product4.php?prod_sn=07-311");
		pList.add("product4.php?prod_sn=07-312");
		pList.add("product4.php?prod_sn=07-313");
		pList.add("product4.php?prod_sn=07-314");
		pList.add("product4.php?prod_sn=07-315");
		pList.add("product4.php?prod_sn=07-316");
		pList.add("product4.php?prod_sn=07-317");
		pList.add("product4.php?prod_sn=07-318");
		pList.add("product4.php?prod_sn=07-319");
		pList.add("product4.php?prod_sn=07-320");
		pList.add("product4.php?prod_sn=07-321");
		pList.add("product4.php?prod_sn=07-322");
		pList.add("product4.php?prod_sn=07-323");
		pList.add("product4.php?prod_sn=07-324");
		pList.add("product4.php?prod_sn=07-325");
		pList.add("product4.php?prod_sn=07-330");
		pList.add("product4.php?prod_sn=07-350");
		pList.add("product4.php?prod_sn=07-351");
		pList.add("product4.php?prod_sn=07-360");
		pList.add("product4.php?prod_sn=07-370");
		pList.add("product4.php?prod_sn=07-401");
		pList.add("product4.php?prod_sn=07-402");
		pList.add("product4.php?prod_sn=07-411");
		pList.add("product4.php?prod_sn=07-450");
		pList.add("product4.php?prod_sn=07-451");
		pList.add("product4.php?prod_sn=07-452");
		pList.add("product4.php?prod_sn=07-453");
		pList.add("product4.php?prod_sn=07-455");
		pList.add("product4.php?prod_sn=07-456");
		pList.add("product4.php?prod_sn=07-460");
		pList.add("product4.php?prod_sn=07-461");
		pList.add("product4.php?prod_sn=07-462");*/
		pList.add("product4.php?prod_sn=07-800");
		pList.add("product4.php?prod_sn=07-801");
		pList.add("product4.php?prod_sn=07-802");
		pList.add("product4.php?prod_sn=07-803");
		pList.add("product4.php?prod_sn=07-804");
		pList.add("product4.php?prod_sn=07-805");
		pList.add("product4.php?prod_sn=07-806");
		pList.add("product4.php?prod_sn=07-811");
		pList.add("product4.php?prod_sn=07-821");
		pList.add("product4.php?prod_sn=07-830");
		pList.add("product4.php?prod_sn=07-831");
		pList.add("product4.php?prod_sn=07-832");
		pList.add("product4.php?prod_sn=07-850");
		pList.add("product4.php?prod_sn=07-888");
		pList.add("product4.php?prod_sn=07-889");
		pList.add("product4.php?prod_sn=07-950");
		pList.add("product4.php?prod_sn=07-951");
		pList.add("product4.php?prod_sn=07-952");
		pList.add("product4.php?prod_sn=07-953");
		pList.add("product4.php?prod_sn=07-954");
		pList.add("product4.php?prod_sn=07-955");
		pList.add("product4.php?prod_sn=07-956");
		pList.add("product4.php?prod_sn=08-000");
		pList.add("product4.php?prod_sn=08-001");
		pList.add("product4.php?prod_sn=08-002");
		pList.add("product4.php?prod_sn=08-003");
		pList.add("product4.php?prod_sn=08-004");
		pList.add("product4.php?prod_sn=08-005");
		pList.add("product4.php?prod_sn=08-006");
		pList.add("product4.php?prod_sn=08-007");
		pList.add("product4.php?prod_sn=08-008");
		pList.add("product4.php?prod_sn=08-009");
		pList.add("product4.php?prod_sn=08-010");
		pList.add("product4.php?prod_sn=08-011");
		pList.add("product4.php?prod_sn=08-012");
		pList.add("product4.php?prod_sn=08-013");
		pList.add("product4.php?prod_sn=08-014");
		pList.add("product4.php?prod_sn=08-015");
		pList.add("product4.php?prod_sn=08-021");
		pList.add("product4.php?prod_sn=08-111");
		pList.add("product4.php?prod_sn=08-112");
		pList.add("product4.php?prod_sn=08-113");
		pList.add("product4.php?prod_sn=08-114");
		pList.add("product4.php?prod_sn=08-115");
		pList.add("product4.php?prod_sn=08-116");
		pList.add("product4.php?prod_sn=08-117");
		pList.add("product4.php?prod_sn=08-119");
		pList.add("product4.php?prod_sn=08-120");
		pList.add("product4.php?prod_sn=08-121");
		pList.add("product4.php?prod_sn=08-141");
		pList.add("product4.php?prod_sn=08-142");
		pList.add("product4.php?prod_sn=08-143");
		pList.add("product4.php?prod_sn=08-144");
		pList.add("product4.php?prod_sn=08-149");
		pList.add("product4.php?prod_sn=08-171");
		pList.add("product4.php?prod_sn=08-172");
		pList.add("product4.php?prod_sn=08-173");
		pList.add("product4.php?prod_sn=08-174");
		pList.add("product4.php?prod_sn=08-175");
		pList.add("product4.php?prod_sn=08-179");
		pList.add("product4.php?prod_sn=08-181");
		pList.add("product4.php?prod_sn=08-182");
		pList.add("product4.php?prod_sn=08-183");
		pList.add("product4.php?prod_sn=08-184");
		pList.add("product4.php?prod_sn=08-185");
		pList.add("product4.php?prod_sn=08-189");
		pList.add("product4.php?prod_sn=08-190");
		pList.add("product4.php?prod_sn=08-200");
		pList.add("product4.php?prod_sn=08-210");
		pList.add("product4.php?prod_sn=08-211");
		pList.add("product4.php?prod_sn=08-212");
		pList.add("product4.php?prod_sn=08-213");
		pList.add("product4.php?prod_sn=08-214");
		pList.add("product4.php?prod_sn=08-215");
		pList.add("product4.php?prod_sn=08-216");
		pList.add("product4.php?prod_sn=08-217");
		pList.add("product4.php?prod_sn=08-218");
		pList.add("product4.php?prod_sn=08-219");
		pList.add("product4.php?prod_sn=08-220");
		pList.add("product4.php?prod_sn=08-222");
		pList.add("product4.php?prod_sn=08-223");
		pList.add("product4.php?prod_sn=08-224");
		pList.add("product4.php?prod_sn=08-225");
		pList.add("product4.php?prod_sn=08-226");
		pList.add("product4.php?prod_sn=08-231");
		pList.add("product4.php?prod_sn=08-291");
		pList.add("product4.php?prod_sn=08-401");
		pList.add("product4.php?prod_sn=08-402");
		pList.add("product4.php?prod_sn=08-450");
		pList.add("product4.php?prod_sn=08-451");
		pList.add("product4.php?prod_sn=08-452");
		pList.add("product4.php?prod_sn=08-453");
		pList.add("product4.php?prod_sn=08-454");
		pList.add("product4.php?prod_sn=08-455");
		pList.add("product4.php?prod_sn=09-001");
		pList.add("product4.php?prod_sn=09-002");
		pList.add("product4.php?prod_sn=09-003");
		pList.add("product4.php?prod_sn=09-004");
		pList.add("product4.php?prod_sn=09-005");
		pList.add("product4.php?prod_sn=09-008");
		pList.add("product4.php?prod_sn=09-009");
		pList.add("product4.php?prod_sn=09-010");
		pList.add("product4.php?prod_sn=09-011");
		pList.add("product4.php?prod_sn=09-012");
		pList.add("product4.php?prod_sn=09-013");
		pList.add("product4.php?prod_sn=09-014");
		pList.add("product4.php?prod_sn=09-015");
		pList.add("product4.php?prod_sn=09-016");
		pList.add("product4.php?prod_sn=09-017");
		pList.add("product4.php?prod_sn=09-018");
		pList.add("product4.php?prod_sn=09-020");
		pList.add("product4.php?prod_sn=09-021");
		pList.add("product4.php?prod_sn=09-022");
		pList.add("product4.php?prod_sn=09-023");
		pList.add("product4.php?prod_sn=09-024");
		pList.add("product4.php?prod_sn=09-051");
		pList.add("product4.php?prod_sn=09-101");
		pList.add("product4.php?prod_sn=09-102");
		pList.add("product4.php?prod_sn=09-103");
		pList.add("product4.php?prod_sn=09-104");
		pList.add("product4.php?prod_sn=09-111");
		pList.add("product4.php?prod_sn=09-112");
		pList.add("product4.php?prod_sn=09-201");
		pList.add("product4.php?prod_sn=09-202");
		pList.add("product4.php?prod_sn=09-210");
		pList.add("product4.php?prod_sn=09-252");
		pList.add("product4.php?prod_sn=09-253");
		pList.add("product4.php?prod_sn=09-310");
		pList.add("product4.php?prod_sn=09-312");
		pList.add("product4.php?prod_sn=09-313");
		pList.add("product4.php?prod_sn=09-323");
		pList.add("product4.php?prod_sn=09-332");
		pList.add("product4.php?prod_sn=09-400");
		pList.add("product4.php?prod_sn=09-401");
		pList.add("product4.php?prod_sn=09-402");
		pList.add("product4.php?prod_sn=09-403");
		pList.add("product4.php?prod_sn=09-501");
		pList.add("product4.php?prod_sn=09-502");
		pList.add("product4.php?prod_sn=09-503");
		pList.add("product4.php?prod_sn=09-504");
		pList.add("product4.php?prod_sn=09-505");
		pList.add("product4.php?prod_sn=09-600");
		pList.add("product4.php?prod_sn=09-601");
		pList.add("product4.php?prod_sn=09-602");
		pList.add("product4.php?prod_sn=09-603");
		pList.add("product4.php?prod_sn=09-604");
		pList.add("product4.php?prod_sn=09-605");
		pList.add("product4.php?prod_sn=09-606");
		pList.add("product4.php?prod_sn=09-607");
		pList.add("product4.php?prod_sn=09-611");
		pList.add("product4.php?prod_sn=09-612");
		pList.add("product4.php?prod_sn=09-616");
		pList.add("product4.php?prod_sn=09-620");
		pList.add("product4.php?prod_sn=09-621");
		pList.add("product4.php?prod_sn=09-640");
		pList.add("product4.php?prod_sn=09-641");
		pList.add("product4.php?prod_sn=09-650");
		pList.add("product4.php?prod_sn=09-651");
		pList.add("product4.php?prod_sn=09-652");
		pList.add("product4.php?prod_sn=09-655");
		pList.add("product4.php?prod_sn=09-800");
		pList.add("product4.php?prod_sn=09-801");
		pList.add("product4.php?prod_sn=09-802");
		pList.add("product4.php?prod_sn=09-803");
		pList.add("product4.php?prod_sn=09-804");
		pList.add("product4.php?prod_sn=09-805");
		pList.add("product4.php?prod_sn=09-851");
		pList.add("product4.php?prod_sn=09-900");
		pList.add("product4.php?prod_sn=09-901");
		pList.add("product4.php?prod_sn=09-902");
		pList.add("product4.php?prod_sn=09-903");
		pList.add("product4.php?prod_sn=09-904");
		pList.add("product4.php?prod_sn=09-911");
		pList.add("product4.php?prod_sn=09-912");
		pList.add("product4.php?prod_sn=09-931");
		pList.add("product4.php?prod_sn=09-932");
		pList.add("product4.php?prod_sn=09-951");
		pList.add("product4.php?prod_sn=09-960");
		pList.add("product4.php?prod_sn=09-981");
		pList.add("product4.php?prod_sn=09-982");
		pList.add("product4.php?prod_sn=10-001");
		pList.add("product4.php?prod_sn=10-002");
		pList.add("product4.php?prod_sn=10-003");
		pList.add("product4.php?prod_sn=10-004");
		pList.add("product4.php?prod_sn=10-005");
		pList.add("product4.php?prod_sn=10-006");
		pList.add("product4.php?prod_sn=10-007");
		pList.add("product4.php?prod_sn=10-008");
		pList.add("product4.php?prod_sn=10-009");
		pList.add("product4.php?prod_sn=10-010");
		pList.add("product4.php?prod_sn=10-011");
		pList.add("product4.php?prod_sn=10-012");
		pList.add("product4.php?prod_sn=10-013");
		pList.add("product4.php?prod_sn=10-014");
		pList.add("product4.php?prod_sn=10-015");
		pList.add("product4.php?prod_sn=10-016");
		pList.add("product4.php?prod_sn=10-017");
		pList.add("product4.php?prod_sn=10-018");
		pList.add("product4.php?prod_sn=10-019");
		pList.add("product4.php?prod_sn=10-020");
		pList.add("product4.php?prod_sn=10-021");
		pList.add("product4.php?prod_sn=10-022");
		pList.add("product4.php?prod_sn=10-023");
		pList.add("product4.php?prod_sn=10-024");
		pList.add("product4.php?prod_sn=10-025");
		pList.add("product4.php?prod_sn=10-026");
		pList.add("product4.php?prod_sn=10-027");
		pList.add("product4.php?prod_sn=10-028");
		pList.add("product4.php?prod_sn=10-029");
		pList.add("product4.php?prod_sn=10-030");
		pList.add("product4.php?prod_sn=10-031");
		pList.add("product4.php?prod_sn=10-032");
		pList.add("product4.php?prod_sn=10-033");
		pList.add("product4.php?prod_sn=10-034");
		pList.add("product4.php?prod_sn=10-035");
		pList.add("product4.php?prod_sn=10-036");
		pList.add("product4.php?prod_sn=10-037");
		pList.add("product4.php?prod_sn=10-038");
		pList.add("product4.php?prod_sn=10-039");
		pList.add("product4.php?prod_sn=10-040");
		pList.add("product4.php?prod_sn=10-041");
		pList.add("product4.php?prod_sn=10-042");
		pList.add("product4.php?prod_sn=10-043");
		pList.add("product4.php?prod_sn=10-044");
		pList.add("product4.php?prod_sn=10-045");
		pList.add("product4.php?prod_sn=10-046");
		pList.add("product4.php?prod_sn=10-047");
		pList.add("product4.php?prod_sn=10-048");
		pList.add("product4.php?prod_sn=10-050");
		pList.add("product4.php?prod_sn=10-051");
		pList.add("product4.php?prod_sn=10-052");
		pList.add("product4.php?prod_sn=10-053");
		pList.add("product4.php?prod_sn=10-054");
		pList.add("product4.php?prod_sn=10-055");
		pList.add("product4.php?prod_sn=10-056");
		pList.add("product4.php?prod_sn=10-057");
		pList.add("product4.php?prod_sn=10-061");
		pList.add("product4.php?prod_sn=10-062");
		pList.add("product4.php?prod_sn=10-063");
		pList.add("product4.php?prod_sn=10-071");
		pList.add("product4.php?prod_sn=10-081");
		pList.add("product4.php?prod_sn=10-082");
		pList.add("product4.php?prod_sn=10-100");
		pList.add("product4.php?prod_sn=10-101");
		pList.add("product4.php?prod_sn=10-112");
		pList.add("product4.php?prod_sn=10-114");
		pList.add("product4.php?prod_sn=10-115");
		pList.add("product4.php?prod_sn=10-116");
		pList.add("product4.php?prod_sn=10-117");
		pList.add("product4.php?prod_sn=10-118");
		pList.add("product4.php?prod_sn=10-119");
		pList.add("product4.php?prod_sn=10-120");
		pList.add("product4.php?prod_sn=10-121");
		pList.add("product4.php?prod_sn=10-122");
		pList.add("product4.php?prod_sn=10-123");
		pList.add("product4.php?prod_sn=10-124");
		pList.add("product4.php?prod_sn=10-131");
		pList.add("product4.php?prod_sn=10-132");
		pList.add("product4.php?prod_sn=10-201");
		pList.add("product4.php?prod_sn=10-202");
		pList.add("product4.php?prod_sn=10-203");
		pList.add("product4.php?prod_sn=10-211");
		pList.add("product4.php?prod_sn=10-212");
		pList.add("product4.php?prod_sn=10-221");
		pList.add("product4.php?prod_sn=10-231");
		pList.add("product4.php?prod_sn=10-232");
		pList.add("product4.php?prod_sn=10-233");
		pList.add("product4.php?prod_sn=10-234");
		pList.add("product4.php?prod_sn=10-271");
		pList.add("product4.php?prod_sn=10-311");
		pList.add("product4.php?prod_sn=10-312");
		pList.add("product4.php?prod_sn=10-340");
		pList.add("product4.php?prod_sn=10-344");
		pList.add("product4.php?prod_sn=10-381");
		pList.add("product4.php?prod_sn=10-441");
		pList.add("product4.php?prod_sn=10-442");
		pList.add("product4.php?prod_sn=10-443");
		pList.add("product4.php?prod_sn=10-444");
		pList.add("product4.php?prod_sn=10-445");
		pList.add("product4.php?prod_sn=10-446");
		pList.add("product4.php?prod_sn=10-447");
		pList.add("product4.php?prod_sn=10-448");
		pList.add("product4.php?prod_sn=10-449");
		pList.add("product4.php?prod_sn=10-450");
		pList.add("product4.php?prod_sn=10-451");
		pList.add("product4.php?prod_sn=10-452");
		pList.add("product4.php?prod_sn=10-461");
		pList.add("product4.php?prod_sn=10-801");
		pList.add("product4.php?prod_sn=10-851");
		pList.add("product4.php?prod_sn=10-901");
		pList.add("product4.php?prod_sn=10-902");
		pList.add("product4.php?prod_sn=10-903");
		pList.add("product4.php?prod_sn=10-911");
		pList.add("product4.php?prod_sn=11-001");
		pList.add("product4.php?prod_sn=11-002");
		pList.add("product4.php?prod_sn=11-003");
		pList.add("product4.php?prod_sn=11-004");
		pList.add("product4.php?prod_sn=11-005");
		pList.add("product4.php?prod_sn=11-006");
		pList.add("product4.php?prod_sn=11-007");
		pList.add("product4.php?prod_sn=11-008");
		pList.add("product4.php?prod_sn=11-009");
		pList.add("product4.php?prod_sn=11-010");
		pList.add("product4.php?prod_sn=11-011");
		pList.add("product4.php?prod_sn=11-100");
		pList.add("product4.php?prod_sn=11-101");
		pList.add("product4.php?prod_sn=11-102");
		pList.add("product4.php?prod_sn=11-111");
		pList.add("product4.php?prod_sn=11-112");
		pList.add("product4.php?prod_sn=11-201");
		pList.add("product4.php?prod_sn=11-202");
		pList.add("product4.php?prod_sn=11-211");
		pList.add("product4.php?prod_sn=11-212");
		pList.add("product4.php?prod_sn=11-221");
		pList.add("product4.php?prod_sn=11-501");
		pList.add("product4.php?prod_sn=11-502");
		pList.add("product4.php?prod_sn=11-503");
		pList.add("product4.php?prod_sn=11-701");
		pList.add("product4.php?prod_sn=11-702");
		pList.add("product4.php?prod_sn=11-703");
		pList.add("product4.php?prod_sn=11-704");
		pList.add("product4.php?prod_sn=11-705");
		pList.add("product4.php?prod_sn=11-706");
		pList.add("product4.php?prod_sn=11-707");
		pList.add("product4.php?prod_sn=11-708");
		pList.add("product4.php?prod_sn=11-709");
		pList.add("product4.php?prod_sn=11-720");
		pList.add("product4.php?prod_sn=11-721");
		pList.add("product4.php?prod_sn=11-722");
		pList.add("product4.php?prod_sn=11-723");
		pList.add("product4.php?prod_sn=11-801");
		pList.add("product4.php?prod_sn=11-811");
		pList.add("product4.php?prod_sn=11-812");
		pList.add("product4.php?prod_sn=11-813");
		pList.add("product4.php?prod_sn=11-850");
		pList.add("product4.php?prod_sn=11-851");
		pList.add("product4.php?prod_sn=11-852");
		pList.add("product4.php?prod_sn=11-861");
		pList.add("product4.php?prod_sn=11-901");
		pList.add("product4.php?prod_sn=11-902");
		pList.add("product4.php?prod_sn=11-911");
		pList.add("product4.php?prod_sn=11-912");
		pList.add("product4.php?prod_sn=11-913");
		pList.add("product4.php?prod_sn=11-921");
		pList.add("product4.php?prod_sn=11-951");
		pList.add("product4.php?prod_sn=11-952");
		pList.add("product4.php?prod_sn=11-961");
		pList.add("product4.php?prod_sn=11-962");
		pList.add("product4.php?prod_sn=11-963");
		pList.add("product4.php?prod_sn=11-964");
		pList.add("product4.php?prod_sn=11-965");
		pList.add("product4.php?prod_sn=11-966");
		pList.add("product4.php?prod_sn=12-002");
		pList.add("product4.php?prod_sn=12-004");
		pList.add("product4.php?prod_sn=12-006");
		pList.add("product4.php?prod_sn=12-007");
		pList.add("product4.php?prod_sn=12-008");
		pList.add("product4.php?prod_sn=12-009");
		pList.add("product4.php?prod_sn=12-010");
		pList.add("product4.php?prod_sn=12-011");
		pList.add("product4.php?prod_sn=12-012");
		pList.add("product4.php?prod_sn=12-102");
		pList.add("product4.php?prod_sn=12-103");
		pList.add("product4.php?prod_sn=12-131");
		pList.add("product4.php?prod_sn=12-201");
		pList.add("product4.php?prod_sn=12-202");
		pList.add("product4.php?prod_sn=12-211");
		pList.add("product4.php?prod_sn=12-212");
		pList.add("product4.php?prod_sn=12-213");
		pList.add("product4.php?prod_sn=12-301");
		pList.add("product4.php?prod_sn=12-311");
		pList.add("product4.php?prod_sn=12-801");
		pList.add("product4.php?prod_sn=12-802");
		pList.add("product4.php?prod_sn=12-803");
		pList.add("product4.php?prod_sn=12-804");
		pList.add("product4.php?prod_sn=12-901");
		pList.add("product4.php?prod_sn=12-902");
		pList.add("product4.php?prod_sn=12-903");
		pList.add("product4.php?prod_sn=13-000");
		pList.add("product4.php?prod_sn=13-001");
		pList.add("product4.php?prod_sn=13-002");
		pList.add("product4.php?prod_sn=13-003");
		pList.add("product4.php?prod_sn=13-004");
		pList.add("product4.php?prod_sn=13-005");
		pList.add("product4.php?prod_sn=13-006");
		pList.add("product4.php?prod_sn=13-007");
		pList.add("product4.php?prod_sn=13-008");
		pList.add("product4.php?prod_sn=13-009");
		pList.add("product4.php?prod_sn=13-010");
		pList.add("product4.php?prod_sn=13-011");
		pList.add("product4.php?prod_sn=13-012");
		pList.add("product4.php?prod_sn=13-013");
		pList.add("product4.php?prod_sn=13-014");
		pList.add("product4.php?prod_sn=13-015");
		pList.add("product4.php?prod_sn=13-016");
		pList.add("product4.php?prod_sn=13-017");
		pList.add("product4.php?prod_sn=13-018");
		pList.add("product4.php?prod_sn=13-019");
		pList.add("product4.php?prod_sn=13-021");
		pList.add("product4.php?prod_sn=13-022");
		pList.add("product4.php?prod_sn=13-023");
		pList.add("product4.php?prod_sn=13-024");
		pList.add("product4.php?prod_sn=13-025");
		pList.add("product4.php?prod_sn=13-026");
		pList.add("product4.php?prod_sn=13-051");
		pList.add("product4.php?prod_sn=13-091");
		pList.add("product4.php?prod_sn=13-092");
		pList.add("product4.php?prod_sn=13-093");
		pList.add("product4.php?prod_sn=13-094");
		pList.add("product4.php?prod_sn=13-095");
		pList.add("product4.php?prod_sn=13-096");
		pList.add("product4.php?prod_sn=13-097");
		pList.add("product4.php?prod_sn=13-098");
		pList.add("product4.php?prod_sn=13-121");
		pList.add("product4.php?prod_sn=13-122");
		pList.add("product4.php?prod_sn=13-123");
		pList.add("product4.php?prod_sn=13-124");
		pList.add("product4.php?prod_sn=13-125");
		pList.add("product4.php?prod_sn=13-131");
		pList.add("product4.php?prod_sn=13-133");
		pList.add("product4.php?prod_sn=13-134");
		pList.add("product4.php?prod_sn=13-135");
		pList.add("product4.php?prod_sn=13-136");
		pList.add("product4.php?prod_sn=13-137");
		pList.add("product4.php?prod_sn=13-142");
		pList.add("product4.php?prod_sn=13-151");
		pList.add("product4.php?prod_sn=13-161");
		pList.add("product4.php?prod_sn=13-171");
		pList.add("product4.php?prod_sn=13-191");
		pList.add("product4.php?prod_sn=13-192");
		pList.add("product4.php?prod_sn=13-198");
		pList.add("product4.php?prod_sn=13-199");
		pList.add("product4.php?prod_sn=13-200");
		pList.add("product4.php?prod_sn=13-201");
		pList.add("product4.php?prod_sn=13-202");
		pList.add("product4.php?prod_sn=13-203");
		pList.add("product4.php?prod_sn=13-204");
		pList.add("product4.php?prod_sn=13-220");
		pList.add("product4.php?prod_sn=13-221");
		pList.add("product4.php?prod_sn=13-222");
		pList.add("product4.php?prod_sn=13-223");
		pList.add("product4.php?prod_sn=13-224");
		pList.add("product4.php?prod_sn=13-225");
		pList.add("product4.php?prod_sn=13-226");
		pList.add("product4.php?prod_sn=13-227");
		pList.add("product4.php?prod_sn=13-228");
		pList.add("product4.php?prod_sn=13-229");
		pList.add("product4.php?prod_sn=13-230");
		pList.add("product4.php?prod_sn=13-231");
		pList.add("product4.php?prod_sn=13-232");
		pList.add("product4.php?prod_sn=13-233");
		pList.add("product4.php?prod_sn=13-239");
		pList.add("product4.php?prod_sn=13-240");
		pList.add("product4.php?prod_sn=13-241");
		pList.add("product4.php?prod_sn=13-242");
		pList.add("product4.php?prod_sn=13-243");
		pList.add("product4.php?prod_sn=13-390");
		pList.add("product4.php?prod_sn=13-391");
		pList.add("product4.php?prod_sn=13-400");
		pList.add("product4.php?prod_sn=13-401");
		pList.add("product4.php?prod_sn=13-430");
		pList.add("product4.php?prod_sn=13-431");
		pList.add("product4.php?prod_sn=13-432");
		pList.add("product4.php?prod_sn=13-433");
		pList.add("product4.php?prod_sn=13-434");
		pList.add("product4.php?prod_sn=13-435");
		pList.add("product4.php?prod_sn=13-436");
		pList.add("product4.php?prod_sn=13-437");
		pList.add("product4.php?prod_sn=13-438");
		pList.add("product4.php?prod_sn=13-439");
		pList.add("product4.php?prod_sn=13-440");
		pList.add("product4.php?prod_sn=13-441");
		pList.add("product4.php?prod_sn=13-442");
		pList.add("product4.php?prod_sn=13-451");
		pList.add("product4.php?prod_sn=13-490");
		pList.add("product4.php?prod_sn=13-499");
		pList.add("product4.php?prod_sn=13-500");
		pList.add("product4.php?prod_sn=13-501");
		pList.add("product4.php?prod_sn=13-600");
		pList.add("product4.php?prod_sn=13-601");
		pList.add("product4.php?prod_sn=13-602");
		pList.add("product4.php?prod_sn=13-641");
		pList.add("product4.php?prod_sn=13-642");
		pList.add("product4.php?prod_sn=13-643");
		pList.add("product4.php?prod_sn=13-644");
		pList.add("product4.php?prod_sn=13-645");
		pList.add("product4.php?prod_sn=13-646");
		pList.add("product4.php?prod_sn=13-651");
		pList.add("product4.php?prod_sn=13-652");
		pList.add("product4.php?prod_sn=13-659");
		pList.add("product4.php?prod_sn=13-901");
		pList.add("product4.php?prod_sn=14-001");
		pList.add("product4.php?prod_sn=14-002");
		pList.add("product4.php?prod_sn=14-003");
		pList.add("product4.php?prod_sn=14-004");
		pList.add("product4.php?prod_sn=14-005");
		pList.add("product4.php?prod_sn=14-006");
		pList.add("product4.php?prod_sn=14-007");
		pList.add("product4.php?prod_sn=14-008");
		pList.add("product4.php?prod_sn=14-009");
		pList.add("product4.php?prod_sn=14-010");
		pList.add("product4.php?prod_sn=14-011");
		pList.add("product4.php?prod_sn=14-012");
		pList.add("product4.php?prod_sn=14-013");
		pList.add("product4.php?prod_sn=14-014");
		pList.add("product4.php?prod_sn=14-301");
		pList.add("product4.php?prod_sn=14-302");
		pList.add("product4.php?prod_sn=14-303");
		pList.add("product4.php?prod_sn=14-851");
		pList.add("product4.php?prod_sn=14-861");
		pList.add("product4.php?prod_sn=14-871");
		pList.add("product4.php?prod_sn=14-900");
		pList.add("product4.php?prod_sn=14-901");
		pList.add("product4.php?prod_sn=14-902");
		pList.add("product4.php?prod_sn=14-903");
		pList.add("product4.php?prod_sn=14-951");
		pList.add("product4.php?prod_sn=14-952");
		pList.add("product4.php?prod_sn=14-953");
		pList.add("product4.php?prod_sn=14-954");
		pList.add("product4.php?prod_sn=15-001");
		pList.add("product4.php?prod_sn=15-002");
		pList.add("product4.php?prod_sn=15-003");
		pList.add("product4.php?prod_sn=15-004");
		pList.add("product4.php?prod_sn=15-005");
		pList.add("product4.php?prod_sn=15-006");
		pList.add("product4.php?prod_sn=15-007");
		pList.add("product4.php?prod_sn=15-008");
		pList.add("product4.php?prod_sn=15-009");
		pList.add("product4.php?prod_sn=15-010");
		pList.add("product4.php?prod_sn=15-011");
		pList.add("product4.php?prod_sn=15-012");
		pList.add("product4.php?prod_sn=15-013");
		pList.add("product4.php?prod_sn=15-014");
		pList.add("product4.php?prod_sn=15-015");
		pList.add("product4.php?prod_sn=15-016");
		pList.add("product4.php?prod_sn=15-050");
		pList.add("product4.php?prod_sn=15-051");
		pList.add("product4.php?prod_sn=15-052");
		pList.add("product4.php?prod_sn=15-053");
		pList.add("product4.php?prod_sn=15-054");
		pList.add("product4.php?prod_sn=15-055");
		pList.add("product4.php?prod_sn=15-101");
		pList.add("product4.php?prod_sn=15-201");
		pList.add("product4.php?prod_sn=15-202");
		pList.add("product4.php?prod_sn=15-203");
		pList.add("product4.php?prod_sn=15-204");
		pList.add("product4.php?prod_sn=15-220");
		pList.add("product4.php?prod_sn=15-301");
		pList.add("product4.php?prod_sn=15-450");
		pList.add("product4.php?prod_sn=15-451");
		pList.add("product4.php?prod_sn=15-452");
		pList.add("product4.php?prod_sn=15-453");
		pList.add("product4.php?prod_sn=15-454");
		pList.add("product4.php?prod_sn=15-455");
		pList.add("product4.php?prod_sn=15-456");
		pList.add("product4.php?prod_sn=15-457");
		pList.add("product4.php?prod_sn=16-001");
		pList.add("product4.php?prod_sn=16-002");
		pList.add("product4.php?prod_sn=16-003");
		pList.add("product4.php?prod_sn=16-004");
		pList.add("product4.php?prod_sn=16-005");
		pList.add("product4.php?prod_sn=16-006");
		pList.add("product4.php?prod_sn=16-007");
		pList.add("product4.php?prod_sn=16-008");
		pList.add("product4.php?prod_sn=16-009");
		pList.add("product4.php?prod_sn=16-010");
		pList.add("product4.php?prod_sn=16-011");
		pList.add("product4.php?prod_sn=16-012");
		pList.add("product4.php?prod_sn=16-013");
		pList.add("product4.php?prod_sn=16-014");
		pList.add("product4.php?prod_sn=16-015");
		pList.add("product4.php?prod_sn=16-016");
		pList.add("product4.php?prod_sn=16-021");
		pList.add("product4.php?prod_sn=16-022");
		pList.add("product4.php?prod_sn=16-023");
		pList.add("product4.php?prod_sn=16-101");
		pList.add("product4.php?prod_sn=16-102");
		pList.add("product4.php?prod_sn=16-111");
		pList.add("product4.php?prod_sn=16-112");
		pList.add("product4.php?prod_sn=16-113");
		pList.add("product4.php?prod_sn=16-114");
		pList.add("product4.php?prod_sn=16-121");
		pList.add("product4.php?prod_sn=16-192");
		pList.add("product4.php?prod_sn=16-201");
		pList.add("product4.php?prod_sn=16-202");
		pList.add("product4.php?prod_sn=16-203");
		pList.add("product4.php?prod_sn=16-204");
		pList.add("product4.php?prod_sn=16-901");
		pList.add("product4.php?prod_sn=16-991");
		pList.add("product4.php?prod_sn=17-001");
		pList.add("product4.php?prod_sn=17-002");
		pList.add("product4.php?prod_sn=17-003");
		pList.add("product4.php?prod_sn=17-004");
		pList.add("product4.php?prod_sn=17-005");
		pList.add("product4.php?prod_sn=17-006");
		pList.add("product4.php?prod_sn=17-007");
		pList.add("product4.php?prod_sn=17-008");
		pList.add("product4.php?prod_sn=17-009");
		pList.add("product4.php?prod_sn=17-010");
		pList.add("product4.php?prod_sn=17-011");
		pList.add("product4.php?prod_sn=17-012");
		pList.add("product4.php?prod_sn=17-013");
		pList.add("product4.php?prod_sn=17-021");
		pList.add("product4.php?prod_sn=17-022");
		pList.add("product4.php?prod_sn=17-023");
		pList.add("product4.php?prod_sn=17-051");
		pList.add("product4.php?prod_sn=17-102");
		pList.add("product4.php?prod_sn=17-103");
		pList.add("product4.php?prod_sn=17-110");
		pList.add("product4.php?prod_sn=17-200");
		pList.add("product4.php?prod_sn=17-211");
		pList.add("product4.php?prod_sn=17-300");
		pList.add("product4.php?prod_sn=17-351");
		pList.add("product4.php?prod_sn=17-353");
		pList.add("product4.php?prod_sn=17-400");
		pList.add("product4.php?prod_sn=17-401");
		pList.add("product4.php?prod_sn=17-402");
		pList.add("product4.php?prod_sn=17-403");
		pList.add("product4.php?prod_sn=17-411");
		pList.add("product4.php?prod_sn=17-412");
		pList.add("product4.php?prod_sn=17-413");
		pList.add("product4.php?prod_sn=17-440");
		pList.add("product4.php?prod_sn=17-441");
		pList.add("product4.php?prod_sn=17-442");
		pList.add("product4.php?prod_sn=17-470");
		pList.add("product4.php?prod_sn=17-471");
		pList.add("product4.php?prod_sn=17-472");
		pList.add("product4.php?prod_sn=17-481");
		pList.add("product4.php?prod_sn=17-500");
		pList.add("product4.php?prod_sn=17-501");
		pList.add("product4.php?prod_sn=17-600");
		pList.add("product4.php?prod_sn=17-601");
		pList.add("product4.php?prod_sn=17-602");
		pList.add("product4.php?prod_sn=18-001");
		pList.add("product4.php?prod_sn=18-002");
		pList.add("product4.php?prod_sn=18-003");
		pList.add("product4.php?prod_sn=18-004");
		pList.add("product4.php?prod_sn=18-005");
		pList.add("product4.php?prod_sn=18-006");
		pList.add("product4.php?prod_sn=18-007");
		pList.add("product4.php?prod_sn=18-008");
		pList.add("product4.php?prod_sn=18-201");
		pList.add("product4.php?prod_sn=18-251");
		pList.add("product4.php?prod_sn=18-261");
		pList.add("product4.php?prod_sn=18-262");
	}
	
	void setcCode(){
		cCode.add("55967");
		cCode.add("55973");
		cCode.add("55978");
		cCode.add("30988");
		cCode.add("55984");
		cCode.add("55987");
		cCode.add("55992");
		cCode.add("55503");
		cCode.add("55505");
		cCode.add("55508");
		cCode.add("55577");
		cCode.add("55581");
		cCode.add("30141");
		cCode.add("55613");
		cCode.add("55759");
		cCode.add("55768");
		cCode.add("A5503");
		cCode.add("50462");
		cCode.add("30268");
		cCode.add("40420");
		cCode.add("55686");
		cCode.add("60200");
		cCode.add("80279");
		cCode.add("A5008");
		cCode.add("55830");
		cCode.add("55871");
		cCode.add("A5515");
		cCode.add("A2025");
		cCode.add("56143");
		cCode.add("A5011");
		cCode.add("40203");
		cCode.add("40220");
		cCode.add("55880");
		cCode.add("A5505");
		cCode.add("55742");
		cCode.add("55021");
		cCode.add("55746");
		cCode.add("56026");
		cCode.add("50485");
		cCode.add("56012");
		cCode.add("A5026");
		cCode.add("60239");
		cCode.add("56043");
		cCode.add("56045");
		cCode.add("56040");
		cCode.add("50540");
		cCode.add("56052");
		cCode.add("56057");
		cCode.add("50546");
		cCode.add("56100");
		cCode.add("56103");
		cCode.add("90085");
		cCode.add("56080");
		cCode.add("40567");
		cCode.add("60286");
		cCode.add("70146");
		cCode.add("A5509");
		cCode.add("56122");
		cCode.add("56127");
		cCode.add("A5510");
		cCode.add("50527");
		cCode.add("56133");
		cCode.add("56170");
		cCode.add("A2022");
		cCode.add("56172");
		cCode.add("A6009");
		cCode.add("A3022");
		cCode.add("30003");
		cCode.add("A5031");
		cCode.add("56062");
		cCode.add("50009");
		cCode.add("A5002");
		cCode.add("55156");
		cCode.add("56178");
		cCode.add("A2003");
		cCode.add("A5006");
		cCode.add("A5027");
		cCode.add("55228");
		cCode.add("55019");
		cCode.add("A5522");
		cCode.add("56192");
		cCode.add("80010");
		cCode.add("50026");
		cCode.add("55023");
		cCode.add("A5039");
		cCode.add("50091");
		cCode.add("55055");
		cCode.add("40587");
		cCode.add("50045");
		cCode.add("A5041");
		cCode.add("55036");
		cCode.add("30195");
		cCode.add("55040");
		cCode.add("55041");
		cCode.add("55048");
		cCode.add("60029");
		cCode.add("50049");
		cCode.add("55088");
		cCode.add("A5050");
		cCode.add("A3053");
		cCode.add("55072");
		cCode.add("A5103");
		cCode.add("60062");
		cCode.add("40182");
		cCode.add("50052");
		cCode.add("55126");
		cCode.add("60051");
		cCode.add("55100");
		cCode.add("60102");
		cCode.add("A5542");
		cCode.add("A8013");
		cCode.add("30315");
		cCode.add("55108");
		cCode.add("A5543");
		cCode.add("55112");
		cCode.add("A6029");
		cCode.add("50061");
		cCode.add("A7002");
		cCode.add("55133");
		cCode.add("A5550");
		cCode.add("40093");
		cCode.add("55142");
		cCode.add("55145");
		cCode.add("A6031");
		cCode.add("A6032");
		cCode.add("A5539");
		cCode.add("A5028");
		cCode.add("55242");
		cCode.add("60088");
		cCode.add("A6036");
		cCode.add("50067");
		cCode.add("80053");
		cCode.add("A4023");
		cCode.add("A5081");
		cCode.add("60099");
		cCode.add("A5562");
		cCode.add("A5089");
		cCode.add("55169");
		cCode.add("55171");
	}

	int waitPeriod = 30000;
	int maxID;
	List<String> nameList = new ArrayList<String>();
	public WebReader(){

		int linkPeriod = 5000;
		String sql = "Select Max(COMPANY_ID) ab from MARKETING_DB_COMPANY ";
		String sql2 = "SELECT A.NAME FROM MARKETING_DB_COMPANY A WHERE A.SOURCE IS NULL ";
		baseURL = "http://www.tami.org.tw/category/";

		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				maxID = Integer.parseInt(rs.getString("ab"));
			}
			System.out.println("Max id setting end...");
			rs = st.executeQuery(sql2);
			
			while(rs.next()){
				nameList.add(new String(rs.getString("NAME").getBytes("ISO8859-1"),"BIG5"));
			}
			System.out.println("name List setting end...");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
				try {
					if(st!=null)
						st.close();
					if(rs!=null)
						rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		
		try {
			
			
			//parseFirst("http://www.tami.org.tw/category/product-new1.php");	
						
			sleep(linkPeriod);
			
			/*for(String s : productList){
				parseSecond(baseURL+s);
				sleep(linkPeriod);*/
				
			setpList();
			
				for(String s2 : pList){
					System.out.println("proccess pList "+s2);
					parseThird(baseURL+s2);
					sleep(linkPeriod);
					
					cCode.clear();
					for(String s3 : cList){
						parseFourth(s3);
						sleep(linkPeriod);
					}
					
					String url=baseURL+"contact_2.php?ms=A3069&on=1";
					for(String s3:cCode){
						System.out.println("proccess "+s3);
						parseFifth(url.replaceAll("=.+&","="+s3+"&" ));
						sleep(linkPeriod);
					}
					
				}
				
			/*}*/

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * note ：
	 * 
	 * *解析網頁
	 * URL url = new URL(strURL); 
	 * Document xmlDoc =  Jsoup.parse(url, 5000);
	 * 
	 * *取出元素集合
	 * Elements a
	 * 
	 * *取出元素內 href屬性值
	 * e.get(0).attr("href")
	 * 
	 * @param strURL
	 * @throws IOException
	 */
	
	List<String> productList = new ArrayList<String>();
	
	public void parseFirst(String strURL) throws IOException{
		URL url = new URL(strURL); 
		Document xmlDoc =  Jsoup.parse(url, waitPeriod); //使用Jsoup jar 去解析網頁
		
		Elements a = xmlDoc.select("a");
		productList.clear();
	
		for(Element e : a){
			
			String href = e.attr("href");
			if(href!=null && href.matches("^p_list\\.php\\?on=\\d{2}$")){
				productList.add(e.attr("href"));
				System.out.println(e.attr("href"));
			}
		}

	}
	
	List<String> pList = new ArrayList<String>();
	public void parseSecond(String strURL) throws IOException{
		URL url = new URL(strURL); 
		Document xmlDoc =  Jsoup.parse(url, waitPeriod); //使用Jsoup jar 去解析網頁
		
		Elements a = xmlDoc.select("a");
		pList.clear();
	
		for(Element e : a){
			String href = e.attr("href");
			if(href!=null && href.matches("^.+\\.php\\?prod_sn=\\d{2}\\-\\d{3}$")){
				System.out.println(e.attr("href"));
				pList.add(e.attr("href"));
			}
		}
	}
	
	List<String> cList = new ArrayList<String>();
	
	
	public void parseThird(String strURL) throws IOException{
		URL url = new URL(strURL); 
		Document xmlDoc =  Jsoup.parse(url, waitPeriod); //使用Jsoup jar 去解析網頁
		
		Elements a = xmlDoc.select("a");
		
		cList.clear();
		
		int max = 0;

		//尋找頁數
		for(Element e : a){
			String onClick = e.attr("onClick");
			if(onClick!=null && onClick.matches("^page_link\\(.+,'.+'\\);$")){
				String s = onClick.split("\\(")[1];
				s = s.split(",")[0];
				if(s!=null && s.matches("^\\d+$") && Integer.parseInt(s)>max){
					max = Integer.parseInt(s);
				}
			}
		}
		
		for(int i = 1 ; i<=max ; i++){
			if(strURL.indexOf("on=")==-1){
				strURL=strURL.replaceAll("php\\?prod", "php?on=1&prod");
			}else{
				strURL=strURL.replaceAll("on="+(i-1), "on="+i);
			}
			cList.add(strURL);
			//System.out.println(strURL);
		}
	}
	List<String> cCode = new ArrayList<String>();
	int i=1;
	public void parseFourth(String strURL) throws IOException{
		i=1;
		URL url = new URL(strURL); 
		Document xmlDoc =  Jsoup.parse(url, waitPeriod); //使用Jsoup jar 去解析網頁
		
		Elements a = xmlDoc.select("a");

		
		for(Element e : a){
			String onClick = e.attr("onClick");
			if(onClick!=null && onClick.matches("^goPost\\('.+','.+'\\);$")){
				String s = onClick.split("'")[3];
				cCode.add(s);
				System.out.println((i++)+":"+s);
			}				
		}
	}
	
	
	public void parseFifth(String strURL) throws IOException{
		URL url = new URL(strURL); 
		Document xmlDoc =  Jsoup.parse(url, waitPeriod); //使用Jsoup jar 去解析網頁
		
		Elements td = xmlDoc.select("td");
		String s = "";
		
		String sql = "INSERT INTO MARKETING_DB_COMPANY (COMPANY_ID,NAME,ADDRESS,PHONE,FAX,WEB,EMAIL,CREATETIME) "
				+ "values(?,?,?,?,?,?,?,sysdate)";
		
		PreparedStatement pst = null;
		boolean duplicated = false; 
		 try {
			pst = conn.prepareStatement(sql);

			for(int i = 0 ; i<td.size() ; i++){
				if("公司電話:".equals(td.get(i).text())){
					s += "公司名稱：" + td.get(i-1).text()+ "\n" ;
					if(nameList.contains(td.get(i-1).text())){
						duplicated = true;
						break;
					}
					
					nameList.add(td.get(i-1).text());
					pst.setInt(1, ++maxID);
					pst.setString(2, new String(td.get(i-1).text().getBytes("BIG5"),"ISO8859-1"));
					s += "公司電話：" + td.get(i+1).text()+ "\n" ;
					pst.setString(4, td.get(i+1).text());
				}else if("公司傳真:".equals(td.get(i).text())){
					s += "公司傳真：" + td.get(i+1).text()+ "\n" ;
					pst.setString(5, td.get(i+1).text());
				}else if("公司地址:".equals(td.get(i).text())){
					s += "公司地址：" + td.get(i+1).text()+ "\n" ;
					pst.setString(3, new String(td.get(i+1).text().getBytes("BIG5"),"ISO8859-1"));
				}else if("工廠電話:".equals(td.get(i).text())){
					s += "工廠電話:" + td.get(i+1).text()+ "\n" ;
					
				}else if("工廠傳真:".equals(td.get(i).text())){
					s += "工廠傳真:" + td.get(i+1).text()+ "\n" ;
					
				}else if("工廠地址:".equals(td.get(i).text())){
					s += "工廠地址:" + td.get(i+1).text()+ "\n" ;
					
				}else if("公司網址:".equals(td.get(i).text())){
					s += "公司網址:" + td.get(i+1).text()+ "\n" ;
					pst.setString(6, td.get(i+1).text());
				}else if("資本額:".equals(td.get(i).text())){
					s += "資本額:" + td.get(i+1).text()+ "\n" ;
				
				}else if("電子郵件:".equals(td.get(i).text())){
					s += "電子郵件:" + td.get(i+1).text()+ "\n" ;
					pst.setString(7, td.get(i+1).text());
				}else if("員工人數:".equals(td.get(i).text())){
					s += "員工人數:" + td.get(i+1).text()+ "\n" ;
				}
				
			}
			if(!duplicated){
				System.out.println(s);
				System.out.println("Insert "+maxID+" result "+pst.executeUpdate());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				if(pst!=null)
					pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
		
		

	}
	
	
	/**********************************************/
	
	public void getBypost(){
		
		String strURL = "http://www.ieatpe.org.tw/qry/qu_3_show.asp?id=12050";
		URL url = null;
		String data = null;
		URLConnection conn = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			url = new URL(strURL);
			conn = url.openConnection();
			inputStreamReader = new InputStreamReader(conn.getInputStream());
			
			bufferedReader = new BufferedReader(inputStreamReader);
			while((data = bufferedReader.readLine()) != null) {
			 System.out.println(data);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parseByJsoup() throws IOException{
		String strURL = "http://www.ieatpe.org.tw/qry/qu_3_show.asp?id=12050";
		URL url = new URL(strURL); 
		Document xmlDoc =  Jsoup.parse(url, 5000); //使用Jsoup jar 去解析網頁
		//依照指定編碼讀取Web
		//Document xmlDoc = Jsoup.parse(url.openConnection().getInputStream(), "windows-1252", strURL);
		
		//(要解析的文件,timeout)
		Elements title = xmlDoc.select("title"); //要解析的tag元素為title
		Elements happy = xmlDoc.select("td");  //要解析的tag元素為td
			
		System.out.println("Title is "+title.get(0).text()); //得到title tag的內容
		System.out.println("you select mood is "+happy.get(1).text()); //得到td tag的內容
		//注意: 因為有好多個td 我想要取得的是<td>樂</td> 是第2個td 所以填get(
		
	}
}
