package AndreySemeynikov.ui.GUI;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.Task.TaskStatus;
import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        TaskFileManager taskFileManager = new TaskFileManager();
        TaskModel taskModel = new TaskModel(taskFileManager);
        TaskView taskView = new TaskView(primaryStage);
        taskView.showMainWindow(primaryStage);
    }



    private void tart(Stage primaryStage) {
        primaryStage.setTitle("Task Manager");

        // Создаем кнопку "Add Task"
        Button addTaskButton = new Button("Add Task");
        Button displayAllTasks = new Button("Display all tasks");

        // Создаем основное окно
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(addTaskButton, displayAllTasks);

        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);

        // Создаем контейнер ScrollPane и добавляем в него основной контейнер
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Создаем сцену для основного окна
        Scene mainScene = new Scene(scrollPane, 600, 400);
        primaryStage.setScene(mainScene);

        // Создаем второе окно для ввода данных о задаче
        Stage addTaskStage = new Stage();
        addTaskStage.initModality(Modality.APPLICATION_MODAL);
        addTaskStage.setTitle("Add Task");

        addTaskStage.setMinWidth(400);
        addTaskStage.setMinHeight(300);


        // Создаем элементы для ввода данных
        TextField titleField = new TextField();
        TextField descriptionField = new TextField();
        DatePicker startDatePicker = new DatePicker();
        DatePicker dueDatePicker = new DatePicker();
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("ACTIVE", "COMPLETED", "OVERDUE");

        // Создаем кнопку "Save"
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {

            String title = titleField.getText();
            String description = descriptionField.getText();
            LocalDate startdate = startDatePicker.getValue();
            LocalDate duedate = dueDatePicker.getValue();
            String taskstatus = statusComboBox.getValue();
            TaskStatus taskStatus = TaskStatus.valueOf(taskstatus);
            Task task = new Task(title, description, startdate, duedate, taskStatus);
            //taskList.add(task);

            System.out.println("Task was created successfully");
            // Закрываем второе окно после сохранения
            addTaskStage.close();
        });


        // Создаем вторичное окно
        VBox addTaskLayout = new VBox(10);
        addTaskLayout.setPadding(new Insets(10));
        addTaskLayout.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Description:"), descriptionField,
                new Label("Start Date:"), startDatePicker,
                new Label("Due Date:"), dueDatePicker,
                new Label("Status:"), statusComboBox,
                saveButton
        );

        ScrollPane addTaskScrollPane = new ScrollPane(addTaskLayout);
        addTaskScrollPane.setFitToWidth(true);
        addTaskScrollPane.setFitToHeight(true);

        // Создаем сцену для второго окна
        Scene addTaskScene = new Scene(addTaskScrollPane, 600, 400);
        addTaskStage.setScene(addTaskScene);

        // Добавляем обработчик событий для кнопки "Add Task"
        addTaskButton.setOnAction(e -> addTaskStage.showAndWait());

        // Отображаем основное окно
        primaryStage.show();
    }
}
