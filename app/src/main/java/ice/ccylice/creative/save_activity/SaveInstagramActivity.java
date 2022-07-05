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



import ice.ccylice.creative.R;
import ice.ccylice.creative.activity.BaseActivity;
import ice.ccylice.creative.util.SaveUtil;


public class SaveInstagramActivity extends BaseActivity {
    private TextView textView;
    private ClipData clipData;
    private ClipboardManager clipboardManager;
    private TextView copy;

    private RelativeLayout open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = findViewById(R.id.result_instagram_tv);


        copy = findViewById(R.id.copy);
        String text = getIntent().getStringExtra("ss");
        textView.setText(text);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        copy.setOnClickListener(v -> {
            String data = textView.getText().toString();
            clipData = ClipData.newPlainText("text",data);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(SaveInstagramActivity.this, "Copy success", Toast.LENGTH_SHORT).show();
        });
        open = findViewById(R.id.Ins_open);
        open.setOnClickListener(v -> SaveUtil.openURL(text, SaveInstagramActivity.this));
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_result_instagram;
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