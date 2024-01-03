package com.example.transportgoods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AccountInfo extends AppCompatActivity {
    ImageView account_info_back;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;//dbroot
    String userID;
    ImageView profilepic;
    public Uri imageURI;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    TextView fetch_fname3,fetch_email,fetch_phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        account_info_back=findViewById(R.id.account_info_back);
        profilepic=findViewById(R.id.profilepic);
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        fetch_fname3=findViewById(R.id.fetch_fname3);
        fetch_email=findViewById(R.id.fetch_email);
        fetch_phoneno=findViewById(R.id.fetch_phoneno);

        DocumentReference ref=firebaseFirestore.collection("user").document(firebaseAuth.getCurrentUser().getUid());
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    fetch_fname3.setText(documentSnapshot.getString("First Name")+" "+documentSnapshot.getString("Last Name"));
                    fetch_email.setText(documentSnapshot.getString("Email"));
                    fetch_phoneno.setText(documentSnapshot.getString("Phone Number"));
                }else {
                    Toast.makeText(AccountInfo.this, "Row not Found", Toast.LENGTH_SHORT).show();

                }            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccountInfo.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference profileRef=storageReference.child("user/"+firebaseAuth.getCurrentUser().getUid()+"/image/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic);
            }
        });

        account_info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountInfo.this,MainActivity.class));
            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
    }

    private void choosePicture() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 &&resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageURI=data.getData();
            //profilepic.setImageURI(imageURI);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pb=new ProgressDialog(this);
        pb.setTitle("Uploading Image...");
        pb.show();

//        final String randomKey= UUID.randomUUID().toString();
        StorageReference riverRef= storageReference.child("user/"+firebaseAuth.getCurrentUser().getUid()+"/image/profile.jpg");

        riverRef.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pb.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded" , Snackbar.LENGTH_LONG).show();
                riverRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilepic);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pb.dismiss();
                Toast.makeText(getApplicationContext(),"Failed to Upload", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercentage=(100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pb.setMessage("Percentage: "+ (int) progressPercentage+"%");
            }
        });
    }

}