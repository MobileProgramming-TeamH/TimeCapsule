package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button loginBtn = findViewById(R.id.goToLoginBtn);
        Button joinBtn = findViewById(R.id.goToJoinBtn);

        Intent moveToLogin = new Intent(this, LoginActivity.class);
        Intent moveToJoin = new Intent(this, JoinActivity.class);

        loginBtn.setOnClickListener(v->startActivity(moveToLogin));
        joinBtn.setOnClickListener(v->startActivity(moveToJoin));
    }
}
