package AndreySemeynikov.ui.GUI;

import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        TaskFileManager taskFileManager = new TaskFileManager();
        TaskModel taskModel = new TaskModel(taskFileManager);
        TaskView taskView = new TaskView(primaryStage);
        taskView.showMainWindow(primaryStage);
    }
}
