package ice.ccylice.creative.save_activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;

import ice.ccylice.creative.R;
import ice.ccylice.creative.activity.BaseActivity;


public class SaveSMSActivity extends BaseActivity {



    private TextView number,body;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCopy() {
        number = findViewById(R.id.res_sms_tv1);
        body = findViewById(R.id.res_sms_tv2);

        String text = getIntent().getStringExtra("ss");
        BarcodeFormat barcodeFormat = (BarcodeFormat)getIntent().getSerializableExtra("tt");
        Result mresult = new Result(text,null,null,barcodeFormat);
        SMSParsedResult smsParsedResult = (SMSParsedResult) ResultParser.parseResult(mresult);
        number.setText(smsParsedResult.getNumbers()[0]);
        body.setText(smsParsedResult.getBody());
        super.onCopy();
        initCopy(body);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_result_sms;
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