package AndreySemeynikov.tasks.read_and_write.storage;

import AndreySemeynikov.tasks.Task.Task;

public interface SaveStrategy {
    void save(Task task);
}
