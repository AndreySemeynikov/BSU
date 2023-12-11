package AndreySemeynikov;

import AndreySemeynikov.tasks.Task;
import AndreySemeynikov.tasks.TaskStatus;
import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException, JAXBException {

        Task task2 = new Task(
                "2nd task",
                LocalDate.of(2023, 12, 5),
                LocalDate.of(2023,12, 8),
                TaskStatus.ACTIVE);
        task2.setDescription("some information about task");

        System.out.println(task2.toString());
        TaskFileManager taskFileManager = new TaskFileManager();
        taskFileManager.saveTaskToFile(task2, "src/main/resources/files/test1.xml", "xml");
        taskFileManager.saveTaskToFile(task2, "src/main/resources/files/test2.json", "json");
    }

}
