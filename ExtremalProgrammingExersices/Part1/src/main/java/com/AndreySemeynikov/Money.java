package com.AndreySemeynikov;

import java.util.Objects;

public abstract class Money {
    protected double amount;
    protected String currency;

    public Money(double amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    abstract Money times(double multiplier);

    static Dollar dollar(double amount){
        return new Dollar(amount, "USD");
    }
    static Franc franc(double amount){
        return new Franc(amount, "CHF");
    }

    String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object object){
        Money money = (Money) object;
        return Objects.equals(amount, money.amount) && getClass().equals(money.getClass());
    }
}

// нужно чтобы при создании объекта у него было поел currency