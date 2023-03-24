package com.videodown.allhd.app.AVideo.VideoAdepter;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.videodown.allhd.app.Activity.Other.PreferenceUtil;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.AVideo.VideoModel.VideoItem;
import com.videodown.allhd.app.AVideo.Videoplayer.VideoPlayerActivity;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


public class MVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public boolean hdvideoplayer_action = false;
    public boolean hdvideoplayer_checktrue = false;
    public final Activity hdvideoplayer_ctx;
    public List<VideoItem> hdvideoplayer_filteredvideoList;
    public final List<VideoItem> hdvideoplayer_list;
    public ActionMode hdvideoplayer_mActionMode;
    public final SparseBooleanArray hdvideoplayer_mSparseBoolMultiSelect = new SparseBooleanArray();
    public final Boolean hdvideoplayer_view;
    private final int hdvideoplayer_wi;

    public int getRealPosition(int i) {
        return i;
    }

    class holder extends RecyclerView.ViewHolder {
        final ImageView hdvideoplayer_i1;
        int hdvideoplayer_position;
        final CheckBox hdvideoplayer_s1;
        TextView hdvideoplayer_txtDate;
        TextView hdvideoplayer_txtDuration;
        TextView hdvideoplayer_txtName;
        TextView hdvideoplayer_txtNew;
        TextView hdvideoplayer_txtSize;
        int hdvideoplayer_xposition;

        holder(View view) {
            super(view);
            this.hdvideoplayer_i1 = (ImageView) view.findViewById(R.id.thumb);
            TextView textView = (TextView) view.findViewById(R.id.name);
            this.hdvideoplayer_txtName = textView;
            textView.setSelected(true);
            this.hdvideoplayer_txtDuration = (TextView) view.findViewById(R.id.duration);
            this.hdvideoplayer_s1 = (CheckBox) view.findViewById(R.id.delete);
            this.hdvideoplayer_txtNew = (TextView) view.findViewById(R.id.txtNew);
            if (MVideoAdapter.this.hdvideoplayer_view.booleanValue()) {
                this.hdvideoplayer_txtSize = (TextView) view.findViewById(R.id.size);
                this.hdvideoplayer_txtDate = (TextView) view.findViewById(R.id.date);
            }
            view.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    holder.this.lambda$new$0$MVideoAdapter$holder(view);
                }
            });
        }

        public void lambda$new$0$MVideoAdapter$holder(View view) {
            if (MVideoAdapter.this.hdvideoplayer_action) {
                if (MVideoAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.get(this.hdvideoplayer_xposition)) {
                    view.setSelected(false);
                    this.hdvideoplayer_s1.setChecked(false);
                    MVideoAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.delete(this.hdvideoplayer_xposition);
                    if (MVideoAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.size() == 0) {
                        MVideoAdapter.this.hdvideoplayer_mActionMode.finish();
                        MVideoAdapter.this.notifyDataSetChanged();
                        return;
                    }
                }
                view.setSelected(true);
                this.hdvideoplayer_s1.setChecked(true);
                MVideoAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.put(this.hdvideoplayer_xposition, true);
                MVideoAdapter.this.hdvideoplayer_mActionMode.setTitle((CharSequence) String.format("%d selected", new Object[]{Integer.valueOf(MVideoAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.size())}));
                return;
            }
            PreferenceUtil instance = PreferenceUtil.getInstance(MVideoAdapter.this.hdvideoplayer_ctx);
            Activity activity = MVideoAdapter.this.hdvideoplayer_ctx;
            instance.setIsPlayVideo(activity, true, "" + MVideoAdapter.this.hdvideoplayer_filteredvideoList.get(MVideoAdapter.this.getRealPosition(this.hdvideoplayer_position)).getDISPLAY_NAME());


            new MainAds().showInterAds(hdvideoplayer_ctx, new InterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {

                    final Intent intent = new Intent(MVideoAdapter.this.hdvideoplayer_ctx, VideoPlayerActivity.class);
                    intent.putExtra("list", (Serializable) MVideoAdapter.this.hdvideoplayer_filteredvideoList);
                    intent.putExtra("position", hdvideoplayer_position);
                    MVideoAdapter.this.hdvideoplayer_ctx.startActivity(intent);

                }
            });


        }
    }

    public MVideoAdapter(Activity activity, List<VideoItem> list, int i, boolean z) {
        this.hdvideoplayer_ctx = activity;
        this.hdvideoplayer_list = list;
        this.hdvideoplayer_wi = i;
        this.hdvideoplayer_filteredvideoList = list;
        this.hdvideoplayer_view = Boolean.valueOf(z);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater from = LayoutInflater.from(this.hdvideoplayer_ctx);
        if (this.hdvideoplayer_view.booleanValue()) {
            view = from.inflate(R.layout.video_list, viewGroup, false);
        } else {
            view = from.inflate(R.layout.video_tile, viewGroup, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.thumb);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height = (this.hdvideoplayer_wi / 2) - 100;
            imageView.setLayoutParams(layoutParams);
        }
        return new holder(view);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        holder holder2 = (holder) viewHolder;
        holder2.hdvideoplayer_position = getRealPosition(i);
        holder2.hdvideoplayer_xposition = i;
        TextView textView = holder2.hdvideoplayer_txtName;
        holder2.hdvideoplayer_txtName.setText(this.hdvideoplayer_filteredvideoList.get(getRealPosition(i)).getDISPLAY_NAME());
        holder2.hdvideoplayer_txtDuration.setText(this.hdvideoplayer_filteredvideoList.get(getRealPosition(i)).getDURATION());
        if (this.hdvideoplayer_view.booleanValue()) {
            TextView textView2 = holder2.hdvideoplayer_txtSize;
            TextView textView3 = holder2.hdvideoplayer_txtDate;
            holder2.hdvideoplayer_txtSize.setText(String.format("Size: %s", new Object[]{this.hdvideoplayer_filteredvideoList.get(getRealPosition(i)).getSIZE()}));
            holder2.hdvideoplayer_txtDate.setText(String.format("Modified: %s", new Object[]{this.hdvideoplayer_filteredvideoList.get(getRealPosition(i)).getDATE()}));
        }
        Glide.with(this.hdvideoplayer_ctx).load(this.hdvideoplayer_filteredvideoList.get(getRealPosition(i)).getDATA()).into(holder2.hdvideoplayer_i1);
        if (this.hdvideoplayer_action) {
            holder2.hdvideoplayer_s1.setVisibility(0);
        } else {
            holder2.hdvideoplayer_s1.setVisibility(4);
        }
        if (this.hdvideoplayer_mSparseBoolMultiSelect.get(getRealPosition(i))) {
            holder2.itemView.setSelected(true);
            holder2.hdvideoplayer_s1.setChecked(true);
        } else {
            holder2.itemView.setSelected(false);
            holder2.hdvideoplayer_s1.setChecked(false);
        }
        PreferenceUtil.getInstance(this.hdvideoplayer_ctx);
        this.hdvideoplayer_filteredvideoList.get(getRealPosition(i)).getDISPLAY_NAME();
    }

    public int getItemCount() {
        return this.hdvideoplayer_filteredvideoList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            public FilterResults performFiltering(CharSequence charSequence) {
                String charSequence2 = charSequence.toString();
                if (charSequence2.isEmpty()) {
                    MVideoAdapter mVideoAdapter = MVideoAdapter.this;
                    mVideoAdapter.hdvideoplayer_filteredvideoList = mVideoAdapter.hdvideoplayer_list;
                } else {
                    ArrayList arrayList = new ArrayList();
                    for (VideoItem next : MVideoAdapter.this.hdvideoplayer_list) {
                        if (next.getDISPLAY_NAME().toLowerCase().contains(charSequence2.toLowerCase())) {
                            arrayList.add(next);
                        }
                    }
                    MVideoAdapter.this.hdvideoplayer_filteredvideoList = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = MVideoAdapter.this.hdvideoplayer_filteredvideoList;
                return filterResults;
            }

            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                MVideoAdapter.this.hdvideoplayer_filteredvideoList = (List) filterResults.values;
                MVideoAdapter.this.notifyDataSetChanged();
            }
        };
    }

    public void moveFile(File file, File file2) throws IOException {
        File file3 = new File(file2, file.getName());
        FileChannel channel = new FileOutputStream(file3).getChannel();
        FileChannel channel2 = new FileInputStream(file).getChannel();
        try {
            channel2.transferTo(0, channel2.size(), channel);
            channel2.close();
            file.delete();
            this.hdvideoplayer_ctx.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file3)));
        } finally {
            if (channel2 != null) {
                channel2.close();
            }
            if (channel != null) {
                channel.close();
            }
        }
    }
}
