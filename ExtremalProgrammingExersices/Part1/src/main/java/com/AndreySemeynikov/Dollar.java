package com.AndreySemeynikov;


/*Цикл TTD
Добавить небольшой тест.
2.Запустить все тесты и убедиться, что�новый�тест�терпит�неудачу.
3.�Внести�небольшое�изменение.
4.� Снова� запустить� тесты� и� убедиться,� что� все� они� успешно
выполняются.
5.�Устранить�дублирование�с�помощью�рефакторинга.*/

public class Dollar {
    Double amount;

    public Dollar(Double amount) {
        this.amount = amount;
    }

    Dollar times(Double multiplier){
        return new Dollar(amount * multiplier);
    }
}
