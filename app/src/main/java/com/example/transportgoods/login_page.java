package com.example.transportgoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class login_page extends AppCompatActivity {

    Button login;
    EditText login_email,login_pass;
    TextView createaccount;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    int usertype=0;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page2);

        login=findViewById(R.id.search_trans);
        login_email=findViewById(R.id.login_email);
        login_pass=findViewById(R.id.login_pass);

        createaccount=findViewById(R.id.createaccount);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
            }
        });

        createaccount.setOnClickListener(view -> {
            startActivity(new Intent(login_page.this, createaccount1.class));

        });
    }
    private void loginUser(){
        String Cemail= login_email.getText().toString();
        String Cpass= login_pass.getText().toString();

        if (TextUtils.isEmpty(Cemail)){
            login_email.setError("Email cannot be empty");
            login_email.requestFocus();
        }else if(TextUtils.isEmpty(Cpass)){
            login_pass.setError("Pass cannot be empty");
            login_pass.requestFocus();
        }else{
            firebaseAuth.signInWithEmailAndPassword(Cemail,Cpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){



                        String userid=task.getResult().getUser().getUid();
                        DocumentReference documentReference=firebaseFirestore.collection("user").document(userid);
                        documentReference.addSnapshotListener(login_page.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                usertype=Integer.parseInt(value.getString("Usertype"));
                                if(usertype==0){
                                    startActivity(new Intent(login_page.this, MainActivity.class));
                                    Toast.makeText(login_page.this, "User logined in successfully", Toast.LENGTH_SHORT).show();

                                }
                                if(usertype==1){
                                    startActivity(new Intent(login_page.this, Transporter_login.class));
                                    Toast.makeText(login_page.this, "Transporter logined in successfully", Toast.LENGTH_SHORT).show();
                                }
//                                startActivity(new Intent(login_page.this, MainActivity.class));


                            }
                        });




                    }else {
                        Toast.makeText(login_page.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}