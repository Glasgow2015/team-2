package uk.org.urbanroots.activities;

import android.app.AlertDialog;
import android.content.Context;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.org.urbanroots.network.Requests;
import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class ReportIssueActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private String[] locations;
    private TextView tvLocation;
    private String mLocation;
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
        mLocation = locations[0];
    }

    public void sendReport(View view) {
        final String title = etTitle.getText().toString();
        final String description = etDescription.getText().toString();
        final String location = tvLocation.getText().toString();

        // The toasts for successfull or failed submit
        final Toast toastSuccess = Toast.makeText(getApplicationContext(), "Report sent",
                Toast.LENGTH_SHORT);
        toastSuccess.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

        final Toast toastFailed = Toast.makeText(getApplicationContext(), "An error occured",
                Toast.LENGTH_SHORT);
        toastFailed.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

        if (title.isEmpty() || description.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_LONG).show();
            return;
        }

        String url = Requests.getInstance().getBaseUrl() + "report/submit/2";
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
        Log.w(LoginScreen.LOG_TAG, jo.toString());
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
                mLocation = locations[which];
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void gotoHome() {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    // Redundant for now
//    private class LocationGetter implements ConnectionCallbacks, OnConnectionFailedListener {
//
//        private GoogleApiClient mGoogleApiClient;
//        private Context mContext;
//
//        LocationGetter(Context c) {
//            mContext = c;
//        }
//
//        protected synchronized void buildGoogleApiClient(Context context) {
//            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }
//
//        @Override
//        public void onConnected(Bundle bundle) {
//
//        }
//
//        @Override
//        public void onConnectionSuspended(int i) {
//
//        }
//
//        @Override
//        public void onConnectionFailed(ConnectionResult connectionResult) {
//
//        }
//    }
}
