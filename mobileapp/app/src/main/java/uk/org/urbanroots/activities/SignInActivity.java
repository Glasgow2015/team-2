package uk.org.urbanroots.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class SignInActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ToolbarVisualiser.visualiseToolbar(this, "Sign In", true);
    }



    public void submitRegistration(View view) {

        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        Log.d(LoginScreen.LOG_TAG, email);
        Log.d(LoginScreen.LOG_TAG, password);

        if (    email.isEmpty() ||
                password.isEmpty())
            Toast.makeText(getApplicationContext(), "Data missing", Toast.LENGTH_LONG).show();
        else {
            // Send data to server here
            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
        }
    }
}
