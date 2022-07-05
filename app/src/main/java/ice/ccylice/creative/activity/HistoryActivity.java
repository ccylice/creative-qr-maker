package ice.ccylice.creative.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import com.google.zxing.client.result.TelParsedResult;
import com.google.zxing.client.result.WifiParsedResult;

import java.util.ArrayList;
import java.util.List;

import ice.ccylice.creative.util.MyPagerAdapter;
import ice.ccylice.creative.util.RecordAdapter;
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


public class HistoryActivity extends BaseActivity {
    ViewPager viewPager;
    ArrayList<View> views;
    View view1, view2;
    ArrayList<String> mtitle;
    MyPagerAdapter myPagerAdapter;
    TabLayout mTab;
    List<QRcode> scanList, createList;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = findViewById(R.id.viewpager);
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.record_view1, null);
        view2 = inflater.inflate(R.layout.record_view2, null);
        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);

        mtitle = new ArrayList<>();
        mtitle.add(getString(R.string.Scan));
        mtitle.add(getString(R.string.Create));

        mTab = findViewById(R.id.tab);
        for (int i = 0; i < mtitle.size(); i++) {
            mTab.addTab(mTab.newTab().setText(mtitle.get(i)));
        }
        mTab.setupWithViewPager(viewPager);
        myPagerAdapter = new MyPagerAdapter(views, mtitle);
        viewPager.setAdapter(myPagerAdapter);
        scan();
        creation();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_history;
    }

    private void scan() {
        List<QRcode> data = new ArrayList<>();
        RecyclerView recyclerView = view1.findViewById(R.id.record_view1_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecordAdapter recordAdapter = new RecordAdapter(this);
        recordAdapter.setData(data);
        recyclerView.setAdapter(recordAdapter);

        Database database = Database.getInstance(this);
        new Thread() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                super.run();
                List<QRcode> list = database.qRcodeDao().getAll();
                scanList = new ArrayList<>();
                for (QRcode o : list) {
                    if (o.type == 1) {
                        scanList.add(o);
                    }
                }
                data.addAll(scanList);

                recyclerView.post(() -> recordAdapter.notifyDataSetChanged());

            }
        }.start();
        recordAdapter.setListener(position -> {
            QRcode qRcode = data.get(position);

            Result result = new Result(qRcode.text, null, null, BarcodeFormat.values()[qRcode.ordinal]);
            ParsedResult parsedResult = ResultParser.parseResult(result);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("ss", result.getText());
            resultIntent.putExtra("tt", result.getBarcodeFormat());
            if (qRcode.type == 1) {
                switch (parsedResult.getType()) {
                    case ADDRESSBOOK:
                        resultIntent.setClass(HistoryActivity.this, SaveContactsActivity.class);
                        AddressBookParsedResult addressBookParsedResult = (AddressBookParsedResult) parsedResult;
                        break;
                    case EMAIL_ADDRESS:
                        resultIntent.setClass(HistoryActivity.this, SaveEmailActivity.class);
                        break;
                    case URI:
                        if (result.getText().contains("www.youtube.com/")) {
                            resultIntent.setClass(HistoryActivity.this, SaveYoutubeActivity.class);
                        } else if (result.getText().contains("www.twitter.com/")) {
                            resultIntent.setClass(HistoryActivity.this, SaveTwitterActivity.class);
                        } else if (result.getText().contains("www.viber")) {
                            resultIntent.setClass(HistoryActivity.this, SaveViberActivity.class);
                        } else if (result.getText().contains("www.paypal.com/")) {
                            resultIntent.setClass(HistoryActivity.this, SavePaypalActivity.class);
                        } else if (result.getText().contains("www.instagram.com/")) {
                            resultIntent.setClass(HistoryActivity.this, SaveInstagramActivity.class);
                        } else if (result.getText().contains("wa.me/")) {
                            resultIntent.setClass(HistoryActivity.this, SaveWhatsappActivity.class);
                        } else if (result.getText().contains("www.facebook.com/")) {
                            resultIntent.setClass(HistoryActivity.this, SaveFacebookActivity.class);
                        } else {
                            resultIntent.setClass(HistoryActivity.this, SaveWebsiteActivity.class);
                        }
                        break;
                    case TEL:
                        TelParsedResult telParsedResult = (TelParsedResult) parsedResult;
                        resultIntent.setClass(HistoryActivity.this, SaveTelActivity.class);
                        break;
                    case SMS:
                        SMSParsedResult smsParsedResult = (SMSParsedResult) parsedResult;
                        resultIntent.setClass(HistoryActivity.this, SaveSMSActivity.class);
                        break;
                    case CALENDAR:
                        CalendarParsedResult calendarParsedResult = (CalendarParsedResult) parsedResult;
                        resultIntent.setClass(HistoryActivity.this, SaveCalendarActivity.class);
                        break;
                    case WIFI:
                        WifiParsedResult wifiParsedResult = (WifiParsedResult) parsedResult;
                        resultIntent.setClass(HistoryActivity.this, SaveWiFiActivity.class);
                        break;
                    default:
                        resultIntent.setClass(HistoryActivity.this, SaveTextActivity.class);
                        break;
                }
                startActivity(resultIntent);
            }
        });
        recordAdapter.setLongClickListener(new RecordAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(int position) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(HistoryActivity.this);
                dialog.setTitle(getString(R.string.Hint));
                dialog.setMessage(getString(R.string.SureToDelete));
                dialog.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                QRcode qRcode = data.remove(position);
                                Database database = Database.getInstance(HistoryActivity.this);
                                database.qRcodeDao().deleteQrcode(qRcode);
                                //data.remove(position);
                                recyclerView.post(() -> recordAdapter.notifyDataSetChanged());
                            }
                        }.start();
                    }
                });
                dialog.setNegativeButton(getString(R.string.NO), null);
                dialog.create().show();
            }

        });

    }

    private void creation() {
        List<QRcode> data = new ArrayList<>();
        RecyclerView recyclerView = view2.findViewById(R.id.record_view2_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecordAdapter recordAdapter = new RecordAdapter(this);
        recordAdapter.setData(data);
        recyclerView.setAdapter(recordAdapter);

        Database database = Database.getInstance(this);
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<QRcode> list = database.qRcodeDao().getAll();
                createList = new ArrayList<>();

                for (QRcode o : list) {
                    if (o.type == 2) {
                        createList.add(o);
                    }
                }
                ;
                data.addAll(createList);

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recordAdapter.notifyDataSetChanged();
                    }
                });

            }
        }.start();
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                return true;
            }
        });
        recordAdapter.setListener(position -> {
            QRcode qRcode = data.get(position);
            Bundle bundle = new Bundle();

            if (qRcode.type == 2) {
                Intent intent = new Intent(HistoryActivity.this, QRcodeActivity.class);
                switch (qRcode.pic_type) {
                    case "Contacts":
                        bundle.putInt("icon", R.drawable.contacts);
                        break;
                    case "E-mail":
                        bundle.putInt("icon", R.drawable.e_mail);
                        break;
                    case "Youtube":
                        bundle.putInt("icon", R.drawable.youtube);
                        break;
                    case "Twitter":
                        bundle.putInt("icon", R.drawable.twitter);
                        break;
                    case "Viber":
                        bundle.putInt("icon", R.drawable.viber);
                        break;
                    case "Paypal":
                        bundle.putInt("icon", R.drawable.paypal);
                        break;
                    case "Instagram":
                        bundle.putInt("icon", R.drawable.instagram);
                        break;
                    case "Whatsapp":
                        bundle.putInt("icon", R.drawable.whatsapp);
                        break;
                    case "Facebook":
                        bundle.putInt("icon", R.drawable.facebook);
                        break;
                    case "Website":
                        bundle.putInt("icon", R.drawable.website);
                        break;
                    case "Tel":
                        bundle.putInt("icon", R.drawable.tel);
                        break;
                    case "SMS":
                        bundle.putInt("icon", R.drawable.sms);
                        break;
                    case "Calendar":
                        bundle.putInt("icon", R.drawable.calendar);
                        break;
                    case "Wi-Fi":
                        bundle.putInt("icon", R.drawable.wifi);
                        break;
                    case "Text":
                        bundle.putInt("icon", R.drawable.text);
                        break;
                }

                intent.putExtra("tv_name", qRcode.pic_type);
                intent.putExtra("tv_tit", qRcode.title);
                intent.putExtra("tv_title", qRcode.text);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
        recordAdapter.setLongClickListener(new RecordAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(int position) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(HistoryActivity.this);
                dialog.setTitle(getString(R.string.Hint));
                dialog.setMessage(getString(R.string.SureToDelete));
                dialog.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                QRcode qRcode = data.remove(position);
                                Database database = Database.getInstance(HistoryActivity.this);
                                database.qRcodeDao().deleteQrcode(qRcode);
                                //data.remove(position);
                                recyclerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        recordAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }.start();
                    }
                });
                dialog.setNegativeButton(getString(R.string.NO), null);
                dialog.create().show();
            }

        });

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