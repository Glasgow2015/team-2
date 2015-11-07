package uk.org.urbanroots.urbanroots;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import uk.org.urbanroots.util.ToolbarVisualiser;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ToolbarVisualiser.visualiseToolbar(this, "Urban Roots", false);
    }
}
