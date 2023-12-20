package AndreySemeynikov.tasks.read_and_write.strategies;

import AndreySemeynikov.tasks.Task.Task;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

public class XMLTaskSerializer implements TaskSerializerStrategy {
    @Override
    public String serializeTask(Task task) throws JAXBException {
            JAXBContext context = JAXBContext.newInstance(Task.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(task, writer);
            return writer.toString();
    }

    @Override
    public Task deserializeTask(String data) throws JAXBException {
            JAXBContext context = JAXBContext.newInstance(Task.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(data);
            return (Task) unmarshaller.unmarshal(reader);
    }
}
