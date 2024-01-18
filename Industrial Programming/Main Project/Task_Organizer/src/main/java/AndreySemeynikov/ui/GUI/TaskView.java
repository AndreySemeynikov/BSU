package AndreySemeynikov.ui.GUI;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.Task.TaskStatus;
import AndreySemeynikov.tasks.encryption.DirectoryFileEncryptor;
import javafx.application.Application;
import jakarta.xml.bind.JAXBException;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.geometry.Insets;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static com.sun.javafx.application.ParametersImpl.getParameters;
import static javafx.application.Application.setUserAgentStylesheet;

public class TaskView {
    private TaskModel taskModel;

    private VBox mainLayout;
    private Button addTaskButton;
    private Button ShowAllTasksButton;
    private Button saveTaskButton;
    private Button SaveAllTasksButton;
    private Button LoadTasksFromDirectroyButton;
    private Button EncryptFilesInDirectory;

    public TaskView(Stage primaryStage) {
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        addTaskButton = new Button("Add Task");
        ShowAllTasksButton = new Button("Show all tasks");
        SaveAllTasksButton = new Button("Save all tasks to files");
        LoadTasksFromDirectroyButton = new Button("Load tasks from directory");
        EncryptFilesInDirectory = new Button("Encrypt files in directory");

        mainLayout.getChildren().addAll(addTaskButton, ShowAllTasksButton,
                SaveAllTasksButton, LoadTasksFromDirectroyButton, EncryptFilesInDirectory);
    }

    public void showMainWindow(Stage primaryStage) {
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        Scene mainScene = new Scene(scrollPane, 600, 400);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Task Manager");
        taskModel = new TaskModel(this);
        addTaskButton.setOnAction(event -> taskModel.handleAddTaskButton(this));
        ShowAllTasksButton.setOnAction(event -> taskModel.handleShowAllTasksButton(this));
        SaveAllTasksButton.setOnAction(event -> taskModel.handleSaveAllTasksButton(this));
        LoadTasksFromDirectroyButton.setOnAction(event -> taskModel.handleLoadTasksFromDirectroyButton(this));
        EncryptFilesInDirectory.setOnAction(event -> taskModel.handleEncryptFilesInDirectoryButton(this));
        primaryStage.show();
    }

    // Handling the add task button
    public void showAddTaskWindow(TaskModel taskModel) {
        // Создаем второе окно для ввода данных о задаче

        Stage addTaskStage = new Stage();
        addTaskStage.initModality(Modality.APPLICATION_MODAL);
        addTaskStage.setTitle("Add Task");

        addTaskStage.setMinWidth(400);
        addTaskStage.setMinHeight(300);

        // Создаем элементы для ввода данных
        TextField titleField = new TextField("Untitled");
        TextField descriptionField = new TextField();
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now());
        DatePicker dueDatePicker = new DatePicker();
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("ACTIVE", "COMPLETED", "OVERDUE");
        statusComboBox.setValue("ACTIVE");

        // Создаем кнопку "Save"
        saveTaskButton = new Button("Create task");
        // обработка при нажатии на save

        Label outputLabel = new Label();

        saveTaskButton.setOnAction(event -> {taskModel.handleCreateTaskButton(this, titleField.getText(),
                descriptionField.getText(), startDatePicker.getValue(), dueDatePicker.getValue(),
                TaskStatus.valueOf(statusComboBox.getValue())); });

        VBox addTaskLayout = new VBox(10);
        addTaskLayout.setPadding(new Insets(10));
        addTaskLayout.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Description:"), descriptionField,
                new Label("Start Date:"), startDatePicker,
                new Label("Due Date:"), dueDatePicker,
                new Label("Status:"), statusComboBox,
                saveTaskButton,
                outputLabel
        );
        ScrollPane addTaskScrollPane = new ScrollPane(addTaskLayout);
        addTaskScrollPane.setFitToWidth(true);
        addTaskScrollPane.setFitToHeight(true);

        // Создаем сцену для второго окна
        Scene addTaskScene = new Scene(addTaskScrollPane, 600, 400);
        addTaskStage.setScene(addTaskScene);

        addTaskStage.show();

        taskModel.setOnSuccessListener(event -> {
            outputLabel.setText("Task was created");

            Duration duration = Duration.seconds(3);
            Timeline timeline = new Timeline(new KeyFrame(duration, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Убираем текст после завершения времени
                    outputLabel.setText("");
                }
            }));

            // Запускаем таймлайн
            timeline.play();
        });
    }

    // Handling the show all task button
    public void showAllTasksWindow(TaskModel taskModel){
        Stage allTaskStage = new Stage();
        allTaskStage.initModality(Modality.APPLICATION_MODAL);
        allTaskStage.setTitle("All tasks");

        allTaskStage.setMinWidth(400);
        allTaskStage.setMinHeight(300);

        TextArea textArea = new TextArea();
        List<Task> taskList = taskModel.getTasks();
        if (taskList.isEmpty()) {
            textArea.appendText("No tasks available.");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                textArea.appendText("Task " + (i + 1) + ":");
                textArea.appendText(" " + task.toString() + "\n");
            }
        }
        Button ExitButton = new Button("Exit");
        ExitButton.setOnAction(event -> allTaskStage.close());

        VBox allTaskLayout = new VBox(10);
        allTaskLayout.setPadding(new Insets(10));
        allTaskLayout.getChildren().addAll(
                textArea, ExitButton
        );

        ScrollPane allTaskScrollPane = new ScrollPane(allTaskLayout);
        allTaskScrollPane.setFitToWidth(true);
        allTaskScrollPane.setFitToHeight(true);

        // Создаем сцену для второго окна
        Scene addTaskScene = new Scene(allTaskScrollPane, 600, 400);
        allTaskStage.setScene(addTaskScene);

        allTaskStage.show();
    }

    public void saveAllTasksWindow(TaskModel taskModel){
        Stage saveTasksStage = new Stage();
        saveTasksStage.initModality(Modality.APPLICATION_MODAL);
        saveTasksStage.setTitle("Save all tasks");

        saveTasksStage.setMinWidth(400);
        saveTasksStage.setMinHeight(300);

        Label pathLabel = new Label("Path to directory:");
        TextField pathTextField = new TextField();

        Label StatusLabel = new Label();

        Label formatLabel = new Label("Choose the format:");
        ComboBox<String> formatComboBox = new ComboBox<>();
        formatComboBox.getItems().addAll("xml", "json");
        formatComboBox.setValue("xml"); // Устанавливаем значение по умолчанию

        Button chooseDirectoryButton = new Button("Select Directory");
        chooseDirectoryButton.setOnAction(event -> {
            // Открываем окно выбора директории
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select a directory");

            // Получаем выбранную директорию
            java.io.File selectedDirectory = directoryChooser.showDialog(saveTasksStage);

            if (selectedDirectory != null) {
                // Устанавливаем выбранный путь в текстовое поле
                pathTextField.setText(selectedDirectory.getAbsolutePath());
            }
        });

        Button executeButton = new Button("Execute");

        executeButton.setOnAction(event -> {
            // Получаем значения из текстового поля и ComboBox
            String path = pathTextField.getText();
            String format = formatComboBox.getValue();

            // Ваш код для выполнения действий с выбранными значениями
            try {
                taskModel.handleExecuteButton(this, path, format);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Path: " + path);
            System.out.println("Format: " + format);
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(pathTextField, pathLabel, chooseDirectoryButton,
                formatLabel, formatComboBox, executeButton, StatusLabel);

        Scene scene = new Scene(root, 300, 250);
        saveTasksStage.setScene(scene);
        saveTasksStage.show();

        taskModel.setOnSuccessListener(event -> {
            StatusLabel.setText("Tasks were saved");
        });
        taskModel.setOnFailureListener(event -> {
            StatusLabel.setText("No tasks to save");
        });
    }

    public void LoadTasksFromDirectoryWindow(TaskModel taskModel) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("File Chooser Example");

        Label pathLabel = new Label("Path to directory:");
        TextField pathTextField = new TextField();

        Label StatusLabel = new Label();

        Button chooseDirectoryButton = new Button("Choose Directory");

        chooseDirectoryButton.setOnAction(event -> {
            // Открываем окно выбора директории
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Выберите директорию");

            // Получаем выбранную директорию
            java.io.File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory != null) {
                // Устанавливаем выбранный путь в текстовое поле
                pathTextField.setText(selectedDirectory.getAbsolutePath());
            }
        });

        ToggleGroup formatToggleGroup = new ToggleGroup();

        RadioButton xmlRadioButton = new RadioButton("XML");
        xmlRadioButton.setToggleGroup(formatToggleGroup);
        xmlRadioButton.setSelected(true);

        RadioButton jsonRadioButton = new RadioButton("JSON");
        jsonRadioButton.setToggleGroup(formatToggleGroup);

        CheckBox encryptedCheckBox = new CheckBox("Файлы зашифрованы");

        Label keyLabel = new Label("Encrypted Key:");
        TextField keyTextField = new TextField();

        Button loadFilesButton = new Button("Load Files");

        // обработка
        loadFilesButton.setOnAction(event -> {
            // Получаем значения из текстового поля, RadioButton и CheckBox
            String path = pathTextField.getText();
            Path directory = Paths.get(path);
            String format = xmlRadioButton.isSelected() ? "xml" : "json";
            boolean encrypted = encryptedCheckBox.isSelected();
            String key = keyTextField.getText();

            try {
                taskModel.handleloadFilesButton(this, directory, format, encrypted, key);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(pathLabel, pathTextField, chooseDirectoryButton,xmlRadioButton,
                jsonRadioButton, encryptedCheckBox, keyLabel, keyTextField, loadFilesButton, StatusLabel);

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        taskModel.setOnSuccessListener(event -> {
            StatusLabel.setText("Tasks were uploaded successfully");
        });
        taskModel.setOnFailureListener(event -> {
            StatusLabel.setText("Tasks weren't uploaded successfully");
        });
    }

    public void EncryptFilesInDirectoryWindow(TaskModel taskModel)
    {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Encryption Window");

        Label pathLabel = new Label("Path to directory:");
        TextField pathTextField = new TextField();
        Label StatusLabel = new Label();

        Button chooseDirectoryButton = new Button("Choose Directory");
        chooseDirectoryButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory");

            java.io.File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory != null) {
                pathTextField.setText(selectedDirectory.getAbsolutePath());
            }
        });

        ComboBox<String> formatComboBox = new ComboBox<>();
        formatComboBox.getItems().addAll("xml", "json");
        formatComboBox.setValue("xml");

        Button encryptButton = new Button("Encrypt");
        encryptButton.setOnAction(event -> {
            String path = pathTextField.getText();
            Path directory = Paths.get(path);
            String selectedFormat = formatComboBox.getValue();
            try {
                taskModel.handleEncryptButton(this, directory, selectedFormat);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Encrypting files in directory: " + path + " Format: " + selectedFormat);
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(pathLabel, pathTextField, chooseDirectoryButton,
                new Label("Choose the format:"), formatComboBox, encryptButton,
                StatusLabel);

        Scene scene = new Scene(root, 300, 250);

        // Изменяем цвет фона
        scene.getRoot().setStyle("-fx-background-color: lightblue;");

        pathLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        pathTextField.setStyle("-fx-font-size: 14px;");
        chooseDirectoryButton.setStyle("-fx-font-size: 14px;");
        formatComboBox.setStyle("-fx-font-size: 14px;");
        encryptButton.setStyle("-fx-font-size: 14px;");

        primaryStage.setScene(scene);
        primaryStage.show();

        taskModel.setOnSuccessListener(event -> {
            StatusLabel.setText("Tasks were uploaded successfully");
        });
        taskModel.setOnFailureListener(event -> {
            StatusLabel.setText("Tasks weren't uploaded successfully");
        });
    }
}
