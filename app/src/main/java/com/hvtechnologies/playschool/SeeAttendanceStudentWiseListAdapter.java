package com.hvtechnologies.playschool;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SeeAttendanceStudentWiseListAdapter extends BaseAdapter {

    private Context mcontext4 ;
    private List<SeeAttendanceStudentWiseClass> mAttendenceStudentWiseList ;

    public SeeAttendanceStudentWiseListAdapter(Context mcontext4, List<SeeAttendanceStudentWiseClass> mAttendenceStudentWiseList) {
        this.mcontext4 = mcontext4;
        this.mAttendenceStudentWiseList = mAttendenceStudentWiseList;
    }


    @Override
    public int getCount() {
        return mAttendenceStudentWiseList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return mAttendenceStudentWiseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mcontext4 , R.layout.see_attendance_student_wise_list_view_adapter , null);
        TextView BatchName = (TextView)v.findViewById(R.id.batchnametxtview);
        TextView Date = (TextView)v.findViewById(R.id.datetxtview);
        TextView Att = (TextView)v.findViewById(R.id.attendencetxtview);
        TextView Time = (TextView)v.findViewById(R.id.timetxtview);

        BatchName.setText(mAttendenceStudentWiseList.get(position).getBatchName());
        Date.setText(mAttendenceStudentWiseList.get(position).getDateIssued());
        Att.setText(mAttendenceStudentWiseList.get(position).getAbsentOrPresent());
        Time.setText(mAttendenceStudentWiseList.get(position).getTime());

        v.setTag(mAttendenceStudentWiseList.get(position).getBatchName());
        return v;


    }
}

