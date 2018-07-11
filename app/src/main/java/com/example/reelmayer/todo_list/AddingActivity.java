package com.example.reelmayer.todo_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddingActivity extends AppCompatActivity {

    SecondActivityAdapter adapter;
    private ImageButton back, add;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        addListenerOnButton();
        TextView textTask = (TextView) findViewById(R.id.textTask);
        textTask.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Semibold.ttf"));
        TextView textProject = (TextView) findViewById(R.id.textProject);
        textProject.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Semibold.ttf"));

        ArrayList<String> projectsArray = new ArrayList<>();
        for (Project project : MainActivity.projects) {
            projectsArray.add(project.getTitle());
        }
        add = (ImageButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String text = editText.getText().toString();
                Todo todo = new Todo(text, adapter.getSelectedPosition() + 1);
                Intent intent = new Intent(".MainActivity");
                intent.putExtra(".Todo",todo);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

       adapter = new SecondActivityAdapter(this, projectsArray);
        ListView projectsList = (ListView) findViewById(R.id.projectsList);
        projectsList.setAdapter(adapter);

    }

    public void addListenerOnButton() {

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
