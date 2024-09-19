package com.AndreySemeynikov;

import java.util.Objects;

public class Money {
    protected Double amount;

    @Override
    public boolean equals(Object object){
        Money money = (Money) object;
        return Objects.equals(amount, money.amount);
    }
}
