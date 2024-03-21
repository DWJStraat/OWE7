package org.fundamentals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseTest {

    @Test
    void connect() {
        assertNotNull(Database.connect());
    }

    @Test
    void close() {
        Database.close(Database.connect());
    }

    @Test
    void commit() {
        Database.commit(Database.connect());
    }

    @Test
    void saveTest() throws SQLException {
        Database.save("test","id, val","1, 2");
    }

    @Test
    void maxTest() throws SQLException {
        int id = Database.getMax("test", "id");
        assertEquals(id, 1);
    }

    @Test
    void get() throws SQLException {
        List<String> data = Database.get("SELECT * FROM test;");
        List<String> row = List.of(data.getFirst().split("\\|"));
        Assertions.assertEquals(row.get(0), "1");
        Assertions.assertEquals(row.get(1), "2");
    }

    @Test
    void removeTest() throws SQLException {
        Database.remove("test", "id = 1");
        List<String> data = Database.get("SELECT * FROM test;");
        assertEquals(data.size(), 0);
    }

    @Test
    void exportTest() throws SQLException {
        Database.exportDb();
    }

//    @Test
//    void create() {
//        Database.BuildDatabase();
//    }
//
//    @Test
//    void destroy() {
//        Database.DropDatabase();
//    }
}