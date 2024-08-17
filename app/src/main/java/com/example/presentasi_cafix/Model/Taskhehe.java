package com.example.presentasi_cafix.Model;

public class Taskhehe {
    private String taskName;
    private String category;
    private String deadline;
    private String description;
    private String id;

    public Taskhehe() {

    }

    public Taskhehe(String taskName, String category, String deadline, String description,String id) {
        this.id = id;
        this.taskName = taskName;
        this.category = category;
        this.deadline = deadline;
        this.description = description;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
}
