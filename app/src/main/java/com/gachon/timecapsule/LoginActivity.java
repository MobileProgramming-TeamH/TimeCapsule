package com.gachon.timecapsule;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "Login";
    private FirebaseAuth mAuth;
    private String Uid;
    boolean isLogin = false;
    Intent moveToMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText enterEmail = findViewById(R.id.login_email);
        EditText enterPassword = findViewById(R.id.login_password);

        Button loginBtn = findViewById(R.id.loginBtn);
        Button findPasswordBtn = findViewById(R.id.findPwBtn);

        moveToMain = new Intent(this, MainActivity.class);
        Intent moveToFindPassword = new Intent(this, FindPasswordActivity.class);


        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterEmail.length() == 0) {
                    StartToast(R.string.email_cannot_empty_warning);
                }
                else if(enterPassword.length() < 6) {
                    StartToast(R.string.password_length_warning);
                } else signIn(enterEmail.getText().toString().trim(), enterPassword.getText().toString().trim());
            }
        });

        findPasswordBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(moveToFindPassword);
            }
        });

    }
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            isLogin = true;
                            for(UserInfo profile : user.getProviderData()){

                                Uid=profile.getUid();

                                Log.d("TOKEN",Uid);
                                //String UId=Uid;
                            }
                            moveToMain.putExtra("Uid", Uid);
                            startActivity(moveToMain);
                            finish();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            StartToast(R.string.login_failed);
                            return;
                        }
                    }
                });
        // [END sign_in_with_email]
    }
    public void StartToast(Integer msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.show();
    }
}
