package vn.iotstar.ltmob280325.indicator_RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstar.ltmob280325.R;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder> {
    private Context context;
    private List<IconModel> icons;

    public IconAdapter(Context context, List<IconModel> icons) {
        this.context = context;
        this.icons = icons;
    }

    public class IconHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView txtIcon;

        public IconHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            txtIcon = itemView.findViewById(R.id.txt_icon);
        }
    }

    @NonNull
    @Override
    public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IconHolder(LayoutInflater.from(context).inflate(R.layout.row_recycleritem_promotion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconHolder holder, int position) {
        IconModel icon = icons.get(position);
        Glide.with(context).load(icon.getImgId()).into(holder.imgIcon);
        holder.txtIcon.setText(icon.getDesc());
    }

    @Override
    public int getItemCount() {
        return icons == null ? 0 : icons.size();
    }

    public void setListenerList(List<IconModel> icons) {
        this.icons = icons;
        notifyDataSetChanged();
    }
}
