package com.example.todoapp.dao;

import com.example.todoapp.business.model.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class TaskDao {

    private final Map<Integer, Task> storage = new HashMap<>();

    {
        save(new Task(1, "Réviser DS de maths", "Séries numériques et probabilités.", false));
        save(new Task(2, "Valider mon PIVE", "PIVE Club Poker.", true));
        save(new Task(3, "Choisir mon parcours de 4A", "SIR ou SIA ?", false));
    }

    public Task save(Task task) {
        storage.put(task.id(), task);
        return task;
    }

    public Optional<Task> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Task> findTodo() {
        return storage.values().stream()
                .filter(task -> Boolean.FALSE.equals(task.done()))
                .toList();
    }

    public boolean delete(int id) {
        return storage.remove(id) != null;
    }

    public boolean update(int id, Task updatedTask) {
        if (!storage.containsKey(id)) {
            return false;
        }

        storage.put(id, updatedTask);
        return true;
    }
}