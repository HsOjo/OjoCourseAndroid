package com.hsojo.ojocourse.adapters;

import android.annotation.SuppressLint;
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

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.item_course, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_teacher = view.findViewById(R.id.tv_teacher);
            holder.tv_time = view.findViewById(R.id.tv_time);
            holder.tv_place = view.findViewById(R.id.tv_place);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CourseBean course = this.courses.get(i);

        String day = course.day == -1 ? "" : new String[]{"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"}[course.day];
        String week = course.week == -1 ? "" : String.format("第%d周", course.week);
        String node = course.node == -1 ? "" : String.format("第%d节", course.node);
        String time = String.format("%s，%s\n%s", week, day, node);
        time = time.equals("，\n") ? "(无)" : time;

        holder.tv_name.setText(course.course);
        holder.tv_teacher.setText(course.teacher + (course.teacher_spare.equals("") ? "" : "," + course.teacher_spare));
        holder.tv_time.setText(time);
        holder.tv_place.setText(course.place);

        return view;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_teacher;
        TextView tv_time;
        TextView tv_place;
    }
}
