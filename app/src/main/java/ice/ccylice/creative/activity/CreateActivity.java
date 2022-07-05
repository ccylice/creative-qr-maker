package ice.ccylice.creative.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ice.ccylice.creative.util.CreateAdapter;
import ice.ccylice.creative.bean.CreateModel;
import ice.ccylice.creative.R;
import ice.ccylice.creative.create_activity.CreateCalendarActivity;
import ice.ccylice.creative.create_activity.CreateEmailActivity;
import ice.ccylice.creative.create_activity.CreateFacebookActivity;
import ice.ccylice.creative.create_activity.CreateInstagramActivity;
import ice.ccylice.creative.create_activity.CreateLinkmanActivity;
import ice.ccylice.creative.create_activity.CreateNoteActivity;
import ice.ccylice.creative.create_activity.CreatePayPalActivity;
import ice.ccylice.creative.create_activity.CreatePhoneActivity;
import ice.ccylice.creative.create_activity.CreateTextActivity;
import ice.ccylice.creative.create_activity.CreateTwitterActivity;
import ice.ccylice.creative.create_activity.CreateViberActivity;
import ice.ccylice.creative.create_activity.CreateWhatsappActivity;
import ice.ccylice.creative.create_activity.CreateWiFiActivity;
import ice.ccylice.creative.create_activity.CreateYoutubeActivity;
import ice.ccylice.creative.create_activity.CreateWebsiteActivity;
import ice.ccylice.creative.util.Item;


public class CreateActivity extends BaseActivity {
    private List<CreateModel> data;
    CreateAdapter adapter;
    RecyclerView recyclerView;
    private List<Item> list;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        data = new ArrayList<>();
        recyclerView = findViewById(R.id.create_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        adapter = new CreateAdapter(this);
        adapter.setData(data);
        recyclerView.setAdapter(adapter);

        adapter.setListener(position -> {
            CreateModel createModel = data.get(position);
            createModel.getCreateText();
            startActivity(new Intent(context,createModel.getaClass()));

        });

        CreateModel item1 = new CreateModel();
        item1.setCreateIcon(R.drawable.text);
        item1.setCreateText(getString(R.string.Text));
        item1.setaClass(CreateTextActivity.class);
        data.add(item1);

        CreateModel item2 = new CreateModel();
        item2.setCreateIcon(R.drawable.website);
        item2.setCreateText(getString(R.string.Website));
        item2.setaClass(CreateWebsiteActivity.class);
        data.add(item2);

        CreateModel item3 = new CreateModel();
        item3.setCreateIcon(R.drawable.wifi);
        item3.setCreateText(getString(R.string.WiFi));
        item3.setaClass(CreateWiFiActivity.class);
        data.add(item3);

        CreateModel item4 = new CreateModel();
        item4.setCreateIcon(R.drawable.facebook);
        item4.setCreateText(getString(R.string.Facebook));
        item4.setaClass(CreateFacebookActivity.class);
        data.add(item4);

        CreateModel item5 = new CreateModel();
        item5.setCreateIcon(R.drawable.youtube);
        item5.setCreateText(getString(R.string.Youtube));
        item5.setaClass(CreateYoutubeActivity.class);
        data.add(item5);

        CreateModel item6 = new CreateModel();
        item6.setCreateIcon(R.drawable.whatsapp);
        item6.setCreateText(getString(R.string.Whatsapp));
        item6.setaClass(CreateWhatsappActivity.class);
        data.add(item6);


        CreateModel item8 = new CreateModel();
        item8.setCreateIcon(R.drawable.contacts);
        item8.setCreateText(getString(R.string.Contacts));
        item8.setaClass(CreateLinkmanActivity.class);
        data.add(item8);

        CreateModel item9 = new CreateModel();
        item9.setCreateIcon(R.drawable.tel);
        item9.setCreateText(getString(R.string.Tel));
        item9.setaClass(CreatePhoneActivity.class);
        data.add(item9);

        CreateModel item10 = new CreateModel();
        item10.setCreateIcon(R.drawable.e_mail);
        item10.setCreateText(getString(R.string.Email));
        item10.setaClass(CreateEmailActivity.class);
        data.add(item10);

        CreateModel item11 = new CreateModel();
        item11.setCreateIcon(R.drawable.sms);
        item11.setCreateText(getString(R.string.SMS));
        item11.setaClass(CreateNoteActivity.class);
        data.add(item11);


        CreateModel item13 = new CreateModel();
        item13.setCreateIcon(R.drawable.paypal);
        item13.setCreateText(getString(R.string.Paypal));
        item13.setaClass(CreatePayPalActivity.class);
        data.add(item13);

        CreateModel item14 = new CreateModel();
        item14.setCreateIcon(R.drawable.instagram);
        item14.setCreateText(getString(R.string.Instagram));
        item14.setaClass(CreateInstagramActivity.class);
        data.add(item14);

        CreateModel item15 = new CreateModel();
        item15.setCreateIcon(R.drawable.viber);
        item15.setCreateText(getString(R.string.Viber));
        item15.setaClass(CreateViberActivity.class);
        data.add(item15);

        CreateModel item16 = new CreateModel();
        item16.setCreateIcon(R.drawable.twitter);
        item16.setCreateText(getString(R.string.Twitter));
        item16.setaClass(CreateTwitterActivity.class);
        data.add(item16);

        CreateModel item17 = new CreateModel();
        item17.setCreateIcon(R.drawable.calendar);
        item17.setCreateText(getString(R.string.Calendar));
        item17.setaClass(CreateCalendarActivity.class);
        data.add(item17);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_create;
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