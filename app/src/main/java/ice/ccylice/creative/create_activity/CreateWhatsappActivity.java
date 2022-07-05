package ice.ccylice.creative.create_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;


import org.json.JSONException;

import java.io.IOException;
import java.util.Locale;

import ice.ccylice.creative.activity.BaseActivity;
import ice.ccylice.creative.activity.CreateActivity;
import ice.ccylice.creative.activity.QRcodeActivity;
import ice.ccylice.creative.R;
import ice.ccylice.creative.data.Database;
import ice.ccylice.creative.data.QRcode;
import ice.sahooz.library.countrypicker.Country;
import ice.sahooz.library.countrypicker.CountryPickerFragment;
import ice.sahooz.library.countrypicker.Language;



@SuppressLint("SetTextI18n")
public class CreateWhatsappActivity extends BaseActivity {
    private ImageView ivFlag;
    private TextView tvName;
    private TextView tvCode;

    private EditText et_input;
    private TextView view_create,tv_input;
    private ImageView iv_qrcode;
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

        ivFlag = findViewById(ice.sahooz.library.countrypicker.R.id.iv_flag);
        tvName = findViewById(ice.sahooz.library.countrypicker.R.id.tv_name);
        tvCode = findViewById(ice.sahooz.library.countrypicker.R.id.tv_code);
        try {
            Country.load(this, getLanguage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ImageView imageView = findViewById(R.id.im_icon);
        imageView.setImageResource(R.drawable.whatsapp);

        tv_input = findViewById(R.id.whatsapp_input1);
        et_input = findViewById(R.id.whatsapp_input2);
        view_create = findViewById(R.id.whatsapp_create);

        view_create.setOnClickListener(v -> generateQrcodeAndDisplay());

        tv_input.setOnClickListener(v -> CountryPickerFragment.newInstance(country -> {
            if(country.flag != 0)
            tv_input.setText("+" + country.code);
        }).show(getSupportFragmentManager(), "country"));
        tv_input.setText("+" + 1);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_whatsapp;
    }

    private void generateQrcodeAndDisplay() {
        String str = tv_input.getText().toString(),
        str2 = et_input.getText().toString(),
        str3 = "http://wa.me/",
        str4 = str+str2;
        content = str3 + str + str2;
        if (content.length() <= 0) {
            Toast.makeText(this, getString(R.string.no_qr_code_entered), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(CreateWhatsappActivity.this, QRcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("icon",R.drawable.whatsapp);
        intent.putExtras(bundle);
        intent.putExtra("tv_title",content);
        intent.putExtra("tv_tit",str4);
        intent.putExtra("tv_name",getString(R.string.Whatsapp));

        startActivity(intent);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Database database = Database.getInstance(CreateWhatsappActivity.this);
                database.qRcodeDao().insertQrcode(new QRcode(content, BarcodeFormat.QR_CODE.ordinal(),2,str,"Whatsapp"));
            }
        }.start();

    }
    private Language getLanguage() {
        Locale locale = this.getResources().getConfiguration().locale;

        if("zh".equals(locale.getLanguage())) {
//            if("CN".equals(locale.getCountry())) {
//                return Language.SIMPLIFIED_CHINESE;
//
//            }
            return Language.TRADITIONAL_CHINESE;
        }

        return Language.ENGLISH;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            assert country != null;
            if(country.flag != 0) ivFlag.setImageResource(country.flag);
            tvName.setText(country.name);
            tvCode.setText("+" + country.code);
        }
    }

    @Override
    protected void onDestroy() {
        Country.destroy();
        super.onDestroy();
    }
    @Override
    protected void setTitle() {
        super.setTitle();
        create_title.setText(getString(R.string.Whatsapp));
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