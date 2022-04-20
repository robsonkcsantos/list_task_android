package com.example.listatarefas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listatarefas.model.Task;
import com.example.listatarefas.model.TaskDAO;

public class FormTaskActivity extends AppCompatActivity {

    EditText editTitleTask, editDescriptionTask;
    Button btnAdicionar;
    private static final TaskDAO dao = new TaskDAO();
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_task);
        setTitle("Cadastrar Tarefa");
        loadWidgets();
        configButtonPersist();
        verifyExtraIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_task_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.form_task_menu_save:
                validFields();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadWidgets() {
        editTitleTask = findViewById(R.id.edit_add_title_task);
        editDescriptionTask = findViewById(R.id.edit_add_description_task);
        btnAdicionar = findViewById(R.id.btn_add_task);
    }

    private void configButtonPersist() {
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validFields();
            }
        });
    }

    private void validFields() {
        if (fieldsNotEmpty()) {
            persistTask();
        } else {
            showMessage("Ai nao gurizao!!");
        }
    }

    private void persistTask() {
        populateTask();
        if (editTask()) {
            edit();
        } else {
            save();
        }
        finish();
    }

    private boolean fieldsNotEmpty() {
        return !editTitleTask.getText().toString().isEmpty()
                && !editDescriptionTask.getText().toString().isEmpty();
    }

    private void verifyExtraIntent() {
        if (editTask()) {
            setTitle("Editar Tarefa");
            task = (Task) getIntent().getSerializableExtra("tarefaSelecionada");
            editDescriptionTask.setText(task.getDescription());
            editTitleTask.setText(task.getTitulo());
            btnAdicionar.setText("Editar");
        }
    }

    private void populateTask() {
        if (editTask()) {
            task.setTitulo(editTitleTask.getText().toString());
            task.setDescription(editDescriptionTask.getText().toString());
        } else {
            task = new Task(
                    editTitleTask.getText().toString(),
                    editDescriptionTask.getText().toString());
        }
    }

    private boolean editTask() {
        return getIntent().hasExtra("tarefaSelecionada");
    }

    private void save() {
        dao.save(task);
        showMessage("Tarefa Adcionada");
    }

    private void edit() {
        dao.edit(task);
        showMessage("Tarefa Editada");
    }

    private void showMessage(String msg) {
        Toast.makeText(
                FormTaskActivity.this,
                msg,
                Toast.LENGTH_SHORT
        ).show();
    }

}