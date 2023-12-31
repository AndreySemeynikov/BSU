package AndreySemeynikov.tasks.read_and_write;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.read_and_write.strategies.JsonTaskSerializer;
import AndreySemeynikov.tasks.read_and_write.strategies.TaskSerializerStrategy;
import AndreySemeynikov.tasks.read_and_write.strategies.XMLTaskSerializer;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskFileManager {
    private TaskSerializerStrategy taskSerializerStrategy;
    public void saveTaskToFile(Task task, Path filePath, String format) throws IOException, JAXBException {
        switch(format){
            case "json":
                this.taskSerializerStrategy = new JsonTaskSerializer();
                break;
            case "xml":
                this.taskSerializerStrategy = new XMLTaskSerializer();
                break;
            default:
                System.out.println("Incorrect format of file save to");
        }

        String data = taskSerializerStrategy.serializeTask(task);
            if (data != null) {
                Files.write(filePath, data.getBytes());
                System.out.println("Your task " + task.getTitle() + " with id " + task.getId() + " was saved successfully");
            }

    }
    public Task loadTaskFromFile(Path filePath, String format) throws IOException, JAXBException {
        switch(format){
            case "json":
                this.taskSerializerStrategy = new JsonTaskSerializer();
                break;
            case "xml":
                this.taskSerializerStrategy = new XMLTaskSerializer();
                break;
            default:
                System.out.println("Incorrect format of file load from");
        }
            String json = new String(Files.readAllBytes(filePath));
            return taskSerializerStrategy.deserializeTask(json);
    }

}
