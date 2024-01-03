package com.example.transportgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Transporter_login extends AppCompatActivity {

    Button logout,search_trans;
    ImageView account_info;
    FirebaseAuth firebaseAuth;
    Spinner starting_point,destination,vehical_type;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter_vehical;
    String start_point,dest,vehi;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_login);
        firebaseAuth=FirebaseAuth.getInstance();
        logout=findViewById(R.id.logout);
        account_info=findViewById(R.id.account_info);
        starting_point=findViewById(R.id.good_pick_up_point);
        destination=findViewById(R.id.vehical_of_good);
        vehical_type=findViewById(R.id.vehical_type);
        adapter=ArrayAdapter.createFromResource(this,R.array.starting_point_and_destination, android.R.layout.simple_spinner_dropdown_item);
        adapter_vehical=ArrayAdapter.createFromResource(this,R.array.vehical_type, android.R.layout.simple_spinner_dropdown_item);
        starting_point.setAdapter(adapter);
        destination.setAdapter(adapter);
        vehical_type.setAdapter(adapter_vehical);
        search_trans=findViewById(R.id.search_trans);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Transporter_Data");

        starting_point.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start_point=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dest=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        vehical_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehi=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        search_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Transporter_login.this, "Staring Point:"+start_point+", Destination:"+dest+", Vehical Type:"+vehi, Toast.LENGTH_SHORT).show();

                TransporterHelperClass ThelperClass=new TransporterHelperClass(start_point,dest,vehi);
                reference.child(firebaseAuth.getCurrentUser().getUid()).setValue(ThelperClass);

                Intent su=new Intent(Transporter_login.this,Search_User.class);
                su.putExtra("SP",start_point);
                su.putExtra("D",dest);
                su.putExtra("V",vehi);
                startActivity(su);

            }
        });


        firebaseAuth=FirebaseAuth.getInstance();

        account_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Transporter_login.this,TransporterAccountInfo.class));
            }
        });




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent=new Intent(Transporter_login.this,login_page.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user=firebaseAuth.getCurrentUser();
//        if(user==null){
//            startActivity(new Intent(MainActivity.this, LoginPage.class));
//        }
//    }
}