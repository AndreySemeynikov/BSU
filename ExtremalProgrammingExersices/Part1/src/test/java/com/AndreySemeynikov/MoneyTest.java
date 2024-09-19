package com.AndreySemeynikov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void testEquality(){
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertTrue(Money.franc(5).equals(Money.franc(5)));
        assertFalse(Money.franc(5).equals(Money.franc(6)));
        assertFalse(Money.dollar(5).equals(Money.franc(5)));
        assertFalse(Money.dollar(5).equals(Money.franc(6)));
    }

    @Test
    void testMultiplication(){
        Money five = Money.dollar(5.0);
        assertEquals(Money.dollar(10.0), five.times(2.0));
        assertEquals(Money.dollar(15.0), five.times(3.0));
    }

    @Test
    public void testCurrency() {
        assertEquals("CHF", Money.franc(6).currency());
        assertEquals("USD", Money.dollar(5).currency());
    }

    @Test
    public void testDifferentClassQuality() {
        assertTrue(new Money(10, "USD").equals(new Dollar(10, "USD")));
    }
}