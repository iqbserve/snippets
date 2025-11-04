/* Authored by iqbserve.de */
package org.isa.snip.dbmodel;

import java.util.logging.Logger;

import org.isa.snip.dbmodel.util.Helper;
import org.isa.snip.dbmodel.util.JdbcConnection;

/**
 */
public class TestApp {

	private static final Logger LOG = Helper.getLoggerFor(TestApp.class);
		
	/**
	 */
	public static void main(String[] pArgs) {
		LOG.info("Start ISA Snippet [org.isa.snip.OracleMetaData]");

		OracleDbModel lModel = new OracleDbModel();
		try {
			lModel.setSchemaOwner("TEST")
				.setDbConnection(new JdbcConnection()
				.setUrl("jdbc:oracle:thin:@//localhost:1521/XEPDB1")
				.setUser("test")
				.connect("testpwd"));
			
			lModel.loadMetaData();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		LOG.info("App run finished");
	}
}
