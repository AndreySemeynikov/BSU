package com.AndreySemeynikov;

import com.AndreySemeynikov.tasks.Task;
import com.AndreySemeynikov.tasks.TaskStatus;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args){

        Task task = new Task("1st task");
        task.setDescription("something about this task");
        task.setStartDate(LocalDate.of(2023, 12, 5));
        task.setDueDate(LocalDate.of(2023, 12, 10));
        task.setStatus(TaskStatus.ACTIVE);
        System.out.println(task.toString());
    }
}
