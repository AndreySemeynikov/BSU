package AndreySemeynikov.tasks.read_and_write.strategies;

import AndreySemeynikov.tasks.Task;
import AndreySemeynikov.util.LocalDateAdapterJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class JsonTaskSerializer implements TaskSerializerStrategy {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapterJson())
            .create();

    @Override
    public String serializeTask(Task task) {
        return gson.toJson(task);
    }

    @Override
    public Task deserializeTask(String data) {
        return gson.fromJson(data, Task.class);
    }
}

