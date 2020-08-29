package com.hvtechnologies.playschool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class PlaySchoolViewAtt extends AppCompatActivity {

    ListView StudentsList ;
    Spinner SelectDate  ;

    private DatabaseReference PlaySchoolRef , DatesRef ;

    private ArrayList<String> Dateid = new ArrayList<>() ;
    private ArrayList<String> Dateval = new ArrayList<>() ;
    private ArrayList<String> Datekey = new ArrayList<>() ;

    private ArrayAdapter<String> adapterSpinner ;

    private List<SeeAttendanceDateWiseClass> datewiselist ;
    private SeeAttendanceDateWiseListAdapter adap ;
    private FirebaseAuth mAuth;
    String PId , PUid ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_school_view_att);


        //Utils.getDatabase();

        mAuth = FirebaseAuth.getInstance();
        StudentsList = (ListView)findViewById(R.id.AttListView);

        PUid = mAuth.getUid() ;

        //SharedPreferences Pref = getSharedPreferences("UserInfoData", MODE_PRIVATE);
        //PId = Pref.getString("Id", "");



        datewiselist = new ArrayList<>();
        adap = new SeeAttendanceDateWiseListAdapter(getApplicationContext() , datewiselist);
        StudentsList.setAdapter(adap);


        SelectDate = (Spinner) findViewById(R.id.spinnerDates);
        adapterSpinner = new ArrayAdapter<String>(this , R.layout.spinner_drop_items , Dateval);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_item);
        SelectDate.setAdapter(adapterSpinner);





        PlaySchoolRef = FirebaseDatabase.getInstance().getReference("Users/PlaySchool/" + PUid) ;
        PlaySchoolRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                PId = dataSnapshot.child("Id").getValue().toString();
                DatesRef = FirebaseDatabase.getInstance().getReference("Attendance/"+PId+"/Date Wise/") ;
                DatesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                            String date = dataSnapshot1.getKey();

                            if(!date.equals("Exist")){

                                String Date = dataSnapshot1.child("Date").getValue(String.class);

                                Dateval.add(Date);
                                adapterSpinner.notifyDataSetChanged();
                                Dateid.add(date);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        DatesRef = FirebaseDatabase.getInstance().getReference("Attendance/"+PId+"/Date Wise/") ;

        DatesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    String date = dataSnapshot1.getKey();

                    if(!date.equals("Exist")){

                        String Date = dataSnapshot1.child("Date").getValue(String.class);

                        Dateval.add(Date);
                        adapterSpinner.notifyDataSetChanged();
                        Dateid.add(date);


                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SelectDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final String dtval = Dateid.get(position);

                DatesRef = FirebaseDatabase.getInstance().getReference("Attendance/"+PId+"/Date Wise/" + dtval + "/") ;
                DatesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        datewiselist.clear();
                        adap.notifyDataSetChanged();


                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                            String datekey = dataSnapshot1.getKey();
                            if(!datekey.equals("Date")) {

                                String BatchName = dataSnapshot1.child("Batch Name").getValue(String.class);
                                String ClassName = dataSnapshot1.child("Class Name").getValue(String.class);
                                String Date = dataSnapshot1.child("Date").getValue(String.class);
                                String Time = dataSnapshot1.child("Time").getValue(String.class);


                                datewiselist.add(new SeeAttendanceDateWiseClass(ClassName, BatchName, Date, Time , dtval , datekey));
                                adap.notifyDataSetChanged();
                                Datekey.add(datekey);

                            }


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {




            }
        });

        StudentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                String ClassName = datewiselist.get(position).getClassName() ;
                String Date = datewiselist.get(position).getDate() ;
                String Time = datewiselist.get(position).getTime() ;
                String dtval = datewiselist.get(position).getDateVal();
                String dtkey = datewiselist.get(position).getDateKey();


                SharedPreferences sharedPrefs = getSharedPreferences("Attendance_Date_Wise", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPrefs.edit();
                edit.putString("Class_Name", ClassName);
                edit.putString("Date",Date);
                edit.putString("Time",Time);
                edit.putString("Date_Val",dtval);
                edit.putString("Date_Key",dtkey);
                edit.putString("Id",PId);

                edit.apply();

                Intent mainIntent = new Intent(PlaySchoolViewAtt.this, PlaySchoolViewParticularAtt.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);



            }
        });





    }
}
