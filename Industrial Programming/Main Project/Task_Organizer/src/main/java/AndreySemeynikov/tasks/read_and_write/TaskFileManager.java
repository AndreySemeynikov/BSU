package AndreySemeynikov.tasks.read_and_write;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.encryption.DirectoryFileEncryptor;
import AndreySemeynikov.tasks.read_and_write.strategies.JsonTaskSerializer;
import AndreySemeynikov.tasks.read_and_write.strategies.TaskSerializerStrategy;
import AndreySemeynikov.tasks.read_and_write.strategies.XMLTaskSerializer;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

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
    public Task loadTaskFromFile(Path filePath, String format, DirectoryFileEncryptor fileEncryptor) throws IOException, JAXBException {

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
        byte[] encryptedData = Files.readAllBytes(filePath);
        // String data = new String(Files.readAllBytes(filePath));

            // Дешифруем данные перед десериализацией
            byte[] decryptedData = fileEncryptor.decryptData(encryptedData);
            String data = new String(decryptedData, StandardCharsets.UTF_8);

            return taskSerializerStrategy.deserializeTask(data);
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
        String data = new String(Files.readAllBytes(filePath));
        return taskSerializerStrategy.deserializeTask(data);
    }

}
