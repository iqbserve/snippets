/* Authored by iqbserve.de */

/**
 * Predefined metadata queries as template literals in `...` backticks
 */
exports.getAllTablesAndColumsSql = (owner) => {
    let sql = `
SELECT
     t.table_name,
     t.temporary,
     c.column_name,
     c.data_scale,
     c.data_type,
     c.data_length,
     c.data_precision,
     c.char_length,
     c.nullable
 FROM
     all_tables t
     LEFT JOIN all_tab_columns c ON ( c.table_name = t.table_name
                                      AND c.owner = t.owner )
 WHERE
     t.owner = '${owner}'
`;
    return sql;
};

/**
 */
exports.getAllColumnConstraintsSql = (owner) => {
    let sql = `
SELECT
     cc.table_name,
     cc.column_name,
     c.constraint_name,
     c.constraint_type,
     c.delete_rule,
     c.search_condition,
     c.r_constraint_name
 FROM
     all_cons_columns cc
     LEFT JOIN all_constraints c ON ( c.constraint_name = cc.constraint_name
                                      AND c.table_name = cc.table_name
                                      AND c.owner = cc.owner )
 WHERE
     cc.owner = '${owner}'
`;
    return sql;
};

/**
 */
exports.getAllIndexesSql = (owner) => {
    let sql = `
 SELECT
     c.index_name,
     c.table_name,
     c.column_name,
     i.uniqueness
 FROM
     all_ind_columns c
     LEFT JOIN all_indexes i ON ( c.index_name = i.index_name
                                  AND c.table_name = i.table_name
                                  AND c.table_owner = i.table_owner )
 WHERE
     c.table_owner = '${owner}'
`;
    return sql;
};