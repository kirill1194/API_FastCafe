package SQL;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.SQLWorkException;

public class SqlServices {
	private static Logger log = LogManager.getLogger(SqlServices.class);
	public static Connection getConnection() throws SQLWorkException {
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource dataSource = (DataSource)envContext.lookup("jdbc/testdb");
			return dataSource.getConnection();
		} catch (NamingException e) {
			log.fatal("SO BAD!! Exception about context JDBC ", e);
			throw new SQLWorkException(0);
		} catch (SQLException e) {
			log.fatal("SQLException. It's bad. ", e);
			throw new SQLWorkException(0);
		}
	}
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			log.fatal("Can't close connection to DB. ", e);
		}
	}


}
