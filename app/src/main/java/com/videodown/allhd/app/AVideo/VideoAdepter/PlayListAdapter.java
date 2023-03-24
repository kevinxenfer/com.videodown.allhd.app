package com.videodown.allhd.app.AVideo.VideoAdepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.AVideo.VideoModel.VideoItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.List;


public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.holder> {
    private final Context hdvideoplayer_ctx;
    private final List<VideoItem> hdvideoplayer_list;
    public final SimpleExoPlayer hdvideoplayer_player;

    public PlayListAdapter(Context context, List<VideoItem> list, SimpleExoPlayer simpleExoPlayer) {
        this.hdvideoplayer_ctx = context;
        this.hdvideoplayer_list = list;
        this.hdvideoplayer_player = simpleExoPlayer;
    }

    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(this.hdvideoplayer_ctx).inflate(R.layout.playlist_card, viewGroup, false));
    }

    public void onBindViewHolder(holder holder2, int i) {
        holder2.hdvideoplayer_position = i;
        Glide.with(this.hdvideoplayer_ctx).load(this.hdvideoplayer_list.get(i).getDATA()).into(holder2.hdvideoplayer_i1);
        holder2.hdvideoplayer_t1.setText(this.hdvideoplayer_list.get(i).getDISPLAY_NAME());
        holder2.hdvideoplayer_t2.setText(this.hdvideoplayer_list.get(i).getDURATION());
    }

    public int getItemCount() {
        return this.hdvideoplayer_list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        final ImageView hdvideoplayer_i1;
        int hdvideoplayer_position;
        final TextView hdvideoplayer_t1;
        final TextView hdvideoplayer_t2;

        holder(View view) {
            super(view);
            this.hdvideoplayer_i1 = (ImageView) view.findViewById(R.id.thumb);
            TextView textView = (TextView) view.findViewById(R.id.name);
            this.hdvideoplayer_t1 = textView;
            textView.setSelected(true);
            this.hdvideoplayer_t2 = (TextView) view.findViewById(R.id.duration);
            view.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    holder.this.lambda$new$0$PlayListAdapter$holder(view);
                }
            });
        }

        public void lambda$new$0$PlayListAdapter$holder(View view) {
            PlayListAdapter.this.hdvideoplayer_player.seekTo(this.hdvideoplayer_position, 0);
        }
    }
}
