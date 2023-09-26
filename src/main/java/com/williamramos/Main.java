package com.williamramos;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.tasks.model.Task;
import com.williamramos.service.EventCreateService;
import com.williamramos.service.ListEventsService;
import com.williamramos.service.ListTasksService;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) throws IOException {
        save();
        listarTarefas();
        listarEventos();

    }
    public static Event save() throws IOException {
        var service = new EventCreateService();
        service.createEvent("Evento Sucesso Programado", "Festa do Amanhecer");
        service.start(LocalDateTime.now().plusDays(3).toString());
        service.end(LocalDateTime.now().plusDays(7).toString());
        service.addParticipantes("wulliamcostaramos@gmail.com");
        service.addParticipantes("denizequeiroz2001@gmail.com");
        return service.save();
    }

    public static void listarTarefas(){
        var tarefaServices = new ListTasksService();
        System.out.println("Listando tarefas");
        List<Task> tarefas = tarefaServices.list();
        if(!tarefas.isEmpty()){
            for (Task tarefa : tarefas) {
                System.out.printf("Tarefa: %s - %s \n",tarefa.getTitle(), tarefa.getDue());            }
        }
    }
    public static void listarEventos(){
        var service = new ListEventsService();

        List<Event> items = service.list();


        items.forEach(item -> System.out.printf("id: %s \nNome: %s \nDescrição : %s \n######################################################## \n", item.getId(),item.getSummary(), item.getDescription()));

        items.forEach(item -> {
            if (item.getRecurrence() != null) {
                System.out.println("Listando eventos recorrentes");
                for (String en : item.getRecurrence()) {
                    System.out.printf("%s \n", en);
                }
            }
        });
    }
}
