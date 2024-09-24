package com.AndreySemeynikov;

public interface Expression {
    Money reduce(Bank bank, String to);
}
