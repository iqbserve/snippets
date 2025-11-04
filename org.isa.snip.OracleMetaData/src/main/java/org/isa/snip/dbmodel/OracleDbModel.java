/* Authored by iqbserve.de */

package org.isa.snip.dbmodel;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import org.isa.snip.dbmodel.util.ExprString;
import org.isa.snip.dbmodel.util.Helper;
import org.isa.snip.dbmodel.util.JdbcConnection;

/**
 */
public class OracleDbModel extends AbstractDbModel {

    private static final Logger LOG = Helper.getLoggerFor(OracleDbModel.class);

    // Text file providing Metadata SQL Queries as flat "xml elements"
    protected static final String SQL_QUERIES_FILE = "/" + OracleDbModel.class.getPackageName().replaceAll("[.]", "/")
            + "/oracle.metadata.queries.sql";

    protected static final String FILTER_MARK = "filter";
    protected static final String OWNER_MARK = "owner";

    // query tag names in file oracle.metadata.queries.sql
    protected static final String QUERY_ALL_TABLES_AND_COLUMNS = "all.tables.and.columns";
    protected static final String QUERY_USER_TABLES_AND_COLUMNS = "user.tables.and.columns";
    protected static final String QUERY_ALL_COLUMN_CONSTRAINTS = "all.column.constraints";
    protected static final String QUERY_USER_COLUMN_CONSTRAINTS = "user.column.constraints";
    protected static final String QUERY_ALL_INDEXES = "all.indexes";
    protected static final String QUERY_USER_INDEXES = "user.indexes";
    protected static final String QUERY_ALL_SEQUENCES = "all.sequences";
    protected static final String QUERY_USER_SEQUENCES = "user.sequences";
    protected static final String QUERY_ALL_TYPES = "all.types";
    protected static final String QUERY_USER_TYPES = "user.types";
    protected static final String QUERY_ALL_TABLE_COMMENTS = "all.table.comments";
    protected static final String QUERY_USER_TABLE_COMMENTS = "user.table.comments";
    protected static final String QUERY_ALL_COLUMN_COMMENTS = "all.column.comments";
    protected static final String QUERY_USER_COLUMN_COMMENTS = "user.column.comments";

    // query column names
    protected static final String qc_table_name = "table_name";
    protected static final String qc_temporary = "temporary";
    protected static final String qc_column_name = "column_name";
    protected static final String qc_data_scale = "data_scale";
    protected static final String qc_data_type = "data_type";
    protected static final String qc_data_length = "data_length";
    protected static final String qc_data_precision = "data_precision";
    protected static final String qc_char_length = "char_length";
    protected static final String qc_nullable = "nullable";

    protected static final String qc_constraint_name = "constraint_name";
    protected static final String qc_constraint_type = "constraint_type";
    protected static final String qc_delete_rule = "delete_rule";
    protected static final String qc_search_condition = "search_condition";
    protected static final String qc_r_constraint_name = "r_constraint_name";

    protected static final String qc_index_name = "index_name";
    protected static final String qc_uniqueness = "uniqueness";

    protected static final String qc_sequence_name = "sequence_name";
    protected static final String qc_increment_by = "increment_by";

    protected static final String qc_type_name = "type_name";

    /**
     */
    public static final Function<AbstractDbMetadata, AbstractDbMetadata> SysObjectEnricher = (def) -> {
        if (def.getName().toUpperCase().startsWith("SYS_")) {
            def.setSysObject(true);
        }
        return def;
    };

    protected static Map<String, String> QueryStatements = null;

    protected JdbcConnection dbConnection;
    protected String schemaOwner = "TEST";

    /**
     */
    public OracleDbModel() {
    }

    /**
     */
    public OracleDbModel setSchemaOwner(String pOwner) {
        schemaOwner = pOwner;
        return this;
    }

    /**
     */
    public void setDbConnection(JdbcConnection pConnection) {
        this.dbConnection = pConnection;
    }

    /**
     */
    @Override
    public void loadMetaData() throws Exception {
        try {
            new TableAndColumnMetaDataLoader().run();
            new ColumnConstraintsMetaDataLoader().run();
            new IndexesMetaDataLoader().run();
            new SequencesMetaDataLoader().run();
            new TypeMetaDataLoader().run();
        } finally {
            dbConnection.close();
        }
    }

    /**
     * Retrieve SQL Metadata Queries from the Queries File.
     */
    protected ExprString getSqlStatement(String pName) throws Exception {
        String lSql = "";
        String lAllStatements = "";
        ExprString lQueryStatement;

        if (QueryStatements == null) {
            lAllStatements = Helper.readResourceFile(SQL_QUERIES_FILE);
            QueryStatements = new HashMap<>();
            QueryStatements.put(SQL_QUERIES_FILE, lAllStatements);
        }

        if (!QueryStatements.containsKey(pName)) {
            String lStart = "<" + pName + ">";
            String lEnd = "</" + pName + ">";
            lAllStatements = QueryStatements.get(SQL_QUERIES_FILE);
            lSql = lAllStatements.substring(lAllStatements.indexOf(lStart) + lStart.length(),
                    lAllStatements.indexOf(lEnd));
            QueryStatements.put(pName, lSql);
        }
        lSql = QueryStatements.get(pName);

        lQueryStatement = new ExprString(lSql.trim());
        lQueryStatement.put(OWNER_MARK, schemaOwner.toUpperCase());
        lQueryStatement.put(FILTER_MARK, "");

        return lQueryStatement;
    }

    /********************************************************************
     * Start Metadata fill methods
     ********************************************************************/

    /**
     */
    private void fillMetadataObj(DbTableMetadata pDef, ResultSet pRs) throws Exception {
        pDef.setTemporary(pRs.getString(qc_temporary));
    }

    /**
     */
    private void fillMetadataObj(DbColumnMetadata pDef, ResultSet pRs) throws Exception {
        pDef.setTablename(pRs.getString(qc_table_name));
        pDef.setType(pRs.getString(qc_data_type));
        pDef.setLength(pRs.getString(qc_data_length));
        pDef.setPrecision(pRs.getString(qc_data_precision));
        pDef.setScale(pRs.getString(qc_data_scale));

        pDef.setNullable(pRs.getString(qc_nullable));
        pDef.setCharlength(pRs.getString(qc_char_length));
    }

    /**
     */
    private void fillMetadataObj(DbConstraintMetadata pDef, ResultSet pRs) throws Exception {
        pDef.setType(pRs.getString(qc_constraint_type));
        pDef.setTablename(pRs.getString(qc_table_name));
        pDef.setColumnName(pRs.getString(qc_column_name));
        pDef.setDeleteRule(pRs.getString(qc_delete_rule));
        pDef.setSearchCondition(pRs.getString(qc_search_condition));
        pDef.setRelationConstraintName(pRs.getString(qc_r_constraint_name));
        SysObjectEnricher.apply(pDef);
    }

    /**
     */
    private void fillMetadataObj(DbIndexMetadata pDef, ResultSet pRs) throws Exception {
        pDef.setName(pRs.getString(qc_index_name));
        pDef.setTablename(pRs.getString(qc_table_name));
        pDef.addColumnname(pRs.getString(qc_column_name));
        pDef.setUniqueness(pRs.getString(qc_uniqueness));
        SysObjectEnricher.apply(pDef);
    }

    /**
     */
    private void fillMetadataObj(DbSequenceMetadata pDef, ResultSet pRs) throws Exception {
        pDef.setIncrement(pRs.getString(qc_increment_by));
    }

    /**
     */
    private void fillMetadataObj(DbTypeMetadata pDef, ResultSet pRs) throws Exception {
    }

    /********************************************************************
     * End Metadata fill methods
     ********************************************************************/

    /********************************************************************
     * Start Concrete internal Metadata Loader classes.
     ********************************************************************/

    /**
     * TableAndColumnMetaDataLoader.
     */
    private class TableAndColumnMetaDataLoader extends AbstractMetaDataLoader {

        @Override
        protected void doLoad() throws Exception {
            String lSql = getSqlStatement(QUERY_ALL_TABLES_AND_COLUMNS).build();
            LOG.info("SQL: " + lSql);

            result = dbConnection.executeSql(lSql);
            boolean lWork = result.next();
            for (rowCount = 0; lWork; rowCount++) {
                lWork = loadFrom(result);
            }
        }

        /**
         */
        private boolean loadFrom(ResultSet pRes) throws Exception {
            DbTableMetadata lTblDef = null;
            DbColumnMetadata lColDef = null;
            String lTableName = pRes.getString(qc_table_name);
            String lColumnName = pRes.getString(qc_column_name);

            if (!tables.containsKey(lTableName)) {
                lTblDef = new DbTableMetadata(lTableName);
                fillMetadataObj(lTblDef, pRes);
                putToMap(tables, lTableName, lTblDef);
            } else {
                lTblDef = tables.get(lTableName);
            }

            String lColumnKey = new StringBuilder(lTableName).append(".").append(lColumnName).toString();

            if (!columns.containsKey(lColumnKey)) {
                lColDef = new DbColumnMetadata(lColumnName);
                fillMetadataObj(lColDef, pRes);
                putToMap(columns, lColumnKey, lColDef);
                lTblDef.addColumn(lColDef);
            }

            return pRes.next();
        }
    }

    /**
     * ColumnConstraintsMetaDataLoader
     */
    private class ColumnConstraintsMetaDataLoader extends AbstractMetaDataLoader {
        /**
         */
        @Override
        protected void doLoad() throws Exception {
            String lSql = getSqlStatement(QUERY_ALL_COLUMN_CONSTRAINTS).build();
            LOG.info("SQL: " + lSql);

            result = dbConnection.executeSql(lSql);
            boolean lWork = result.next();
            for (rowCount = 0; lWork; rowCount++) {
                lWork = loadFrom(result);
            }
        }

        /**
         */
        private boolean loadFrom(ResultSet pRes) throws Exception {
            DbColumnMetadata lColDef = null;
            DbConstraintMetadata lConstDef = null;
            String lTableName = pRes.getString(qc_table_name);
            String lColumnName = pRes.getString(qc_column_name);
            String lConstraintName = pRes.getString(qc_constraint_name);

            String lColumnKey = new StringBuilder(lTableName).append(".").append(lColumnName).toString();

            lConstDef = getConstraintDef(lConstraintName);
            if (lConstDef == null) {
                lConstDef = new DbConstraintMetadata(lConstraintName);
                fillMetadataObj(lConstDef, pRes);

                if (lConstDef.isSysObject()) {
                    putToMap(sysConstraints, lConstraintName, lConstDef);
                } else {
                    putToMap(constraints, lConstraintName, lConstDef);
                }

                lColDef = columns.get(lColumnKey);
                if (lColDef != null) {
                    lColDef.addConstraint(lConstDef);
                } else {
                    LOG.warning("Unknown Column Reference found while reading column constraints [" + lColumnKey + "]");
                }
            }

            return pRes.next();
        }
    }

    /**
     * IndexesMetaDataLoader
     */
    private class IndexesMetaDataLoader extends AbstractMetaDataLoader {

        @Override
        protected void doLoad() throws Exception {
            String lSql = getSqlStatement(QUERY_ALL_INDEXES).build();
            LOG.info("SQL: " + lSql);

            result = dbConnection.executeSql(lSql);
            boolean lWork = result.next();
            for (rowCount = 0; lWork; rowCount++) {
                lWork = loadFrom(result);
            }
        }

        /**
         */
        private boolean loadFrom(ResultSet pRes) throws Exception {
            DbColumnMetadata lColDef = null;
            DbIndexMetadata lIndexDef = null;
            String lTableName = pRes.getString(qc_table_name);
            String lColumnName = pRes.getString(qc_column_name);
            String lIndexName = pRes.getString(qc_index_name);

            String lColumnKey = new StringBuilder(lTableName).append(".").append(lColumnName).toString();

            lIndexDef = getIndexDef(lIndexName);
            if (lIndexDef == null) {
                lIndexDef = new DbIndexMetadata(lIndexName);
                fillMetadataObj(lIndexDef, pRes);

                if (lIndexDef.isSysObject()) {
                    putToMap(sysIndexes, lIndexName, lIndexDef);
                } else {
                    putToMap(indexes, lIndexName, lIndexDef);
                }

                lColDef = columns.get(lColumnKey);
                if (lColDef != null) {
                    lColDef.addIndex(lIndexDef);
                } else {
                    LOG.warning("Unknown Column Reference found while reading indexes [" + lColumnKey + "]");
                }
            }
            return pRes.next();
        }
    }

    /**
     * SequencesMetaDataLoader
     */
    private class SequencesMetaDataLoader extends AbstractMetaDataLoader {

        @Override
        protected void doLoad() throws Exception {
            String lSql = getSqlStatement(QUERY_ALL_SEQUENCES).build();
            LOG.info("SQL: " + lSql);

            result = dbConnection.executeSql(lSql);
            boolean lWork = result.next();
            for (rowCount = 0; lWork; rowCount++) {
                lWork = loadFrom(result);
            }
        }

        /**
         */
        private boolean loadFrom(ResultSet pRes) throws Exception {
            String lSequenceName = "";
            DbSequenceMetadata lSeqDef = null;

            lSequenceName = pRes.getString(qc_sequence_name);
            lSeqDef = new DbSequenceMetadata(lSequenceName);
            fillMetadataObj(lSeqDef, pRes);
            putToMap(sequences, lSequenceName, lSeqDef);

            return pRes.next();
        }
    }

    /**
     * TypeMetaDataLoader
     */
    private class TypeMetaDataLoader extends AbstractMetaDataLoader {

        @Override
        protected void doLoad() throws Exception {
            String lSql = getSqlStatement(QUERY_ALL_TYPES).build();
            LOG.info("SQL: " + lSql);

            result = dbConnection.executeSql(lSql);
            boolean lWork = result.next();
            for (rowCount = 0; lWork; rowCount++) {
                lWork = loadFrom(result);
            }
        }

        /**
         */
        private boolean loadFrom(ResultSet pRes) throws Exception {
            String lTypeName = "";
            DbTypeMetadata lTypeDef = null;

            lTypeName = pRes.getString(qc_type_name);
            lTypeDef = new DbTypeMetadata(lTypeName);
            fillMetadataObj(lTypeDef, pRes);
            putToMap(types, lTypeName, lTypeDef);

            return pRes.next();
        }
    }

    /********************************************************************
     * End Concrete internal Metadata Loader classes.
     ********************************************************************/

    /**
     * The loaders abstract base class.
     */
    private abstract class AbstractMetaDataLoader {
        protected ResultSet result = null;
        protected int rowCount = 0;

        /**
         */
        protected void run() throws Exception {
            try {
                doBeforeLoad();
                doLoad();
                doAfterLoad();
            } finally {
                dbConnection.closeCurrentStatement();
            }
        }

        /**
         */
        protected void doBeforeLoad() {
            LOG.info("[" + getClass().getSimpleName() + "] Start");
            rowCount = 0;
        }

        /**
         */
        protected void doAfterLoad() throws Exception {
            if (result != null) {
                result.close();
            }
            dbConnection.closeCurrentStatement();
            LOG.info("[" + getClass().getSimpleName() + "] [" + rowCount + "] rows processed\n");
            rowCount = 0;
        }

        /**
         */
        abstract protected void doLoad() throws Exception;
    }
}
