
/* Authored by iqbserve.de */

const oracledb = require('oracledb');
const model = require('./oracleDbModel.cjs');
const queries = require('./oracleDbMetadataQueries.cjs');
const fs = require('node:fs');

oracledb.outFormat = oracledb.OUT_FORMAT_OBJECT;

const props = {
    modelFile : '/tmp/OracleDbModel.json'
}

/**
 * Read a database schema metadata model from an Oracle Database,
 * save it as json in a file
 * read it back from the json file
 */
function main() {

    loadDbModelFromDatabase({
        run: true, //toggle db loading true/false
        nextStep: (dbModel=null) => {
            if(dbModel!=null){
                writeDbModelToJsonFile(dbModel);
            }else{
                readDbModelFromJsonFile();
            }
        }
    });
}

/**
 * Load an entire metamodel from a database
 */
async function loadDbModelFromDatabase(params = null) {

    if(params != null && params.run){
        console.log("Start DB loading ...");
        console.time("LoadTimeMetadata");

        let schemaOwner = "TEST"; //should be uppercase for oracle sql query
        let query = "";
        let result = null;
        let dbModel = new model.OracleDbModel();

        const connection = await oracledb.getConnection({
            user: schemaOwner,
            password: "testpwd",
            connectString: "localhost/XEPDB1"
        });

        //loading tables and columns
        query = queries.getAllTablesAndColumsSql(schemaOwner);
        result = await connection.execute(query);
        dbModel.loadTablesAndColumsFrom(result);

        //loading constraints
        query = queries.getAllColumnConstraintsSql(schemaOwner);
        result = await connection.execute(query);
        dbModel.loadColumnConstraintsFrom(result);

        //loading indexes
        query = queries.getAllIndexesSql(schemaOwner);
        result = await connection.execute(query);
        dbModel.loadIndexesFrom(result);

        await connection.close();

        console.timeEnd("LoadTimeMetadata");
        console.log("Finished model loading from Database");

        params.nextStep(dbModel);
    }else{
        params.nextStep();
    }    
}

/**
 * Write a previously loaded model to a JSON file
 */
function writeDbModelToJsonFile(dbModel){

    let content = dbModel.toJSONString();
    fs.writeFileSync(props.modelFile, content);

    console.log("DbModel saved to ["+props.modelFile+"]", dbModel);
}

/**
 * Read a model from a JSON file
 */
function readDbModelFromJsonFile(){

    let content = fs.readFileSync(props.modelFile, 'utf-8');
    let dbModel = new model.OracleDbModel().fromJSON(content);

    console.log("DbModel read from ["+props.modelFile+"]", dbModel);
}

main();


