package com.AndreySemeynikov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void testEquality(){
        assertTrue(new Dollar(5.0).equals(new Dollar(5.0)));
        assertFalse(new Dollar(5.0).equals(new Dollar(6.0)));
        assertTrue(new Franc(5.0).equals(new Franc(5.0)));
        assertFalse(new Franc(5.0).equals(new Franc(6.0)));
        assertFalse(new Dollar(5.0).equals(new Franc(5.0)));
        assertFalse(new Dollar(5.0).equals(new Franc(6.0)));

    }

}