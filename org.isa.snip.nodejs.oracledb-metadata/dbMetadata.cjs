/* Authored by iqbserve.de */

/**
 *  Metadata base class.
 */
class AbstractDbMetadata {
    name = "";
    sysObject = false;

    constructor(name) {
        this.name = name;
    }

    isSysObject() {
        return this.sysObject;
    }

    getName() {
        return this.name;
    }

    setSysObject(flag) {
        this.sysObject = flag;
    }

    toJSON() { 
        return {
            name: this.name,
            sysObject: this.sysObject
        };
    }    
    
    toJSONString(space=0) { 
        return JSON.stringify(this.toJSON(), null, space)
    }

    fromJSON(json) { 
        if(typeof json === 'string' || json instanceof String){
            json = JSON.parse(json);
        }
        Object.assign(this, json);
        return this;
    }
}

/**
 */
class DbTableMetadata extends AbstractDbMetadata {
    temporary = "";
    columnNames = new Set();

    addColumn(def){
        this.columnNames.add(def.getName());
    }

    toJSON() { 
        let json = super.toJSON();
        json.temporary = this.temporary;
        json.columnNames = Array.from(this.columnNames.values());
        return json;
    }

    fromJSON(json) { 
        super.fromJSON(json);
        this.columnNames = new Set(this.columnNames);
        return this;
    }
}

/**
 */
class DbColumnMetadata extends AbstractDbMetadata {
    tableName = "";
    type = "";
    length = "";
    charLength = "";
    precision = "";
    nullable = "";

    toJSON() { 
        let json = super.toJSON();
        json.tableName = this.tableName;
        json.type = this.type;
        json.length = this.length;
        json.charLength = this.charLength;
        json.precision = this.precision;
        json.nullable = this.nullable;
        return json;
    }    
}

/**
 */
class DbConstraintMetadata extends AbstractDbMetadata {
    tableName = "";
    columnName = "";
    type = "";
    deleteRule = "";
    searchCondition = "";
    relationConstraintName = "";

    toJSON() { 
        let json = super.toJSON();
        json.tableName = this.tableName;
        json.columnName = this.columnName;
        json.type = this.type;
        json.deleteRule = this.deleteRule;
        json.searchCondition = this.searchCondition;
        json.relationConstraintName = this.relationConstraintName;
        return json;
    }    
}

/**
 */
class DbIndexMetadata extends AbstractDbMetadata {
    tableName = "";
    uniqueness = "";
    columnNames = new Set();

    addColumnName(name) {
	    this.columnNames.add(name);
	}

    toJSON() { 
        let json = super.toJSON();
        json.tableName = this.tableName;
        json.uniqueness = this.uniqueness;
        json.columnNames = Array.from(this.columnNames.values());
        return json;
    }    

    fromJSON(json) { 
        super.fromJSON(json);
        this.columnNames = new Set(this.columnNames);
        return this;
    }
}

/**
 */
enrichMetaData = (def) => {
    if (def.getName().toUpperCase().startsWith("SYS_")) {
        def.setSysObject(true);
    }
    return def;
};

module.exports = {
    DbTableMetadata: DbTableMetadata,
    DbColumnMetadata: DbColumnMetadata,
    DbConstraintMetadata : DbConstraintMetadata,
    DbIndexMetadata : DbIndexMetadata,
    enrichMetaData : enrichMetaData
}
