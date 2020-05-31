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


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();

    EditText et_username;
    EditText et_password;
    Button btn_register;
    Button btn_login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.btn_register = findViewById(R.id.btn_register);
        this.btn_login = findViewById(R.id.btn_login);
        this.et_username = findViewById(R.id.et_username);
        this.et_password = findViewById(R.id.et_password);

        this.btn_register.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        this.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = getApplicationContext();

                final String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                OjoCourseService.User user = OjoCourseService.generateUser();
                Call<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>> call_bind = user.bind(new OjoCourseService.UserBindRequest(username, password));
                call_bind.enqueue(new Callback<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>>() {
                    @Override
                    public void onResponse(Call<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>> call, Response<OjoCourseService.BaseResponse<OjoCourseService.UserInfo>> response) {
                        OjoCourseService.BaseResponse<OjoCourseService.UserInfo> body = response.body();
                        if (body != null) {
                            if (body.error == 0) {
                                Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                                callbackLogin(
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

    public void callbackLogin(String username, String name, String number, String token) {
        UserBean login_user = new UserBean(username, name, number, token);
        login_user.saveData(this.getApplicationContext());
        MainActivity.login_user = login_user;
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}