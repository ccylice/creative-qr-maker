package ice.ccylice.creative.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ice.ccylice.creative.save_activity.SaveEmailActivity;
import ice.ccylice.creative.save_activity.SaveYoutubeActivity;
import ice.ccylice.creative.util.ActivityUtils;
import ice.ccylice.creative.R;


public abstract class BaseActivity extends AppCompatActivity {
    protected ImageView iv_back;
    protected TextView create_title;
    protected boolean hasTitle = true;
    RelativeLayout save_share;
    TextView save_copy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        ActivityUtils.addActivity(this);
        if (hasTitle) {
            initTitle();
        }
    }
    protected void initShare(String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(Intent.createChooser(intent,getTitle()));

    }
    protected void initCopy(TextView textView){
        save_copy.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String data = textView.getText().toString();
            ClipData clipData = ClipData.newPlainText("text",data);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this, "Copy success", Toast.LENGTH_SHORT).show();
        });
    }



    protected void onShare(){
    }
    protected void initTitle() {
        iv_back = findViewById(R.id.iv_back);
        create_title = findViewById(R.id.tv_title1);
        save_share = findViewById(R.id.save_share);
        save_copy = findViewById(R.id.copy);
        if (iv_back != null) {
            iv_back.setOnClickListener(v -> onBack());
        }
        if (save_share != null){
            save_share.setOnClickListener(v -> onShare());
        }
        if (save_copy != null){
            save_copy.setOnClickListener(v -> onCopy());
        }
    }
    public void onCopy(){

    }
    protected void setTitle() {
    }
    protected void onBack() {
    }
    protected abstract int initLayout();
    protected void startNext(Intent intent, boolean isFinish) {

        startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }
    public void startNext(Intent intent) {
        startNext(intent, true);
    }
    public void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startNext(intent);
        finish();
    }
}
