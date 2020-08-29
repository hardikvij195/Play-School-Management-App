package com.hvtechnologies.playschool;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SeeAttendanceOfParticularClassAdapter extends BaseAdapter{

    private Context mcontext6 ;
    private List<SeeAttendanceOfParticularClass> mAttendanceOfParticularClass ;

    public SeeAttendanceOfParticularClassAdapter(Context mcontext6, List<SeeAttendanceOfParticularClass> mAttendanceOfParticularClass) {
        this.mcontext6 = mcontext6;
        this.mAttendanceOfParticularClass = mAttendanceOfParticularClass;
    }

    @Override
    public int getCount() {
        return mAttendanceOfParticularClass.size();
    }

    @Override
    public Object getItem(int position) {
        return mAttendanceOfParticularClass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mcontext6 , R.layout.see_attendance_date_wise_particular_class_list_adapter , null);
        TextView Name = (TextView)v.findViewById(R.id.textViewNameOfStud);
        TextView Marked = (TextView)v.findViewById(R.id.textViewMarkOfStud);

        Name.setText(mAttendanceOfParticularClass.get(position).getStudentName());
        Marked.setText(mAttendanceOfParticularClass.get(position).getMarked());

        v.setTag(mAttendanceOfParticularClass.get(position).StudentName);
        return v;
    }
}

