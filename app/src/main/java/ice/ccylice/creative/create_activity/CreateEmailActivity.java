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

import com.google.zxing.BarcodeFormat;

import ice.ccylice.creative.activity.BaseActivity;
import ice.ccylice.creative.R;

import ice.ccylice.creative.activity.CreateActivity;
import ice.ccylice.creative.activity.QRcodeActivity;
import ice.ccylice.creative.data.Database;
import ice.ccylice.creative.data.QRcode;


public class CreateEmailActivity extends BaseActivity {
    private EditText et_input1;
    private TextView view_create;
    private ImageView iv_qrcode;
    private ImageView iv_back;
    private TextView tv_title;
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
        imageView.setImageResource(R.drawable.e_mail);
        et_input1 = findViewById(R.id.email_input1);
        view_create = findViewById(R.id.email_create);
        view_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQrcodeAndDisplay();
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_email;
    }

    private void generateQrcodeAndDisplay() {
        String str1 = et_input1.getText().toString(),
                str5 = "mailto:";
        content = str5 + str1 ;
        if (content.length() <= 0) {
            Toast.makeText(this, R.string.no_qr_code_entered, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(CreateEmailActivity.this, QRcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("icon",R.drawable.e_mail);
        intent.putExtras(bundle);
        intent.putExtra("tv_title",content);
        intent.putExtra("tv_tit",str1);
        intent.putExtra("tv_name",getString(R.string.Email));

            startActivity(intent);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Database database = Database.getInstance(CreateEmailActivity.this);
                database.qRcodeDao().insertQrcode(new QRcode(content, BarcodeFormat.QR_CODE.ordinal(),2,str1,"Email"));
            }
        }.start();
    }
    @Override
    protected void setTitle() {
        super.setTitle();
        create_title.setText(getString(R.string.Email));
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