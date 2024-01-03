package com.example.transportgoods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class TransporterAccountInfo extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    ImageView profilepic;
    public Uri imageURI;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    EditText fileselect;
    Button upload;
    TextView fetch_fname3,fetch_email,fetch_phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_account_info);

        fetch_fname3=findViewById(R.id.fetch_fname3);
        fetch_email=findViewById(R.id.fetch_email);
        fetch_phoneno=findViewById(R.id.fetch_phoneno);
        profilepic=findViewById(R.id.profilepic);
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Upload PDF");
        firebaseAuth=FirebaseAuth.getInstance();
        fileselect=findViewById(R.id.fileselect);
        upload=findViewById(R.id.upload);

        upload.setEnabled(false);

        StorageReference profileRef=storageReference.child("user/"+firebaseAuth.getCurrentUser().getUid()+"/image/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic);
            }
        });

        DocumentReference ref=firebaseFirestore.collection("user").document(firebaseAuth.getCurrentUser().getUid());
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    fetch_fname3.setText(documentSnapshot.getString("First Name")+" "+documentSnapshot.getString("Last Name"));
                    fetch_email.setText(documentSnapshot.getString("Email"));
                    fetch_phoneno.setText(documentSnapshot.getString("Phone Number"));
                }else {
                    Toast.makeText(TransporterAccountInfo.this, "Row not Found", Toast.LENGTH_SHORT).show();

                }            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TransporterAccountInfo.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });



        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        fileselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });
    }

    private void selectPDF() {
    Intent pdfpickerIntent=new Intent();
    pdfpickerIntent.setType("application/pdf");
    pdfpickerIntent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(pdfpickerIntent,"PDF FILE SELECT"),12);

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

        if(requestCode==12 &&resultCode==RESULT_OK && data!=null && data.getData()!=null){
            upload.setEnabled(true);
            fileselect.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") +1));

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDFFileFirebase(data.getData());
                }
            });
        }

    }

    private void uploadPDFFileFirebase(Uri data) {
        final ProgressDialog pdfPD=new ProgressDialog(this);
        pdfPD.setTitle("File is loding...");
        pdfPD.show();

        StorageReference pdfreference=storageReference.child("user/"+firebaseAuth.getCurrentUser().getUid()+"/Licence"+"/uploadPDF"+System.currentTimeMillis()+".pdf");

        pdfreference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri=uriTask.getResult();

                putPDF putPDF=new putPDF(fileselect.getText().toString(),uri.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);

                Toast.makeText(TransporterAccountInfo.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                pdfPD.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                pdfPD.setMessage("File Uploaded.."+(int)progress+"%");

            }
        });
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