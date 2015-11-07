package uk.org.urbanroots.network;

public final class Requests {
    private Requests mInstance;

    private Requests() {};

    public Requests getInstance() {
        if (mInstance == null) mInstance = new Requests();
        return mInstance;
    }

    public void POST() {

    }
}
