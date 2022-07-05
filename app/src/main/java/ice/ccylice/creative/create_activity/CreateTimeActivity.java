package ice.ccylice.creative.create_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.util.Calendar;

import ice.ccylice.creative.activity.BaseActivity;
import ice.ccylice.creative.R;
import ice.ccylice.creative.activity.CreateActivity;

public class CreateTimeActivity extends BaseActivity {
    private TextView textView1,textView2,cal_tv1,cal_tv2;
    private CalendarView calendarView,calendarView2;
    private Calendar calendar;
    private Calendar calendar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calendarView = findViewById(R.id.cal);
        calendarView2 = findViewById(R.id.cal2);
        textView1 = findViewById(R.id.time_text1);
        textView2 = findViewById(R.id.time_text2);
        cal_tv1 = findViewById(R.id.Calendar_input2);
        cal_tv2 = findViewById(R.id.Calendar_input3);
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CreateTimeActivity.this, year + "年" + month + "月" + dayOfMonth +  "日" , Toast.LENGTH_SHORT).show();
                calendar.set(year,month,dayOfMonth);
            }
        });
        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar2.set(year,month,dayOfMonth);
            }
        });


        textView2.setOnClickListener(v -> {
            String months,month2s,days,day2s;
            int year =  calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            months = month+"";
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            days = day+"";
            int year2 = calendar2.get(Calendar.YEAR);
            int month2 = calendar2.get(Calendar.MONTH);
            month2s = month2+"";
            int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
            day2s = day2+"";
            if (month < 10){
                months = "0" + months;
            }
            if (month2 < 10){
                month2s = "0" + month2s;
            }
            if (day < 10){
                days = "0" + days;
            }
            if (day2 < 10){
                day2s = "0" + day2s;
            }
            Intent intent = new Intent();
            intent.putExtra("year",year+"");
            intent.putExtra("month",months);
            intent.putExtra("day",days);
            intent.putExtra("year2",year2+"");
            intent.putExtra("month2",month2s);
            intent.putExtra("day2",day2s);
            setResult(RESULT_OK,intent);
            finish();
        });
        textView1.setOnClickListener(v -> finish());
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_time;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMain();
    }
    @Override
    protected void onBack() {
        super.onBack();
        goMain();
    }
    @Override
    public void goMain() {
        Intent intent = new Intent(this, CreateActivity.class);
        startNext(intent);
        finish();
    }
}