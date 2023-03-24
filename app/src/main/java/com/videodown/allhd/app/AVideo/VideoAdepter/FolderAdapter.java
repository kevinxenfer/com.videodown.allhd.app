package com.videodown.allhd.app.AVideo.VideoAdepter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.videodown.allhd.app.AVideo.VideoModel.MediaQuery;
import com.videodown.allhd.app.AVideo.VideoModel.VideoItem;
import com.videodown.allhd.app.AVideo.VideoModel.folder;
import com.videodown.allhd.app.AVideo.Videoplayer.VideoListActivity;
import com.videodown.allhd.app.Activity.Other.PreferenceUtil;
import com.videodown.allhd.app.R;
import com.google.gms.ads.AdUtils;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.google.gms.ads.databinding.LayoutAdLargeBinding;
import com.preference.PowerPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public boolean hdvideoplayer_action = false;
    public boolean hdvideoplayer_checktrue = false;
    public Activity hdvideoplayer_context;
    public ArrayList<folder> hdvideoplayer_folderList;
    public ActionMode hdvideoplayer_mActionMode;
    public final SparseBooleanArray hdvideoplayer_mSparseBoolMultiSelect = new SparseBooleanArray();
    public MediaQuery hdvideoplayer_mediaQuery;
    public MyListener hdvideoplayer_myListener;

    private final int AD_TYPE_GOOGLE1 = 1;

    public interface MyListener {
        void clickFoldermenu(int i, int i2);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView hdvideoplayer_filesCount;
        TextView hdvideoplayer_folderName;
        AppCompatImageView hdvideoplayer_imgFolder;
        AppCompatImageView hdvideoplayer_imgMore;
        int hdvideoplayer_position;
        RelativeLayout hdvideoplayer_rlSelect;
        CheckBox hdvideoplayer_s1;
        TextView hdvideoplayer_txtNew;
        private LayoutAdLargeBinding binding;

        public ListViewHolder(View view) {
            super(view);
            this.hdvideoplayer_imgFolder = view.findViewById(R.id.imgFolder);
            this.hdvideoplayer_folderName = view.findViewById(R.id.folderName);
            this.hdvideoplayer_filesCount = view.findViewById(R.id.filesCount);
            this.hdvideoplayer_txtNew = view.findViewById(R.id.txtNew);
            this.hdvideoplayer_rlSelect = view.findViewById(R.id.rlSelect);
            this.hdvideoplayer_s1 = view.findViewById(R.id.checklong);
            view.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    ListViewHolder.this.lambda$new$0$FolderAdapter$ListViewHolder(view);
                }
            });
        }

        ListViewHolder(@NonNull LayoutAdLargeBinding view) {
            super(view.getRoot());
            binding = view;
        }

        public void lambda$new$0$FolderAdapter$ListViewHolder(View view) {
            if (FolderAdapter.this.hdvideoplayer_action) {
                if (FolderAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.get(this.hdvideoplayer_position)) {
                    view.setSelected(false);
                    this.hdvideoplayer_s1.setChecked(false);
                    FolderAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.delete(this.hdvideoplayer_position);
                    if (FolderAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.size() == 0) {
                        FolderAdapter.this.hdvideoplayer_mActionMode.finish();
                        FolderAdapter.this.notifyDataSetChanged();
                        return;
                    }
                }
                view.setSelected(true);
                this.hdvideoplayer_s1.setChecked(true);
                FolderAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.put(this.hdvideoplayer_position, true);
                FolderAdapter.this.hdvideoplayer_mActionMode.setTitle((CharSequence) String.format("%d selected", new Object[]{Integer.valueOf(FolderAdapter.this.hdvideoplayer_mSparseBoolMultiSelect.size())}));
                return;
            }

            new MainAds().showInterAds(hdvideoplayer_context, new InterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                    final Intent intent = new Intent(FolderAdapter.this.hdvideoplayer_context, VideoListActivity.class);
                    intent.putExtra("id", FolderAdapter.this.hdvideoplayer_folderList.get(hdvideoplayer_position).bid);
                    intent.putExtra("name", FolderAdapter.this.hdvideoplayer_folderList.get(hdvideoplayer_position).bucket);
                    FolderAdapter.this.hdvideoplayer_context.startActivity(intent);
                }
            });


        }
    }

    public FolderAdapter(Activity activity, ArrayList<folder> arrayList, MyListener myListener) {
        this.hdvideoplayer_context = activity;
        this.hdvideoplayer_folderList = arrayList;
        this.hdvideoplayer_myListener = myListener;
        this.hdvideoplayer_mediaQuery = new MediaQuery(this.hdvideoplayer_context);
        setAds(false);
    }

    public void setAds(boolean ischeck) {

        int PARTICLE_AD_DISPLAY_COUNT = PowerPreference.getDefaultFile().getInt(AdUtils.ListNativeAfterCount);


        PARTICLE_AD_DISPLAY_COUNT = PARTICLE_AD_DISPLAY_COUNT * 3;
        hdvideoplayer_folderList.removeAll(Collections.singleton(null));

        ArrayList<folder> tempArr = new ArrayList<>();
        for (int i = 0; i < hdvideoplayer_folderList.size(); i++) {
            if (hdvideoplayer_folderList.size() > PARTICLE_AD_DISPLAY_COUNT) {
                if (i != 0) {
                    if (i % PARTICLE_AD_DISPLAY_COUNT == 0) {
                        tempArr.add(null);
                    }
                }
                tempArr.add(hdvideoplayer_folderList.get(i));
            } else {
                tempArr.add(hdvideoplayer_folderList.get(i));
            }
        }
        if (hdvideoplayer_folderList.size() > 0) {
            if (hdvideoplayer_folderList.size() % PARTICLE_AD_DISPLAY_COUNT == 0) {
                tempArr.add(null);
            }
        }

        this.hdvideoplayer_folderList = tempArr;
        if (ischeck) {
            notifyDataSetChanged();
        }

    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == AD_TYPE_GOOGLE1) {
            return new ListViewHolder(LayoutAdLargeBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
        } else
            return new ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_folder, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {

        ListViewHolder listViewHolder = (ListViewHolder) viewHolder;
        if (getItemViewType(i) == AD_TYPE_GOOGLE1) {
            new MainAds().showListNativeAds(hdvideoplayer_context, listViewHolder.binding.adFrameLarge, AdUtils.AD_NORMAL);
        } else {
            listViewHolder.hdvideoplayer_position = i;
            TextView textView = listViewHolder.hdvideoplayer_folderName;
            listViewHolder.hdvideoplayer_folderName.setSelected(true);
            textView.setText("" + this.hdvideoplayer_folderList.get(i).bucket);
            Log.e("djfkdjfk", "onBindViewHolder: " + this.hdvideoplayer_folderList.get(i).bucket);
            TextView textView2 = listViewHolder.hdvideoplayer_filesCount;
            textView2.setText("" + this.hdvideoplayer_folderList.get(i).count + " Video(s)");
            TextView textView3 = listViewHolder.hdvideoplayer_folderName;
            TextView textView4 = listViewHolder.hdvideoplayer_filesCount;
            AppCompatImageView appCompatImageView = listViewHolder.hdvideoplayer_imgMore;
            AppCompatImageView roundedImageView = listViewHolder.hdvideoplayer_imgFolder;
            boolean z = this.hdvideoplayer_action;
            Boolean.valueOf(false);
            if (z) {
                listViewHolder.hdvideoplayer_s1.setVisibility(View.VISIBLE);
            } else {
                listViewHolder.hdvideoplayer_s1.setVisibility(View.INVISIBLE);
            }
            if (this.hdvideoplayer_mSparseBoolMultiSelect.get(i)) {
                listViewHolder.itemView.setSelected(true);
                listViewHolder.hdvideoplayer_s1.setChecked(true);
            } else {
                listViewHolder.itemView.setSelected(false);
                listViewHolder.hdvideoplayer_s1.setChecked(false);
            }
            try {
                List<VideoItem> allVideo = this.hdvideoplayer_mediaQuery.getAllVideo(this.hdvideoplayer_folderList.get(i).bid, 0);
                for (int i2 = 0; i2 < allVideo.size(); i2++) {
                    PreferenceUtil instance = PreferenceUtil.getInstance(this.hdvideoplayer_context);
                    Activity activity = this.hdvideoplayer_context;
                    if (!instance.getIsPlayVideo(activity, "" + allVideo.get(i2).getDISPLAY_NAME())) {
                        Boolean.valueOf(true);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getItemCount() {
        return this.hdvideoplayer_folderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (hdvideoplayer_folderList.get(position) == null) {
            return AD_TYPE_GOOGLE1;
        } else {
            return 0;
        }
    }

}
