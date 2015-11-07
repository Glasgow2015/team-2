package uk.org.urbanroots.network;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import uk.org.urbanroots.models.IssueModel;

public class SendDescriptionTask extends AsyncTask<IssueModel, Void, Void>{

    @Override
    protected Void doInBackground(IssueModel... params) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("name", params[0].getTitle());
            jo.put("description", params[0].getDescription());
            jo.put("latitude", params[0].getLatitude());
            jo.put("longitude", params[0].getLongitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
