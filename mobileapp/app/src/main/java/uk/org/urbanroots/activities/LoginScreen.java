package uk.org.urbanroots.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import uk.org.urbanroots.network.TaskPollAlarm;
import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class LoginScreen extends AppCompatActivity {
    public static final String LOG_TAG = "jpmorganchase";
    public static final String PREFS_NAME = "jpmorganchaseprefsfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ToolbarVisualiser.visualiseToolbar(this, "Urban Roots", false, true);

        Intent alarmIntent = new Intent(this, TaskPollAlarm.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginScreen.this, 0, alarmIntent, 0);

        final AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 4000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }

    public void reportProblem(View view) {
        Intent intent = new Intent(this, ReportIssueActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
