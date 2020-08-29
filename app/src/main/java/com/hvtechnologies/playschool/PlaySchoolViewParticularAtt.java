package com.hvtechnologies.playschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlaySchoolViewParticularAtt extends AppCompatActivity {

    private FirebaseDatabase database;
    TextView classname , date , time ;
    ListView StudentsNameAttendenceDateWise ;

    private List<SeeAttendanceOfParticularClass> StudentAttList;
    private SeeAttendanceOfParticularClassAdapter adapter ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_school_view_particular_att);


        StudentAttList = new ArrayList<>();
        StudentsNameAttendenceDateWise = (ListView)findViewById(R.id.ListViewOfStudentsSeeAttDateWiseTeacherActivity);
        adapter = new SeeAttendanceOfParticularClassAdapter(getApplicationContext() , StudentAttList);
        StudentsNameAttendenceDateWise.setAdapter(adapter);


        classname = (TextView)findViewById(R.id.ClassNameSeeAttDateWiseTeacherActivity);
        date = (TextView)findViewById(R.id.DateSeeAttDateWiseTeacherActivity);
        time = (TextView)findViewById(R.id.TimeSeeAttDateWiseTeacherActivity);

        SharedPreferences Pref = getSharedPreferences("Attendance_Date_Wise", MODE_PRIVATE);
        String ClassName = Pref.getString("Class_Name", "");
        String Date = Pref.getString("Date","");
        String Time = Pref.getString("Time","");
        String DateVal1 = Pref.getString("Date_Val","");
        String DateVal2 = Pref.getString("Date_Key","");
        String PId = Pref.getString("Id","");



        classname.setText(ClassName);
        date.setText(Date);
        time.setText(Time);


        database = FirebaseDatabase.getInstance();
        DatabaseReference Data = database.getReference("Attendance/"+PId+"/Date Wise/" + DateVal1 + "/" + DateVal2 + "/" + "Att" + "/");

        Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    String name = dataSnapshot1.child("Name").getValue(String.class);
                    String marked = dataSnapshot1.child("Att").getValue(String.class);
                    StudentAttList.add(new SeeAttendanceOfParticularClass( name , marked));
                    adapter.notifyDataSetChanged();

                }

                Collections.sort(StudentAttList, new Comparator<SeeAttendanceOfParticularClass>() {
                    @Override
                    public int compare(SeeAttendanceOfParticularClass o1, SeeAttendanceOfParticularClass o2) {

                        return o1.getStudentName().compareToIgnoreCase(o2.getStudentName());

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {




            }
        });






    }
}
