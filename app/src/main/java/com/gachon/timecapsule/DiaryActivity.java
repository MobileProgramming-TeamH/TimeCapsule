package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {
    private final static String TAG = "Diary";

    private String UId="";
    private String Answer="";
    private String Question="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Intent moveToQuestion = new Intent(this, QuestionActivity.class);
        Intent moveToAnswer = new Intent(this, AnswerActivity.class);

        Intent intent = getIntent();
        final String date = intent.getExtras().getString("Date");

        Log.d(TAG, "Intent");

        Log.d(TAG, "get date" + date);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(date);

        TextView tv_todayQuestion;
        TextView tv_todayAnswer;
        Button back_button;

        tv_todayQuestion=(TextView)findViewById(R.id.tv_todayQuestion);
        tv_todayAnswer=(TextView)findViewById(R.id.tv_todayAnswer);
        back_button=(Button)findViewById(R.id.button1);


        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                finish();
            }
        });

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){
                    Log.d("TOKEN","YES");
                    for(UserInfo profile : user.getProviderData()){

                        String Uid=profile.getUid();

                        Log.d("TOKEN",Uid);
                        UId=Uid;


                        HashMap<Object, String> info = new HashMap<>();
                        info.put("question", "0");
                        info.put("Answer","0");
                        info.put("Diary","0");

                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                        database.collection("Users").document(Uid).collection("Date").document(date).set(info);
                    }
                }

                else{
                    Log.d("N_TOKEN","NO");
                }

            }
        });


        tv_todayQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Log.d("TOKEN","YES");
                    for(UserInfo profile : user.getProviderData()){

                        String Uid=profile.getUid();

                        Log.d("TOKEN",Uid);
                        UId=Uid;


                    }
                }

                else{
                    Log.d("N_TOKEN","NO");
                }


                moveToQuestion.putExtra("Uid", UId);
                moveToQuestion.putExtra("Date", date);
                startActivity(moveToQuestion);


            }
        });
        tv_todayAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Log.d("TOKEN","YES");
                    for(UserInfo profile : user.getProviderData()) {

                        String Uid = profile.getUid();

                        Log.d("TOKEN", Uid);
                        UId = Uid;

                    }
                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                        DocumentReference docRef = database.collection("Users").document(UId).collection("Date").document(date);

                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Answer=(String)document.get("Answer");
                                        Question=(String)document.get("question");

                                        moveToAnswer.putExtra("Ans", Answer);
                                        moveToAnswer.putExtra("Que", Question);
                                        startActivity(moveToAnswer);

                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                }

                else{
                    Log.d("N_TOKEN","NO");
                }







            }
        });




    }
}
