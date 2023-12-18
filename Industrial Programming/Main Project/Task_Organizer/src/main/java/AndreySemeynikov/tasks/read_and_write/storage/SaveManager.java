package AndreySemeynikov.tasks.read_and_write.storage;

import AndreySemeynikov.tasks.Task;

public class SaveManager {
    private SaveStrategy saveStrategy;

    public SaveManager(SaveStrategy saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public void setSaveStrategy(SaveStrategy saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public void save(Task task) {
        saveStrategy.save(task);
    }
}
