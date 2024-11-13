package com.example.labex7;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchTodosTask.FetchTodosListener {

    private ListView todoListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> todoTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ListView and ArrayList
        todoListView = findViewById(R.id.todoListView);
        todoTitles = new ArrayList<>();

        // Set up ArrayAdapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoTitles);
        todoListView.setAdapter(adapter);

        // Start fetching ToDo items
        new FetchTodosTask(this).execute();
    }

    @Override
    public void onFetchTodosComplete(ArrayList<Todo> todos) {
        // Clear the list and add the new titles
        todoTitles.clear();
        for (Todo todo : todos) {
            todoTitles.add(todo.getTitle() + " - " + (todo.isCompleted() ? "Completed" : "Pending"));
        }

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();
    }
}
