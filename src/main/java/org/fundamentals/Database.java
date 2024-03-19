package org.fundamentals;

import org.blast.BlastHit;

import java.io.File;
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

    public static void query(Connection conn, String query) {
//        query = "USE " + db + "; " + query;
        log.log(Level.INFO, "Executing query: {0}", query);
        try {
            conn.createStatement().execute(query);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    public static void save(String Table, String[] columns, String[] values) {
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

    public static void save(String Table, String columsn, String values){
        String[] columns = columsn.split(",");
        String[] vals = values.split(",");
        save(Table, columns, vals);
    }

    public static void remove(String Table, String condition) {
        String query = "DELETE FROM " + Table + " WHERE " + condition + ";";
        execute(query);
    }

    public static List<String> get(String query) {
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
        }
        close(conn);
        return results;
    }

    public static void execute(String query) {
        Connection conn = connect();
        query(conn, query);
        commit(conn);
        close(conn);
    }

    public static void execute(String[] queries) {
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

    public static void saveBlastHit(BlastHit blastHit) {

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
