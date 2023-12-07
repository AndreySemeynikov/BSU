package com.AndreySemeynikov.tasks.read_and_write;


import com.AndreySemeynikov.tasks.Task;
import com.google.gson.Gson;

public class TaskSerializer_JSON {
    private static final Gson gson = new Gson();
    public static String serializeTask (Task task)
    {
        return gson.toJson(task);
    }
    public static Task deserializeTask(String json)
    {
        return gson.fromJson(json, Task.class);
    }
}
