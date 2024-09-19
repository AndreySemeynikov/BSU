package com.AndreySemeynikov;


/*Цикл TTD
Добавить небольшой тест.
2.Запустить все тесты и убедиться, что�новый�тест�терпит�неудачу.
3.�Внести�небольшое�изменение.
4.� Снова� запустить� тесты� и� убедиться,� что� все� они� успешно
выполняются.
5.�Устранить�дублирование�с�помощью�рефакторинга.*/

import java.util.Objects;

public class Franc {

    private Double amount;

    public Franc(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    Franc times(Double multiplier){
        return new Franc(amount * multiplier);
    }

    @Override
    public boolean equals(Object object) {
        Franc dollar = (Franc) object;

        return Objects.equals(amount, dollar.amount);
    }


}
