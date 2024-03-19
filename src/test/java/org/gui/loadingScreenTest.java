package org.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class loadingScreenTest {

    @Test
    void main() throws InterruptedException {
        loadingScreen.Main();
        Thread.sleep(10000);
    }
}