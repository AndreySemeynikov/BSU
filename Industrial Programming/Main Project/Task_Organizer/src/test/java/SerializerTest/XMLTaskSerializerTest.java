package SerializerTest;

import AndreySemeynikov.tasks.Task.Task;
import AndreySemeynikov.tasks.read_and_write.strategies.XMLTaskSerializer;
import jakarta.xml.bind.JAXBException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class XMLTaskSerializerTest {
    private final XMLTaskSerializer serializer = new XMLTaskSerializer();

    @Test
    public void serializeTask_shouldProduceValidXml() throws JAXBException {
        Task task = new Task("ExampleTask", "ExampleDescription");

        String xml = serializer.serializeTask(task);

        // Проверяем, что xml не пустой и содержит ожидаемые элементы
        Assertions.assertThat(xml).isNotEmpty();
        Assertions.assertThat(xml).contains("<title>ExampleTask</title>");
        Assertions.assertThat(xml).contains("<description>ExampleDescription</description>");
    }

    @Test
    public void deserializeTask_shouldProduceValidTaskObject() throws JAXBException {
        String xml = "<task><title>ExampleTask</title><dueDate>2023-01-01</dueDate></task>";

        Task task = serializer.deserializeTask(xml);

        // Проверяем, что объект Task был корректно восстановлен из XML
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getTitle()).isEqualTo("ExampleTask");
        Assertions.assertThat(task.getDueDate()).isEqualTo(LocalDate.of(2023, 1, 1));
    }

}