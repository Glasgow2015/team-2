package uk.org.urbanroots.network;

public final class Requests {
    private static Requests mInstance;
    private final String mBaseUrl = "http://ec2-54-216-216-131.eu-west-1.compute.amazonaws.com/";
    private Requests() {};

    public static Requests getInstance() {
        if (mInstance == null) mInstance = new Requests();
        return mInstance;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }
}
