package com.pedantic.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

public class Task {

    private int id;
    private String taskName;

    @JsonbDateFormat("dd.MM.yyyy")
    private LocalDate dueDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public static void main(String[] args) {
        JsonObject date = Json.createObjectBuilder().add("Date", LocalDate.now().toString()).build();
        System.out.println(date);

    }
}
