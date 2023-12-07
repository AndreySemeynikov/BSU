package com.AndreySemeynikov.tasks.read_and_write;

import com.AndreySemeynikov.tasks.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TaskFileManager {
    public static void saveTaskToFile(Task task, String filePath) throws IOException {
        String json = TaskSerializer_JSON.serializeTask(task);
        if(json!=null)
        {
            Files.write(Paths.get(filePath), json.getBytes());
        }
    }
    public static Task loadTaskFromFile(String filePath) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(filePath)));
        return TaskSerializer_JSON.deserializeTask(json);
    }
}
