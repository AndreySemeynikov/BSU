package com.AndreySemeynikov.tasks;

import java.time.LocalDate;
import java.util.List;

public class Task {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private TaskStatus status;
    private List<String> attachedFiles;

    public Task(String title,String description, LocalDate startDate, LocalDate dueDate, TaskStatus status, List<String> attachedFiles)
    {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
        this.attachedFiles = attachedFiles;
    }
    public Task(String title, LocalDate startDate, LocalDate dueDate, TaskStatus status)
    {
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
    }
    public Task(String title, LocalDate startDate, LocalDate dueDate)
    {
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
    public Task(String title)
    {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    public String toString()
    {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate.toString() +
                ", dueDate=" + dueDate.toString() +
                ", status=" + status +
                ", attachedFiles=" + attachedFiles +
                '}';
    }
}
