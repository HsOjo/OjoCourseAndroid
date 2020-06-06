package com.hsojo.ojocourse.services.ojocourse.response;

import com.hsojo.ojocourse.models.CourseModel;

import java.util.List;

public class CourseQuery {
    public List<CourseModel> courses;

    public CourseQuery(List<CourseModel> courses) {
        this.courses = courses;
    }
}
