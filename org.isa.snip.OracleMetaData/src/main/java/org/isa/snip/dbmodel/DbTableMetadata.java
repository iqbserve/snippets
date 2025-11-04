/* Authored by iqbserve.de */
package org.isa.snip.dbmodel;

/**
 */
public class DbTableMetadata extends AbstractDbMetadata {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String temporary = "";
	
	/**
	 */
	public DbTableMetadata(String pName) {
		super(pName);
	}

	/**
	 */
	public void addColumn(DbColumnMetadata pDef) {
		//TODO
	}

	/**
	 */
	public String getTemporary() {
		return temporary;
	}

	/**
	 */
	public void setTemporary(String temporary) {
		this.temporary = temporary;
	}
}
