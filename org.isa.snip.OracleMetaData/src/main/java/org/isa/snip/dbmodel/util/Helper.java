/* Authored by iqbserve.de */

package org.isa.snip.dbmodel.util;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * A tooling class to provide more or less "useful" helper functions. 
 */
public class Helper {

	//initialize logging from a properties file
	static {
		try {
			LogManager.getLogManager().readConfiguration(Helper.class.getResourceAsStream("/logging.properties"));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 */
	private Helper() {
	}

	/**
	 */
	public static Logger getLoggerFor(Class<?> pClazz) {
		return Logger.getLogger(pClazz.getName());
	}

	/**
	 * Read text from a file on the classpath into a String.
	 */
	public static String readResourceFile(String pPath) {
		InputStream lInput = null;
		Scanner lScanner = null;
		String lContent = "";
		try {
			lInput = Helper.class.getResourceAsStream(pPath);
			lScanner = new Scanner(lInput).useDelimiter("\\A");
			lContent = lScanner.hasNext() ? lScanner.next() : "";
		} finally {
			if (lScanner != null) {
				lScanner.close();
			}
		}
		return lContent;
	}
}
