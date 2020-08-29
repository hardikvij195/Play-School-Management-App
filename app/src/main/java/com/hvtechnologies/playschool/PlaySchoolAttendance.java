package com.hvtechnologies.playschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlaySchoolAttendance extends AppCompatActivity {


    Button TakeAtt , ViewAtt ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_school_attendance);


        TakeAtt = (Button)findViewById(R.id.TakeAttBtn);
        ViewAtt = (Button)findViewById(R.id.ViewAttBtn);

        TakeAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent mainIntent = new Intent(PlaySchoolAttendance.this, PlaySchoolTakeAttendance.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


        ViewAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent mainIntent = new Intent(PlaySchoolAttendance.this, PlaySchoolViewAtt.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


    }
}
