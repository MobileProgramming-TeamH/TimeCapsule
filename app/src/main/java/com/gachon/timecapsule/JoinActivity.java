package com.gachon.timecapsule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class JoinActivity extends AppCompatActivity {

    private final static String TAG = "Join";
    private FirebaseAuth mAuth;
    private String userEmail;
    private Intent moveToLogin;
    public boolean is_password_confirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        mAuth = FirebaseAuth.getInstance();

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText passwordCheck = findViewById(R.id.pwdcheck);

        moveToLogin = new Intent(this, LoginActivity.class);

        Button joinBtn = findViewById(R.id.joinBtn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = email.getText().toString().trim();

                if (password.getText().toString().equals(passwordCheck.getText().toString()))
                    is_password_confirm = true;
                else
                    is_password_confirm = false;

                if(is_password_confirm){
                    if(email.length() == 0 || password.length() == 0){
                        StartToast(R.string.login_blank);
                    } else createAccount(email.getText().toString().trim(), password.getText().toString().trim());
                }
                else{
                    StartToast(R.string.password_not_equal_warning);
                }
            }
        });

    }
    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            StartToast(R.string.join_success);
                            FirebaseUser user = mAuth.getCurrentUser();
                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", userEmail);
                            FirebaseFirestore database = FirebaseFirestore.getInstance();
                            database.collection("Users").document(mAuth.getCurrentUser().getUid()).set(hashMap);


                            startActivity(moveToLogin);
                            finish();
                        }
                        else{
                            if(password.length() < 6){
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                StartToast(R.string.password_length_warning);
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                StartToast(R.string.password_email_warning);
                            }
                            return;
                        }
                    }
                });

    }

    public void StartToast(Integer msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}