package uk.org.urbanroots.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
    }

    public void submitSignIn(View view) {
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        if(email.equals("admin") && password.equals("admin")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Logging in...",
                    Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, LoggedInActivity.class);
            startActivity(intent);
        } else{
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid login",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    }

