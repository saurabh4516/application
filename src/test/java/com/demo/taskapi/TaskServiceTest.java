package com.demo.taskapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService();
    }

    @Test
    void createAssignsIdAndStartsNotDone() {
        Task task = service.create("Write report");
        assertEquals(1L, task.getId());
        assertEquals("Write report", task.getTitle());
        assertFalse(task.isDone());
    }

    @Test
    void findAllReturnsTasksInOrder() {
        service.create("A");
        service.create("B");
        assertEquals(2, service.findAll().size());
        assertEquals("A", service.findAll().get(0).getTitle());
    }

    @Test
    void markDoneUpdatesTask() {
        Task task = service.create("Deploy");
        assertTrue(service.markDone(task.getId()).isDone());
    }

    @Test
    void deleteRemovesTask() {
        Task task = service.create("Temp");
        service.delete(task.getId());
        assertThrows(TaskNotFoundException.class, () -> service.findById(task.getId()));
    }

    @Test
    void findMissingTaskThrows() {
        assertThrows(TaskNotFoundException.class, () -> service.findById(99L));
    }
}
