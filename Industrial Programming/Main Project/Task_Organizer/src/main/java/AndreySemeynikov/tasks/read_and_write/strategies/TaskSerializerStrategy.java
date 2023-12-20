package AndreySemeynikov.tasks.read_and_write.strategies;

import AndreySemeynikov.tasks.Task.Task;
import jakarta.xml.bind.JAXBException;

public interface TaskSerializerStrategy {
    String serializeTask(Task task) throws JAXBException;
    Task deserializeTask(String data) throws JAXBException;
}
