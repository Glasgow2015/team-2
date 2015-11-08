package uk.org.urbanroots.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import uk.org.urbanroots.network.Requests;
import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class ReportIssueActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private String[] locations;
    private TextView tvLocation;
    private RequestQueue mRequestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);
        ToolbarVisualiser.visualiseToolbar(this, "New Issue", true);

        etTitle = (EditText) findViewById(R.id.et_title);
        etDescription = (EditText) findViewById(R.id.et_description);
        tvLocation = (TextView) findViewById(R.id.tv_chosen_location);

        mRequestQueue = Volley.newRequestQueue(this);
        locations = getResources().getStringArray(R.array.locations);
    }

    public void sendReport(View view) {
        final String title = etTitle.getText().toString();
        final String description = etDescription.getText().toString();
        final String location = tvLocation.getText().toString();

        // The toasts for successfull or failed submit
        final Toast toastSuccess = Toast.makeText(getApplicationContext(), "Report sent",
                Toast.LENGTH_SHORT);

        final Toast toastFailed = Toast.makeText(getApplicationContext(), "An error occurred",
                Toast.LENGTH_SHORT);

        if (title.isEmpty() || description.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_LONG).show();
            return;
        }

        String url = Requests.getInstance(getApplicationContext()).getBaseUrl() + "report/submit/2";
        Log.w(LoginScreen.LOG_TAG, url);

        JSONObject jo = new JSONObject();
        try {
            jo.put("name", title);
            jo.put("description", description);
            jo.put("location", location);
            jo.put("latitude", "0");
            jo.put("longitude", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.w(LoginScreen.LOG_TAG, response.toString());
                        toastSuccess.show();
                        gotoHome();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastFailed.show();
                        error.printStackTrace();
                    }
                });

        mRequestQueue.add(postRequest);
    }

    public void pickLocation(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a location");
        builder.setItems(locations, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvLocation.setText(locations[which]);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void gotoHome() {
        this.finish();
    }
}
