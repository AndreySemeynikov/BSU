package com.AndreySemeynikov;


/*Цикл TTD
Добавить небольшой тест.
2.Запустить все тесты и убедиться, что�новый�тест�терпит�неудачу.
3.�Внести�небольшое�изменение.
4.� Снова� запустить� тесты� и� убедиться,� что� все� они� успешно
выполняются.
5.�Устранить�дублирование�с�помощью�рефакторинга.*/

import java.util.Objects;

public class Dollar extends Money{

    public Dollar(double amount, String currency) {
        super(amount, currency);
    }

    Dollar times(double multiplier){
        return Money.dollar(amount * multiplier);
    }
}
