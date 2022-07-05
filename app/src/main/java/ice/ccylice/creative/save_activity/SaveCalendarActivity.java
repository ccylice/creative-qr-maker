package ice.ccylice.creative.save_activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.ResultParser;


import java.text.SimpleDateFormat;

import ice.ccylice.creative.R;
import ice.ccylice.creative.activity.BaseActivity;


public class SaveCalendarActivity extends BaseActivity {

    private TextView title,start,end,dis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start = findViewById(R.id.res_cal_tv2);
        end = findViewById(R.id.res_cal_tv3);
        dis = findViewById(R.id.res_cal_tv4);
        String text = getIntent().getStringExtra("ss");
        BarcodeFormat barcodeFormat = (BarcodeFormat)getIntent().getSerializableExtra("tt");
        Result mresult = new Result(text,null,null,barcodeFormat);
        CalendarParsedResult calendarParsedResult = (CalendarParsedResult) ResultParser.parseResult(mresult);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String starttime = sdf.format(calendarParsedResult.getStart());
        String endtime = sdf.format(calendarParsedResult.getEnd());
        start.setText(starttime);
        end.setText(endtime);
        dis.setText(calendarParsedResult.getDescription());
    }
    @Override
    public void onCopy() {
        super.onCopy();
        title = findViewById(R.id.res_cal_tv1);
        String text = getIntent().getStringExtra("ss");
        BarcodeFormat barcodeFormat = (BarcodeFormat)getIntent().getSerializableExtra("tt");
        Result mresult = new Result(text,null,null,barcodeFormat);
        CalendarParsedResult calendarParsedResult = (CalendarParsedResult) ResultParser.parseResult(mresult);
        title.setText(calendarParsedResult.getSummary());
        initCopy(title);
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_result_calendar;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onBack() {
        super.onBack();
        finish();
    }

    @Override
    protected void onShare() {
        super.onShare();
        String text = getIntent().getStringExtra("ss");
        initShare(text);
    }
}