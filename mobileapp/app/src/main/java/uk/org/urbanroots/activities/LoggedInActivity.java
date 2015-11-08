package uk.org.urbanroots.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;

import uk.org.urbanroots.adapters.TaskAdapter;
import uk.org.urbanroots.models.Task;
import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class LoggedInActivity extends AppCompatActivity {


    private final Task [] mockTasks = {
        new Task("description 1", "Title 2", "Location  3"),
            new Task("description 2", "Title 3", "Location 5"),
            new Task("description 2", "Title 3", "Location 5"),
            new Task("description 2", "Title 3", "Location 5")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        ToolbarVisualiser.visualiseToolbar(this, "Urban Roots", false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_tasks);
        rv.setLayoutManager(llm);
        TaskAdapter ta = new TaskAdapter(new ArrayList<>(Arrays.asList(mockTasks)));
        rv.setAdapter(ta);

    }
}
