package uk.org.urbanroots.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.org.urbanroots.urbanroots.R;
import uk.org.urbanroots.util.ToolbarVisualiser;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ToolbarVisualiser.visualiseToolbar(this, "Sign In", true);
    }
}
