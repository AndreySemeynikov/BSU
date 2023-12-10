package AndreySemeynikov.tasks.read_and_write;
/*
package AndreySemeynikov.tasks.read_and_write;

import AndreySemeynikov.tasks.Task;
import AndreySemeynikov.tasks.read_and_write.strategies.*;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public class TaskFileManager {
    private FileFormatStrategy fileFormatStrategy;
    private TaskSerializerStrategy taskSerializerStrategy;

    public TaskFileManager(FileFormatStrategy fileFormatStrategy)
    {
        this.fileFormatStrategy = fileFormatStrategy;
        this.taskSerializerStrategy =
    }

    public void setFileFormatStrategy(FileFormatStrategy fileFormatStrategy)
    {
        this.fileFormatStrategy = fileFormatStrategy;
    }

    public void saveTaskToFile(Task task, String filePath) throws IOException, JAXBException {
        String data = taskSerializerStrategy.serializeTask(task);
        fileFormatStrategy.saveToFile(filePath, data);
    }

    public Task loadTaskFromFile(String filePath) throws IOException, JAXBException {
        String data = fileFormatStrategy.loadFromFile(filePath);
        return TaskSerializerStrategy.deserializeTask(data);
    }
}
*/


import AndreySemeynikov.tasks.Task;
import AndreySemeynikov.tasks.read_and_write.strategies.JsonTaskSerializer;
import AndreySemeynikov.tasks.read_and_write.strategies.TaskSerializerStrategy;
import AndreySemeynikov.tasks.read_and_write.strategies.XMLTaskSerializer;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TaskFileManager {
    private TaskSerializerStrategy taskSerializerStrategy;

    public void saveTaskToFile(Task task, String filePath, String format) throws IOException, JAXBException {
        switch(format){
            case "json":
                this.taskSerializerStrategy = new JsonTaskSerializer();
                break;
            case "xml":
                this.taskSerializerStrategy = new XMLTaskSerializer();
                break;
            default:
                System.out.println("Incorrect format");
        }

        String data = taskSerializerStrategy.serializeTask(task);
            if (data != null) {
                Files.write(Paths.get(filePath), data.getBytes());
            }

    }

    public Task loadTaskFromFile(String filePath, String format) throws IOException, JAXBException {
        switch(format){
            case "json":
                this.taskSerializerStrategy = new JsonTaskSerializer();
                break;
            case "xml":
                this.taskSerializerStrategy = new XMLTaskSerializer();
                break;
            default:
                System.out.println("Incorrect format");
        }
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            return taskSerializerStrategy.deserializeTask(json);
    }
}
