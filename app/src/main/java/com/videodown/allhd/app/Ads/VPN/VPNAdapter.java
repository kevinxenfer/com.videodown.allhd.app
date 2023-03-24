package com.videodown.allhd.app.Ads.VPN;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.preference.PowerPreference;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;

import java.util.ArrayList;
import java.util.List;


public class VPNAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    List<VpnListCountry> arrayList = new ArrayList<>();
    ItemClickListener itemClickListener;
    int selectedPosition = -1;

    public interface ItemClickListener {
        void onClick(String s);
    }

    public VPNAdapter(Activity context, List<VpnListCountry> arrayList,
                      ItemClickListener itemClickListener) {
        this.activity = context;
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;

        for (int i = 0; i < arrayList.size(); i++) {
            if (PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, PowerPreference.getDefaultFile().getString(Constant.Default_Country_code, "us")).equalsIgnoreCase(arrayList.get(i).getCode())) {
                selectedPosition = i;
                break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vpn, parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,
                                 int position) {


        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtName.setText(arrayList.get(position).getName());

        holder.radioButton.setChecked(position
                == selectedPosition);

        if (!activity.isFinishing()) {
            Glide.with(activity)
                    .load(arrayList.get(position).getFlag())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.ivImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.radioButton.setChecked(true);
            }
        });

        // set listener on radio button
        holder.radioButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(
                            CompoundButton compoundButton,
                            boolean b) {
                        // check condition
                        if (b) {
                            // When checked
                            // update selected position
                            selectedPosition
                                    = holder.getAdapterPosition();
                            // Call listener
                            itemClickListener.onClick(arrayList.get(holder.getAdapterPosition()).getCode());
                        }
                    }
                });
    }

    @Override
    public long getItemId(int position) {
        // pass position
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        // pass position
        return position;
    }

    @Override
    public int getItemCount() {
        // pass total list size
        return arrayList.size();
    }

    public void refreshData(List<VpnListCountry> arrayList) {
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        // Initialize variable
        RadioButton radioButton;
        TextView txtName;
        TextView code;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Assign variable
            radioButton
                    = itemView.findViewById(R.id.radio_button);
            txtName = itemView.findViewById(R.id.vpn_country_name);
            code = itemView.findViewById(R.id.code);
            ivImage = itemView.findViewById(R.id.flag);
        }
    }
}
