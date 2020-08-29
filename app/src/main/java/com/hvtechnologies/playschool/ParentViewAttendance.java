package com.hvtechnologies.playschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
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
import java.util.List;


public class ParentViewAttendance extends AppCompatActivity {


    TextView NameOfStud ;
    ListView Att;

    private DatabaseReference ClassRef ;

    private List<SeeAttendanceStudentWiseClass> StudentAttList;
    private SeeAttendanceStudentWiseListAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view_attendance);

        StudentAttList = new ArrayList<>();
        Att = (ListView)findViewById(R.id.AttListView);
        adapter = new SeeAttendanceStudentWiseListAdapter(getApplicationContext() , StudentAttList);
        Att.setAdapter(adapter);

        SharedPreferences Pref = getSharedPreferences("UserInfoData", MODE_PRIVATE);
        String Name = Pref.getString("NameData", "");
        String Cls = Pref.getString("ClassData", "");
        String TutId = Pref.getString("TutId", "");
        String StdId = Pref.getString("UserId", "");


        NameOfStud = (TextView)findViewById(R.id.textView41);


        ClassRef = FirebaseDatabase.getInstance().getReference("Attendance/"+ TutId +"/Student Wise/" + Cls +"/"+ StdId + "/") ;
        ClassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                StudentAttList.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    String dt = dataSnapshot1.getKey();
                    if(!dt.equals("Name") && !dt.equals("Userid") && !dt.equals("Roll Number")){

                        for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){

                            String Att = dataSnapshot2.child("Att").getValue(String.class);
                            String Batch = dataSnapshot2.child("Batch").getValue(String.class);
                            String Date = dataSnapshot2.child("Date").getValue(String.class);
                            String Time = dataSnapshot2.child("Time").getValue(String.class);

                            StudentAttList.add(new SeeAttendanceStudentWiseClass( Batch , Att , Date ,Time));
                            adapter.notifyDataSetChanged();

                        }


                    }

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
