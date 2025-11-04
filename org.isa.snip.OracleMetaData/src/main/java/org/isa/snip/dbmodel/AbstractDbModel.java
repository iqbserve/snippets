/* Authored by iqbserve.de */

package org.isa.snip.dbmodel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.isa.snip.dbmodel.util.Helper;

/**
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractDbModel {

	private static final Logger LOG = Helper.getLoggerFor(AbstractDbModel.class);
	
	protected Map<String, DbTableMetadata> tables = new LinkedHashMap<>();
	protected Map<String, DbColumnMetadata> columns = new LinkedHashMap<>();
	protected Map<String, DbConstraintMetadata> constraints = new LinkedHashMap<>();
	protected Map<String, DbConstraintMetadata> sysConstraints = new LinkedHashMap<>();
	protected Map<String, DbIndexMetadata> indexes = new LinkedHashMap<>();
	protected Map<String, DbIndexMetadata> sysIndexes = new LinkedHashMap<>();
	protected Map<String, DbSequenceMetadata> sequences = new LinkedHashMap<>();
	protected Map<String, DbTypeMetadata> types = new LinkedHashMap<>();
	
	/**
	 */
	public AbstractDbModel() {
	}

	/**
	 */
	public abstract void loadMetaData() throws Exception;

	/**
	 */
	protected void putToMap(Map pDestMap, Object pKey, Object pData) {
		if (!pDestMap.containsKey(pKey)) {
			pDestMap.put(pKey, pData);
		}else {
			LOG.warning("Duplicate metadata key: ["+pKey+"] ["+pData.getClass().getSimpleName()+"]");
		}
	}

	/**
	 */
	protected DbConstraintMetadata getConstraintDef(String pName) {
		if (constraints.containsKey(pName)) {
			return constraints.get(pName);
		}
		return sysConstraints.get(pName);
	}

	/**
	 */
	protected DbIndexMetadata getIndexDef(String pName) {
		if (indexes.containsKey(pName)) {
			return indexes.get(pName);
		}
		return sysIndexes.get(pName);
	}

}
