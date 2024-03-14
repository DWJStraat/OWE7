package org.fundamentals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void connect() {
        assertNotNull(Database.connect());
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