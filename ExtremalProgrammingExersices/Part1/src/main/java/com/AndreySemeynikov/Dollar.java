package com.AndreySemeynikov;


/*Цикл TTD
Добавить небольшой тест.
2.Запустить все тесты и убедиться, что�новый�тест�терпит�неудачу.
3.�Внести�небольшое�изменение.
4.� Снова� запустить� тесты� и� убедиться,� что� все� они� успешно
выполняются.
5.�Устранить�дублирование�с�помощью�рефакторинга.*/

import java.util.Objects;

public class Dollar {
    Double amount;

    public Dollar(Double amount) {
        this.amount = amount;
    }

    Dollar times(Double multiplier){
        return new Dollar(amount * multiplier);
    }

    public boolean equals(Dollar dollar) {
        return Objects.equals(amount, dollar.amount);
    }
}
