package com.hvtechnologies.playschool;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.List;

public class AttendanceListAdapter extends  BaseAdapter{

    private Context mcontext ;
    private List<AttendanceClass> mAttendenceList ;

    public AttendanceListAdapter(Context mcontext, List<AttendanceClass> mAttendenceList) {
        this.mcontext = mcontext;
        this.mAttendenceList = mAttendenceList;
    }


    @Override
    public int getCount() {
        return mAttendenceList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return mAttendenceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = View.inflate(mcontext , R.layout.take_attendence_list_view_adapter , null);
        TextView NameOfStudentText = (TextView)v.findViewById(R.id.NameOfStudentTextTakeAttendenceAdminActivity);
        TextView AbsentOrPresentText = (TextView)v.findViewById(R.id.PresentOrAbsentTextTakeAttendenceAdminActivity);

        NameOfStudentText.setText(mAttendenceList.get(position).getNameOfStudent() + "\n" + mAttendenceList.get(position).getStudentId());
        AbsentOrPresentText.setText(mAttendenceList.get(position).getAbsentOrPresent());

        v.setTag(mAttendenceList.get(position).getStudentUserId());
        return v;

    }
}
