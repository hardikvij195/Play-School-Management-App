package com.hvtechnologies.playschool;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SeeAttendanceDateWiseListAdapter extends BaseAdapter {

    private Context mcontext5 ;
    private List<SeeAttendanceDateWiseClass> mAttendanceDateWiseList ;

    public SeeAttendanceDateWiseListAdapter(Context mcontext5, List<SeeAttendanceDateWiseClass> mAttendanceDateWiseList) {
        this.mcontext5 = mcontext5;
        this.mAttendanceDateWiseList = mAttendanceDateWiseList;
    }

    @Override
    public int getCount() {
        return mAttendanceDateWiseList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAttendanceDateWiseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mcontext5 , R.layout.see_attendance_date_wise_list_adapter , null);
        TextView BatchName = (TextView)v.findViewById(R.id.batchnameseeattdatewise);
        TextView ClassName = (TextView)v.findViewById(R.id.classnameseeattdatewise);
        TextView Date = (TextView)v.findViewById(R.id.dateseeattdatewise);
        TextView Time = (TextView)v.findViewById(R.id.timeseeattdatewise);

        BatchName.setText(mAttendanceDateWiseList.get(position).getBatchName());
        Date.setText(mAttendanceDateWiseList.get(position).getDate());
        ClassName.setText(mAttendanceDateWiseList.get(position).getClassName());
        Time.setText(mAttendanceDateWiseList.get(position).getTime());

        v.setTag(mAttendanceDateWiseList.get(position).getBatchName());
        return v;



    }
}
