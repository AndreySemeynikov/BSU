package SerializerTest;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.read_and_write.strategies.JsonTaskSerializer;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import java.time.LocalDate;

public class JsonTaskSerializerTest {

    private final JsonTaskSerializer serializer = new JsonTaskSerializer();

    @Test
    public void serializeTask_ExampleTask_ExampleDescription() {
        Task task = new Task("ExampleTask", "ExampleDescription");
        String json = serializer.serializeTask(task);
        Assertions.assertThat(json).isNotEmpty();
        Assertions.assertThat(json).contains("ExampleTask");
        Assertions.assertThat(json).contains("ExampleDescription");
    }

    @Test
    public void deserializeTask_ExampleTask_2023_01_01() {
        String json = "{\"title\":\"ExampleTask\",\"dueDate\":\"2023-01-01\"}";
        Task task = serializer.deserializeTask(json);
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getTitle()).isEqualTo("ExampleTask");
        Assertions.assertThat(task.getDueDate()).isEqualTo(LocalDate.of(2023, 1, 1));
    }
    @Test
    public void deserializeTask_withInvalidJson_shouldThrowJsonSyntaxException() {
        String invalidJson = "{invalid}";
        try {
            serializer.deserializeTask(invalidJson);
            Assertions.fail("Expected JsonSyntaxException, but no exception was thrown");
        } catch (JsonSyntaxException e) {
            Assertions.assertThat(e.getMessage()).contains("Expected ':' at line 1 column 10 path $.invalid");
        }
    }

    @Test
    public void deserializeTask_withEmptyJson_shouldReturnNull() {
        String emptyJson = "";
        Task task = serializer.deserializeTask(emptyJson);
        Assertions.assertThat(task).isNull();
    }
}
