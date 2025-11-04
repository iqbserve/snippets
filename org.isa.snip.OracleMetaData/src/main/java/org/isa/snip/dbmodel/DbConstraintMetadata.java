/* Authored by iqbserve.de */

package org.isa.snip.dbmodel;

/**
 */
public class DbConstraintMetadata extends AbstractDbMetadata {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private String type = "";
	private String tablename = "";
	private String columnName = "";
	private String deleteRule = "";
	private String searchCondition = "";
	private String relationConstraintName = "";

	public DbConstraintMetadata(String pName) {
		super(pName);
	}

	/**
	 */
	public boolean isPrimaryKey() {
		return "P".equalsIgnoreCase(type);
	}

	/**
	 */
	public boolean isForeignKey() {
		return "R".equalsIgnoreCase(type);
	}

	/**
	 */
	public boolean isNotNull() {
		return "C".equalsIgnoreCase(type);
	}

	/**
	 */
	public String getType() {
		return type;
	}

	/**
	 */
	public void setType(String type) {
		this.type = type;
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
	public String getColumnName() {
		return columnName;
	}

	/**
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 */
	public String getDeleteRule() {
		return deleteRule;
	}

	/**
	 */
	public void setDeleteRule(String deleteRule) {
		this.deleteRule = deleteRule;
	}

	/**
	 */
	public String getSearchCondition() {
		return searchCondition;
	}

	/**
	 */
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	/**
	 */
	public String getRelationConstraintName() {
		return relationConstraintName;
	}

	/**
	 */
	public void setRelationConstraintName(String relationConstraintName) {
		this.relationConstraintName = relationConstraintName;
	}

}
