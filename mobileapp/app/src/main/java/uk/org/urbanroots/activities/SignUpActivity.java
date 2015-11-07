package uk.org.urbanroots.activities;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

        ToolbarVisualiser.visualiseToolbar(this, "Sign Up", true);

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
        final String email = etTitle.getText().toString();
        final String password = etTitle.getText().toString();
        final String fname = etTitle.getText().toString();
        final String lname = etTitle.getText().toString();
        final String contact = etTitle.getText().toString();
        final String location = etTitle.getText().toString();
        final String skills = etTitle.getText().toString();

        Log.d(LoginScreen.LOG_TAG, email);
        Log.d(LoginScreen.LOG_TAG, password);
        Log.d(LoginScreen.LOG_TAG, fname);
        Log.d(LoginScreen.LOG_TAG, lname);
        Log.d(LoginScreen.LOG_TAG, contact);
        Log.d(LoginScreen.LOG_TAG, location);
        Log.d(LoginScreen.LOG_TAG, skills);

    }
}
