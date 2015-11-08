package uk.org.urbanroots.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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

    public static void visualiseToolbar(AppCompatActivity activity, String text, boolean shouldHaveBack, boolean icon) {
        visualiseToolbar(activity, text, shouldHaveBack);

        Drawable d = ContextCompat.getDrawable(activity, R.drawable.logo);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        Drawable d2 = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(bitmap, 195, 100, true));
        activity.getSupportActionBar().setLogo(d2);
    }
}
