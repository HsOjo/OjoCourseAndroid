package com.hsojo.ojocourse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hsojo.ojocourse.beans.UserBean;
import com.hsojo.ojocourse.services.OjoCourseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btn_back;
    Button btn_register;
    EditText et_username;
    EditText et_password;
    EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.btn_back = findViewById(R.id.btn_back);
        this.btn_register = findViewById(R.id.btn_register);
        this.et_username = findViewById(R.id.et_username);
        this.et_password = findViewById(R.id.et_password);
        this.et_number = findViewById(R.id.et_number);

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = getApplicationContext();

                final String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String number = et_number.getText().toString();

                OjoCourseService.User user = OjoCourseService.generateUser();
                Call<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>> call_register = user.register(new OjoCourseService.UserRegisterRequest(username, password, number));
                call_register.enqueue(new Callback<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>>() {
                    @Override
                    public void onResponse(Call<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>> call, Response<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>> response) {
                        OjoCourseService.BaseResponse<OjoCourseService.UserInfo> body = response.body();
                        if (body != null) {
                            if (body.error == 0) {
                                Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                                callbackRegister(
                                        username,
                                        body.data.name,
                                        body.data.number,
                                        body.data.token
                                );
                            } else {
                                Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>> call, Throwable t) {
                        Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void callbackRegister(String username, String name, String number, String token) {
        UserBean login_user = new UserBean(username, name, number, token);
        login_user.saveData(this.getApplicationContext());
        MainActivity.login_user = login_user;
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}