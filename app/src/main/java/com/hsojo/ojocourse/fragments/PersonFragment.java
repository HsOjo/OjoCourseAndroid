package com.hsojo.ojocourse.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hsojo.ojocourse.LoginActivity;
import com.hsojo.ojocourse.MainActivity;
import com.hsojo.ojocourse.R;
import com.hsojo.ojocourse.beans.UserBean;


public class PersonFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        this.initView(view);
        return view;
    }

    private void initView(View view) {
        final UserBean login_user = MainActivity.login_user;
        final Context context = this.getContext();
        final MainActivity activity = (MainActivity) this.getActivity();

        EditText et_username = view.findViewById(R.id.et_username);
        EditText et_name = view.findViewById(R.id.et_name);
        EditText et_number = view.findViewById(R.id.et_number);
        Button btn_logout = view.findViewById(R.id.btn_logout);

        et_name.setText(login_user.name);
        et_username.setText(login_user.username);
        et_number.setText(login_user.number);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert context != null;
                if (login_user.clearData(context)) {
                    Toast.makeText(context, R.string.logout_success, Toast.LENGTH_SHORT).show();
                    assert activity != null;
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                } else {
                    Toast.makeText(context, R.string.logout_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
