package AndreySemeynikov.ui;

import AndreySemeynikov.tasks.Task;
import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private TaskFileManager taskFileManager;
    private List<Task> taskList = new ArrayList<>();
    public ConsoleUI(TaskFileManager taskFileManager){
        this.taskFileManager = taskFileManager;
    }
    public void start() throws IOException, JAXBException {
        while(true){
            printMenu();
            int choice = readUserChoice();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    displayAllTasks();
                    break;
                case 3:
                    saveTasksToFiles(); // all tasks are saved to different files in folder from user
                    break;
                case 4:
                    loadTasksFromFiles();
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

    private void loadTasksFromFiles() throws IOException, JAXBException {
        Path directoryPath = openDirectory();
        String fileExtension = chooseFormatOfFile();
        Path[] files = Files.list(directoryPath).toArray(Path[]::new);
        if(files.length !=0){
            for (Path file : files) {
                if (file.getFileName().toString().endsWith(fileExtension)) {
                    Path filePath = file.toAbsolutePath();

                    Task task = taskFileManager.loadTaskFromFile(filePath, fileExtension);
                    taskList.add(task);

                    System.out.println(file.getFileName().toString());
                }
            }
        }
        else {
            System.out.println("No such files in this directory");
        }
    }

    private Path openDirectory() throws IOException {
        while (true) {
            System.out.print("Enter the filepath to the directory: ");
            String directoryPath = reader.readLine();
            Path directory = Paths.get(directoryPath);

            if (Files.isDirectory(directory)) {
                return directory;
            } else {
                System.out.println("The specified directory does not exist or" +
                        "is not a valid directory. Please enter a valid directory path.");
            }
        }
    }
    private String chooseFormatOfFile() throws IOException {
        while (true) {
            printFormatOfFile();
            int choice = readUserChoice();

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
    private void saveTasksToFiles() throws IOException, JAXBException {
        if(taskList.isEmpty()){
            System.out.println("No tasks to save");
        } else {
            Path directoryPath = openDirectory(); // открывем используя функцию
            String fileExtension = chooseFormatOfFile();
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                taskFileManager.saveTaskToFile(task,
                        Path.of(directoryPath + "/task" + String.valueOf(i + 1) + "." + fileExtension),
                        fileExtension);
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
                /*System.out.println("Task " + (i + 1) + ":");
                System.out.println("Title: " + task.getTitle());
                System.out.println("Description: " + task.getDescription());
                System.out.println("StartDate: " + task.getStartDate());
                System.out.println("DueDate: " + task.getDueDate());
                System.out.println("Status: " + task.getStatus());
                System.out.println("Attached files: " + task.getAttachedFiles());*/
                System.out.println("Task " + (i + 1) + ":");
                System.out.println(task.toString());

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
    private int readUserChoice() {
        try {
            System.out.print("Enter your choice: ");
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid choice.");
            return -1;
        }
    }
    private void addTask() throws IOException {
        System.out.println("Enter task details:");
        System.out.print("Title: ");

        String title = reader.readLine();
        System.out.print("Description: ");
        String description = reader.readLine();
        Task task = new Task(title, description);
        taskList.add(task);
    }
    private void addTaskFromFile() throws IOException {
        Task task = new Task();
        taskList.add(task);
    }

}
