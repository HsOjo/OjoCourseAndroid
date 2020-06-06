package com.hsojo.ojocourse.services.ojocourse.response;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;

public class CourseInfo {
    public CurrentInfo current_info;
    public Map<Integer, Map<Integer, String>> dates;
    public ArrayList<Integer> weeks;

    public CourseInfo(CurrentInfo current_info, Map<Integer, Map<Integer, String>> dates, ArrayList<Integer> weeks) {
        this.current_info = current_info;
        this.dates = dates;
        this.weeks = weeks;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("<CourseInfo current_info=%s dates=%s weeks=%s>", current_info.toString(), dates.toString(), weeks.toString());
    }

    public static class CurrentInfo {
        public String stu_year;
        public String date;
        public int day;
        public int week;

        public CurrentInfo(String stu_year, String date, int day, int week) {
            this.stu_year = stu_year;
            this.date = date;
            this.day = day;
            this.week = week;
        }

        @SuppressLint("DefaultLocale")
        @NonNull
        @Override
        public String toString() {
            return String.format("<CurrentInfo stu_year='%s' date='%s' day=%d week=%d>", stu_year, date, day, week);
        }
    }
}
