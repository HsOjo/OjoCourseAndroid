package com.hsojo.ojocourse.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hsojo.ojocourse.R;
import com.hsojo.ojocourse.beans.CourseBean;

public class CourseDialog extends DialogFragment {
    private TextView tv_name;
    private TextView tv_class;
    private TextView tv_time;
    private TextView tv_place;
    private TextView tv_teacher;
    private TextView tv_type;
    private TextView tv_remark;
    private CourseBean course;

    public CourseDialog(CourseBean course) {
        this.course = course;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_course, container, false);

        this.tv_name = view.findViewById(R.id.tv_name);
        this.tv_class = view.findViewById(R.id.tv_class);
        this.tv_time = view.findViewById(R.id.tv_time);
        this.tv_place = view.findViewById(R.id.tv_place);
        this.tv_teacher = view.findViewById(R.id.tv_teacher);
        this.tv_type = view.findViewById(R.id.tv_type);
        this.tv_remark = view.findViewById(R.id.tv_remark);

        Button btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        this.setCourse(this.course);

        return view;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void setCourse(CourseBean course) {
        String day = course.day == -1 ? "" : new String[]{"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"}[course.day];
        String week = course.week == -1 ? "" : String.format("第%d周", course.week);
        String node = course.node == -1 ? "" : String.format("第%d节", course.node);
        String time = String.format("%s，%s，%s", week, day, node);
        time = time.equals("，，") ? "(无)" : time;

        tv_name.setText(course.course);
        tv_class.setText(course.class_);
        tv_time.setText(time);
        tv_place.setText(course.place.equals("") ? "(无)" : course.place);
        tv_teacher.setText(course.teacher + (course.teacher_spare.equals("") ? "" : "," + course.teacher_spare));
        tv_type.setText(course.type.equals("") ? "(无)" : course.type);
        tv_remark.setText(course.remark.equals("") ? "(无)" : course.remark);
    }
}
