package com.example.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    TextView tvFirstHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        tvFirstHello = findViewById(R.id.tvFirstHello);
        String username = getIntent().getStringExtra("username");
        tvFirstHello.setText("Hello: " + username);
    }
}

