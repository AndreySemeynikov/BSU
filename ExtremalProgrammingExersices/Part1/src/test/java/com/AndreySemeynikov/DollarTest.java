package com.AndreySemeynikov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DollarTest {
    @Test
    void testMultiplication() {
        Dollar five = new Dollar(5.0);
        assertEquals(new Dollar(10.0), five.times(2.0));
        assertEquals(new Dollar(15.0), five.times(3.0));
    }

    @Test
    void testEquality() {
        assertTrue(new Dollar(5.0).equals(new Dollar(5.0)));
        assertFalse(new Dollar(5.0).equals(new Dollar(6.0)));
    }
}