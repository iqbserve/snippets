/* Authored by iqbserve.de */

package org.isa.snip.dbmodel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * A rudimentary jdbc connection class.
 */
public class JdbcConnection {
	private String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
	private String user = "test";

	private int fetchSize = 50;

	private Connection connection = null;
	private PreparedStatement statement = null;

	/**
	 */
	public JdbcConnection connect(String pPwd) throws Exception {
		connection = DriverManager.getConnection(url, user, pPwd);
		connection.setAutoCommit(false);
		return this;
	}

	/**
	 */
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 */
	public void closeCurrentStatement() throws Exception {
		if (statement != null && !statement.isClosed()) {
			statement.close();
		}
	}

	/**
	 */
	public ResultSet executeSql(String pSql) throws Exception {
		closeCurrentStatement();
		statement = connection.prepareStatement(pSql);
		statement.setFetchSize(fetchSize);
		return statement.executeQuery();
	}
	
	/**
	 */
	public JdbcConnection setUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 */
	public JdbcConnection setUser(String user) {
		this.user = user;
		return this;
	}
}

