package com.AndreySemeynikov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DollarTest {
    @Test
    void testMultiplication() {
        Dollar five = new Dollar(5.0);
        assertEquals(Money.dollar(10), five.times(2.0));
        assertEquals(Money.dollar(15), five.times(3.0));
    }

    @Test
    void testEquality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    }
}