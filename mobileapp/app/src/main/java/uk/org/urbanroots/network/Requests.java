package uk.org.urbanroots.network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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

import uk.org.urbanroots.activities.LoggedInActivity;
import uk.org.urbanroots.activities.LoginScreen;
import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.Persistence;

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
        String url = getBaseUrl() + "volunteer/2/jobs";
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

    public void pollAreThereNewTasks() {
        Log.w(LoginScreen.LOG_TAG, "are we here?");
        String url = getBaseUrl() + "volunteer/2/jobs";
        JsonRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.w(LoginScreen.LOG_TAG, String.valueOf(response.length()));
                        if (response.length() != Persistence.getInstance(mContext).getNumberCachedTaskKeys()) {
                            // Notify
                            buildNotification();
                            Persistence.getInstance(mContext).setNumberCachedTaskKeys(response.length());
                            Log.w(LoginScreen.LOG_TAG, "we have a difference");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(LoginScreen.LOG_TAG, "problem");
                        error.printStackTrace();
                    }
                });

        mRequestQueue.add(getRequest);
    }

    public void registerObserver(TaskPollObserver tpo) {
        mObservers.add(tpo);
    }

    private void buildNotification() {
        NotificationCompat.Builder mBuilder =
               new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Urban Roots")
                        .setContentText("You have been invited to a volunteering event!");

        Intent resultIntent = new Intent(mContext, LoggedInActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(LoggedInActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(9312, mBuilder.build());
    }
}
