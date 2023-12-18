package AndreySemeynikov.ui;

import AndreySemeynikov.tasks.Task;
import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import jakarta.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {
    private TaskFileManager taskFileManager;
    private List<Task> taskList = new ArrayList<>();
    public ConsoleUI(TaskFileManager taskFileManager){
        this.taskFileManager = taskFileManager;
    }
    public void start() throws IOException, JAXBException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            printMenu();
            int choice = readUserChoice(reader);

            switch (choice) {
                case 1:
                    addTask(reader);
                    break;
                case 2:
                    displayAllTasks();
                    break;
                case 3:
                    saveTasksToFiles(reader); // all tasks are saved to different files in folder from user
                    break;
                case 4:
                    loadTasksFromFiles(reader);
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void loadTasksFromFiles(BufferedReader reader) throws IOException {
        System.out.print("Enter the filepath to the folder: ");
        String filepath = reader.readLine();
        System.out.print("Enter the format of files: ");
    }

    private String openDirectory(BufferedReader reader) throws IOException {
        while (true) {
            System.out.print("Enter the filepath to the directory: ");
            String directoryPath = reader.readLine();
            File directory = new File(directoryPath);

            if (directory.exists() && directory.isDirectory()) {
                return directoryPath;
            } else {
                System.out.println("The specified directory does not exist or is not a valid directory. Please enter a valid directory path.");
            }
        }
    }
    private String chooseFormatOfFile(BufferedReader reader) throws IOException {
        while (true) {
            printFormatOfFile();
            int choice = readUserChoice(reader);

            switch (choice) {
                case 1:
                    return "xml";
                case 2:
                    return "json";
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    private void saveTasksToFiles(BufferedReader reader) throws IOException, JAXBException {
        if(taskList.isEmpty()){
            System.out.println("No tasks to save");
        } else {
            String directoryPath = openDirectory(reader); // открывем используя функцию
            String format = chooseFormatOfFile(reader);
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                taskFileManager.saveTaskToFile(task,
                        directoryPath + "/task" + String.valueOf(i + 1) + "." + format,
                        format);
                }
            }
        }

    private void displayAllTasks() {
        System.out.println("All tasks: ");
        if (taskList.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                System.out.println("Task " + (i + 1) + ":");
                System.out.println("Title: " + task.getTitle());
                System.out.println("Description: " + task.getDescription());
                System.out.println("StartDate: " + task.getStartDate());
                System.out.println("DueDate: " + task.getDueDate());
                System.out.println("Status: " + task.getStatus());
                System.out.println("Attached files: " + task.getAttachedFiles());

                // Выводите остальные свойства задачи, если они есть
                System.out.println();
            }
        }
    }
    private void printMenu() {
        System.out.println("1. Add Task");
        System.out.println("2. Display All Tasks");
        System.out.println("3. Save Tasks to File");
        System.out.println("4. Load Tasks from File");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
    private void printFormatOfFile(){
        System.out.println("Choose format of file");
        System.out.println("1. XML");
        System.out.println("2. JSON");
    }
    private int readUserChoice(BufferedReader reader) {
        try {
            System.out.print("Enter your choice: ");
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid choice.");
            return -1;
        }
    }
    private void addTask(BufferedReader reader) throws IOException {
        System.out.println("Enter task details:");

        System.out.print("Title: ");
        String title = reader.readLine();

        System.out.print("Description: ");
        String description = reader.readLine();

        // You may include additional logic for reading other task details

        Task task = new Task(title, description);
        taskList.add(task);
    }

}
