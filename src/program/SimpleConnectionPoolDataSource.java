package program;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

public class SimpleConnectionPoolDataSource implements DataSource{

	private Properties props;  
	private String driver;
    private String url;  
    private String user;  
    private String passwd;  
    private int max; // 连接池中最大Connection数目  
    private List<Connection> conns;  
    
	public SimpleConnectionPoolDataSource()   throws IOException, ClassNotFoundException {  
        this("jdbc.properties");  
    }  
	
	public SimpleConnectionPoolDataSource(String configFile)  throws IOException, ClassNotFoundException {
		props = new Properties();  
		props.load(new FileInputStream(configFile));  
		url = props.getProperty("cc.openhome.url");  
		user = props.getProperty("cc.openhome.user");  
		passwd = props.getProperty("cc.openhome.password");  
		max = Integer.parseInt(props.getProperty("cc.openhome.poolmax"));  
		
		conns = Collections.synchronizedList(new ArrayList<Connection>());  
		Class.forName(props.getProperty("cc.openhome.driver"));
	}
	
	public SimpleConnectionPoolDataSource(String driver,String url,String user,String passwd,int max)  throws IOException, ClassNotFoundException {  
		this.url = url;
		this.user = user;
		this.passwd = passwd;
		this.max = max;
		
		conns = Collections.synchronizedList(new ArrayList<Connection>());  
		Class.forName(props.getProperty("cc.openhome.driver"));
	}
	
	@Override
	public synchronized Connection getConnection() throws SQLException {  
		System.out.println("get Connect!");
		if(conns.isEmpty()) {  
			return new ConnectionWrapper(DriverManager.getConnection(url, user, passwd), conns,max) ;  
		}else {  
			return conns.remove(conns.size() - 1);  
		}
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
