package org.fundamentals;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.blast.Blast;
import org.blast.BlastHit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final String url = Config.get("db_url");
    private static final String user = Config.get("db_user");
    private static final String password = Config.get("db_password");
    private static final String db = Config.get("db_name");
    private static final Logger log = Logger.getLogger(Database.class.getName());

    public static void main(String[] args) {
    }

    public static Connection connect() {
        log.log(Level.INFO, "Connecting to database {0}", url);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://" + url + "/" + db, user, password
            );
            conn.setAutoCommit(true);
            conn.setSchema(db);
        } catch (SQLException e) {
            if(e.getMessage().startsWith("No suitable driver found for")){
                log.log(Level.SEVERE, "Error: {}", e.getMessage());
                log.log(Level.SEVERE, "Please ensure that the MariaDB driver is installed and available" +
                        " in the classpath");
            }
            else {
                log.log(Level.SEVERE, "An error occurred :" + e);
                log.log(Level.SEVERE, "" + e.getCause());
                log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            }


        }
        return conn;
    }

    public static void close(Connection conn) {
        try {
            conn.close();
            log.log(Level.INFO, "Connection closed");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    public static void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    public static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    public static void query(Connection conn, String query) throws SQLException {
//        query = "USE " + db + "; " + query;
        log.log(Level.INFO, "Executing query: {0}", query);
        try {
            conn.createStatement().execute(query);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            throw new SQLException(e);
        }
    }

    public static void save(String Table, String[] columns, String[] values) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO " + Table + " (");
        for (String column : columns) {
            query.append(column).append(", ");
        }
        query.setLength(query.length() - 2);
        query.append(") VALUES (");
        for (String value : values) {
            query.append(value).append(", ");
        }
        query.setLength(query.length() - 2);
        query.append(");");
        execute(query.toString());
    }

    public static void save(String Table, String columsn, String values) throws SQLException {
        String[] columns = columsn.split(",");
        String[] vals = values.split(",");
        save(Table, columns, vals);
    }

    public static void remove(String Table, String condition) throws SQLException {
        String query = "DELETE FROM " + Table + " WHERE " + condition + ";";
        execute(query);
    }

    public static List<List<String>> bigGet(String query) throws SQLException {
        Connection conn = connect();
        ResultSet data;
        List<List<String>> results = new ArrayList<>();
        try {
            data = conn.createStatement().executeQuery(query);
            while (data.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= data.getMetaData().getColumnCount(); i++) {
                    row.add(data.getString(i));
                }
                results.add(row);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            throw(new SQLException(e));
        }
        close(conn);
        return results;
    }
    public static List<String> get(String query) throws SQLException {
        Connection conn = connect();
        ResultSet data;
        List<String> results = new ArrayList<>();
        try {
            data = conn.createStatement().executeQuery(query);
            while (data.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= data.getMetaData().getColumnCount(); i++) {
                    row.append(data.getString(i)).append("|");
                }
                row.setLength(row.length() - 1);
                results.add(row.toString());
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            throw(new SQLException(e));
        }
        close(conn);
        return results;
    }

    public static void execute(String query) throws SQLException {
        Connection conn = connect();
        query(conn, query);
        commit(conn);
        close(conn);
    }

    public static void execute(String[] queries) throws SQLException {
        Connection conn = connect();
        for (String query : queries) {
            query(conn, query);
        }
        commit(conn);
        close(conn);
    }

    private static String readSql(String path) {
        File file = new File(path);
        try(java.util.Scanner scanner = new java.util.Scanner(file)){
            return scanner.useDelimiter("\\A").next();
        } catch (java.io.FileNotFoundException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return "";
    }

    public static void executeFromSql(String path) {
        Connection conn = connect();
        try {
            String query = "use " + db + "; " + readSql(path);
            log.log(Level.INFO, "Executing query: {0}", query);
            conn.createStatement().execute(query);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        commit(conn);
        close(conn);
    }

    public static void saveBlast(Blast blastObject) throws SQLException {
        int seqId = saveSequence(blastObject);
        int orfId = saveOrf(blastObject, seqId);
        for (BlastHit hit : blastObject.hits) {
            int blastId = saveBlastResults(hit);
            String[] columns = {"orfs_orf_id, blast_results_result_id"};
            String[] values = {orfId + "", blastId + ""};
            save("orfs_blast_results", columns, values);
        }
    }

    private static int saveSequence(Blast blastObject) throws SQLException {
        if (!get("SELECT * FROM sequence WHERE sequence = '" + blastObject.sequence + "';").isEmpty()) {
            return Integer.parseInt(get("SELECT seq_id FROM sequence WHERE sequence = '" + blastObject.sequence + "';").getFirst().
                    split("\\|")[0]);
        }
        String[] columns = {"seq_id, sequence"};
        Integer seqId = getNewId("sequence", "seq_id");
        String[] values = {seqId +", '" + blastObject.sequence + "'"};
        save("sequence", columns, values);
        return getMax("sequence", "id");
    }

    private static int saveOrf(Blast blastObject, int seqId) throws SQLException {
        String sequence = blastObject.sequence;
        String hashval = Integer.toString(sequence.hashCode());
        if (!get("SELECT * FROM orfs WHERE hash = " + hashval + ";").isEmpty()) {
            return Integer.parseInt(get("SELECT orf_id FROM orfs WHERE hash = " + hashval + ";").getFirst().
                    split("\\|")[0]);
        }
        String[] columns = {"seq, hash, sequence_seq_id, orf_id"};
        String[] values = {sqlString(sequence), hashval, seqId + "", getNewId("orfs", "orf_id") + ""};
        save("orfs", columns, values);
        return getMax("orfs", "orf_id");
    }

    private static String sqlString(String string){
        return "'" + string + "'";
    }

    private static int saveBlastResults(BlastHit blastHit) throws SQLException {
        if (!get("SELECT * FROM blast_results WHERE bit_score = " + blastHit.bitScore + ";").isEmpty()) {
            return Integer.parseInt(get("SELECT result_id FROM blast_results WHERE bit_score = " + blastHit.bitScore + ";").getFirst().
                    split("\\|")[0]);
        }
        String[] columns = {"result_id, bit_score, score, eval, identity, positives, gaps, length, accession"};
        String[] values = {String.valueOf(getNewId("blast_results", "result_id")), blastHit.bitScore.toString(),
                blastHit.score + "", sqlString(blastHit.eval), blastHit.identity + "",
                blastHit.positives + "", blastHit.gaps + "", blastHit.length + "", sqlString(blastHit.accession)};
        save("blast_results", columns, values);
        return getMax("blast_results", "result_id");

    }

    public static int getMax(String table, String collumn) throws SQLException {
        List<String> data = get("SELECT max(" + collumn + ") FROM " + table + ";");
        String row = data.getFirst().split("\\|")[0];
        if (row.equals("null")) {
            return -1;
        }
        return Integer.parseInt(row);
    }

    public static int getNewId(String table, String collumn) throws SQLException {
        return getMax(table, collumn) + 1;
    }

    public static List<List<String>> join() throws SQLException {
        return(bigGet("""
                SELECT seq, bit_score, score, eval, identity, positives, gaps, length, accession\s
                FROM orfs
                    JOIN orfs_blast_results\s
                        ON orfs.orf_id = orfs_blast_results.orfs_orf_id
                    JOIN blast_results\s
                        ON orfs_blast_results.blast_results_result_id = blast_results.result_id;"""));
    }

    public static void exportDb() throws SQLException {
        // with inspiration from https://stackoverflow.com/a/37778241
        try (
                XSSFWorkbook workbook = new XSSFWorkbook();
                FileOutputStream output = new FileOutputStream("export.xlsx") ){
            XSSFSheet sheet = workbook.createSheet("Export");
            List<List<String>> data = join();
            int rowNum = 0;
            int cellNum = 0;
            for (List<String> rowContents : data) {
                Row row = sheet.createRow(rowNum++);
                cellNum = 0;
                for (String cell : rowContents) {
                    row.createCell(cellNum++).setCellValue(cell);
                }
            }
            workbook.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

// todo: add a method that builds or drops the database

//    public static void BuildDatabase() {
//        executeFromSql("src/main/resources/sql/create.sql");
//    }
//
//    public static void DropDatabase() {
//        executeFromSql("src/main/resources/sql/drop.sql");
//    }



}
