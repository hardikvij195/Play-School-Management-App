package com.hvtechnologies.playschool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.HashMap;

public class PlaySchool extends AppCompatActivity {


    Button LogOut , AddP , DelP , AddC , DelC ,  Att ;
    private FirebaseAuth mAuth;
    DatabaseReference ParentRef , ClassRef , PlaySchoolRef;

    private ArrayList<NameIdClass> ParentNames = new ArrayList<>() ;
    private ArrayList<String> ParentNamesString = new ArrayList<>() ;

    private ArrayList<String> ClassNamesString = new ArrayList<>() ;
    private ArrayList<String> StudentNamesString = new ArrayList<>() ;
    private ArrayList<StudentInfoClass> StudentNames = new ArrayList<>() ;

    private ArrayAdapter<String> adapterSpinnerClass ;

    private ArrayAdapter<String> adapterSpinnerStudentNames ;

    private ArrayList<PlaySchoolClass> PlaySchoolInfo = new ArrayList<>() ;

    String PUid  , PId , PName , PAddress , PPhone , PEmail  ;
    TextView txt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_school);


        mAuth = FirebaseAuth.getInstance();
        txt = (TextView)findViewById(R.id.textView);
        LogOut = (Button)findViewById(R.id.LogOut);
        AddP = (Button)findViewById(R.id.AddBtn);
        AddC = (Button)findViewById(R.id.AddClassBtn);
        DelC = (Button)findViewById(R.id.DelClassBtn);
        DelP = (Button)findViewById(R.id.DelBtn);
        Att = (Button)findViewById(R.id.AttendanceBtn);

        PUid = mAuth.getCurrentUser().getUid();

        adapterSpinnerClass = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , ClassNamesString);
        adapterSpinnerStudentNames = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , StudentNamesString);


        PlaySchoolRef = FirebaseDatabase.getInstance().getReference("Users/PlaySchool/" + PUid) ;
        PlaySchoolRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                PName = dataSnapshot.child("Name").getValue().toString();
                PId = dataSnapshot.child("Id").getValue().toString();
                PPhone = dataSnapshot.child("Phone").getValue().toString();
                PAddress = dataSnapshot.child("Address").getValue().toString();
                PEmail = dataSnapshot.child("Email").getValue().toString();
                txt.setText("Play School \n\nName : " + PName + "\nEmail : " + PEmail + "\nNumber : " + PPhone + "\nAddress : " + PAddress );

                SharedPreferences sharedPrefs = getSharedPreferences("UserInfoData" , Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPrefs.edit();
                edit.putString("Id" , PId );
                edit.apply();




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


        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(PlaySchool.this , "Sign Out" , Toast.LENGTH_SHORT ).show();
                mAuth.signOut();
                Intent mainIntent = new Intent(PlaySchool.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


        AddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(PlaySchool.this, AddParent.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });



        AddC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaySchool.this)
                        .setCancelable(false);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box_add_class_teacher, null);
                final EditText NameOfClass = (EditText) mView.findViewById(R.id.NameTxtType);
                final Button canc = (Button)mView.findViewById(R.id.button19);
                final Button ok = (Button)mView.findViewById(R.id.button20);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                canc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if( !NameOfClass.getText().toString().isEmpty()){

                            ClassRef = FirebaseDatabase.getInstance().getReference("PlaySchool/" + PId + "/Classes/" + NameOfClass.getText().toString().trim().toUpperCase() ) ;
                            HashMap<String,String> dataMap = new HashMap<String, String>();
                            dataMap.put("Exist" , "True");
                            ClassRef.setValue(dataMap);
                            Toast.makeText( PlaySchool.this , "Class : " + NameOfClass.getText().toString() + " Created" , Toast.LENGTH_SHORT).show();
                            NameOfClass.setText("");

                        }else{

                            Toast.makeText( PlaySchool.this , "No Class Added" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        DelC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaySchool.this)
                        .setCancelable(false);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box_delete_class, null);
                final Spinner Sp2 = (Spinner) mView.findViewById(R.id.spinner2);
                final Button canc1 = (Button) mView.findViewById(R.id.button19);
                final Button ok1 = (Button) mView.findViewById(R.id.button20);

                Sp2.setAdapter(adapterSpinnerClass);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();



                ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int ps = Sp2.getSelectedItemPosition();

                        if(ps != -1){

                            String Name = ClassNamesString.get(ps);
                            ClassRef = FirebaseDatabase.getInstance().getReference("PlaySchool/" + PId + "/Classes/" + Name  ) ;
                            ClassRef.removeValue();
                            adapterSpinnerClass.notifyDataSetChanged();

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


        DelP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaySchool.this)
                        .setCancelable(false);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box_delete_parent, null);
                final Spinner SpCls = (Spinner) mView.findViewById(R.id.spinnerClass);
                final Spinner SpStuds = (Spinner) mView.findViewById(R.id.spinnerStudentNames);
                final Button canc1 = (Button) mView.findViewById(R.id.button19);
                final Button ok1 = (Button) mView.findViewById(R.id.button20);
                final TextView StdInfo = (TextView) mView.findViewById(R.id.textView2);

                SpCls.setAdapter(adapterSpinnerClass);
                SpStuds.setAdapter(adapterSpinnerStudentNames);


                SpCls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String ClName = ClassNamesString.get(position);

                        ParentRef = FirebaseDatabase.getInstance().getReference("PlaySchool/" + PId + "/Students/" + ClName + "/") ;
                        ParentRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                ParentNames.clear();
                                ParentNamesString.clear();
                                StudentNamesString.clear();
                                StudentNames.clear();
                                StdInfo.setText("");
                                adapterSpinnerStudentNames.notifyDataSetChanged();

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                                    String StdId = dataSnapshot1.getKey();
                                    String StdName = dataSnapshot1.child("StudName").getValue().toString();
                                    String StdUid = dataSnapshot1.child("Uid").getValue().toString();
                                    String ParentName = dataSnapshot1.child("Name").getValue().toString();
                                    String Num = dataSnapshot1.child("Phone").getValue().toString();

                                    StdInfo.setText("Student Name : " + StdName + "\nParent Name : " + ParentName + "\nNumber : " + Num );

                                    ParentNamesString.add(ParentName);
                                    StudentNamesString.add(StdName);
                                    StudentNames.add(new StudentInfoClass( StdName , ParentName, Num  , StdId , StdUid ));
                                    adapterSpinnerStudentNames.notifyDataSetChanged();
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

                SpStuds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        String StdName = StudentNames.get(position).getStdName();
                        String ParentName = StudentNames.get(position).getPName();
                        String Num = StudentNames.get(position).getNum();


                        StdInfo.setText("Student Name : " + StdName + "\nParent Name : " + ParentName + "\nNumber : " + Num );




                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        int psClass = SpCls.getSelectedItemPosition();
                        int psStuds = SpStuds.getSelectedItemPosition();

                        if(!ClassNamesString.isEmpty() && !StudentNamesString.isEmpty()  ){

                            String CName = ClassNamesString.get(psClass);
                            String SId = StudentNames.get(psStuds).getStdId();
                            String SUid = StudentNames.get(psStuds).getSUid();
                            ParentRef = FirebaseDatabase.getInstance().getReference("Users/Parents/" + SUid ) ;
                            ParentRef.removeValue();
                            ParentRef = FirebaseDatabase.getInstance().getReference("PlaySchool/" + PId + "/Students/"+ CName + "/" + SId ) ;
                            ParentRef.removeValue();
                            adapterSpinnerStudentNames.notifyDataSetChanged();

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



        Att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(PlaySchool.this, PlaySchoolAttendance.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });




    }
}
