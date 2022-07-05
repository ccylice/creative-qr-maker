package ice.ccylice.creative.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ice.ccylice.creative.BuildConfig;
import ice.ccylice.creative.R;
import ice.ccylice.creative.util.QRCodeUtil;


public class QRcodeActivity extends BaseActivity {
    ImageView back,QRCode,QRCode_Icon;
    TextView tv_title,tv_name,save,share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QRCode = findViewById(R.id.qrcode);
        QRCode_Icon = findViewById(R.id.qrcode_icon);
        tv_title = findViewById(R.id.qrcode_tv1);
        tv_name = findViewById(R.id.qrcode_tv2);

        int ss = getIntent().getIntExtra("icon",-1);
        String title = getIntent().getStringExtra("tv_title");
        String name = getIntent().getStringExtra("tv_name");
        String tit = getIntent().getStringExtra("tv_tit");


        Bitmap qrcode = QRCodeUtil.createQRCodeBitmap(title, 650, 650, "UTF-8",null, Color.BLACK, Color.WHITE);
        QRCode.setImageBitmap(qrcode);
        tv_title.setText(tit);
        tv_name.setText(name);
        QRCode_Icon.setImageResource(ss);

        save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(QRcodeActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(QRcodeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                return;

            }


            File pathFile = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES);
            if (!pathFile.exists()){
                pathFile.mkdir();
            }
            File file = new File(pathFile,name + ".png");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                qrcode.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(QRcodeActivity.this, getString(R.string.Success), Toast.LENGTH_SHORT).show();
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri);
                sendBroadcast(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        share = findViewById(R.id.share);
        share.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(QRcodeActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(QRcodeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return;

            }
            File pathFile = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES);
            if (!pathFile.exists()){
                pathFile.mkdir();
            }
            File file = new File(pathFile,name + ".png");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                qrcode.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                //Uri uri = Uri.fromFile(file);
                Uri uri = FileProvider.getUriForFile(QRcodeActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri);
                sendBroadcast(intent);
                Intent mintent = new Intent(Intent.ACTION_SEND);
                mintent.setType("image/png");
                mintent.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(mintent,getTitle()));
            } catch (IOException e) {
                e.printStackTrace();
            }


        });
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.putExtra(Intent.EXTRA_STREAM,qrcode);
//                startActivity(Intent.createChooser(intent,title));
//
//            }
//        });

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_qrcode;
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