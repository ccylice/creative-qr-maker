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


public class SaveFacebookActivity extends BaseActivity {
    private TextView textView;
    private ClipData clipData;
    private ClipboardManager clipboardManager;
    private TextView copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCopy() {
        textView = findViewById(R.id.result_face_tv);
        copy = findViewById(R.id.copy);
        String text = getIntent().getStringExtra("ss");
        textView.setText(text);
        super.onCopy();
        initCopy(textView);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_result_facebook;
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