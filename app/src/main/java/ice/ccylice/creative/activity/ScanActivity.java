package ice.ccylice.creative.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import com.google.zxing.client.result.TelParsedResult;
import com.google.zxing.client.result.WifiParsedResult;

import ice.ccylice.creative.R;
import ice.ccylice.creative.data.Database;
import ice.ccylice.creative.data.QRcode;
import ice.ccylice.creative.save_activity.SaveCalendarActivity;
import ice.ccylice.creative.save_activity.SaveContactsActivity;
import ice.ccylice.creative.save_activity.SaveEmailActivity;
import ice.ccylice.creative.save_activity.SaveFacebookActivity;
import ice.ccylice.creative.save_activity.SaveInstagramActivity;
import ice.ccylice.creative.save_activity.SavePaypalActivity;
import ice.ccylice.creative.save_activity.SaveSMSActivity;
import ice.ccylice.creative.save_activity.SaveTelActivity;
import ice.ccylice.creative.save_activity.SaveTextActivity;
import ice.ccylice.creative.save_activity.SaveTwitterActivity;
import ice.ccylice.creative.save_activity.SaveViberActivity;
import ice.ccylice.creative.save_activity.SaveWebsiteActivity;
import ice.ccylice.creative.save_activity.SaveWhatsappActivity;
import ice.ccylice.creative.save_activity.SaveWiFiActivity;
import ice.ccylice.creative.save_activity.SaveYoutubeActivity;
import ice.ccylice.creative.util.ImageUtil;
import ice.dkaishu.zxinglib.activity.CaptureFragment;
import ice.dkaishu.zxinglib.activity.CodeUtils;


public class ScanActivity extends BaseActivity {
    CaptureFragment captureFragment;
    public static final int REQUEST_IMAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.custom_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

        ImageView textView = findViewById(R.id.flashlight);
        textView.setOnClickListener(new View.OnClickListener() {
            boolean a = false;
            @Override
            public void onClick(View v) {
                if (a){
                    CodeUtils.setLight(false);
                    a = false;
                }else {
                    CodeUtils.setLight(true);
                    a = true;
                }

            }
        });
        ImageView textView1 = findViewById(R.id.gallery);
        textView1.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE);
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_scan;
    }
    /**
     * 二维码解析回调函数c
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, Result result) {

            ParsedResult parsedResult = ResultParser.parseResult(result);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("ss",result.getText());
//            resultIntent.putExtras(parsedResult.getType())
            resultIntent.putExtra("tt",result.getBarcodeFormat());
            String pic_type;
            String title;
            switch (parsedResult.getType()){
                case ADDRESSBOOK:
                    pic_type = "Contacts";
                    resultIntent.setClass(ScanActivity.this, SaveContactsActivity.class);
                    AddressBookParsedResult addressBookParsedResult = (AddressBookParsedResult) parsedResult;
                    String[] text1 = addressBookParsedResult.getNames();
                    if (text1==null||text1.length<1){
                        title = "";
                    }else {
                        title = text1[0];
                    }
                    break;
                case EMAIL_ADDRESS:
                    pic_type = "Email";
                    title = result.getText();
                    resultIntent.setClass(ScanActivity.this, SaveEmailActivity.class);
                    break;
                case URI:
                    if (result.getText().contains("www.youtube.com/")){
                        pic_type = "Youtube";
                        title = result.getText();
                        resultIntent.setClass(ScanActivity.this, SaveYoutubeActivity.class);
                    }
                    else if (result.getText().contains("www.twitter.com/")){
                        pic_type = "Twitter";
                        title = result.getText();
                        resultIntent.setClass(ScanActivity.this, SaveTwitterActivity.class);
                    }
                    else if (result.getText().contains("www.viber")){
                        pic_type = "Viber";
                        title = result.getText();
                        resultIntent.setClass(ScanActivity.this, SaveViberActivity.class);
                    }
                    else if (result.getText().contains("www.paypal.com/")){
                        pic_type = "Paypal";
                        title = result.getText();
                        resultIntent.setClass(ScanActivity.this, SavePaypalActivity.class);
                    }
                    else if (result.getText().contains("www.instagram.com/")){
                        pic_type = "Instagram";
                        title = result.getText();
                        resultIntent.setClass(ScanActivity.this, SaveInstagramActivity.class);
                    }
                    else if (result.getText().contains("wa.me/")){
                        pic_type = "Whatsapp";
                        title = result.getText();
                        resultIntent.setClass(ScanActivity.this, SaveWhatsappActivity.class);
                    }
                    else if (result.getText().contains("www.facebook.com/")){
                        pic_type = "Facebook";
                        title = result.getText();
                        resultIntent.setClass(ScanActivity.this, SaveFacebookActivity.class);
                    }
                    else {
                        pic_type = "Website";
                        title = result.getText();
                        resultIntent.setClass(ScanActivity.this, SaveWebsiteActivity.class);
                    }
                    break;
                case TEL:
                    pic_type = "Tel";
                    TelParsedResult telParsedResult = (TelParsedResult) parsedResult;
                    title = telParsedResult.getNumber();
                    resultIntent.setClass(ScanActivity.this, SaveTelActivity.class);
                    break;
                case SMS:
                    pic_type = "SMS";
                    SMSParsedResult smsParsedResult = (SMSParsedResult) parsedResult;
                    title = smsParsedResult.getNumbers()[0];
                    resultIntent.setClass(ScanActivity.this, SaveSMSActivity.class);
                    break;
                case CALENDAR:
                    pic_type = "Calendar";
                    CalendarParsedResult calendarParsedResult = (CalendarParsedResult) parsedResult;
                    title = calendarParsedResult.getSummary();
                    resultIntent.setClass(ScanActivity.this, SaveCalendarActivity.class);
                    break;
                case WIFI:
                    pic_type = "Wi-Fi";
                    WifiParsedResult wifiParsedResult = (WifiParsedResult) parsedResult;
                    title = wifiParsedResult.getSsid();
                    resultIntent.setClass(ScanActivity.this, SaveWiFiActivity.class);
                    break;
                default:
                    pic_type = "Text";
                    title = result.getText();
                    resultIntent.setClass(ScanActivity.this, SaveTextActivity.class);
                    break;
            }

            startActivity(resultIntent);

            String finalTitle = title;
            String finalPic_type = pic_type;
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Database database = Database.getInstance(ScanActivity.this);
                    database.qRcodeDao().insertQrcode(new QRcode(result.getText(),result.getBarcodeFormat().ordinal(),1, finalTitle, finalPic_type));
                }
            }.start();
//           database.qRcodeDao().insertQrcode(new QRcode(result.getText(),result.getBarcodeFormat().ordinal(),1,title,pic_type));
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle       = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);

//            CustomActivity.this.finish();
        }
    };
    private ActivityResultLauncher<Intent> myResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == REQUEST_IMAGE){
                        Intent intent = result.getData();
                    }
                }
            }
    );
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(ScanActivity.this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, Result result) {
                            ParsedResult parsedResult = ResultParser.parseResult(result);
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("ss",result.getText());
                            resultIntent.putExtra("tt",result.getBarcodeFormat());


                            String pic_type;
                            String title;
                            //new Result()
                            switch (parsedResult.getType()){
                                case ADDRESSBOOK:
                                    pic_type = "Contacts";
                                    resultIntent.setClass(ScanActivity.this, SaveContactsActivity.class);
                                    AddressBookParsedResult addressBookParsedResult = (AddressBookParsedResult) parsedResult;
                                    String[] text1 = addressBookParsedResult.getNames();
                                    if (text1==null||text1.length<1){
                                        title = "";
                                    }else {
                                        title = text1[0];
                                    }
                                    break;
                                case EMAIL_ADDRESS:
                                    pic_type = "Email";
                                    title = result.getText();
                                    resultIntent.setClass(ScanActivity.this, SaveEmailActivity.class);
                                    break;
                                case URI:
                                    if (result.getText().contains("www.youtube.com/")){
                                        pic_type = "Youtube";
                                        title = result.getText();
                                        resultIntent.setClass(ScanActivity.this, SaveYoutubeActivity.class);
                                    }
                                    else if (result.getText().contains("www.twitter.com/")){
                                        pic_type = "Twitter";
                                        title = result.getText();
                                        resultIntent.setClass(ScanActivity.this, SaveTwitterActivity.class);
                                    }
                                    else if (result.getText().contains("www.viber")){
                                        pic_type = "Viber";
                                        title = result.getText();
                                        resultIntent.setClass(ScanActivity.this, SaveViberActivity.class);
                                    }
                                    else if (result.getText().contains("www.paypal.com/")){
                                        pic_type = "Paypal";
                                        title = result.getText();
                                        resultIntent.setClass(ScanActivity.this, SavePaypalActivity.class);
                                    }
                                    else if (result.getText().contains("www.instagram.com/")){
                                        pic_type = "Instagram";
                                        title = result.getText();
                                        resultIntent.setClass(ScanActivity.this, SaveInstagramActivity.class);
                                    }
                                    else if (result.getText().contains("wa.me/")){
                                        pic_type = "Whatsapp";
                                        title = result.getText();
                                        resultIntent.setClass(ScanActivity.this, SaveWhatsappActivity.class);
                                    }
                                    else if (result.getText().contains("www.facebook.com/")){
                                        pic_type = "Facebook";
                                        title = result.getText();
                                        resultIntent.setClass(ScanActivity.this, SaveFacebookActivity.class);
                                    }
                                    else {
                                        pic_type = "Website";
                                        title = result.getText();
                                        resultIntent.setClass(ScanActivity.this, SaveWebsiteActivity.class);
                                    }
                                    break;
                                case TEL:
                                    pic_type = "Tel";
                                    TelParsedResult telParsedResult = (TelParsedResult) parsedResult;
                                    title = telParsedResult.getNumber();
                                    resultIntent.setClass(ScanActivity.this, SaveTelActivity.class);
                                    break;
                                case SMS:
                                    pic_type = "SMS";
                                    SMSParsedResult smsParsedResult = (SMSParsedResult) parsedResult;
                                    title = smsParsedResult.getNumbers()[0];
                                    resultIntent.setClass(ScanActivity.this, SaveSMSActivity.class);
                                    break;
                                case CALENDAR:
                                    pic_type = "Calendar";
                                    CalendarParsedResult calendarParsedResult = (CalendarParsedResult) parsedResult;
                                    title = calendarParsedResult.getSummary();
                                    resultIntent.setClass(ScanActivity.this, SaveCalendarActivity.class);
                                    break;
                                case WIFI:
                                    pic_type = "Wi-Fi";
                                    WifiParsedResult wifiParsedResult = (WifiParsedResult) parsedResult;
                                    title = wifiParsedResult.getSsid();
                                    resultIntent.setClass(ScanActivity.this, SaveWiFiActivity.class);
                                    break;
                                default:
                                    pic_type = "Text";
                                    title = result.getText();
                                    resultIntent.setClass(ScanActivity.this, SaveTextActivity.class);
                                    break;
                            }
                            startActivity(resultIntent);
                            String finalTitle = title;
                            String finalPic_type = pic_type;
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    Database database = Database.getInstance(ScanActivity.this);
                                    database.qRcodeDao().insertQrcode(new QRcode(result.getText(),result.getBarcodeFormat().ordinal(),1, finalTitle, finalPic_type));
                                }
                            }.start();
                        }
                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ScanActivity.this, "error", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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