package com.AndreySemeynikov;

public class Money implements Expression {
    protected double amount;
    protected String currency;

    public Money(double amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    static Money dollar(double amount){
        return new Money(amount, "USD");
    }
    static Money franc(double amount){
        return new Money(amount, "CHF");
    }

    String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object object){
        Money money = (Money) object;
        return amount == money.amount && currency.equals(money.currency);
    }

    Money times(double multiplier) {
        return new Money(amount*multiplier, currency);
    }

    Expression plus(Money addend){
        return new Sum(this, addend);
    }

    public Money reduce(Bank bank, String to){
        double rate = bank.rate(currency, to);
        return new Money(amount / rate, to);
    }

}

// нужно чтобы при создании объекта у него было поел currency