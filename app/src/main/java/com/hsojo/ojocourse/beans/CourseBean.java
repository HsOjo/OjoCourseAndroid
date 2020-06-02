package com.hsojo.ojocourse.beans;

public class CourseBean {
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

    public CourseBean(String class_, String course, int day, int node, String place, String remark, String teacher, String teacher_spare, String type, int week) {
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
}
