package com.videodown.allhd.app.AllStory.AllStoryAdepter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.Activity.StoryDownloader.StoryVideoPlayerActivity;
import com.videodown.allhd.app.Activity.StoryModel.WhatsappStatusModel;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AllStory.Allvideointerfaces.FileListWhatsappClickInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;


public class WhatsappStatusAdapter extends RecyclerView.Adapter<WhatsappStatusAdapter.ViewHolder> {
    public Context context;
    ProgressDialog dialogProgress;
    private ArrayList<WhatsappStatusModel> fileArrayList;
    private FileListWhatsappClickInterface fileListClickInterface;
    String fileName = "";
    private LayoutInflater layoutInflater;
    public String saveFilePath = (MyUtils.downloadWhatsAppDir + File.separator);

    public WhatsappStatusAdapter(Context context2, ArrayList<WhatsappStatusModel> arrayList) {
        this.context = context2;
        this.fileArrayList = arrayList;
        initProgress();
    }

    public WhatsappStatusAdapter(Context context2, ArrayList<WhatsappStatusModel> arrayList, FileListWhatsappClickInterface fileListWhatsappClickInterface) {
        this.context = context2;
        this.fileArrayList = arrayList;
        this.fileListClickInterface = fileListWhatsappClickInterface;
        initProgress();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.items_whatsapp_view, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final WhatsappStatusModel whatsappStatusModel = this.fileArrayList.get(i);
        if (whatsappStatusModel.getUri().toString().endsWith(".mp4")) {
            viewHolder.ivPlay.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivPlay.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT > 29) {
            Glide.with(this.context).load(whatsappStatusModel.getUri()).into(viewHolder.pcw);
        } else {
            Glide.with(this.context).load(whatsappStatusModel.getPath()).into(viewHolder.pcw);
        }
        viewHolder.tvDownload.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.createFileFolder();
                if (Build.VERSION.SDK_INT > 29) {
                    try {
                        if (whatsappStatusModel.getUri().toString().endsWith(".mp4")) {
                            WhatsappStatusAdapter whatsappStatusAdapter = WhatsappStatusAdapter.this;
                            whatsappStatusAdapter.fileName = "status_" + System.currentTimeMillis() + ".mp4";
                            new DownloadFileTask().execute(new String[]{whatsappStatusModel.getUri().toString()});
                            return;
                        }
                        WhatsappStatusAdapter whatsappStatusAdapter2 = WhatsappStatusAdapter.this;
                        whatsappStatusAdapter2.fileName = "status_" + System.currentTimeMillis() + ".png";
                        new DownloadFileTask().execute(new String[]{whatsappStatusModel.getUri().toString()});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String path = whatsappStatusModel.getPath();
                    String substring = path.substring(path.lastIndexOf("/") + 1);
                    try {
                        FileUtils.copyFileToDirectory(new File(path), new File(WhatsappStatusAdapter.this.saveFilePath));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    String substring2 = substring.substring(12);
                    Context context = WhatsappStatusAdapter.this.context;
                    String[] strArr = {new File(WhatsappStatusAdapter.this.saveFilePath + substring2).getAbsolutePath()};
                    String[] strArr2 = new String[1];
                    strArr2[0] = whatsappStatusModel.getUri().toString().endsWith(".mp4") ? "video/*" : "image/*";
                    MediaScannerConnection.scanFile(context, strArr, strArr2, new MediaScannerConnection.MediaScannerConnectionClient() {
                        public void onMediaScannerConnected() {
                        }

                        public void onScanCompleted(String str, Uri uri) {
                        }
                    });
                    new File(WhatsappStatusAdapter.this.saveFilePath, substring).renameTo(new File(WhatsappStatusAdapter.this.saveFilePath, substring2));
                    Context context2 = WhatsappStatusAdapter.this.context;
                    Toast.makeText(context2, WhatsappStatusAdapter.this.context.getResources().getString(R.string.saved_to) + WhatsappStatusAdapter.this.saveFilePath + substring2, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(WhatsappStatusAdapter.this.context, StoryVideoPlayerActivity.class);
                intent.putExtra("PathVideo", whatsappStatusModel.getUri().toString());
                WhatsappStatusAdapter.this.context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        ArrayList<WhatsappStatusModel> arrayList = this.fileArrayList;
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

    public void initProgress() {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        this.dialogProgress = progressDialog;
        progressDialog.setProgressStyle(0);
        this.dialogProgress.setTitle("Saving");
        this.dialogProgress.setMessage("Saving. Please wait...");
        this.dialogProgress.setIndeterminate(true);
        this.dialogProgress.setCanceledOnTouchOutside(false);
    }

    class DownloadFileTask extends AsyncTask<String, String, String> {
        public void WP_DL_file_task(String str, Uri uri) {
        }

        public void onProgressUpdate(String... strArr) {
        }

        DownloadFileTask() {
        }

        public String doInBackground(String... strArr) {
            try {
                InputStream openInputStream = WhatsappStatusAdapter.this.context.getContentResolver().openInputStream(Uri.parse(strArr[0]));
                File file = new File(MyUtils.downloadWhatsAppDir + File.separator + WhatsappStatusAdapter.this.fileName);
                file.setWritable(true, false);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = openInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileOutputStream.close();
                        openInputStream.close();
                        return null;
                    }
                }
            } catch (IOException e) {
                System.out.println("error in creating a file");
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(final String str) {
            MyUtils.setToast(WhatsappStatusAdapter.this.context, WhatsappStatusAdapter.this.context.getResources().getString(R.string.download_complete));
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    String[] strArr = null;
                    MediaScannerConnection.scanFile(WhatsappStatusAdapter.this.context, new String[]{new File(MyUtils.downloadWhatsAppDir + File.separator + WhatsappStatusAdapter.this.fileName).getAbsolutePath()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String str, Uri uri) {
                            DownloadFileTask.this.WP_DL_file_task(str, uri);
                        }
                    });
                    return;
                }
                WhatsappStatusAdapter.this.context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(MyUtils.downloadWhatsAppDir + File.separator + WhatsappStatusAdapter.this.fileName))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onCancelled() {
            super.onCancelled();
        }
    }
}
