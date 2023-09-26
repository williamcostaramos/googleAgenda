package com.williamramos.service;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.williamramos.facade.ConectCalendarFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListTasksService {
    private List<Task> tasks;
    private Tasks service;

    public ListTasksService() {
        this.service = ConectCalendarFacade.getTasksService();
        this.tasks = new ArrayList<>();
    }

    public List<Task> list() {
        try {
            String taskListId = "@default";
            Tasks.TasksOperations.List request = service.tasks().list(taskListId);
            var tarefas = request.execute();
            return tarefas.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
