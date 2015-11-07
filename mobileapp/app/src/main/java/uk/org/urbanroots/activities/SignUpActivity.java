package uk.org.urbanroots.activities;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class SignUpActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etFname;
    EditText etLname;
    EditText etContact;
    EditText etLocation;
    EditText etEmail;
    EditText etPassword;
    EditText etSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ToolbarVisualiser.visualiseToolbar(this, "Register", true);

        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etFname = (EditText) findViewById(R.id.et_fname);
        etLname = (EditText) findViewById(R.id.et_lname);
        etContact = (EditText) findViewById(R.id.et_phone);
        etLocation = (EditText) findViewById(R.id.et_location);
        etSkills = (EditText) findViewById(R.id.et_skills);


       // Log.d(LoginScreen.LOG_TAG, "test");

    }

    public void submitRegistration(View view) {

        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String fname = etFname.getText().toString();
        final String lname = etLname.getText().toString();
        final String contact = etContact.getText().toString();
        final String location = etLocation.getText().toString();
        final String skills = etSkills.getText().toString();

        Log.d(LoginScreen.LOG_TAG, email);
        Log.d(LoginScreen.LOG_TAG, password);
        Log.d(LoginScreen.LOG_TAG, fname);
        Log.d(LoginScreen.LOG_TAG, lname);
        Log.d(LoginScreen.LOG_TAG, contact);
        Log.d(LoginScreen.LOG_TAG, location);
        Log.d(LoginScreen.LOG_TAG, skills);


        if (    email.isEmpty() ||
                password.isEmpty() ||
                fname.isEmpty() ||
                lname.isEmpty() ||
                contact.isEmpty() ||
                location.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Data missing",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            // Send data to server here
            Toast toast = Toast.makeText(getApplicationContext(), "Registration Successfull",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
        }
    }

}
