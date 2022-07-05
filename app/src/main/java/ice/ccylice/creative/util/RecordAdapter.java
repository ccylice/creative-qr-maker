package ice.ccylice.creative.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import ice.ccylice.creative.R;
import ice.ccylice.creative.data.QRcode;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ContacHolder> {

    private Context context;
    public List<QRcode> data = new ArrayList<>();
    private onItemViewClickListener listener;
    private OnItemLongClickListener longClickListener;

    public RecordAdapter(Context context){

        this.context = context;
    }
    public void setData(List<QRcode> data){
        this.data = data;
    }

    public onItemViewClickListener getListener() {
        return listener;
    }

    public void setListener(onItemViewClickListener listener) {
        this.listener = listener;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ContacHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContacHolder(LayoutInflater.from(context). inflate(R.layout.list_record_item,parent ,false));
    }

    @NonNull



    @Override
    public void onBindViewHolder(@NonNull ContacHolder holder, @SuppressLint("RecyclerView") int position) {
        QRcode qRcode = data.get(position);
        holder.recordIconName.setText(qRcode.pic_type);
        holder.recordText.setText(qRcode.title);

//        int ii= A.map.get(qRcode.pic_type);
//        holder.recordIcon.setImageResource(ii);
        switch (qRcode.pic_type) {
            case "Contacts":
                holder.recordIcon.setImageResource(R.drawable.contacts);
                break;
            case "Email":
                holder.recordIcon.setImageResource(R.drawable.e_mail);
                break;
            case "Youtube":
                holder.recordIcon.setImageResource(R.drawable.youtube);
                break;
            case "Twitter":
                holder.recordIcon.setImageResource(R.drawable.twitter);
                break;
            case "Viber":
                holder.recordIcon.setImageResource(R.drawable.viber);
                break;
            case "Paypal":
                holder.recordIcon.setImageResource(R.drawable.paypal);
                break;
            case "Instagram":
                holder.recordIcon.setImageResource(R.drawable.instagram);
                break;
            case "Whatsapp":
                holder.recordIcon.setImageResource(R.drawable.whatsapp);
                break;
            case "Facebook":
                holder.recordIcon.setImageResource(R.drawable.facebook);
                break;
            case "Website":
                holder.recordIcon.setImageResource(R.drawable.website);
                break;
            case "Tel":
                holder.recordIcon.setImageResource(R.drawable.tel);
                break;
            case "SMS":
                holder.recordIcon.setImageResource(R.drawable.sms);
                break;
            case "Calendar":
                holder.recordIcon.setImageResource(R.drawable.calendar);
                break;
            case "Wi-Fi":
                holder.recordIcon.setImageResource(R.drawable.wifi);
                break;
            case "Text":
                holder.recordIcon.setImageResource(R.drawable.text);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {//条目点击
            @Override
            public void onClick(View view) {
                if(null != listener){
                    listener.onItemClick(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != longClickListener){
                    longClickListener.onLongClick(position);
                }
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ContacHolder extends RecyclerView.ViewHolder {

        ImageView recordIcon;

        TextView recordText;

        TextView recordIconName;

        ImageView record_forward;


        public ContacHolder(@NonNull View itemView) {
            super(itemView);
            recordIcon = itemView.findViewById(R.id.record_item_icon);
            recordText = itemView.findViewById(R.id.record_item_text);
            recordIconName = itemView.findViewById(R.id.record_item_icon_name);
            record_forward = itemView.findViewById(R.id.record_forward);



        }
    }
    public interface onItemViewClickListener{
        void onItemClick(int position);//条目单击
    }

    public interface OnItemLongClickListener{
        void onLongClick(int position);
    }

}
