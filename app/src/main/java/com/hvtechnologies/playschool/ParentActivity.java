package com.hvtechnologies.playschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentActivity extends AppCompatActivity {



    Button ViewAtt , Logout ;
    private FirebaseAuth mAuth;
    DatabaseReference ParentInfo  ;
    TextView Info ;
    String Uid ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        Utils.getDatabase();

        Info = (TextView)findViewById(R.id.ParentInfo);
        ViewAtt =(Button)findViewById(R.id.ViewAtt);
        Logout = (Button)findViewById(R.id.LogOut);
        mAuth = FirebaseAuth.getInstance();

        Uid = mAuth.getCurrentUser().getUid();

        ParentInfo = FirebaseDatabase.getInstance().getReference("Users/Parents/" + Uid + "/") ;
        ParentInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String StdName = dataSnapshot.child("StudName").getValue().toString();
                String ParentName = dataSnapshot.child("Name").getValue().toString();
                String Address = dataSnapshot.child("Address").getValue().toString();
                String Class = dataSnapshot.child("Class").getValue().toString();
                String Email = dataSnapshot.child("Email").getValue().toString();
                String Phone = dataSnapshot.child("Phone").getValue().toString();
                String PlaySchId = dataSnapshot.child("PlaySchoolId").getValue().toString();
                String Id = dataSnapshot.child("Id").getValue().toString();

                Info.setText("Student Name : " + StdName + "\nParent Name : " + ParentName + "\nNumber : " +
                        Phone + "\nAddress : " + Address + "\nEmail : " + Email + "\nClass : " + Class  );


                SharedPreferences sharedPrefs = getSharedPreferences("UserInfoData" , Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPrefs.edit();
                edit.putString("NameData" , StdName );
                edit.putString("EmailData" , Email );
                edit.putString("PhoneNumberData" , Phone);
                edit.putString("ClassData",Class);
                edit.putString("UserId",Uid);
                edit.putString("Roll Number",Id);
                edit.putString("TutId",PlaySchId);
                edit.apply();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ParentActivity.this , "Sign Out" , Toast.LENGTH_SHORT ).show();
                mAuth.signOut();
                Intent mainIntent = new Intent(ParentActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


        ViewAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(ParentActivity.this, ParentViewAttendance.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });






    }
}
