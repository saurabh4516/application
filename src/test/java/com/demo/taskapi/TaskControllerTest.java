package com.demo.taskapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService service;

    @Test
    void createReturns201() throws Exception {
        when(service.create("Learn Jenkins")).thenReturn(new Task(1L, "Learn Jenkins", false));

        mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Learn Jenkins\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn Jenkins"));
    }

    @Test
    void blankTitleReturns400() throws Exception {
        mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listReturnsTasks() throws Exception {
        when(service.findAll()).thenReturn(List.of(new Task(1L, "A", false)));

        mvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A"));
    }

    @Test
    void missingTaskReturns404() throws Exception {
        when(service.findById(99L)).thenThrow(new TaskNotFoundException(99L));

        mvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound());
    }
}
