package ice.ccylice.creative.activity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import ice.ccylice.creative.util.ActivityUtils;
import ice.ccylice.creative.R;

public class ExitActivity extends BaseActivity {
    private TextView yes;
    private TextView no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hasTitle = false;
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        yes = findViewById(R.id.exit_yes);
        no = findViewById(R.id.exit_no);
        yes.setOnClickListener(v -> {
            ActivityUtils.removeAll();
            System.exit(0);
        });
        no.setOnClickListener(v -> {
            startNext(new Intent(ExitActivity.this,MainActivity.class));
            finish();
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_exit;
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