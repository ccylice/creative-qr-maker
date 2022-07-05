package ice.ccylice.creative.create_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;


import ice.ccylice.creative.activity.BaseActivity;
import ice.ccylice.creative.activity.CreateActivity;
import ice.ccylice.creative.activity.QRcodeActivity;
import ice.ccylice.creative.R;
import ice.ccylice.creative.data.Database;
import ice.ccylice.creative.data.QRcode;


public class CreateWiFiActivity extends BaseActivity {
    private EditText et_input,et_input2;
    private TextView view_create;
    private RadioButton WPA,WEP,Nu;
    private ImageView iv_qrcode;

    private EditText et_width, et_height;
    private Spinner sp_error_correction_level, sp_margin, sp_color_black, sp_color_white;
    private String content;//二维码内容
    private int width, height;//宽度，高度
    private String error_correction_level, margin;//容错率，空白边距
    private int color_black, color_white;//黑色色块，白色色块

    private Bitmap qrcode_bitmap;
    private RadioGroup radioGroup;
    private int checkedId = R.id.WPA;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = findViewById(R.id.im_icon);
        imageView.setImageResource(R.drawable.wifi);
        radioGroup =  findViewById(R.id.tre);
        WEP = findViewById(R.id.WEP);
        WPA = findViewById(R.id.WPA);
        Nu = findViewById(R.id.wu);

        et_input = findViewById(R.id.et_input);
        et_input2 = findViewById(R.id.et_input2);
        view_create = findViewById(R.id.wifi_create);
        view_create.setOnClickListener(v -> generateQrcodeAndDisplay());
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> CreateWiFiActivity.this.checkedId = checkedId);


    }

    @Override
    protected int initLayout() {
        return R.layout.activity_wi_fi;
    }

    private void generateQrcodeAndDisplay() {

        String str = "WIFI:S:",str1 = ";T:WEP;P:",str2 = ";T:WPA;P:",
                str4 = et_input.getText().toString(),
                str5 = et_input2.getText().toString();

        switch (checkedId){
            case R.id.WPA:
                content = str + str4 +  str2 + str5 + ";;";
                break;
            case R.id.WEP:
                content = str + str4 +  str1 + str5 + ";;";
                break;
            case R.id.wu:
                content = str + str4 + ";;";
                break;
        }
        if (content.length() <= 0) {
            Toast.makeText(this, getString(R.string.no_qr_code_entered), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(CreateWiFiActivity.this, QRcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("icon",R.drawable.wifi);
        intent.putExtras(bundle);
        intent.putExtra("tv_title",content);
        intent.putExtra("tv_tit",str4);
        intent.putExtra("tv_name",getString(R.string.WiFi));

            startActivity(intent);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Database database = Database.getInstance(CreateWiFiActivity.this);
                database.qRcodeDao().insertQrcode(new QRcode(content, BarcodeFormat.QR_CODE.ordinal(),2,str4,"Wi-Fi"));
            }
        }.start();

    }
    @Override
    protected void setTitle() {
        super.setTitle();
        create_title.setText(getString(R.string.WiFi));
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