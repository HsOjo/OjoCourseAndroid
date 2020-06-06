package com.hsojo.ojocourse.services.ojocourse;

import com.hsojo.ojocourse.services.ojocourse.request.CourseInfoRequest;
import com.hsojo.ojocourse.services.ojocourse.request.CourseQueryRequest;
import com.hsojo.ojocourse.services.ojocourse.request.UserBindRequest;
import com.hsojo.ojocourse.services.ojocourse.request.UserRegisterRequest;
import com.hsojo.ojocourse.services.ojocourse.response.BaseResponse;
import com.hsojo.ojocourse.services.ojocourse.response.CourseInfo;
import com.hsojo.ojocourse.services.ojocourse.response.CourseQuery;
import com.hsojo.ojocourse.services.ojocourse.response.UserInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class OjoCourseService {
    public static String API_URL = "https://ojocourse.hsojo.com";

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

    public interface User {
        @POST("/user/register")
        Call<BaseResponse<UserInfo>> register(@Body UserRegisterRequest request);

        @POST("/user/bind")
        Call<BaseResponse<UserInfo>> bind(@Body UserBindRequest request);

        @GET("/user/face/{token}")
        Call<ResponseBody> face(@Path("token") String token);
    }

    public interface Course {
        @POST("/course/info")
        Call<BaseResponse<CourseInfo>> info(@Body CourseInfoRequest request);

        @POST("/course/query")
        Call<BaseResponse<CourseQuery>> query(@Body CourseQueryRequest request);
    }
}
