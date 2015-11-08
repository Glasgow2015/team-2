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
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import uk.org.urbanroots.adapters.TaskAdapter;
import uk.org.urbanroots.models.Task;
import uk.org.urbanroots.network.Requests;
import uk.org.urbanroots.network.TaskPollObserver;
import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class LoggedInActivity extends AppCompatActivity implements TaskPollObserver{

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        ToolbarVisualiser.visualiseToolbar(this, "Urban Roots", false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        Requests.getInstance(getApplicationContext()).registerObserver(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_tasks);
        mRecyclerView.setLayoutManager(llm);
        Requests.getInstance(getApplicationContext()).pollTasks();

    }

    @Override
    public void onNewTaskData(JSONObject newTaskData) {
        if (mRecyclerView.getAdapter() == null) {
            LinkedList<Task> alt = new LinkedList<>();
            Iterator<String> jsonIterator = newTaskData.keys();

            while(jsonIterator.hasNext()) {
                String nextKey = jsonIterator.next();
                try {
                    JSONObject taskBody = newTaskData.getJSONObject(nextKey);
                    String description = taskBody.getString("description");
                    String location = taskBody.getString("location");
                    alt.addLast(new Task(description, nextKey, location));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mTaskAdapter = new TaskAdapter(alt);
            mRecyclerView.setAdapter(mTaskAdapter);
        }
    }
}
