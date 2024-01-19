package AndreySemeynikov.ui;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.Task.TaskStatus;
import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleUITest {
    private ConsoleUI consoleUI;

    @BeforeEach
    public void setUp() {
        TaskFileManager taskFileManager = new TaskFileManager();
        consoleUI = new ConsoleUI(taskFileManager);
    }

    @Test
    public void testAddTaskWithTitleAndDescription() throws IOException {
        String title = "TaskTitle";
        String description = "TaskDescription";
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate dueDate = LocalDate.parse("2024-01-10");
        TaskStatus status = TaskStatus.ACTIVE;

        Task task = new Task(title, description, startDate, dueDate, status);
        consoleUI.addToTaskList(task);

        List<Task> taskList = consoleUI.getTaskList();
        assertEquals(1, taskList.size());
        assertEquals(title, taskList.get(0).getTitle());
        assertEquals(description, taskList.get(0).getDescription());
        assertEquals(startDate, taskList.get(0).getStartDate());
        assertEquals(dueDate, taskList.get(0).getDueDate());
        assertEquals(status, taskList.get(0).getStatus());
    }

    @Test
    public void testAddTaskWithoutTitleAndDescription() throws IOException {
        String title = "Untitled";
        String description = null;
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate dueDate = LocalDate.parse("2024-01-10");
        TaskStatus status = TaskStatus.ACTIVE;

        Task task = new Task(title, description, startDate, dueDate, status);
        consoleUI.addToTaskList(task);

        List<Task> taskList = consoleUI.getTaskList();
        assertEquals(1, taskList.size());
        assertEquals(title, taskList.get(0).getTitle());
        assertEquals(description, taskList.get(0).getDescription());
        assertEquals(startDate, taskList.get(0).getStartDate());
        assertEquals(dueDate, taskList.get(0).getDueDate());
        assertEquals(status, taskList.get(0).getStatus());
    }

}