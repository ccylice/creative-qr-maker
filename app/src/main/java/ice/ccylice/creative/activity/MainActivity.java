package ice.ccylice.creative.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ice.ccylice.creative.R;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {openQuitDialog();}

    private void openQuitDialog() {
            startActivity(new Intent(this, ExitActivity.class));
            finish();
    }

    public void onScan(View v){
            startActivity(new Intent(this,ScanActivity.class));
    }

    public void onCreates(View v){
            startActivity(new Intent(this,CreateActivity.class));
    }

    public void onHistory(View v){startActivity(new Intent(this,HistoryActivity.class));}

    public void onSettings(View v){startActivity(new Intent(this,SettingsActivity.class));}
}