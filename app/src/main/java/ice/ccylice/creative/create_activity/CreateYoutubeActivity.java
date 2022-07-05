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
import ice.ccylice.creative.R;

import ice.ccylice.creative.activity.CreateActivity;
import ice.ccylice.creative.activity.QRcodeActivity;
import ice.ccylice.creative.data.Database;
import ice.ccylice.creative.data.QRcode;


public class CreateYoutubeActivity extends BaseActivity {
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
    private int checkedId = R.id.Youtube_url;
    private RadioButton b1,b2,b3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = findViewById(R.id.im_icon);
        imageView.setImageResource(R.drawable.youtube);
        radioGroup =  findViewById(R.id.Youtube_group);
        b1 = findViewById(R.id.Youtube_url);
        b2 = findViewById(R.id.Youtube_video_id);
        b3 = findViewById(R.id.Youtube_channel_id);
        et_input = findViewById(R.id.youtube_input1);
        view_create = findViewById(R.id.Youtube_create);
        view_create.setOnClickListener(v -> generateQrcodeAndDisplay());
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> CreateYoutubeActivity.this.checkedId = checkedId);
        b1.setOnClickListener(v -> {
            et_input.setHint(R.string.enter_youtube_url);
        });
        b2.setOnClickListener(v -> et_input.setHint(R.string.enter_youtube_video_id));
        b3.setOnClickListener(v -> et_input.setHint(R.string.enter_youtube_channel_id));
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_youtube;
    }

    private void generateQrcodeAndDisplay() {

        String str = "http://www.youtube.com/",
                str2 = et_input.getText().toString(),
                str3 = "http://www.youtube.com/watch?v=",
                str4 = "http://www.youtube.com/channel/";

        switch (checkedId){
            case R.id.Youtube_url:
                content = str + str2;
                break;
            case R.id.Youtube_video_id:
                content = str3 + str2;
                break;
            case R.id.Youtube_channel_id:
                content = str4 + str2;
                break;
        }
        if (content.length() <= 0) {
            Toast.makeText(this, getString(R.string.no_qr_code_entered), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(CreateYoutubeActivity.this, QRcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("icon",R.drawable.youtube);
        intent.putExtras(bundle);
        intent.putExtra("tv_title",content);
        intent.putExtra("tv_tit",str2);
        intent.putExtra("tv_name",getString(R.string.Youtube));

            startActivity(intent);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Database database = Database.getInstance(CreateYoutubeActivity.this);
                database.qRcodeDao().insertQrcode(new QRcode(content, BarcodeFormat.QR_CODE.ordinal(),2,str2,"Youtube"));
            }
        }.start();

    }
    @Override
    protected void setTitle() {
        super.setTitle();
        create_title.setText(getString(R.string.Youtube));
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