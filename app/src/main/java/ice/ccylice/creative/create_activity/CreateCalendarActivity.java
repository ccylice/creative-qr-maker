package ice.ccylice.creative.create_activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.google.zxing.BarcodeFormat;

import ice.ccylice.creative.activity.BaseActivity;
import ice.ccylice.creative.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import ice.ccylice.creative.activity.CreateActivity;
import ice.ccylice.creative.activity.MainActivity;
import ice.ccylice.creative.activity.QRcodeActivity;
import ice.ccylice.creative.data.Database;
import ice.ccylice.creative.data.QRcode;



public class CreateCalendarActivity extends BaseActivity {
    private static final String TAG = "sssssss";
    private EditText et_input1,et_input2;
    private TextView view_create,tv_input1,tv_input2;
    private ImageView iv_qrcode;
    private EditText et_width, et_height;
    private Spinner sp_error_correction_level, sp_margin, sp_color_black, sp_color_white;
    private String content;//二维码内容
    private int width, height;//宽度，高度
    private String error_correction_level, margin;//容错率，空白边距
    private int color_black, color_white;//黑色色块，白色色块

    private Bitmap qrcode_bitmap;
    String start,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ImageView imageView = findViewById(R.id.im_icon);
        imageView.setImageResource(R.drawable.calendar);
        et_input1 = findViewById(R.id.Calendar_input1);
        et_input2 = findViewById(R.id.Calendar_input4);
        tv_input1 = findViewById(R.id.Calendar_input2);
        tv_input2 = findViewById(R.id.Calendar_input3);
        view_create = findViewById(R.id.cale_create);
        view_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQrcodeAndDisplay();
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CreateCalendarActivity.this, CreateTimeActivity.class), 1);
            }
        };
        tv_input1.setOnClickListener(onClickListener);
        tv_input2.setOnClickListener(onClickListener);

        java.util.Calendar data = java.util.Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        simpleDateFormat.format(new Date());
        String time=simpleDateFormat.format(new Date());
        tv_input1.setText(time);
        tv_input2.setText(time);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
        String time1 = simpleDateFormat1.format(new Date());
        start = time1;
        end = time1;
    }



    @Override
    protected int initLayout() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1&&resultCode == RESULT_OK){
            String year = data.getExtras().getString("year");
            String month = data.getExtras().getString("month");
            String day = data.getExtras().getString("day");
            String year2 = data.getExtras().getString("year2");
            String month2 = data.getExtras().getString("month2");
            String day2 = data.getExtras().getString("day2");
            tv_input1.setText(year+"."+month+"."+day);
            tv_input2.setText(year2+"."+month2+"."+day2);
            start = year+""+month+""+day;
            end = year2+""+month2+""+day2;
        }


    }

    private void generateQrcodeAndDisplay() {
        String str1 = et_input1.getText().toString(),
                str2 = et_input2.getText().toString(),
                str3 = start,
                str4 = end,
                str5 = "Title:",
                str6 = "Start:",
                str7 = "End:",
                str8 = "Description:",
                str9 = "\r\n";

        content = CalendarUtil.a(str1,str3,str4,"",str2);
        if (content.length() <= 0) {
            Toast.makeText(this, getString(R.string.no_qr_code_entered), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(CreateCalendarActivity.this, QRcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("icon",R.drawable.calendar);
        intent.putExtras(bundle);
        intent.putExtra("tv_title",content);
        intent.putExtra("tv_tit",str1);
        intent.putExtra("tv_name",getString(R.string.Calendar));

        startActivity(intent);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Database database = Database.getInstance(CreateCalendarActivity.this);
                database.qRcodeDao().insertQrcode(new QRcode(content, BarcodeFormat.QR_CODE.ordinal(),2,str1,"Calendar"));
            }
        }.start();

    }
    @Override
    protected void setTitle() {
        super.setTitle();
        create_title.setText(getString(R.string.Calendar));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMain();
    }
    @Override
    protected void onBack() {
        goMain();
    }
    @Override
    public void goMain() {
        Intent intent = new Intent(this, CreateActivity.class);
        startNext(intent);
        finish();
    }
}