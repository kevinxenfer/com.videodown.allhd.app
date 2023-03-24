package com.videodown.allhd.app.AVideo.VideoAdepter;

import android.annotation.SuppressLint;
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

import com.videodown.allhd.app.AVideo.VideoModel.VideoItem;
import com.videodown.allhd.app.AVideo.Videoplayer.VideoPlayerActivity;
import com.videodown.allhd.app.Activity.Other.PreferenceUtil;
import com.videodown.allhd.app.R;
import com.bumptech.glide.Glide;
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

public class AllVideoListVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public boolean hdvideoplayer_action = false;
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

        ImageView hdvideoplayer_i1;
        int hdvideoplayer_position;
        CheckBox hdvideoplayer_s1;
        TextView hdvideoplayer_txtDate;
        TextView hdvideoplayer_txtDuration;
        TextView hdvideoplayer_txtName;
        TextView hdvideoplayer_txtNew;
        TextView hdvideoplayer_txtSize;
        int hdvideoplayer_xposition;

        holder(View view) {
            super(view);
            hdvideoplayer_i1 = view.findViewById(R.id.thumb);
            hdvideoplayer_txtName = view.findViewById(R.id.name);
            hdvideoplayer_txtName.setSelected(true);
            hdvideoplayer_txtDuration = view.findViewById(R.id.duration);
            hdvideoplayer_s1 = view.findViewById(R.id.delete);
            hdvideoplayer_txtNew = view.findViewById(R.id.txtNew);
            if (hdvideoplayer_view.booleanValue()) {
                hdvideoplayer_txtSize = view.findViewById(R.id.size);
                hdvideoplayer_txtDate = view.findViewById(R.id.date);
            }
            view.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    if (hdvideoplayer_action) {
                        if (hdvideoplayer_mSparseBoolMultiSelect.get(hdvideoplayer_xposition)) {
                            view.setSelected(false);
                            hdvideoplayer_s1.setChecked(false);
                            hdvideoplayer_mSparseBoolMultiSelect.delete(hdvideoplayer_xposition);
                            if (hdvideoplayer_mSparseBoolMultiSelect.size() == 0) {
                                hdvideoplayer_mActionMode.finish();
                                notifyDataSetChanged();
                                return;
                            }
                        }
                        view.setSelected(true);
                        hdvideoplayer_s1.setChecked(true);
                        hdvideoplayer_mSparseBoolMultiSelect.put(hdvideoplayer_xposition, true);
                        hdvideoplayer_mActionMode.setTitle((CharSequence) String.format("%d selected", new Object[]{Integer.valueOf(AllVideoListVideoAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.size())}));
                        return;
                    }
                    PreferenceUtil instance = PreferenceUtil.getInstance(AllVideoListVideoAdapter.this.hdvideoplayer_ctx);
                    Activity activity = AllVideoListVideoAdapter.this.hdvideoplayer_ctx;
                    instance.setIsPlayVideo(activity, true, "" + AllVideoListVideoAdapter.this.hdvideoplayer_filteredvideoList.get(AllVideoListVideoAdapter.this.getRealPosition(hdvideoplayer_position)).getDISPLAY_NAME());

                    new MainAds().showInterAds(activity, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            final Intent intent = new Intent(AllVideoListVideoAdapter.this.hdvideoplayer_ctx, VideoPlayerActivity.class);
                            intent.putExtra("list", (Serializable) AllVideoListVideoAdapter.this.hdvideoplayer_filteredvideoList);
                            intent.putExtra("position", hdvideoplayer_position);
                            AllVideoListVideoAdapter.this.hdvideoplayer_ctx.startActivity(intent);
                        }
                    });
                }
            });
        }
    }

    public AllVideoListVideoAdapter(Activity activity, List<VideoItem> list, int i, boolean z) {
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

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
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
                    AllVideoListVideoAdapter allVideoListVideoAdapter = AllVideoListVideoAdapter.this;
                    allVideoListVideoAdapter.hdvideoplayer_filteredvideoList = allVideoListVideoAdapter.hdvideoplayer_list;
                } else {
                    ArrayList arrayList = new ArrayList();
                    for (VideoItem next : hdvideoplayer_list) {
                        if (next.getDISPLAY_NAME().toLowerCase().contains(charSequence2.toLowerCase())) {
                            arrayList.add(next);
                        }
                    }
                    AllVideoListVideoAdapter.this.hdvideoplayer_filteredvideoList = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = hdvideoplayer_filteredvideoList;
                return filterResults;
            }

            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hdvideoplayer_filteredvideoList = (List) filterResults.values;
                notifyDataSetChanged();
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
