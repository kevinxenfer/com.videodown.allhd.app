package com.videodown.allhd.app.AllStory.AllStoryAdepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.AllStory.Allvideointerfaces.UserListInterface;
import com.videodown.allhd.app.AllStory.story.TrayModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private Context context;
    public ArrayList<TrayModel> trayModelArrayList;
    public UserListInterface userListInterface;

    public UserListAdapter(Context context2, ArrayList<TrayModel> arrayList, UserListInterface userListInterface2) {
        this.context = context2;
        this.trayModelArrayList = arrayList;
        this.userListInterface = userListInterface2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_user_list, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.realName.setText(this.trayModelArrayList.get(i).getUser().getFull_name());
        Glide.with(this.context).load(this.trayModelArrayList.get(i).getUser().getProfile_pic_url()).thumbnail(0.2f).into((ImageView) viewHolder.storyPc);
        viewHolder.RLStoryLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UserListAdapter.this.userListInterface.userListClick(i, UserListAdapter.this.trayModelArrayList.get(i));
            }
        });
    }

    public int getItemCount() {
        ArrayList<TrayModel> arrayList = this.trayModelArrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout RLStoryLayout;
        public TextView realName;
        public CircleImageView storyPc;
        public TextView userName;

        public ViewHolder(View view) {
            super(view);
            this.RLStoryLayout = (RelativeLayout) view.findViewById(R.id.RLStoryLayout);
            this.realName = (TextView) view.findViewById(R.id.real_name);
            this.storyPc = (CircleImageView) view.findViewById(R.id.story_pc);
            this.userName = (TextView) view.findViewById(R.id.user_name);
        }
    }
}
