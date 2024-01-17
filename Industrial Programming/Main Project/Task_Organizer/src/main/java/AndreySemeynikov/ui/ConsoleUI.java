package AndreySemeynikov.ui;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.Task.TaskStatus;
import AndreySemeynikov.tasks.encryption.DirectoryFileEncryptor;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println("5. Encrypt files in directory");
        System.out.println("6. Exit");
    }
    private void printListOfStatus() {
        System.out.println("1. Active");
        System.out.println("2. Completed");
        System.out.println("3. Overdue");
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
                    EncryptFilesInDirectory();
                    break;
                case 6:
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
        DirectoryFileEncryptor fileEncryptor = new DirectoryFileEncryptor();
        Path directoryPath = openDirectory();
        String fileExtension = chooseFormatOfFile();
        Path[] files = Files.list(directoryPath).toArray(Path[]::new);

        boolean encrypted = fileEncryptor.askUserForEncryption();
        if(encrypted){
            String decryptionKey = fileEncryptor.askUserForDecryptionKey();
            // Загружаем ключ для дешифрации
            fileEncryptor.loadDecryptionKey(decryptionKey);
        }
        if(files.length !=0){
            for (Path file : files) {
                if (file.getFileName().toString().endsWith(fileExtension)) {
                    Path filePath = file.toAbsolutePath();
                    Task task;
                    if (encrypted) {
                        task = taskFileManager.loadTaskFromFile(filePath, fileExtension, fileEncryptor);
                    }
                    else {
                        task = taskFileManager.loadTaskFromFile(filePath, fileExtension);
                    }
                    taskList.add(task);
                    deleteDuplicate(task);
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
    private void EncryptFilesInDirectory() throws IOException {
        DirectoryFileEncryptor fileEncryptor = new DirectoryFileEncryptor();
        Path directoryPath = openDirectory();
        String fileExtension = chooseFormatOfFile();

        Files.list(directoryPath)
                .filter(file -> file.toString().endsWith("." + fileExtension))
                .forEach(file -> {
                    try {
                        fileEncryptor.encryptFile(file);
                        System.out.println("File encrypted successfully: " + file.getFileName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        // Сохраняем ключ в файл
        fileEncryptor.saveKeyToFile(directoryPath.resolve("encryptionKey.key"));
        System.out.println("Encryption key: " + fileEncryptor.getBase64EncodedKey());
        System.out.println("Encryption key saved successfully.");

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

    TaskStatus chooseStatusOfTask() throws IOException {
        while (true) {
            printListOfStatus();
            int choice = readUserChoice();
            TaskStatus taskStatus;

            switch (choice) {
                case 1:
                    return taskStatus = TaskStatus.ACTIVE;
                case 2:
                    return taskStatus = TaskStatus.COMPLETED;
                case 3:
                    return taskStatus = TaskStatus.OVERDUE;
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
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter your choice: ");
            return scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid choice.");
            return -1;
        }
    }
    private LocalDate inputLocalDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Format: day month year; ");
        System.out.print("input your data: ");
        String regex = "^(0[1-9]|[12][0-9]|3[01]) (0[1-9]|1[0-2]) \\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        LocalDate startdate = null;
        boolean k = false;
        while(!k)
        {
            String startDateInput = scanner.nextLine();
            String startDate = startDateInput.isEmpty() ? null : startDateInput;
            Matcher matcher = pattern.matcher(startDate);
            if(startDate == null){
                return null;
            }
            if(startDate != null && matcher.matches())
            {
                startdate = LocalDate.parse(startDate, formatter);
                //System.out.println("Parse was successful");
                return startdate;
            }
            else {
                System.out.println("Input error, try another input. Follow example: 15 01 2022");
            }
        }
        return null;
    }
    private void addTask() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the task fields (if you do not want to enter, leave blank): ");

        System.out.print("Title: ");
        String titleInput = scanner.nextLine();
        String title = titleInput.isEmpty() ? "Untitled" : titleInput;

        System.out.print("Description: ");
        String descriptionInput = scanner.nextLine();
        String description  = descriptionInput.isEmpty() ? null : descriptionInput;

        System.out.println("Start date: ");
        LocalDate startdate = inputLocalDate();

        System.out.println("Due date: ");
        LocalDate duedate = inputLocalDate();

        System.out.println("Status: ");
        TaskStatus taskStatus = chooseStatusOfTask();
        Task task = new Task(title, description, startdate, duedate, taskStatus);
        taskList.add(task);
        System.out.println("Task was created successfully");
    }

    private void deleteDuplicate(Task task)
    {
        long id = task.getId();

        for(int i = 0; i < taskList.size()-1; i++)
        {
            if(id == taskList.get(i).getId()){
                taskList.remove(taskList.size()-1);
                System.out.println("Task with id = " + id + " was duplicate");
            }
        }
    }

}