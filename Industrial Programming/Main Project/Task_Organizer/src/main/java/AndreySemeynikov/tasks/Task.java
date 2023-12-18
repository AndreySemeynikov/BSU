package AndreySemeynikov.tasks;

import AndreySemeynikov.util.LocalDateAdapterXML;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {
        private static final AtomicLong idGenerator = new AtomicLong(0);
        @XmlElement
        private long id;

        @XmlElement
        private String title;
        @XmlElement
        private String description;
        @XmlElement
        @XmlJavaTypeAdapter(LocalDateAdapterXML.class)
        private LocalDate startDate;
        @XmlElement
        @XmlJavaTypeAdapter(LocalDateAdapterXML.class)
        private LocalDate dueDate;
        @XmlElement
        private TaskStatus status;
        @XmlElementWrapper(name = "attachedFiles")
        @XmlElement(name = "file")
        private List<String> attachedFiles;

    public Task(){
        this.id = generateId();
        this.title = null;
        this.description = null;
        this.startDate = null;
        this.dueDate = null;
        this.status = null;
        this.attachedFiles = null;
    }
    public Task(String title, String description){
        this.id = generateId();
        this.title = title;
        this.description = description;
        this.startDate = null;
        this.dueDate = null;
        this.status = null;
        this.attachedFiles = null;
    }
    public Task(String title,
                String description,
                LocalDate startDate,
                LocalDate dueDate,
                TaskStatus status,
                List<String> attachedFiles)
    {
        this.id = generateId();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
        this.attachedFiles = attachedFiles;
    }
    public Task(String title, LocalDate startDate, LocalDate dueDate, TaskStatus status)
    {
        this.id = generateId();
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
    }
    public Task(String title, LocalDate startDate, LocalDate dueDate)
    {
        this.id = generateId();
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
    public Task(String title)
    {
        this.id = generateId();
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.id = generateId();
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.id = generateId();
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<String> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<String> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
    public void addAttachedFile(String fileName) {
        attachedFiles.add(fileName);
    }

    public void removeAttachedFile(String fileName) {
        attachedFiles.remove(fileName);
    }
    public long getId() {
        return id;
    }
    public static long generateId() {
        return idGenerator.getAndIncrement();
    }

    public String toString()
    {
        return "Task{" +
                "id=" + id +'\''+
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +  '\'' +
                ", dueDate=" + dueDate + '\'' +
                ", status=" + status + '\'' +
                ", attachedFiles=" + attachedFiles +
                '}';
    }
}
