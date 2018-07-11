package com.example.reelmayer.todo_list;

import java.util.ArrayList;

public class Project {
    private String title;
    private Integer id;
    private ArrayList<Todo> todos = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Todo getTodo(int position) {
        return todos.get(position);
    }

    public void addTodo(Todo todo) {
        this.todos.add(todo);
    }

    public ArrayList<Todo> getTodos() {
        return todos;
    }
}

