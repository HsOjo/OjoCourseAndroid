package com.hsojo.ojocourse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hsojo.ojocourse.models.UserModel;
import com.hsojo.ojocourse.services.ojocourse.OjoCourseService;
import com.hsojo.ojocourse.services.ojocourse.request.UserBindRequest;
import com.hsojo.ojocourse.services.ojocourse.response.BaseResponse;
import com.hsojo.ojocourse.services.ojocourse.response.UserInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();

    OjoCourseService.User service_user;

    EditText et_username;
    EditText et_password;
    Button btn_register;
    Button btn_login;
    ImageView iv_face;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Context context = this.getApplicationContext();
        final Resources resources = this.getResources();

        this.service_user = OjoCourseService.generateUser();

        this.btn_register = findViewById(R.id.btn_register);
        this.btn_login = findViewById(R.id.btn_login);
        this.et_username = findViewById(R.id.et_username);
        this.et_password = findViewById(R.id.et_password);
        this.iv_face = findViewById(R.id.iv_face);

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

                Call<BaseResponse<UserInfo>> call_bind = service_user.bind(new UserBindRequest(username, password));
                call_bind.enqueue(new Callback<BaseResponse<UserInfo>>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(Call<BaseResponse<UserInfo>> call, Response<BaseResponse<UserInfo>> response) {
                        BaseResponse<UserInfo> body = response.body();
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
                                Toast.makeText(context, body.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<UserInfo>> call, Throwable t) {
                        Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        this.et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String username = String.valueOf(charSequence);
                final String path_face = username + ".jpg";
                File file_face = new File(context.getFilesDir(), path_face);
                if (file_face.exists()) {
                    try {
                        FileInputStream io = context.openFileInput(path_face);
                        int size = (int) file_face.length();
                        byte[] img_data = new byte[size];
                        io.read(img_data);
                        io.close();

                        Bitmap bmp_face = BitmapFactory.decodeByteArray(img_data, 0, size);
                        iv_face.setImageBitmap(bmp_face);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    iv_face.setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_foreground));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void callbackLogin(String username, String name, final String number, String token) {
        final Context context = getApplicationContext();
        UserModel login_user = new UserModel(username, name, number, token);

        final String path_face = username + ".jpg";
        service_user.face(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null) {
                        byte[] img_data = body.bytes();
                        FileOutputStream io = context.openFileOutput(path_face, Context.MODE_PRIVATE);
                        io.write(img_data);
                        io.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        login_user.saveData(this.getApplicationContext());
        MainActivity.login_user = login_user;
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}