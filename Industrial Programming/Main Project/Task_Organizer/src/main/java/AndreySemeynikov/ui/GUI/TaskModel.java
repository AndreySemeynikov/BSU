package AndreySemeynikov.ui.GUI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.Task.TaskStatus;
import AndreySemeynikov.tasks.encryption.DirectoryFileEncryptor;
import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import jakarta.xml.bind.JAXBException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TaskModel {
    private TaskView taskView;
    private TaskFileManager taskFileManager;
    private List<Task> taskList = new ArrayList<>();
    private EventHandler<ActionEvent> onSuccessListener;
    private EventHandler<ActionEvent> onFailureListener;

    public TaskModel(TaskFileManager taskFileManager) {
        this.taskFileManager = taskFileManager;
        this.taskList = new ArrayList<>();
    }

    public TaskModel(TaskView taskView) {
        this.taskView = taskView;
        this.taskList = new ArrayList<>();
        this.taskFileManager = new TaskFileManager();
    }

    public TaskModel() {
        this.taskFileManager = new TaskFileManager();
        this.taskList = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public void handleAddTaskButton(TaskView taskView)
    {
        taskView.showAddTaskWindow(this);
    }
    public void handleShowAllTasksButton(TaskView taskView)
    {
        taskView.showAllTasksWindow(this);
    }
    public void handleCreateTaskButton(TaskView taskView, String title, String description,
                                       LocalDate startDate, LocalDate dueDate, TaskStatus status) {

        Task task = new Task(title, description, startDate, dueDate, status);
        taskList.add(task);
        System.out.println("Task was created");
//        for(int i=0; i<taskList.size(); i++)
//        {
//            System.out.println(taskList.get(i).toString());
//        }
        onSuccessListener.handle(new ActionEvent());
    }

    public void removeTask(Task task) {
        taskList.remove(task);
    }

    public void handleSaveAllTasksButton(TaskView taskView) {
        taskView.saveAllTasksWindow(this);
    }
    public void handleExecuteButton(TaskView taskView, String path, String format) throws JAXBException, IOException {

        if(taskList.isEmpty()){
            System.out.println("No tasks to save");
            onFailureListener.handle(new ActionEvent());
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                taskFileManager.saveTaskToFile(task,
                        Path.of(path + "/task" + String.valueOf(i + 1) + "." + format),
                        format);
            }
            onSuccessListener.handle(new ActionEvent());
        }
    }
    public void handleLoadTasksFromDirectroyButton(TaskView taskView){
        taskView.LoadTasksFromDirectoryWindow(this);
    }
    public void handleEncryptFilesInDirectoryButton(TaskView taskView)
    {
        taskView.EncryptFilesInDirectoryWindow(this);
    }
    public void handleloadFilesButton(TaskView taskViewview, Path directoryPath, String fileExtension,
                                boolean encrypted, String decryptionKey) throws IOException, JAXBException {
        DirectoryFileEncryptor fileEncryptor = new DirectoryFileEncryptor();
        Path[] files = Files.list(directoryPath).toArray(Path[]::new);

        if(encrypted){
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
                    onSuccessListener.handle(new ActionEvent());
                }
            }
        }
        else {
            System.out.println("No such files in this directory");
            onFailureListener.handle(new ActionEvent());
        }
    }
    public void handleEncryptButton(TaskView taskView, Path directoryPath, String fileExtension) throws IOException {
        DirectoryFileEncryptor fileEncryptor = new DirectoryFileEncryptor();

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
        //fileEncryptor.saveKeyToFile(directoryPath.resolve("encryptionKey.key"));
        System.out.println("Encryption key: " + fileEncryptor.getBase64EncodedKey());
        System.out.println("Encryption key saved successfully.");

    }


    public void deleteDuplicate(Task task)
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

    public void setOnSuccessListener(EventHandler<ActionEvent> onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }

    public void setOnFailureListener(EventHandler<ActionEvent> onFailureListener) {
        this.onFailureListener = onFailureListener;
    }

    // Additional methods for managing tasks and their data
}

