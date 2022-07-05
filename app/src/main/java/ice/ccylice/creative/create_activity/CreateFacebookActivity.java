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


public class CreateFacebookActivity extends BaseActivity {
    private EditText et_input;
    private TextView view_create;
    private ImageView iv_qrcode;
    private EditText et_width, et_height;
    private Spinner sp_error_correction_level, sp_margin, sp_color_black, sp_color_white;
    private String content;//二维码内容
    private int width, height;//宽度，高度
    private String error_correction_level, margin;//容错率，空白边距
    private int color_black, color_white;//黑色色块，白色色块

    private Bitmap qrcode_bitmap;
    private RadioGroup radioGroup;
    private int checkedId = R.id.ID;
    private RadioButton b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ImageView imageView = findViewById(R.id.im_icon);
        imageView.setImageResource(R.drawable.facebook);
        radioGroup =  findViewById(R.id.face_group);
        b1 = findViewById(R.id.ID);
        b2 = findViewById(R.id.URL);
        et_input = findViewById(R.id.facebook_input);
        view_create = findViewById(R.id.facebook_create);


        view_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQrcodeAndDisplay();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                CreateFacebookActivity.this.checkedId = checkedId;
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input.setHint(R.string.enter_facebook_id);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input.setHint(R.string.facebook_profile_url);
            }
        });

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_facebook;
    }

    private void generateQrcodeAndDisplay() {

        String str = "http://www.facebook.com/",
                str2 = et_input.getText().toString();

        switch (checkedId){
            case R.id.ID:
                content = str + str2 + "/";
                break;
            case R.id.URL:
                content = str + str2;
                break;

        }
        width = 650;
        height = 650;
        color_black = Color.BLACK;
        color_white = Color.WHITE;
        if (content.length() <= 0) {
            Toast.makeText(this, R.string.no_qr_code_entered, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(CreateFacebookActivity.this, QRcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("icon",R.drawable.facebook);
        intent.putExtras(bundle);
        intent.putExtra("tv_title",content);
        intent.putExtra("tv_tit",str2);
        intent.putExtra("tv_name",getString(R.string.Facebook));

        startActivity(intent);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Database database = Database.getInstance(CreateFacebookActivity.this);
                database.qRcodeDao().insertQrcode(new QRcode(content, BarcodeFormat.QR_CODE.ordinal(),2,str2,"Facebook"));
            }
        }.start();

    }
    @Override
    protected void setTitle() {
        super.setTitle();
        create_title.setText(getString(R.string.Facebook));
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