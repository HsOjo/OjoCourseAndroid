package com.hsojo.ojocourse.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.hsojo.ojocourse.MainActivity;
import com.hsojo.ojocourse.R;
import com.hsojo.ojocourse.adapters.CourseListAdapter;
import com.hsojo.ojocourse.dialogs.CourseDialog;
import com.hsojo.ojocourse.models.CourseModel;
import com.hsojo.ojocourse.services.ojocourse.OjoCourseService;
import com.hsojo.ojocourse.services.ojocourse.request.CourseInfoRequest;
import com.hsojo.ojocourse.services.ojocourse.request.CourseQueryRequest;
import com.hsojo.ojocourse.services.ojocourse.response.BaseResponse;
import com.hsojo.ojocourse.services.ojocourse.response.CourseInfo;
import com.hsojo.ojocourse.services.ojocourse.response.CourseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleFragment extends Fragment {
    private static final String TAG = ScheduleFragment.class.getName();
    ListView lv_course;
    Spinner sp_week;
    Spinner sp_day;
    Button btn_query;

    ArrayList<CourseModel> courses;
    ArrayList<String> weeks;
    ArrayList<String> days;
    HashMap<String, Integer> weeks_map;
    HashMap<String, Integer> days_map;

    int selected_week = -1;
    int selected_day = -1;
    OjoCourseService.Course service_course;
    private ArrayAdapter<String> adapter_week;
    private ArrayAdapter<String> adapter_day;
    private CourseListAdapter adapter_course;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        this.initView(view);
        return view;
    }

    private void initView(View view) {
        Context context = view.getContext();
        this.courses = new ArrayList<>();
        this.service_course = OjoCourseService.generateCourse();

        lv_course = view.findViewById(R.id.lv_course);
        sp_week = view.findViewById(R.id.sp_week);
        sp_day = view.findViewById(R.id.sp_day);
        btn_query = view.findViewById(R.id.btn_query);

        adapter_course = new CourseListAdapter(context, this.courses);
        lv_course.setAdapter(adapter_course);
        lv_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CourseModel course = (CourseModel) lv_course.getItemAtPosition(i);
                CourseDialog dialog = new CourseDialog(course);
                assert getFragmentManager() != null;
                dialog.show(getFragmentManager(), dialog.getTag());
            }
        });

        this.weeks = new ArrayList<>();
        adapter_week = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, this.weeks);
        adapter_week.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_week.setAdapter(adapter_week);
        sp_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AppCompatTextView tv_item = (AppCompatTextView) view;
                selected_week = weeks_map.get(String.valueOf(tv_item.getText()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.days = new ArrayList<>();
        adapter_day = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, this.days);
        adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_day.setAdapter(adapter_day);
        sp_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AppCompatTextView tv_item = (AppCompatTextView) view;
                selected_day = days_map.get(String.valueOf(tv_item.getText()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryCourse();
            }
        });

        this.initSpinners();
    }

    public void initSpinners() {
        final Context context = getContext();

        Call<BaseResponse<CourseInfo>> call_info = this.service_course.info(new CourseInfoRequest(MainActivity.login_user.token));
        call_info.enqueue(new Callback<BaseResponse<CourseInfo>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<BaseResponse<CourseInfo>> call, Response<BaseResponse<CourseInfo>> response) {
                BaseResponse<CourseInfo> body = response.body();
                if (body != null) {
                    if (body.error == 0) {
                        days.clear();
                        days_map = new HashMap<>();
                        days_map.put("(全部)", -1);
                        days.add("(全部)");
                        int value = 1;
                        int current_day_index = 0;
                        for (String key : new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"}) {
                            if (body.data.current_info.day == value) {
                                key += " (当前)";
                                current_day_index = days.size();
                            }
                            days_map.put(key, value);
                            days.add(key);
                            value++;
                        }
                        adapter_day.notifyDataSetChanged();

                        weeks.clear();
                        weeks_map = new HashMap<>();
                        weeks_map.put("(全部)", -1);
                        weeks.add("(全部)");

                        int current_week_index = 0;
                        for (int week : body.data.weeks) {
                            String key = String.format("%d周", week);
                            if (body.data.current_info.week == week) {
                                key += " (当前)";
                                current_week_index = weeks.size();
                            }
                            weeks_map.put(key, week);
                            weeks.add(key);
                        }
                        adapter_week.notifyDataSetChanged();

                        final int final_current_day_index = current_day_index;
                        final int final_current_week_index = current_week_index;
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sp_day.setSelection(final_current_day_index);
                                sp_week.setSelection(final_current_week_index);
                            }
                        });
                    } else {
                        Toast.makeText(context, body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CourseInfo>> call, Throwable t) {
                Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void queryCourse() {
        final Context context = getContext();

        Call<BaseResponse<CourseQuery>> call_query = service_course.query(new CourseQueryRequest(MainActivity.login_user.token, false, this.selected_week, this.selected_day));
        call_query.enqueue(new Callback<BaseResponse<CourseQuery>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<BaseResponse<CourseQuery>> call, Response<BaseResponse<CourseQuery>> response) {
                BaseResponse<CourseQuery> body = response.body();
                if (body != null) {
                    if (body.error == 0) {
                        courses.clear();
                        for (CourseModel course : body.data.courses) {
                            CourseModel course_model = new CourseModel(
                                    course.class_, course.course, course.day, course.node, course.place, course.remark, course.teacher, course.teacher_spare, course.type, course.week
                            );
                            courses.add(course_model);
                        }
                        adapter_course.notifyDataSetChanged();

                        if (body.data.courses.size() == 0) {
                            Toast.makeText(context, R.string.message_no_course, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CourseQuery>> call, Throwable t) {
                Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show();
            }
        });
    }
}
