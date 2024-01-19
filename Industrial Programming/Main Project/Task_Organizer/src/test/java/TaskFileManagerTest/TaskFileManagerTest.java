package TaskFileManagerTest;

import AndreySemeynikov.tasks.Task.Task;

import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static AndreySemeynikov.tasks.Task.TaskStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;

class TaskFileManagerTest {
    private TaskFileManager taskFileManager;
    @TempDir
    static Path testFilePath;
    private Task testTask;


    @BeforeEach
    void setUp() {
        taskFileManager = new TaskFileManager();
        testTask = new Task("testTitle", "testDescription",
                LocalDate.of(2023, 12, 20),
                LocalDate.of(2023, 12, 25),
                ACTIVE);
    }
    @Test
    void saveTaskInJsonFormat() throws IOException, JAXBException {
        Path path = testFilePath.resolve("TestTask.json");
        taskFileManager.saveTaskToFile(testTask, path, "json");
        assertAll(
                () -> assertTrue(Files.exists(path), "File should exist"));
                //() -> assertLinesMatch(lines, Files.readAllLines(numbers)));

    }
    @Test
    void saveTaskInXMLFormat() throws IOException, JAXBException {
        Path path = testFilePath.resolve("TestTask.xml");
        taskFileManager.saveTaskToFile(testTask, path, "xml");
        assertAll(
                () -> assertTrue(Files.exists(path), "File should exist"));
        //() -> assertLinesMatch(lines, Files.readAllLines(numbers)));

    }

}