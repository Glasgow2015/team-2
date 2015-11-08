package uk.org.urbanroots.models;

public class Task {
    private final String mDescription;
    private final String mTitle;
    private final String mLocation;

    public Task(String d, String t, String l) {
        mDescription = d;
        mTitle = t;
        mLocation = l;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLocation() {
        return mLocation;
    }
}
