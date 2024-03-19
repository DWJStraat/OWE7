package org.fundamentals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void saveTest() {
        Database.save("test","id, val","1, 2");
    }

    @Test
    void maxTest() {
        int id = Database.getMax("test", "id");
        assertEquals(id, 1);
    }

    @Test
    void get() {
        List<String> data = Database.get("SELECT * FROM test;");
        List<String> row = List.of(data.getFirst().split("\\|"));
        Assertions.assertEquals(row.get(0), "1");
        Assertions.assertEquals(row.get(1), "2");
    }

    @Test
    void removeTest(){
        Database.remove("test", "id = 1");
        List<String> data = Database.get("SELECT * FROM test;");
        assertEquals(data.size(), 0);
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