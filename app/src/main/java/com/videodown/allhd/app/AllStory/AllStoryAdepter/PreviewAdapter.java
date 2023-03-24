package com.videodown.allhd.app.AllStory.AllStoryAdepter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.Activity.StoryDownloader.StoryVideoPlayerActivity;
import com.videodown.allhd.app.Activity.StoryModel.StatusModel;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;

import java.util.ArrayList;

public class PreviewAdapter extends PagerAdapter {
    Activity activity;
    ArrayList<StatusModel> imageList;

    public PreviewAdapter(Activity activity, ArrayList<StatusModel> arrayList) {
        this.activity = activity;
        this.imageList = arrayList;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.preview_list_item, viewGroup, false);
        ImageView imageView = inflate.findViewById(R.id.imageView);
        ImageView imageView2 = inflate.findViewById(R.id.iconplayer);
        if (!MyUtils.getBack(this.imageList.get(i).getFilePath(), "((\\.mp4|\\.webm|\\.ogg|\\.mpK|\\.avi|\\.mkv|\\.flv|\\.mpg|\\.wmv|\\.vob|\\.ogv|\\.mov|\\.qt|\\.rm|\\.rmvb\\.|\\.asf|\\.m4p|\\.m4v|\\.mp2|\\.mpeg|\\.mpe|\\.mpv|\\.m2v|\\.3gp|\\.f4p|\\.f4a|\\.f4b|\\.f4v)$)").isEmpty()) {
            imageView2.setVisibility(View.VISIBLE);
        } else {
            imageView2.setVisibility(View.GONE);
        }
        Glide.with(this.activity).load(this.imageList.get(i).getFilePath()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public final void onClick(View view) {
                PreviewAdapter.this.m1784x769c5d73(i, view);
            }
        });
        viewGroup.addView(inflate);
        return inflate;
    }

    public void m1784x769c5d73(int i, View view) {

        new MainAds().showInterAds(activity, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                if (!MyUtils.getBack(imageList.get(i).getFilePath(), "((\\.mp4|\\.webm|\\.ogg|\\.mpK|\\.avi|\\.mkv|\\.flv|\\.mpg|\\.wmv|\\.vob|\\.ogv|\\.mov|\\.qt|\\.rm|\\.rmvb\\.|\\.asf|\\.m4p|\\.m4v|\\.mp2|\\.mpeg|\\.mpe|\\.mpv|\\.m2v|\\.3gp|\\.f4p|\\.f4a|\\.f4b|\\.f4v)$)").isEmpty()) {
                    MyUtils.mPath = imageList.get(i).getFilePath();
                    Intent intent = new Intent(activity, StoryVideoPlayerActivity.class);
                    intent.putExtra("PathVideo", imageList.get(i).getFilePath());
                    activity.startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getCount() {
        return this.imageList.size();
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((RelativeLayout) obj);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((RelativeLayout) obj);
    }
}
