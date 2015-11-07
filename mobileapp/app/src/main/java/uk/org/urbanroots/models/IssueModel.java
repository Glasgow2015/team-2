package uk.org.urbanroots.models;

import org.json.JSONObject;

public class IssueModel {
    private final String mTitle;
    private final String mDescription;
    private final double mLatitude;
    private final double mLongitude;
    private final String mArea;

    public IssueModel(String title, String description, double latitude, double longitude, String area){
        mTitle = title;
        mDescription = description;
        mLatitude = latitude;
        mLongitude = longitude;
        mArea = area;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public String getArea() {
        return mArea;
    }
}
