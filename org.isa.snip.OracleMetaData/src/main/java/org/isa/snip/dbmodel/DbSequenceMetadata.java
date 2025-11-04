/* Authored by iqbserve.de */

package org.isa.snip.dbmodel;

/**
 */
public class DbSequenceMetadata extends AbstractDbMetadata {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private String start = "";
	private String increment = "";

	/**
	 */
	public DbSequenceMetadata(String pName) {
		super(pName);
	}

	/**
	 */
	public String getStart() {
		return start;
	}

	/**
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 */
	public String getIncrement() {
		return increment;
	}

	/**
	 */
	public void setIncrement(String increment) {
		this.increment = increment;
	}

}
