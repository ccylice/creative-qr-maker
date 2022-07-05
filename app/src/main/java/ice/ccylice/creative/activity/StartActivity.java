package ice.ccylice.creative.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;

import ice.ccylice.creative.R;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().getDecorView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                getWindow().getDecorView().removeOnAttachStateChangeListener(this);
                load();
//                setContentView(R.layout.activity_start);


            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
    }
    private void load(){
        long startTime = SystemClock.elapsedRealtime();


        long elapsedTime = SystemClock.elapsedRealtime() - startTime;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            //这里打开app的首页
            startMain();
            finish();
        }, Math.max(0, 3000 - elapsedTime)); //不足3秒则等待3秒
    }

    private void startMain() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}