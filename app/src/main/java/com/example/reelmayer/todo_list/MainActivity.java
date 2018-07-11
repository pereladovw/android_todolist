

package com.example.reelmayer.todo_list;

        import android.app.Activity;
        import android.content.Context;

        import android.content.Intent;
        import android.os.Bundle;

        import android.view.View;
        import android.widget.ListView;
        import android.widget.Toast;


        import com.google.gson.Gson;
        import com.google.gson.JsonArray;
        import com.google.gson.JsonElement;

        import com.google.gson.JsonObject;
        import com.koushikdutta.ion.Ion;
        import com.scalified.fab.ActionButton;

        import java.util.ArrayList;



        import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends Activity {

    private ListView listView;
    private CustomAdapter mAdapter;
    private ActionButton add_btm;

    public static ArrayList<Project> projects = new ArrayList<Project>();
    public static ArrayList<Todo> todos = new ArrayList<Todo>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            JsonArray result = Ion.with(this)
                    .load("https://murmuring-gorge-28716.herokuapp.com/projects/projects/")
                    .asJsonArray()
                    .get();

            if (result != null) {
                for (JsonElement projectJsonElement : result) {
                    projects.add(new Gson().fromJson(projectJsonElement, Project.class));

                }
            }else
                Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();

        } catch (Exception e) { e.printStackTrace();}



        try {
            JsonArray result = Ion.with(this)
                    .load("https://murmuring-gorge-28716.herokuapp.com/projects/todos/")
                    .asJsonArray()
                    .get();
            if (result != null) {

                for (JsonElement todoJsonElement : result) {
                    Todo gTodo = new Gson().fromJson(todoJsonElement, Todo.class);
                    String line = todoJsonElement.toString();
                    String[] parts = line.split(",");
                    String[] scomp = parts[2].split(":");
                    String[] sid = parts[3].split(":");
                    gTodo.project_id = Integer.parseInt(sid[1]);
                    gTodo.isCompleted = Boolean.parseBoolean(scomp[1]);

                    todos.add(gTodo);

                }

            } else
                Toast.makeText(getApplicationContext(), "nulltodos", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {e.printStackTrace();}


        listView = (ListView) findViewById(R.id.listView);

        addListenerOnButton();



        mAdapter = new CustomAdapter(this);
        for (Project project : projects) {

            mAdapter.addSectionHeaderItem(new Todo(project.getTitle(), projects.size() + 2));
            for(Todo todo :todos) {
                if(todo.project_id == project.getId()) {

                    mAdapter.addItem(todo);
                }
            }
        }


        listView.setAdapter(mAdapter);


    }


    public void addListenerOnButton() {

        add_btm = (ActionButton) findViewById(R.id.add_btm);
        add_btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(".AddingActivity");
                startActivityForResult(intent, 1);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent == null) {
            return;
        }
        Todo newTodo = (Todo) intent.getParcelableExtra(".Todo");

        mAdapter.reset();
        todos.add(newTodo);
        newTodo.id = todos.size();
        JsonObject createparam = new JsonObject();
        createparam.addProperty("project_id", newTodo.project_id);
        createparam.addProperty("id",newTodo.id  );
        createparam.addProperty("text", newTodo.text);
        Ion.with(this).load("https://murmuring-gorge-28716.herokuapp.com/projects/androidcreate")
                .setJsonObjectBody(createparam)
                .asJsonObject();

        for (Project project : projects) {

            mAdapter.addSectionHeaderItem(new Todo(project.getTitle(), projects.size() + 2));
            for (Todo todo : todos) {
                if (todo.project_id == project.getId()) {

                    mAdapter.addItem(todo);
                }
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

