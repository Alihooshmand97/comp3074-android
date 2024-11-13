package com.example.labex7;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class FetchTodosTask extends AsyncTask<Void, Void, ArrayList<Todo>> {
    private static final String API_URL = "https://jsonplaceholder.typicode.com/todos?userId=1";
    private FetchTodosListener listener;

    public interface FetchTodosListener {
        void onFetchTodosComplete(ArrayList<Todo> todos);
    }

    public FetchTodosTask(FetchTodosListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Todo> doInBackground(Void... voids) {
        ArrayList<Todo> todos = new ArrayList<>();
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonTodo = jsonArray.getJSONObject(i);
                int userId = jsonTodo.getInt("userId");
                int id = jsonTodo.getInt("id");
                String title = jsonTodo.getString("title");
                boolean completed = jsonTodo.getBoolean("completed");

                todos.add(new Todo(userId, id, title, completed));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todos;
    }

    @Override
    protected void onPostExecute(ArrayList<Todo> todos) {
        if (listener != null) {
            listener.onFetchTodosComplete(todos);
        }
    }
}
