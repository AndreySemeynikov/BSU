package AndreySemeynikov.ui;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.Task.TaskStatus;
import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private TaskFileManager taskFileManager;
    private List<Task> taskList = new ArrayList<>();
    public ConsoleUI(TaskFileManager taskFileManager){
        this.taskFileManager = taskFileManager;
    }
    private void printMenu() {
        System.out.println("1. Add Task");
        System.out.println("2. Display All Tasks");
        System.out.println("3. Save Tasks to File");
        System.out.println("4. Load Tasks from File");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
    public void startConsole() throws IOException, JAXBException {
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
                    loadTasksFromDirectory();
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
    private void displayAllTasks() {
        System.out.println("All tasks: ");
        if (taskList.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                System.out.println("Task " + (i + 1) + ":");
                System.out.println(task.toString());
                System.out.println();
            }
        }
    }
    private void loadTasksFromDirectory() throws IOException, JAXBException {
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

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the task fields (if you do not want to enter, leave blank): ");

        System.out.print("Title: ");
        String titleInput = scanner.nextLine();
        String title = titleInput.isEmpty() ? "Untitled" : titleInput;

        System.out.print("Description: ");
        String descriptionInput = scanner.nextLine();
        String description  = descriptionInput.isEmpty() ? null : descriptionInput;

        System.out.print("Start date: ");
        String startDateInput = scanner.nextLine();
        String startDate = startDateInput.isEmpty() ? null : startDateInput;
        LocalDate startdate = LocalDate.parse(startDate, DateTimeFormatter.BASIC_ISO_DATE); // потом добавить обработку ошибок

        System.out.print("Start date: ");
        String dueDateInput = scanner.nextLine();
        String dueDate = startDateInput.isEmpty() ? null : dueDateInput;
        LocalDate duedate = LocalDate.parse(dueDate, DateTimeFormatter.BASIC_ISO_DATE); // потом добавить обработку ошибок

        System.out.print("Status: ");
        String statusInput = scanner.nextLine();
        String status = statusInput.isEmpty() ? null : statusInput;
        TaskStatus taskStatus = TaskStatus.valueOf(status);

        // Создаем объект задачи, используя введенные значения
        Task task = new Task(title, description, startdate, duedate, taskStatus);
        scanner.close();
        taskList.add(task);
    }
}