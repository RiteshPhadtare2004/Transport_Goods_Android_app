package com.example.transportgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button logout,submit_user_good_data;
    ImageView account_info;
    FirebaseAuth firebaseAuth;
    Spinner good_pick_up_point,good_drop_point,vehical_of_good;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter_vehical;
    String gpup,gdp,vog,good_of_add_pu,good_of_add_drop,good_of_name,phone;
    EditText acc_address_pu,acc_address_drop_point,good_name,phoneno;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout=findViewById(R.id.logout);
        account_info=findViewById(R.id.account_info);
        firebaseAuth=FirebaseAuth.getInstance();

        submit_user_good_data=findViewById(R.id.submit_user_good_data);
        acc_address_pu=findViewById(R.id.acc_address_pu);
        acc_address_drop_point=findViewById(R.id.acc_address_drop_point);
        good_name=findViewById(R.id.good_name);
        phoneno=findViewById(R.id.phone_no);



        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("User_Data");


        account_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AccountInfo.class));
            }
        });




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent=new Intent(MainActivity.this,login_page.class);
                startActivity(intent);
            }
        });

        good_pick_up_point = (Spinner) findViewById(R.id.good_pick_up_point);
        good_drop_point = (Spinner) findViewById(R.id.good_drop_point);
        vehical_of_good=(Spinner) findViewById(R.id.vehical_of_good);
        adapter = ArrayAdapter.createFromResource(this, R.array.starting_point_and_destination, android.R.layout.simple_spinner_item);
        adapter_vehical=ArrayAdapter.createFromResource(this,R.array.vehical_type, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_vehical.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        good_pick_up_point.setAdapter(adapter);
        good_drop_point.setAdapter(adapter);
        vehical_of_good.setAdapter(adapter_vehical);

        good_pick_up_point.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gpup= parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        good_drop_point.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gdp= parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vehical_of_good.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                vog= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit_user_good_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                good_of_add_pu=acc_address_pu.getText().toString();
                good_of_add_drop=acc_address_drop_point.getText().toString();
                good_of_name=good_name.getText().toString();
                phone=phoneno.getText().toString();
                Toast.makeText(MainActivity.this, "Trasporter will contact you soon", Toast.LENGTH_SHORT).show();

                UserHelperClass UhelperClass=new UserHelperClass(gpup,gdp,vog,good_of_add_pu,good_of_add_drop,good_of_name,phone);
                reference.child(gpup).child(gdp).setValue(UhelperClass);

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