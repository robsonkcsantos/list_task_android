package com.example.listatarefas.model;

import java.util.ArrayList;

public class TaskDAO {

    private final static ArrayList<Task> tasks = new ArrayList<>();

    public ArrayList<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    public Task findTaskById(Task t) {
        for (Task tarefa : tasks) {
            if (tarefa.getId() == t.getId()) {
                return tarefa;
            }
        }
        return null;
    }

    public void edit(Task t) {
        Task edit = findTaskById(t);
        if (edit != null) {
            int posicao = tasks.indexOf(edit);
            tasks.set(posicao, t);
        }
    }

    public void save(Task task) {
        tasks.add(task);
    }

    public void remove(Task t) {
        Task remove = findTaskById(t);

        if (remove != null) {
            tasks.remove(remove);
        }
    }
}
