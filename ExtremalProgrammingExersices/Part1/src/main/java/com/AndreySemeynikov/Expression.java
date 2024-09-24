package com.AndreySemeynikov;

public interface Expression {
    Money reduce(Bank bank, String to);
    Expression plus(Expression addend);
    Expression times(double multiplier);
}
