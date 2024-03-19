package org.fundamentals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void get() {
        assertEquals("true", Config.get("test_var"));
    }
}