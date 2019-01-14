package springData;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

// THIS CLASS NEEDS TO BE CONFIGURED FOR OBVIOUS REASONS
 

@Configuration
public class DbConfig {

	// information about accessing the Departmental MySQL server
	// https://campus.cs.le.ac.uk/labsupport/usinglinux/mysqlaccountdetails
	private String USERNAME = "**REDACTED**";
	private String PASSWORD = "**REDACTED**"; // in ~/.my.cnf
	
	// from campus
//	private String HOST = "mysql.mcscw3.le.ac.uk";
//	private int PORT = 3306;
	
	// off-campus: after executing 
	// ssh -fNg -L 3307:mysql.mcscw3.le.ac.uk:3306 abc123@xanthus.mcscw3.le.ac.uk
	private String HOST = "127.0.0.1";
	private int PORT = 3307;
				
    @Bean
    public DriverManagerDataSource dataSource() {
    	String path = System.getProperty("user.home") + "/CO2006.mysql.properties";
    	if (Files.isReadable(Paths.get(path))) {
    		try {
				Files.write(Paths.get("gradle/dbconf.log"), ("DbConfig used from " + path + "\n").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
			} catch (IOException e) {}
    		Properties mysqlProps = new Properties();
    		try {
				mysqlProps.load(new FileInputStream(path));
			} catch (Exception e) {
				e.printStackTrace();
			}
    		this.HOST = mysqlProps.getProperty("host");
    		this.PASSWORD = mysqlProps.getProperty("password");
    		this.USERNAME = mysqlProps.getProperty("username");
    		this.PORT = Integer.parseInt(mysqlProps.getProperty("port"));
    		System.out.println("INFO: CO2006.mysql.properties supplied username and password");
    	} else {
    		try {
				Files.write(Paths.get("gradle/dbconf.log"), ("No DbConfig in " + path + "\n").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
			} catch (IOException e) {}
    		System.out.println("INFO: using fields in DbConfig.java because " + path + " is missing.");
    	}
    	
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        // jdbc:mysql://host:port/db
        ds.setUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + USERNAME );
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        return ds;
    }
}


