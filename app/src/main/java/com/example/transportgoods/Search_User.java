package com.example.transportgoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Search_User extends AppCompatActivity {
    RecyclerView recycler;
    FirebaseAuth firebaseAuth;
    Button logout;
    ImageView account_info;
    MyRecycleAdapter adapter;
    List<UserHelperClass> mList = new ArrayList<>();
    DatabaseReference mMembersRef,ref;
    public String Start_point,destination,vehical_type;
    TextView t1,t2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        recycler=findViewById(R.id.recycler);



        logout=findViewById(R.id.logout);
        account_info=findViewById(R.id.account_info);
        Start_point=getIntent().getStringExtra("SP");
        destination=getIntent().getStringExtra("D");
        vehical_type=getIntent().getStringExtra("V");
        firebaseAuth=FirebaseAuth.getInstance();

        account_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Search_User.this,AccountInfo.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent=new Intent(Search_User.this,login_page.class);
                startActivity(intent);
            }
        });

        mMembersRef = FirebaseDatabase.getInstance().getReference().child("User_Data").child(Start_point);
        //ref=FirebaseDatabase.getInstance().getReference("Transporter_Data");

//        mMembersRef.child(Start_point).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@androidx.annotation.NonNull Task<DataSnapshot> task) {
//
//                        DataSnapshot dataSnapshot=task.getResult();
//                        pickup_point=String.valueOf(dataSnapshot.child("pick_Up_Point_of_Good").getValue());
//                        vehi=String.valueOf(dataSnapshot.child("vehical_Good_can_be_travelled").getValue());
//
//
//            }
//        });
//        mMembersRef.child(Start_point).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
//            @Override
//            public void onSuccess(DataSnapshot dataSnapshot) {
//                pickup_point=String.valueOf(dataSnapshot.child("pick_Up_Point_of_Good").getValue());
//              vehi=String.valueOf(dataSnapshot.child("vehical_Good_can_be_travelled").getValue());
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@androidx.annotation.NonNull Exception e) {
//                Toast.makeText(Search_User.this, "Data Not Available", Toast.LENGTH_SHORT).show();
//            }
//        });


        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecycleAdapter(mList,this);

        mMembersRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserHelperClass pojo = dataSnapshot.getValue(UserHelperClass.class);
                adapter.notifyDataSetChanged();
//                if (pojo.getAge()>=18&& pojo.getGender().equals("male")){
//                    mList.add(pojo);
//                }


                    if(pojo.Pick_Up_Point_of_Good.equals(Start_point)&& pojo.vehical_Good_can_be_travelled.equals(vehical_type)){
                        mList.add(pojo);

                        Toast.makeText(Search_User.this, "List Added", Toast.LENGTH_SHORT).show();
                    }
                //mList.add(pojo);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter.notifyDataSetChanged();
        recycler.setAdapter(adapter);



    }
}