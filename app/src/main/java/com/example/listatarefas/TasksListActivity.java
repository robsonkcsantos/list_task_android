package com.example.listatarefas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listatarefas.model.Task;
import com.example.listatarefas.model.TaskDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TasksListActivity extends AppCompatActivity {

    private ListView listaTarefas;
    private FloatingActionButton fabAddTask;
    final static TaskDAO dao = new TaskDAO();
    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        loadWidgets();
        configFABAddTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configListTask();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_task_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        selectMenuContext(item);

        //if (item.getItemId() == R.id.list_tasks_menu_delete) {
        //    removeTask(item);
        //} else {
        //    showMessage("Nada feito hoje!");
        //}

        return super.onContextItemSelected(item);
    }

    private void selectMenuContext(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_tasks_menu_delete:
                removeTask(item);
                break;
            case R.id.list_tasks_menu_info:
                showMessage("Nada feito hoje!");
                break;
        }
    }

    private void removeTask(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Task selectTask = adapter.getItem(menuInfo.position);
        dao.remove(selectTask);
        adapter.remove(selectTask);
        adapter.notifyDataSetChanged();

        showMessage("Tarefa Deletada");
    }

    private void loadWidgets() {
        fabAddTask = findViewById(R.id.fab_add_task);
        listaTarefas = findViewById(R.id.lista_tarefas);
    }

    private void configFABAddTask() {
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        TasksListActivity.this,
                        FormTaskActivity.class
                ));
            }
        });
    }

    private void configListTask() {
        configAdapterList();
        recoveryAdapter();
        configActionClickList();
    }

    private void configAdapterList() {
        adapter = new ArrayAdapter<Task>(
                TasksListActivity.this,
                android.R.layout.simple_list_item_1
        );
        listaTarefas.setAdapter(adapter);
    }

    private void recoveryAdapter() {
        adapter.clear();
        adapter.addAll(dao.findAll());
    }

    private void configActionClickList() {
        configShortClkick();
        configLongClick();
    }

    private void configLongClick(){
        /*
        listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task selectTask = (Task) adapterView.getItemAtPosition(i);
                dao.remove(selectTask);
                adapter.remove(selectTask);
                adapter.notifyDataSetChanged();
                return true;
            }
        });*/
        registerForContextMenu(listaTarefas);
    }

    private void configShortClkick() {
        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task selectTask = (Task) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(
                        TasksListActivity.this,
                        FormTaskActivity.class
                );
                intent.putExtra("tarefaSelecionada",selectTask);
                startActivity(intent);
            }
        });
    }

    private void showMessage(String msg) {
        Toast.makeText(
                TasksListActivity.this,
                msg,
                Toast.LENGTH_SHORT
        ).show();
    }

}