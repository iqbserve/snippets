/* Authored by iqbserve.de */
package org.isa.snip.dbmodel;

import java.io.Serializable;

/**
 * Abstract db metadata base class.
 */
public abstract class AbstractDbMetadata implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String name = "";
	private String sql = "";
	private boolean isSysObject = false;

	/**
	 */
	protected AbstractDbMetadata() {
	}

	/**
	 */
	protected AbstractDbMetadata(String pName) {
		setName(pName);
	}

	/**
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 */
	public String getName() {
		return name;
	}

	/**
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 */
	public String getSql() {
		return sql;
	}

	/**
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 */
	public boolean isSysObject() {
		return isSysObject;
	}

	/**
	 */
	public void setSysObject(boolean isSysObject) {
		this.isSysObject = isSysObject;
	}
}
