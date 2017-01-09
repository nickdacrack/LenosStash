package org.rotaract9210.d9210events;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ComingSoonActivity extends AppCompatActivity {

    TextView tvMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/URGH TYPE PERSONAL USE.otf");
        tvMessage = (TextView) findViewById(R.id.tvComing_Soon_Message);
        tvMessage.setTypeface(typeface);
    }
}
