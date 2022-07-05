package ice.ccylice.creative.create_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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


public class CreateWebsiteActivity extends BaseActivity {
    private EditText et_input;
    private TextView view_create;
    private ImageView iv_qrcode;

    private RadioButton www,com;
    private EditText et_width, et_height;
    private Spinner sp_error_correction_level, sp_margin, sp_color_black, sp_color_white;
    private String content;//二维码内容
    private int width, height;//宽度，高度
    private String error_correction_level, margin;//容错率，空白边距
    private int color_black, color_white;//黑色色块，白色色块

    private Bitmap qrcode_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ImageView imageView = findViewById(R.id.im_icon);
        imageView.setImageResource(R.drawable.website);
        www = findViewById(R.id.www);
        com = findViewById(R.id.com);
        et_input = findViewById(R.id.web_input);
        view_create = findViewById(R.id.web_create);
        www.setOnClickListener(v -> et_input.setText(getString(R.string.hender)));
        com.setOnClickListener(v -> {
            String str = et_input.getText().toString();
            String str1 = str + ".com";
            et_input.setText(str1);
        });
        view_create.setOnClickListener(v -> generateQrcodeAndDisplay());

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_website;
    }

    private void generateQrcodeAndDisplay() {
        String str = et_input.getText().toString();
        content = str;

        if (content.length() <= 0) {
            Toast.makeText(this, getString(R.string.no_qr_code_entered), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(CreateWebsiteActivity.this, QRcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("icon",R.drawable.website);
        intent.putExtras(bundle);
        intent.putExtra("tv_title",content);
        intent.putExtra("tv_tit",str);
        intent.putExtra("tv_name",getString(R.string.Website));

            startActivity(intent);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Database database = Database.getInstance(CreateWebsiteActivity.this);
                database.qRcodeDao().insertQrcode(new QRcode(content, BarcodeFormat.QR_CODE.ordinal(),2,str,"Website"));
            }
        }.start();

    }
    @Override
    protected void setTitle() {
        super.setTitle();
        create_title.setText(getString(R.string.Website));
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