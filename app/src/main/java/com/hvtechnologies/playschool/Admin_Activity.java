package com.hvtechnologies.playschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import android.os.Bundle;

public class Admin_Activity extends AppCompatActivity {


    Button Add , Logout , Del ;
    private FirebaseAuth mAuth;
    DatabaseReference PlaySchoolRef  ;

    private ArrayList<PlaySchoolClass> PlaySchoolNames = new ArrayList<>() ;
    private ArrayList<String> PlaySchoolNamesString = new ArrayList<>() ;
    private ArrayAdapter<String> adapterSpinnerPlaySchool ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);



        Utils.getDatabase();


        Add =(Button)findViewById(R.id.AddBtn);
        Del = (Button)findViewById(R.id.DelBtn);
        Logout = (Button)findViewById(R.id.LogOut);
        mAuth = FirebaseAuth.getInstance();

        adapterSpinnerPlaySchool = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , PlaySchoolNamesString);


        PlaySchoolRef = FirebaseDatabase.getInstance().getReference("Users/PlaySchool/") ;
        PlaySchoolRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                PlaySchoolNames.clear();
                PlaySchoolNamesString.clear();
                adapterSpinnerPlaySchool.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    String PUid = dataSnapshot1.getKey();
                    String PName = dataSnapshot1.child("Name").getValue().toString();
                    String PId = dataSnapshot1.child("Id").getValue().toString();
                    String Num = dataSnapshot1.child("Phone").getValue().toString();
                    String Address = dataSnapshot1.child("Address").getValue().toString();
                    String Email = dataSnapshot1.child("Email").getValue().toString();


                    PlaySchoolNamesString.add(PName);
                    PlaySchoolNames.add(new PlaySchoolClass(PName , PId  , PUid  , Address , Email, Num));
                    adapterSpinnerPlaySchool.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Admin_Activity.this , "Sign Out" , Toast.LENGTH_SHORT ).show();
                mAuth.signOut();
                Intent mainIntent = new Intent(Admin_Activity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(Admin_Activity.this, CreatePlaySchoolActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);

            }
        });


        Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Admin_Activity.this)
                        .setCancelable(false);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box_delete_playschool, null);

                final Spinner Sp2 = (Spinner) mView.findViewById(R.id.spinner2);
                final Button canc1 = (Button) mView.findViewById(R.id.button19);
                final Button ok1 = (Button) mView.findViewById(R.id.button20);
                final TextView Info = (TextView) mView.findViewById(R.id.textViewPlaySchoolInfo);

                Sp2.setAdapter(adapterSpinnerPlaySchool);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                Sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        int ps = position;

                        if(ps != -1) {

                            String Id = PlaySchoolNames.get(ps).getId();
                            String Uid = PlaySchoolNames.get(ps).getUid();
                            String Name = PlaySchoolNames.get(ps).getName();
                            String Phone = PlaySchoolNames.get(ps).getPhone();
                            String Address = PlaySchoolNames.get(ps).getAddress();
                            String Email = PlaySchoolNames.get(ps).getEmail();

                            Info.setText("Name : " + Name + "\nPhone : " + Phone + "\nAddress : " + Address + "\nEmail : " + Email);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int ps = Sp2.getSelectedItemPosition();

                        if(ps != -1){

                            String Id = PlaySchoolNames.get(ps).getId();
                            String Uid = PlaySchoolNames.get(ps).getUid();

                            PlaySchoolRef = FirebaseDatabase.getInstance().getReference("PlaySchool/" + Id ) ;
                            PlaySchoolRef.removeValue();
                            PlaySchoolRef = FirebaseDatabase.getInstance().getReference("Users/PlaySchool/" + Uid ) ;
                            PlaySchoolRef.removeValue();
                            adapterSpinnerPlaySchool.notifyDataSetChanged();
                        }
                    }
                });


                canc1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

            }
        });
    }
}
