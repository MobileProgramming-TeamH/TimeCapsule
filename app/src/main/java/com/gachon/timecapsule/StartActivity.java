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
        Button signinBtn = findViewById(R.id.goToSigninBtn);

        Intent moveToLogin = new Intent(this, LoginActivity.class);
        // 회원가입 Intent 선언

        loginBtn.setOnClickListener(v->startActivity(moveToLogin));
    }
}
