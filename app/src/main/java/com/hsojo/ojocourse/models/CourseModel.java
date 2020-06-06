package com.hsojo.ojocourse.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

public class CourseModel {
    public String class_;
    public String course;
    public int day;
    public int node;
    public String place;
    public String remark;
    public String teacher;
    public String teacher_spare;
    public String type;
    public int week;

    public CourseModel(String class_, String course, int day, int node, String place, String remark, String teacher, String teacher_spare, String type, int week) {
        this.class_ = class_;
        this.course = course;
        this.day = day;
        this.node = node;
        this.place = place;
        this.remark = remark;
        this.teacher = teacher;
        this.teacher_spare = teacher_spare;
        this.type = type;
        this.week = week;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("<Course class_='%s' course='%s' day=%d node=%d place='%s' remark='%s' teacher='%s' teacher_spare='%s' type='%s' week=%d>", class_, course, day, node, place, remark, teacher, teacher_spare, type, week);
    }
}
