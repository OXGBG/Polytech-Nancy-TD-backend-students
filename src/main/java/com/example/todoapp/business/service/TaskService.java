package com.example.todoapp.business.service;

import com.example.todoapp.business.model.Task;
import com.example.todoapp.dao.TaskDao;


import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskDao dao =new TaskDao();
    /**
     * Persist {@link Task} model.
     * @param task task to save.
     * @return task model.
     */
    public Task save(Task task) {
        return dao.save(task);
    }

    /**
     * Retrieve {@link Task} model by id.
     * @param id identifier of the {@link Task}.
     * @return {@link Task} model wrapped by Optional.
     */
    public Optional<Task> findById(int id) {
        return dao.findById(id);
    }

    /**
     * Retrieve all {@link Task} models.
     * @return list of {@link Task} models.
     */
    public List<Task> findAll() {
        return dao.findAll();
    }

    /**
     * Retrieve all {@link Task} models that are not done.
     * @return list of {@link Task} models to do.
     */
    public List<Task> findTodo() {
        return dao.findTodo();
    }

    /**
     * Delete {@link Task} model by id.
     * @param id identifier of the {@link Task}.
     * @return true if deleted, false if not found.
     */
    public boolean delete(int id) {
        return dao.delete(id);
    }

    /**
     * Update {@link Task} model by id.
     * @param id identifier of the {@link Task}.
     * @param updatedTask task to update.
     * @return true if updated, false if not found.
     */
    public boolean update(int id, Task updatedTask) {
        return dao.update(id, updatedTask);
    }

}
