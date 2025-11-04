/* Authored by iqbserve.de */
package org.isa.snip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.isa.snip.dbmodel.DbColumnMetadata;
import org.isa.snip.dbmodel.DbTableMetadata;
import org.junit.jupiter.api.Test;

/**
 */
public class DbModelMetadataBaseTest {

	/**
	 */
	public DbModelMetadataBaseTest() {
	}
	
	@Test
	public void testInstantiation()  {
        DbTableMetadata lTable;
        DbColumnMetadata lColumn;
        
        lTable = new DbTableMetadata("User");
        lColumn = new DbColumnMetadata(lTable.getName(), "userid");

        
        assertEquals("User", lTable.getName());
        
        assertEquals("userid", lColumn.getName());
        assertEquals("User", lColumn.getTablename());

	}

}
