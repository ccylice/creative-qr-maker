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



import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ResultParser;

import ice.ccylice.creative.R;
import ice.ccylice.creative.activity.BaseActivity;


public class SaveContactsActivity extends BaseActivity {
    private TextView name,phone,email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected int initLayout() {
        return R.layout.activity_result_contacts;
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
    public void onCopy() {
        name = findViewById(R.id.res_con_tv1);
        phone = findViewById(R.id.res_con_tv2);
        email = findViewById(R.id.res_con_tv3);
        String text = getIntent().getStringExtra("ss");
        BarcodeFormat barcodeFormat = (BarcodeFormat)getIntent().getSerializableExtra("tt");
        Result mresult = new Result(text,null,null,barcodeFormat);
        AddressBookParsedResult addressBookParsedResult = (AddressBookParsedResult) ResultParser.parseResult(mresult);
        //name.setText(text);
        String[] text1 = addressBookParsedResult.getNames();
        if (text1==null||text1.length<1){
            name.setText("");
        }else {
            name.setText(text1[0]);
        }
        String[] text2 = addressBookParsedResult.getPhoneNumbers();
        phone.setText(text2[0]);
        String[] text3 = addressBookParsedResult.getEmails();
        email.setText(text3[0]);
        super.onCopy();
        initCopy(name);
    }

    @Override
    protected void onShare() {
        super.onShare();
        String text = getIntent().getStringExtra("ss");
        initShare(text);
    }
}