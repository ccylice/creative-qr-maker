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
import com.google.zxing.client.result.TelParsedResult;
import com.google.zxing.client.result.TelResultParser;

import ice.ccylice.creative.R;
import ice.ccylice.creative.activity.BaseActivity;


public class SaveTelActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCopy() {
        super.onCopy();
        TextView textView = findViewById(R.id.result_tel_input);
        String text = getIntent().getStringExtra("ss");
        BarcodeFormat barcodeFormat = (BarcodeFormat)getIntent().getSerializableExtra("tt");
        Result mresult = new Result(text,null,null,barcodeFormat);
        TelParsedResult telParsedResult = new TelResultParser().parse(mresult);
        textView.setText(telParsedResult.getNumber());
        initCopy(textView);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_result_tel;
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