package ice.ccylice.creative.save_activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import ice.ccylice.creative.R;
import ice.ccylice.creative.activity.BaseActivity;


public class SaveTextActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCopy() {
        super.onCopy();
        TextView textView = findViewById(R.id.result_text_input);
        String text = getIntent().getStringExtra("ss");
        textView.setText(text);
        initCopy(textView);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_result_text;
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