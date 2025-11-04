/* Authored by iqbserve.de */

package org.isa.snip.dbmodel;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 */
public class DbIndexMetadata extends AbstractDbMetadata {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String tablename = "";
	private Set<String> columnnames = new LinkedHashSet<>();
	private String uniqueness = "";

	/**
	 */
	public DbIndexMetadata(String pName) {
		super(pName);
	}

	  /**
	   */
	  public boolean isUnique() {
	    return "UNIQUE".equalsIgnoreCase(uniqueness);
	  }

	  /**
	   */
	  public String getTablename() {
	    return tablename;
	  }

	  /**
	   */
	  public void setTablename(String tablename) {
	    this.tablename = tablename;
	  }

	  /**
	   */
	  public Set< String > getColumnnames() {
	    return columnnames;
	  }

	  /**
	   */
	  public void addColumnname(String pColumnname) {
	    columnnames.add(pColumnname);
	  }

	  /**
	   */
	  public void addAllColumnnames(List< String > pColumnnames) {
	    columnnames.addAll(pColumnnames);
	  }

	  /**
	   */
	  public String getUniqueness() {
	    return uniqueness;
	  }

	  /**
	   */
	  public void setUniqueness(String uniqueness) {
	    this.uniqueness = uniqueness;
	  }

}
