package com.videodown.allhd.app.AllStory.AllStoryAdepter;




import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.videodown.allhd.app.R;
import com.videodown.allhd.app.Activity.StoryDownloader.StoryVideoPlayerActivity;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AllStory.story.ItemModel;

import java.util.ArrayList;


public class StoriesListAdapter extends RecyclerView.Adapter<StoriesListAdapter.ViewHolder> {
    public Context context;
    private ArrayList<ItemModel> storyItemModelList;

    public StoriesListAdapter(Context context2, ArrayList<ItemModel> arrayList) {
        this.context = context2;
        this.storyItemModelList = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.items_whatsapp_view, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ItemModel itemModel = this.storyItemModelList.get(i);
        try {
            if (itemModel.getMedia_type() == 2) {
                viewHolder.ivPlay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivPlay.setVisibility(View.GONE);
            }
            Glide.with(this.context).load(itemModel.getImage_versions2().getCandidates().get(0).getUrl()).into(viewHolder.pcw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                        Intent intent = new Intent(StoriesListAdapter.this.context, StoryVideoPlayerActivity.class);
                        intent.putExtra("PathVideo", itemModel.getVideo_versions().get(0).getUrl());
                        StoriesListAdapter.this.context.startActivity(intent);


            }
        });
        viewHolder.tvDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (itemModel.getMedia_type() == 2) {
                    String url = itemModel.getVideo_versions().get(0).getUrl();
                    String str = MyUtils.RootDirectoryInsta;
                    Context context = StoriesListAdapter.this.context;
                    MyUtils.startDownload(url, str, context, "story_" + itemModel.getId() + ".mp4");
                    return;
                }
                String url2 = itemModel.getImage_versions2().getCandidates().get(0).getUrl();
                String str2 = MyUtils.RootDirectoryInsta;
                Context context2 = StoriesListAdapter.this.context;
                MyUtils.startDownload(url2, str2, context2, "story_" + itemModel.getId() + ".png");
            }
        });
    }

    public int getItemCount() {
        ArrayList<ItemModel> arrayList = this.storyItemModelList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout RLM;
        public ImageView ivPlay;
        public ImageView pcw;
        public RelativeLayout rlMain;
        public TextView tvDownload;

        public ViewHolder(View view) {
            super(view);
            this.RLM = (RelativeLayout) view.findViewById(R.id.RLM);
            this.ivPlay = (ImageView) view.findViewById(R.id.iv_play);
            this.pcw = (ImageView) view.findViewById(R.id.pcw);
            this.rlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
            this.tvDownload = (TextView) view.findViewById(R.id.tv_download);
        }
    }
}
