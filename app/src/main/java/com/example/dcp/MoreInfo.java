package com.example.dcp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreInfo extends MainActivity {
    private Button aboutus;
    private Button special;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MoreInfo","intent");
        setContentView(R.layout.more_info);
        aboutus = (Button)findViewById(R.id.aboutus);
        special= (Button)findViewById(R.id.special);

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreInfo.this, aboutus.class));
            }
        });
        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreInfo.this, MainActivity.class));
            }
        });
    }
}
