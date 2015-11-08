package uk.org.urbanroots.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.Persistence;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class LoginScreen extends AppCompatActivity {
    public static final String LOG_TAG = "jpmorganchase";
    public static final String PREFS_NAME = "jpmorganchaseprefsfile";
    public static final String TASK_NOTIFICATION_GROUP = "task_notifications_group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ToolbarVisualiser.visualiseToolbar(this, "Urban Roots", false, true);
        Persistence.getInstance(getApplicationContext()).alarmFireUp();
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
