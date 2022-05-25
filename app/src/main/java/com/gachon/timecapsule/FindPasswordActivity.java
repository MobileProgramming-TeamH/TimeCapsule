package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class FindPasswordActivity extends AppCompatActivity {

    private final static String TAG = "FindPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);

        EditText getEmail = findViewById(R.id.getEmail);


        Button sendEmailBtn = findViewById(R.id.sendEmailBtn);

        Intent moveToLogin = new Intent(this, LoginActivity.class);

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = getEmail.getText().toString();
                if (emailAddress == null)
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    Log.d(TAG, "emailAddress: " + emailAddress);
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "이메일을 보냈습니다.", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "메일 보내기 실패!", Toast.LENGTH_LONG).show();
                                    }
                                }

                            });
                }
            }

            public void sendPasswordReset(String emailAddress) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "emailAddress: " + emailAddress);
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(getApplicationContext(), "비밀번호 재설정 이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d(TAG, "Failed");
                                    Toast.makeText(getApplicationContext(), "이메일을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}