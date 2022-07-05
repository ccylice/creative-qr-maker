package ice.ccylice.creative.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ice.ccylice.creative.BuildConfig;
import ice.ccylice.creative.R;


public class SettingsActivity extends BaseActivity {
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        version = findViewById(R.id.setting_tv2);
        String str = BuildConfig.VERSION_NAME;
        try {
            version.setText(String.format(getString(R.string.app_version),str));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMain();
    }
    @Override
    public void onBack() {
        super.onBack();
        goMain();
    }
}