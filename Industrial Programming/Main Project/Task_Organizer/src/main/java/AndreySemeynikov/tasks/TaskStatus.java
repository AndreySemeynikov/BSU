package AndreySemeynikov.tasks;

public enum TaskStatus {
    ACTIVE("Active"),
    COMPLETED("Completed"),
    OVERDUE("Overdue");

    private final String status;
    TaskStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return status;
    }
}
