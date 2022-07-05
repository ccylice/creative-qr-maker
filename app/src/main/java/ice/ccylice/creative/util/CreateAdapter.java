package ice.ccylice.creative.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import ice.ccylice.creative.bean.CreateModel;
import ice.ccylice.creative.R;

public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.ContacHolder>{
    private Context context;
    public List<CreateModel> data;
    private onItemViewClickListener listener;

    public CreateAdapter(Context context){

        this.context = context;
    }
    public void setData(List<CreateModel> data){
        this.data = data;
    }

    public onItemViewClickListener getListener() {
        return listener;
    }

    public void setListener(onItemViewClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ContacHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContacHolder(LayoutInflater.from(context). inflate(R.layout.list_create_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ContacHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.createIcon.setImageResource(data.get(position).getCreateIcon());
        holder.createText.setText(data.get(position).getCreateText());//品牌
        holder.item.setOnClickListener(v -> listener.onItemClick(position));

    }



    @Override
    public int getItemCount() {
        return null != data ? data.size() : 0;
    }

    class ContacHolder extends RecyclerView.ViewHolder {

        ImageView createIcon;//图片

        TextView createText;//品牌

        RelativeLayout item;


        public ContacHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.create_item);
            createIcon = itemView.findViewById(R.id.list_item_icon);//图片
            createText = itemView.findViewById(R.id.list_item_text);//名字

        }
    }
    public interface onItemViewClickListener{
        void onItemClick(int position);//条目单击
    }
}
