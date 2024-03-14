package org.fundamentals;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
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
        query = "USE " + db + "; " + query;
        log.log(Level.INFO, "Executing query: {0}", query);
        try {
            conn.createStatement().execute(query);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "An error occurred :" + e);
            log.log(Level.SEVERE, "" + e.getCause());
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
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

//    public static void saveBlastHit(BlastHit blastHit) {
//        String query = "INSERT INTO blast
//        execute(query);
//    }

// todo: add a method that builds or drops the database

//    public static void BuildDatabase() {
//        executeFromSql("src/main/resources/sql/create.sql");
//    }
//
//    public static void DropDatabase() {
//        executeFromSql("src/main/resources/sql/drop.sql");
//    }



}
