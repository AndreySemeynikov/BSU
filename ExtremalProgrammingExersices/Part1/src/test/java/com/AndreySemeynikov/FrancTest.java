package com.AndreySemeynikov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrancTest {
    @Test
    void testMultiplication() {
        Franc five = new Franc(5.0);
        assertEquals(new Franc(10.0), five.times(2.0));
        assertEquals(new Franc(15.0), five.times(3.0));
    }

    @Test
    void testEquality() {
        assertTrue(new Franc(5.0).equals(new Franc(5.0)));
        assertFalse(new Franc(5.0).equals(new Franc(6.0)));
    }
}