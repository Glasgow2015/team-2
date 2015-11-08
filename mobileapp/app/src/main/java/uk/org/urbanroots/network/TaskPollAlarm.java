package uk.org.urbanroots.network;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import uk.org.urbanroots.activities.LoggedInActivity;
import uk.org.urbanroots.activities.LoginScreen;

public class TaskPollAlarm extends BroadcastReceiver {
    public TaskPollAlarm() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Requests.getInstance(context).pollAreThereNewTasks();
    }
}
