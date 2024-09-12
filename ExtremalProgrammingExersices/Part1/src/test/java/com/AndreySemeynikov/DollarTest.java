package com.AndreySemeynikov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DollarTest {
    @Test
    void testMultiplication() {
        Dollar five = new Dollar(5.0);
        five.times(2.0);
        assertEquals(10, five.amount);
    }
}