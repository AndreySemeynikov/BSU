package com.AndreySemeynikov;

import java.util.Objects;

public abstract class Money {
    protected double amount;

    abstract Money times(double multiplier);

    static Dollar dollar(double amount){
        return new Dollar(amount);
    }
    static Franc franc(double amount){
        return new Franc(amount);
    }
    @Override
    public boolean equals(Object object){
        Money money = (Money) object;
        return Objects.equals(amount, money.amount) && getClass().equals(money.getClass());
    }
}
