package com.videodown.allhd.app.AVideo.VideoAdepter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class VideoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public boolean hdvideoplayer_action = false;
    public final Activity hdvideoplayer_ctx;
    public List<VideoItem> hdvideoplayer_filteredvideoList;
    public final List<VideoItem> hdvideoplayer_list;
    public ActionMode hdvideoplayer_mActionMode;
    public final SparseBooleanArray hdvideoplayer_mSparseBoolMultiSelect = new SparseBooleanArray();
    MediaMetadataRetriever hdvideoplayer_retriever;
    public final Boolean hdvideoplayer_view;
    private final int hdvideoplayer_wi;

    class holder extends RecyclerView.ViewHolder {
        ImageView hdvideoplayer_i1;
        int hdvideoplayer_position;
        final CheckBox hdvideoplayer_s1;
        TextView hdvideoplayer_txtDate;
        TextView hdvideoplayer_txtDuration;
        TextView hdvideoplayer_txtName;
        TextView hdvideoplayer_txtNew;
        TextView hdvideoplayer_txtSize;

        holder(View view) {
            super(view);
            this.hdvideoplayer_i1 = (ImageView) view.findViewById(R.id.thumb);
            TextView textView = (TextView) view.findViewById(R.id.name);
            this.hdvideoplayer_txtName = textView;
            textView.setSelected(true);
            this.hdvideoplayer_txtDuration = (TextView) view.findViewById(R.id.duration);
            this.hdvideoplayer_s1 = (CheckBox) view.findViewById(R.id.delete);
            this.hdvideoplayer_txtNew = (TextView) view.findViewById(R.id.txtNew);
            if (VideoRecyclerAdapter.this.hdvideoplayer_view.booleanValue()) {
                this.hdvideoplayer_txtSize = (TextView) view.findViewById(R.id.size);
                this.hdvideoplayer_txtDate = (TextView) view.findViewById(R.id.date);
            }
            view.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    holder.this.lambda$new$0$VideoRecyclerAdapter$holder(view);
                }
            });
        }

        public void lambda$new$0$VideoRecyclerAdapter$holder(View view) {
            if (VideoRecyclerAdapter.this.hdvideoplayer_action) {
                if (VideoRecyclerAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.get(this.hdvideoplayer_position)) {
                    view.setSelected(false);
                    this.hdvideoplayer_s1.setChecked(false);
                    VideoRecyclerAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.delete(this.hdvideoplayer_position);
                    if (VideoRecyclerAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.size() == 0) {
                        VideoRecyclerAdapter.this.hdvideoplayer_mActionMode.finish();
                        VideoRecyclerAdapter.this.notifyDataSetChanged();
                        return;
                    }
                }
                view.setSelected(true);
                this.hdvideoplayer_s1.setChecked(true);
                VideoRecyclerAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.put(this.hdvideoplayer_position, true);
                VideoRecyclerAdapter.this.hdvideoplayer_mActionMode.setTitle((CharSequence) String.format("%d selected", new Object[]{Integer.valueOf(VideoRecyclerAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.size())}));
                return;
            }
            PreferenceUtil instance = PreferenceUtil.getInstance(VideoRecyclerAdapter.this.hdvideoplayer_ctx);
            Activity activity = VideoRecyclerAdapter.this.hdvideoplayer_ctx;
            instance.setIsPlayVideo(activity, true, "" + VideoRecyclerAdapter.this.hdvideoplayer_filteredvideoList.get(this.hdvideoplayer_position).getDISPLAY_NAME());
            Intent intent = new Intent(VideoRecyclerAdapter.this.hdvideoplayer_ctx, VideoPlayerActivity.class);
            intent.putExtra("list", (Serializable) VideoRecyclerAdapter.this.hdvideoplayer_filteredvideoList);
            intent.putExtra("position", this.hdvideoplayer_position);
            VideoRecyclerAdapter.this.hdvideoplayer_ctx.startActivity(intent);
        }
    }

    public VideoRecyclerAdapter(Activity activity, List<VideoItem> list, int i, boolean z) {
        this.hdvideoplayer_ctx = activity;
        this.hdvideoplayer_list = list;
        this.hdvideoplayer_wi = i;
        this.hdvideoplayer_filteredvideoList = list;
        this.hdvideoplayer_view = Boolean.valueOf(z);
        this.hdvideoplayer_retriever = new MediaMetadataRetriever();
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
        VideoItem videoItem = this.hdvideoplayer_filteredvideoList.get(i);
        this.hdvideoplayer_retriever = new MediaMetadataRetriever();
        File file = new File(this.hdvideoplayer_filteredvideoList.get(i).getDATA());
        long length = file.length();
        videoItem.setSIZE(size(String.valueOf(length)));
        this.hdvideoplayer_retriever.setDataSource(this.hdvideoplayer_ctx, Uri.fromFile(file));
        String str = "" + Long.parseLong(this.hdvideoplayer_retriever.extractMetadata(9));
        videoItem.setDURATION(duration(str));
        long lastModified = file.lastModified();
        videoItem.setDATE(date(lastModified));
        holder holder2 = (holder) viewHolder;
        holder2.hdvideoplayer_position = i;
        try {
            TextView textView = holder2.hdvideoplayer_txtName;
            TextView textView2 = holder2.hdvideoplayer_txtSize;
            TextView textView3 = holder2.hdvideoplayer_txtDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder2.hdvideoplayer_txtName.setText(this.hdvideoplayer_filteredvideoList.get(i).getDISPLAY_NAME());
        holder2.hdvideoplayer_txtDuration.setText(duration(str));
        if (this.hdvideoplayer_view.booleanValue()) {
            holder2.hdvideoplayer_txtSize.setText(String.format("Size: %s", new Object[]{size(String.valueOf(length))}));
            holder2.hdvideoplayer_txtDate.setText(String.format("Modified: %s", new Object[]{date(lastModified)}));
        }
        Glide.with(this.hdvideoplayer_ctx).load(this.hdvideoplayer_filteredvideoList.get(i).getDATA()).into(holder2.hdvideoplayer_i1);
        if (this.hdvideoplayer_action) {
            holder2.hdvideoplayer_s1.setVisibility(View.VISIBLE);
        } else {
            holder2.hdvideoplayer_s1.setVisibility(View.INVISIBLE);
        }
        if (this.hdvideoplayer_mSparseBoolMultiSelect.get(i)) {
            holder2.itemView.setSelected(true);
            holder2.hdvideoplayer_s1.setChecked(true);
            return;
        }
        holder2.itemView.setSelected(false);
        holder2.hdvideoplayer_s1.setChecked(false);
    }

    public int getItemCount() {
        return this.hdvideoplayer_filteredvideoList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            public FilterResults performFiltering(CharSequence charSequence) {
                String charSequence2 = charSequence.toString();
                if (charSequence2.isEmpty()) {
                    VideoRecyclerAdapter videoRecyclerAdapter = VideoRecyclerAdapter.this;
                    videoRecyclerAdapter.hdvideoplayer_filteredvideoList = videoRecyclerAdapter.hdvideoplayer_list;
                } else {
                    ArrayList arrayList = new ArrayList();
                    for (VideoItem next : VideoRecyclerAdapter.this.hdvideoplayer_list) {
                        if (next.getDISPLAY_NAME().toLowerCase().contains(charSequence2.toLowerCase())) {
                            arrayList.add(next);
                        }
                    }
                    VideoRecyclerAdapter.this.hdvideoplayer_filteredvideoList = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = VideoRecyclerAdapter.this.hdvideoplayer_filteredvideoList;
                return filterResults;
            }

            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                VideoRecyclerAdapter.this.hdvideoplayer_filteredvideoList = (List) filterResults.values;
                VideoRecyclerAdapter.this.notifyDataSetChanged();
            }
        };
    }

    public void moveFile(File file, File file2) throws IOException {
        FileChannel channel = new FileOutputStream(new File(file2, file.getName())).getChannel();
        FileChannel fileChannel = null;
        try {
            fileChannel = new FileInputStream(file).getChannel();
            fileChannel.transferTo(0, fileChannel.size(), channel);
            fileChannel.close();
            file.delete();
        } finally {
            if (fileChannel != null) {
                fileChannel.close();
            }
            if (channel != null) {
                channel.close();
            }
        }
    }

    private String size(String str) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        double parseDouble = Double.parseDouble(str) / 1024.0d;
        if (parseDouble < 1024.0d) {
            return decimalFormat.format(parseDouble) + " KB";
        }
        double d = parseDouble / 1024.0d;
        if (d < 1024.0d) {
            return decimalFormat.format(d) + " MB";
        }
        return decimalFormat.format(d / 1024.0d) + " GB";
    }

    private String duration(String str) {
        try {
            long parseInt = (long) Integer.parseInt(str);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(parseInt) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(parseInt));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(parseInt) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(parseInt));
            if (TimeUnit.MILLISECONDS.toHours(parseInt) >= 1) {
                return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toHours(parseInt)), Long.valueOf(minutes), Long.valueOf(seconds)});
            }
            return String.format("%02d:%02d", new Object[]{Long.valueOf(minutes), Long.valueOf(seconds)});
        } catch (Exception unused) {
            return "00:00";
        }
    }

    private String date(long j) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(j));
    }
}
