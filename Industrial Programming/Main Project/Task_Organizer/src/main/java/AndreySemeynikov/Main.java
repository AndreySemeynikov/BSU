package AndreySemeynikov;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import AndreySemeynikov.ui.ConsoleUI;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, JAXBException {
        TaskFileManager taskFileManager = new TaskFileManager();
        ConsoleUI consoleUI = new ConsoleUI(taskFileManager);
        consoleUI.startConsole();

       /*Task task2 = new Task(
                "2nd task",
                LocalDate.of(2023, 12, 5),
                LocalDate.of(2023,12, 8),
                TaskStatus.ACTIVE);
        task2.setDescription("some information about task");
        System.out.println(task2.toString());

        Task task1 = taskFileManager.loadTaskFromFile("src/main/resources/files/test1.xml", "xml");
        List<Task> list_of_tasks = new ArrayList<>();

        src/main/resources/files/User1
        */


    }

}
