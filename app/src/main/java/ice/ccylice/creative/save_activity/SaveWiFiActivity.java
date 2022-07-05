package ice.ccylice.creative.save_activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.WifiParsedResult;

import ice.ccylice.creative.R;
import ice.ccylice.creative.activity.BaseActivity;


public class SaveWiFiActivity extends BaseActivity{

    private ClipData clipData;
    private ClipboardManager clipboardManager;
    private TextView copy;
    private TextView wifi,pswd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onCopy() {
        super.onCopy();
        wifi = findViewById(R.id.res_wifi_tv1);
        pswd = findViewById(R.id.res_wifi_tv2);
        String text = getIntent().getStringExtra("ss");
        BarcodeFormat barcodeFormat = (BarcodeFormat)getIntent().getSerializableExtra("tt");
        Result mresult = new Result(text,null,null,barcodeFormat);
        WifiParsedResult wifiParsedResult = (WifiParsedResult) ResultParser.parseResult(mresult);
        wifi.setText(wifiParsedResult.getSsid());
        pswd.setText(wifiParsedResult.getPassword());
        initCopy(wifi);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_result_wi_fi;
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