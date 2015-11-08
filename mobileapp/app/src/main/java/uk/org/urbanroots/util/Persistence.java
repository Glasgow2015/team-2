package uk.org.urbanroots.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import uk.org.urbanroots.activities.LoginScreen;

public class Persistence {
    private static Persistence instance;
    private Context mContext;
    private Persistence(Context context) {
       mContext = context;
    }

    public static Persistence getInstance(Context context) {
        if (instance == null) {
           instance = new Persistence(context);
        }
        return instance;
    }

    public int getNumberCachedTaskKeys() {
        SharedPreferences sp = mContext.getSharedPreferences(LoginScreen.PREFS_NAME, 0);
        Log.w(LoginScreen.LOG_TAG, "is this working");
        return sp.getInt("tasknumber", 0);
    }

    public void setNumberCachedTaskKeys(int number) {
        SharedPreferences sp = mContext.getSharedPreferences(LoginScreen.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("tasknumber", number);
        editor.commit();
    }
}
