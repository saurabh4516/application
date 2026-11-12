package com.demo.taskapi;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public Task create(String title) {
        Task task = new Task(sequence.incrementAndGet(), title, false);
        store.put(task.getId(), task);
        return task;
    }

    public List<Task> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(Task::getId))
                .toList();
    }

    public Task findById(Long id) {
        Task task = store.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    public Task markDone(Long id) {
        Task task = findById(id);
        task.setDone(true);
        return task;
    }

    public void delete(Long id) {
        findById(id);
        store.remove(id);
    }
}
