package com.revature.JdbcBank;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {
	private static Connection connectionInstance = null;
	private static Logger log = LogManager.getLogger(ConnectionUtil.class);

	private ConnectionUtil() {
	}

	public static Connection getConnection() {
		if (ConnectionUtil.connectionInstance != null) {
			return ConnectionUtil.connectionInstance;
		}

		InputStream in = null;

		try {
			// load informations from properties file
			Properties props = new Properties();
			in = new FileInputStream(
					"C:\\Users\\Tenzing\\Documents\\JDBCBank\\src\\main\\resources\\connection.properties");
			props.load(in);

			// get the connection
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String endpoint = props.getProperty("jdbc.url");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");

			connectionInstance = DriverManager.getConnection(endpoint, username, password);
			return connectionInstance;
		} catch (Exception e) {
			log.error("unable to get connection to database");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
}
