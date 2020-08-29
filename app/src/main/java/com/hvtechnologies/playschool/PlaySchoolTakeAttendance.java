package com.hvtechnologies.playschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;





public class PlaySchoolTakeAttendance extends AppCompatActivity {

    Button SubmitAttendance ;
    ListView StudentsList ;
    Spinner ClassNames  ;


    private DatabaseReference PlaySchoolRef , ClassRef , StudentRef ;
    private FirebaseAuth mAuth;

    private AttendanceListAdapter adapter ;
    private List<AttendanceClass> mAttendanceList ;
    private ProgressDialog mProgress;
    String PUid  , PId  ;

    private ArrayList<String> ClassNamesString = new ArrayList<>() ;
    private ArrayAdapter<String> adapterSpinnerClass ;

    private FirebaseDatabase database ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_school_take_attendance);




        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        SubmitAttendance = (Button)findViewById(R.id.button2);
        StudentsList = (ListView)findViewById(R.id.AttListView);
        ClassNames = (Spinner)findViewById(R.id.spinnerDates);


        PUid = mAuth.getCurrentUser().getUid();

        adapterSpinnerClass = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , ClassNamesString);
        ClassNames.setAdapter(adapterSpinnerClass);


        mAttendanceList = new ArrayList<>();
        adapter = new AttendanceListAdapter(getApplicationContext(), mAttendanceList);
        StudentsList.setAdapter(adapter);


        PlaySchoolRef = FirebaseDatabase.getInstance().getReference("Users/PlaySchool/" + PUid) ;
        PlaySchoolRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                PId = dataSnapshot.child("Id").getValue().toString();

                ClassRef = FirebaseDatabase.getInstance().getReference("PlaySchool/" + PId + "/Classes/") ;
                ClassRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ClassNamesString.clear();
                        adapterSpinnerClass.notifyDataSetChanged();

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                            String name = dataSnapshot1.getKey();
                            ClassNamesString.add(name);
                            adapterSpinnerClass.notifyDataSetChanged();

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


        ClassRef = FirebaseDatabase.getInstance().getReference("PlaySchool/" + PId + "/Classes/") ;
        ClassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ClassNamesString.clear();
                adapterSpinnerClass.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    String name = dataSnapshot1.getKey();
                    ClassNamesString.add(name);
                    adapterSpinnerClass.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ClassNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String ClName = ClassNamesString.get(position);

                StudentRef = FirebaseDatabase.getInstance().getReference("PlaySchool/" + PId + "/Students/" + ClName + "/") ;
                StudentRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mAttendanceList.clear();
                        adapter.notifyDataSetChanged();

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){


                            String StdId = dataSnapshot1.getKey();
                            String StdName = dataSnapshot1.child("StudName").getValue().toString();
                            String StdUid = dataSnapshot1.child("Uid").getValue().toString();
                            String Num = dataSnapshot1.child("Phone").getValue().toString();

                            mAttendanceList.add(new AttendanceClass(StdName, "Present", StdUid, StdId, Num));
                            adapter.notifyDataSetChanged();
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

                String Att = mAttendanceList.get(position).getAbsentOrPresent();
                if (Att.equals("Present")) {

                    mAttendanceList.get(position).setAbsentOrPresent("Absent");
                    adapter.notifyDataSetChanged();

                } else {

                    mAttendanceList.get(position).setAbsentOrPresent("Present");
                    adapter.notifyDataSetChanged();

                }

            }
        });




        SubmitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaySchoolTakeAttendance.this)
                        .setCancelable(false);
                mBuilder.setTitle("Confirm");
                mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();

                        if (!ClassNames.getSelectedItem().toString().isEmpty() && !mAttendanceList.isEmpty()) {


                            Date date = new Date();  // to get the date
                            SimpleDateFormat du = new SimpleDateFormat("dd-MM-yyyy"); // getting date in this format
                            final String formattedDateRealDate = du.format(date.getTime());

                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd"); // getting date in this format
                            final String formattedDate = df.format(date.getTime());
                            SimpleDateFormat dt = new SimpleDateFormat("HHmmss"); // getting date in this format
                            final String formattedDate1 = dt.format(date.getTime());
                            SimpleDateFormat dtd = new SimpleDateFormat("HH:mm"); // getting date in this format
                            final String formattedDateRealTime = "Time : " + dtd.format(date.getTime());


                            final String dateupload1 = "-" + formattedDate;

                            final String dateupload3 = "-" + formattedDate1;

                            SharedPreferences sharedPrefs = getSharedPreferences("NoticeInfoData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPrefs.edit();
                            edit.putString("dateupload1", dateupload1);
                            edit.putString("dateupload3", dateupload3);
                            edit.putString("datereal", formattedDateRealDate);
                            edit.apply();


                            DatabaseReference SeeDate1 = database.getReference("Attendance/" + PId + "/Date Wise/");

                            SeeDate1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.child(dateupload1).exists()) {

                                        String classname = ClassNames.getSelectedItem().toString();
                                        DatabaseReference Attendence = database.getReference("Attendance/" + PId + "/Date Wise/" + dateupload1 + "/" + dateupload3 + "/");
                                        HashMap<String, String> StudentAttendenceMap = new HashMap<String, String>();
                                        StudentAttendenceMap.put("Class Name", classname);
                                        StudentAttendenceMap.put("Date", formattedDateRealDate);
                                        StudentAttendenceMap.put("Time", formattedDateRealTime);
                                        Attendence.setValue(StudentAttendenceMap);

                                        for (int i = 0; i < mAttendanceList.size(); i++) {

                                            String Name = mAttendanceList.get(i).getNameOfStudent();
                                            String Userid = mAttendanceList.get(i).getStudentUserId();
                                            String AbsentOrPresent = mAttendanceList.get(i).getAbsentOrPresent();
                                            String Roll = mAttendanceList.get(i).getStudentId();


                                            DatabaseReference Attendence2 = database.getReference("Attendance/" + PId + "/Date Wise/" + dateupload1 + "/" + dateupload3 + "/" + "Att" + "/" + Userid + "/");
                                            HashMap<String, String> StudentAttendenceMap2 = new HashMap<String, String>();
                                            StudentAttendenceMap2.put("Name", Name);
                                            StudentAttendenceMap2.put("Userid", Userid);
                                            StudentAttendenceMap2.put("Att", AbsentOrPresent);
                                            StudentAttendenceMap2.put("Roll Number", Roll);
                                            Attendence2.setValue(StudentAttendenceMap2);


                                        }

                                    } else {

                                        String classname = ClassNames.getSelectedItem().toString();
                                        SharedPreferences Pref = getSharedPreferences("userinfo", MODE_PRIVATE);
                                        String Id = Pref.getString("Id", "");
                                        DatabaseReference AddDate = database.getReference("Attendance/" + PId + "/Date Wise/" + dateupload1);

                                        HashMap<String, String> StudentAttendenceAddDate = new HashMap<String, String>();
                                        StudentAttendenceAddDate.put("Date", formattedDateRealDate);
                                        AddDate.setValue(StudentAttendenceAddDate);

                                        DatabaseReference Attendence = database.getReference("Attendance/" + PId + "/Date Wise/" + dateupload1 + "/" + dateupload3 + "/");

                                        HashMap<String, String> StudentAttendenceMap = new HashMap<String, String>();
                                        StudentAttendenceMap.put("Class Name", classname);
                                        StudentAttendenceMap.put("Date", formattedDateRealDate);
                                        StudentAttendenceMap.put("Time", formattedDateRealTime);
                                        Attendence.setValue(StudentAttendenceMap);


                                        for (int i = 0; i < mAttendanceList.size(); i++) {


                                            String Name = mAttendanceList.get(i).getNameOfStudent();
                                            String Userid = mAttendanceList.get(i).getStudentUserId();
                                            String AbsentOrPresent = mAttendanceList.get(i).getAbsentOrPresent();
                                            String Roll = mAttendanceList.get(i).getStudentId();

                                            DatabaseReference Attendence2 = database.getReference("Attendance/" + PId + "/Date Wise/" + dateupload1 + "/" + dateupload3 + "/" + "Att" + "/" + Userid + "/");
                                            HashMap<String, String> StudentAttendenceMap2 = new HashMap<String, String>();
                                            StudentAttendenceMap2.put("Name", Name);
                                            StudentAttendenceMap2.put("Userid", Userid);
                                            StudentAttendenceMap2.put("Att", AbsentOrPresent);
                                            StudentAttendenceMap2.put("Roll Number", Roll);

                                            Attendence2.setValue(StudentAttendenceMap2);

                                        }


                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {


                                }

                            });

                            // Send Messages Here --------------------------------------


                            final String classname = ClassNames.getSelectedItem().toString();

                            for (int i = 0; i < mAttendanceList.size(); i++) {

                                final String Name = mAttendanceList.get(i).getNameOfStudent();
                                final String Userid = mAttendanceList.get(i).getStudentUserId();
                                final String AbsentOrPresent = mAttendanceList.get(i).getAbsentOrPresent();
                                final String Roll = mAttendanceList.get(i).getStudentId();

                                DatabaseReference checkuserid = database.getReference("Attendance/" + PId + "/Student Wise/" + classname + "/");

                                checkuserid.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.child(Userid).exists()) {

                                            DatabaseReference checkdate = database.getReference("Attendance/" + PId + "/Student Wise/" + classname + "/" + Userid + "/");
                                            checkdate.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                                        String keya = dataSnapshot1.getKey();
                                                        if (!keya.equals("Name") && !keya.equals("Userid")) {

                                                            if (keya.equals(dateupload1)) {


                                                                DatabaseReference AddAttendence7 = database.getReference("Attendance/" + PId + "/Student Wise/" + classname + "/" + Userid + "/" + dateupload1 + "/" + dateupload3);

                                                                HashMap<String, String> StudentAttendenceMap = new HashMap<String, String>();
                                                                StudentAttendenceMap.put("Att", AbsentOrPresent);

                                                                StudentAttendenceMap.put("Time", formattedDateRealTime);
                                                                StudentAttendenceMap.put("Date", formattedDateRealDate);
                                                                AddAttendence7.setValue(StudentAttendenceMap);

                                                                //Toast.makeText(Take_Attendance_Activity_Teacher_Activity.this, "Date Exist", Toast.LENGTH_SHORT).show();


                                                            } else {


                                                                DatabaseReference AddAttendence6 = database.getReference("Attendance/" + PId + "/Student Wise/" + classname + "/" + Userid + "/" + dateupload1 + "/" + dateupload3);

                                                                HashMap<String, String> StudentAttendenceMap = new HashMap<String, String>();
                                                                StudentAttendenceMap.put("Att", AbsentOrPresent);

                                                                StudentAttendenceMap.put("Time", formattedDateRealTime);
                                                                StudentAttendenceMap.put("Date", formattedDateRealDate);
                                                                AddAttendence6.setValue(StudentAttendenceMap);

                                                                //Toast.makeText(Take_Attendance_Activity_Teacher_Activity.this, "Date Does Not Exist", Toast.LENGTH_SHORT).show();


                                                            }


                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {


                                                }

                                            });


                                            //Toast.makeText(Take_Attendance_Activity_Teacher_Activity.this, "User id exist", Toast.LENGTH_SHORT).show();


                                        } else {

                                            DatabaseReference Attendence2 = database.getReference("Attendance/" + PId + "/Student Wise/" + classname + "/" + Userid + "/");

                                            HashMap<String, String> StudentAttendenceMap2 = new HashMap<String, String>();
                                            StudentAttendenceMap2.put("Name", Name);
                                            StudentAttendenceMap2.put("Userid", Userid);
                                            StudentAttendenceMap2.put("Roll Number", Roll);
                                            Attendence2.setValue(StudentAttendenceMap2);


                                            DatabaseReference AddAttendence4 = database.getReference("Attendance/" + PId + "/Student Wise/" + classname + "/" + Userid + "/" + dateupload1 + "/" + dateupload3);

                                            HashMap<String, String> StudentAttendenceMap = new HashMap<String, String>();
                                            StudentAttendenceMap.put("Att", AbsentOrPresent);

                                            StudentAttendenceMap.put("Time", formattedDateRealTime);
                                            StudentAttendenceMap.put("Date", formattedDateRealDate);
                                            AddAttendence4.setValue(StudentAttendenceMap);

                                            //Toast.makeText(Take_Attendance_Activity_Teacher_Activity.this, "User id does not exist", Toast.LENGTH_SHORT).show();


                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {


                                    }
                                });
                            }

                            Toast.makeText(PlaySchoolTakeAttendance.this, "Attendance has been marked", Toast.LENGTH_SHORT).show();



                        }
                    }
                });
                mBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();






            }
        });








    }


}
