package com.example.transportgoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.OnProgressListener;

import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class createaccount1 extends AppCompatActivity {
    public static final String TAG = "TAG";
    Button create;
    EditText firstname,lastname,email,email_pass,phonenumber;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    RadioButton transporterrb,userrb;
    String usertype;




    String Cemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount1);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        transporterrb=findViewById(R.id.transporterrb);
        userrb=findViewById(R.id.userrb);
        email=findViewById(R.id.email);
        email_pass=findViewById(R.id.email_pass);
        phonenumber=findViewById(R.id.phonenumber);
        create=findViewById(R.id.create);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();



        create.setOnClickListener(view -> {
            createAccount1();
        });
    }



    private void createAccount1(){
        Cemail= email.getText().toString();
        String Cpass= email_pass.getText().toString();
        String Cfname= firstname.getText().toString();
        String Clname= lastname.getText().toString();
        String Cphoneno= phonenumber.getText().toString();

        if(transporterrb.isChecked()){
            usertype="1";
        }
        if(userrb.isChecked()){
            usertype="0";
        }

        if (TextUtils.isEmpty(Cfname)){
            firstname.setError("First name cannot be empty");
            firstname.requestFocus();
        }else if (TextUtils.isEmpty(Clname)){
            lastname.setError("Last name cannot be empty");
            lastname.requestFocus();
        }
        else if (TextUtils.isEmpty(Cphoneno)){
            phonenumber.setError("Phone number cannot be empty");
            phonenumber.requestFocus();
        }
        else if (TextUtils.isEmpty(Cemail)){
            email.setError("Email cannot be empty");
            email.requestFocus();
        }else if(TextUtils.isEmpty(Cpass)){
            email_pass.setError("Pass cannot be empty");
            email_pass.requestFocus();
        }else if(!transporterrb.isChecked() && !userrb.isChecked()){
            transporterrb.setError("Check one radiobutton");
            transporterrb.requestFocus();
            userrb.setError("Check one radiobutton");
            userrb.requestFocus();
        }else {
            firebaseAuth.createUserWithEmailAndPassword(Cemail,Cpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(createaccount1.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                        userID=firebaseAuth.getCurrentUser().getUid();
                        DocumentReference documentReference= firebaseFirestore.collection("user").document(userID);
                        Map<String, Object> user= new HashMap<>();
                        user.put("First Name",Cfname);
                        user.put("Last Name", Clname);
                        user.put("Email", Cemail);
                        user.put("Phone Number",Cphoneno);
                        user.put("Usertype",usertype);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: user profile is create for" +userID);

                            }
                        });


                        startActivity(new Intent(createaccount1.this, login_page.class));
                    }else{
                        Toast.makeText(createaccount1.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}