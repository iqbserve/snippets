<!-- Authored by iqbserve.de -->

<!--Oracle Metadata SQL Queries-->

<!--Tables and Columns from all-->
<all.tables.and.columns>
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
     t.owner = '${owner}' ${filter}

</all.tables.and.columns>
<!--END-->


<!--Tables and Columns from user-->
<user.tables.and.columns>
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
     user_tables t
     LEFT JOIN user_tab_columns c ON ( c.table_name = t.table_name )
 ${filter}

</user.tables.and.columns>
<!--END-->


<!--Column Constraints from all-->
<all.column.constraints>
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
     cc.owner = '${owner}' ${filter}
     
</all.column.constraints>
<!--END-->


<!--Column Constraints from user-->
<user.column.constraints>
SELECT
     cc.table_name,
     cc.column_name,
     c.constraint_name,
     c.constraint_type,
     c.delete_rule,
     c.search_condition,
     c.r_constraint_name   
 FROM
     user_cons_columns cc
     LEFT JOIN user_constraints c ON ( c.constraint_name = cc.constraint_name
                                       AND c.table_name = cc.table_name
                                       AND c.owner = cc.owner )
 WHERE
     cc.owner = '${owner}' ${filter}
     
</user.column.constraints>
<!--END-->


<!--Indexes from all-->
<all.indexes>
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
     c.table_owner = '${owner}' ${filter}
     
</all.indexes>
<!--END-->


<!--Indexes from user-->   
<user.indexes>    
SELECT
     c.index_name,
     c.table_name,
     c.column_name,
     i.uniqueness
 FROM
     user_ind_columns c
     LEFT JOIN user_indexes i ON ( c.index_name = i.index_name
                                   AND c.table_name = i.table_name )
 ${filter}

</user.indexes>
<!--END-->

        
<!--Sequences from all-->
<all.sequences>
SELECT
     sequence_name,
     increment_by
 FROM
     all_sequences
 WHERE
     sequence_owner = '${owner}'
     
</all.sequences>
<!--END-->


<!--Sequences from user-->
<user.sequences>
SELECT
     sequence_name,
     increment_by
 FROM
     user_sequences
     
</user.sequences>
<!--END-->


<!--Types from all-->
<all.types>
SELECT
     type_name
 FROM
     all_types
 WHERE
     owner = '${owner}'
     
</all.types>
<!--END-->


<!--Types from user-->
<user.types>
SELECT
     type_name
 FROM
     user_types
     
</user.types>
<!--END-->


<!--Table Comments from all-->
<all.table.comments>
SELECT
     table_name,
     comments
 FROM
     all_tab_comments
 WHERE
     table_type = 'TABLE'
     AND comments IS NOT NULL
     AND owner = '${owner}' 
     ${filter}
     
</all.table.comments>
<!--END-->


<!--Table Comments from user-->
<user.table.comments>
SELECT
     table_name,
     comments
 FROM
     user_tab_comments
 WHERE
     table_type = 'TABLE'
     AND comments IS NOT NULL 
     ${filter}
     
</user.table.comments>
<!--END-->


<!--Column Comments from all-->
<all.column.comments>
SELECT
     table_name,
     column_name,
     comments
 FROM
     all_col_comments
 WHERE
     comments IS NOT NULL
     AND owner = '${owner}'
     ${filter}
     
</all.column.comments>
<!--END-->


<!--Column Comments from user-->
<user.column.comments>
SELECT
     table_name,
     column_name,
     comments
 FROM
     user_col_comments
 WHERE
     comments IS NOT NULL
     ${filter}
     
</user.column.comments>
<!--END-->

