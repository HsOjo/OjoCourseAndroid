package com.hsojo.ojocourse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hsojo.ojocourse.R;
import com.hsojo.ojocourse.beans.CourseBean;

import java.util.List;

public class CourseListAdapter extends BaseAdapter {
    private Context context;
    private List<CourseBean> courses;

    public CourseListAdapter(Context context, List<CourseBean> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int i) {
        return courses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.item_course, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_teacher = view.findViewById(R.id.tv_teacher);
            holder.tv_teacher_spare = view.findViewById(R.id.tv_teacher_spare);
            holder.tv_place = view.findViewById(R.id.tv_place);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CourseBean course = this.courses.get(i);
        holder.tv_name.setText(course.course);
        holder.tv_teacher.setText(course.teacher);
        holder.tv_teacher_spare.setText(course.teacher_spare);
        holder.tv_place.setText(course.place);

        return view;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_teacher;
        TextView tv_teacher_spare;
        TextView tv_place;
    }
}
