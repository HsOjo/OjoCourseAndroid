package com.hsojo.ojocourse.services;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class OjoCourseService {
    public static String API_URL = "https://ojocourse.hsojo.com";

    public static class BaseResponse<T> {
        public T data;
        public int error;

        public BaseResponse(T data, int error) {
            this.data = data;
            this.error = error;
        }
    }

    public static class UserRegisterRequest {
        public String username;
        public String password;
        public String number;

        public UserRegisterRequest(String username, String password, String number) {
            this.username = username;
            this.password = password;
            this.number = number;
        }
    }

    public static class UserBindRequest {
        public String username;
        public String password;

        public UserBindRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public static class UserInfo {
        public String name;
        public String number;
        public String token;


        public UserInfo(String name, String number, String token) {
            this.name = name;
            this.number = number;
            this.token = token;
        }

        @SuppressLint("DefaultLocale")
        @NonNull
        @Override
        public String toString() {
            return String.format("<UserInfo name='%s' number='%s' token='%s'>", name, number, token);
        }
    }

    public interface User {
        @POST("/user/register")
        Call<BaseResponse<UserInfo>> register(@Body UserRegisterRequest request);

        @POST("/user/bind")
        Call<BaseResponse<UserInfo>> bind(@Body UserBindRequest request);

        @POST("/user/face/{token}")
        Call<ResponseBody> face(@Path("token") String token);
    }

    public static class CourseInfoRequest {
        public String token;

        public CourseInfoRequest(String token) {
            this.token = token;
        }
    }

    public static class CourseQueryRequest {
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

    public static class CourseInfo {
        public CurrentInfo current_info;
        public Map<Integer, Map<Integer, String>> dates;

        public CourseInfo(CurrentInfo current_info, Map<Integer, Map<Integer, String>> dates) {
            this.current_info = current_info;
            this.dates = dates;
        }

        @SuppressLint("DefaultLocale")
        @NonNull
        @Override
        public String toString() {
            return String.format("<CourseInfo current_info=%s dates=%s>", current_info.toString(), dates.toString());
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

    public static class CourseQuery {
        public List<Course> courses;

        public CourseQuery(List<Course> courses) {
            this.courses = courses;
        }

        public static class Course {
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

            public Course(String class_, String course, int day, int node, String place, String remark, String teacher, String teacher_spare, String type, int week) {
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
    }

    public interface Course {
        @POST("/course/info")
        Call<BaseResponse<CourseInfo>> info(@Body CourseInfoRequest request);

        @POST("/course/query")
        Call<BaseResponse<CourseQuery>> query(@Body CourseQueryRequest request);
    }

    public static User generateUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(User.class);
    }

    public static Course generateCourse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(Course.class);
    }
}
