package com.hsojo.ojocourse.services.ojocourse.request;

public class CourseQueryRequest {
    public String token;
    public boolean sync;
    public int week;
    public int day;

    public CourseQueryRequest(String token, boolean sync, int week, int day) {
        this.token = token;
        this.sync = sync;
        this.week = week;
        this.day = day;
    }

    public CourseQueryRequest(String token, boolean sync) {
        this.token = token;
        this.sync = sync;
        this.week = -1;
        this.day = -1;
    }
}
