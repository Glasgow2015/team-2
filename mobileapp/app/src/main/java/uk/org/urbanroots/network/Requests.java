package uk.org.urbanroots.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.org.urbanroots.activities.LoginScreen;

public final class Requests {
    private static Requests mInstance;
    private final String mBaseUrl = "http://ec2-54-216-216-131.eu-west-1.compute.amazonaws.com/";
    private RequestQueue mRequestQueue;
    private Context mContext;
    private List<TaskPollObserver> mObservers;

    private Requests(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        mObservers = new ArrayList<>();
    }

    public static Requests getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Requests(context);
        }
        return mInstance;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void pollTasks() {
        String url = getBaseUrl() + "/volunteer/2/jobs";
        JsonRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        for (TaskPollObserver tpo : mObservers) tpo.onNewTaskData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        mRequestQueue.add(getRequest);
    }

    public void registerObserver(TaskPollObserver tpo) {
        mObservers.add(tpo);
    }
}
