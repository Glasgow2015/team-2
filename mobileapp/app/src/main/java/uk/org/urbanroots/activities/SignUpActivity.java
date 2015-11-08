package uk.org.urbanroots.activities;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import uk.org.urbanroots.network.Requests;
import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFname;
    private EditText etLname;
    private EditText etContact;
    private EditText tvLocation;
    private EditText etEmail;
    private EditText etPassword;
//    private EditText etSkills;
    private RequestQueue mRequestQueue;

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
        tvLocation = (EditText) findViewById(R.id.tv_location);
//        etSkills = (EditText) findViewById(R.id.et_skills);
        mRequestQueue = Volley.newRequestQueue(this);
    }

    public void submitRegistration(View view) {
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String fname = etFname.getText().toString();
        final String lname = etLname.getText().toString();
        final String contact = etContact.getText().toString();
        final String location = tvLocation.getText().toString();

        String url = Requests.getInstance().getBaseUrl() + "volunteer/apply";

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
            JSONObject jo = new JSONObject();
            try {
                jo.put("username", email);
                jo.put("first_name", fname);
                jo.put("last_name", lname);
                jo.put("phone_number", contact);
                jo.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.w(LoginScreen.LOG_TAG, response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

            mRequestQueue.add(postRequest);
        }
    }

}
