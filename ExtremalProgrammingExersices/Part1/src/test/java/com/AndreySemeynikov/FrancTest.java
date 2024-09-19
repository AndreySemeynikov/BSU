package com.AndreySemeynikov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrancTest {
    @Test
    void testMultiplication() {
        Money five = Money.franc(5);
        assertEquals(Money.franc(10), five.times(2.0));
        assertEquals(Money.franc(15), five.times(3.0));
    }

    @Test
    void testEquality() {
        assertTrue(Money.franc(5).equals(Money.franc(5)));
        assertFalse(Money.franc(5).equals(Money.franc(6)));
    }
}