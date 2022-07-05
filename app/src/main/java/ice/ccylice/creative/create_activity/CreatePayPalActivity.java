package ice.ccylice.creative.create_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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


public class CreatePayPalActivity extends BaseActivity {
    private EditText et_input;
    private TextView view_create;
    private RadioButton Link,Username;
    private ImageView iv_qrcode;

    private EditText et_width, et_height;
    private Spinner sp_error_correction_level, sp_margin, sp_color_black, sp_color_white;
    private String content;//二维码内容
    private int width, height;//宽度，高度
    private String error_correction_level, margin;//容错率，空白边距
    private int color_black, color_white;//黑色色块，白色色块

    private Bitmap qrcode_bitmap;
    private RadioGroup radioGroup;
    private int checkedId = R.id.Me_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = findViewById(R.id.im_icon);
        imageView.setImageResource(R.drawable.paypal);
        radioGroup =  findViewById(R.id.tre);
        Link = findViewById(R.id.Me_link);
        Username = findViewById(R.id.Me_username);

        et_input = findViewById(R.id.paypal_input1);
        view_create = findViewById(R.id.paypal_create);


        view_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQrcodeAndDisplay();
            }
        });

        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input.setHint(R.string.paypalme_link);
            }
        });
        Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input.setHint(R.string.paypalme_username);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                CreatePayPalActivity.this.checkedId = checkedId;
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_pay_pal;
    }

    private void generateQrcodeAndDisplay() {

        String str = "paypal.me/",str2 = "https://www.paypal.com/paypalme/",
                str4 = et_input.getText().toString();

        switch (checkedId){
            case R.id.Me_link:
                content = str + str4;
                break;
            case R.id.Me_username:
                content = str2 + str4;
                break;
        }
        width = 650;
        height = 650;
        color_black = Color.BLACK;
        color_white = Color.WHITE;
        if (content.length() <= 0) {
            Toast.makeText(this, getString(R.string.no_qr_code_entered), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(CreatePayPalActivity.this, QRcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("icon",R.drawable.paypal);
        intent.putExtras(bundle);
        intent.putExtra("tv_title",content);
        intent.putExtra("tv_tit",str4);
        intent.putExtra("tv_name",getString(R.string.Paypal));

        startActivity(intent);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Database database = Database.getInstance(CreatePayPalActivity.this);
                database.qRcodeDao().insertQrcode(new QRcode(content, BarcodeFormat.QR_CODE.ordinal(),2,str4,"Paypal"));
            }
        }.start();

    }
    @Override
    protected void setTitle() {
        super.setTitle();
        create_title.setText(getString(R.string.Paypal));
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