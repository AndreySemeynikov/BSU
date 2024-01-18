package AndreySemeynikov.ui;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.Task.TaskStatus;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public interface UIInteface {
    void addTask() throws IOException;
    void displayAllTasks();
    void loadTasksFromDirectory() throws IOException, JAXBException;
    void saveTasksToFiles() throws IOException, JAXBException;
    void EncryptFilesInDirectory() throws IOException;
    Path openDirectory() throws IOException;
    String chooseFormatOfFile() throws IOException;
    TaskStatus chooseStatusOfTask() throws IOException;
    LocalDate inputLocalDate();
    void deleteDuplicate(Task task);

}
