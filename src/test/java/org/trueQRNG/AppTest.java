package org.trueQRNG;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {

    @Test
    void testMain() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
