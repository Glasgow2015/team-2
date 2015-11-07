package uk.org.urbanroots.util;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import uk.org.urbanroots.urbanroots.R;

public class ToolbarVisualiser {
    public static void visualiseToolbar(AppCompatActivity activity, String text, boolean shouldHaveBack) {
        activity.setSupportActionBar((Toolbar) activity.findViewById(R.id.app_bar));
        if (shouldHaveBack) {
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        activity.getSupportActionBar().setTitle(text);
    }
}
