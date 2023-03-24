package com.videodown.allhd.app.AllStory.Allvideofilemanager;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.videodown.allhd.app.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FilePicker extends ListActivity {
    private static final String DEFAULT_INITIAL_DIRECTORY = Environment.getExternalStorageDirectory().toString();
    public static final String EXTRA_ACCEPTED_FILE_EXTENSIONS = "accepted_file_extensions";
    public static final String EXTRA_FILE_PATH = "file_path";
    public static final String EXTRA_SHOW_HIDDEN_FILES = "show_hidden_files";
    protected FilePickerListAdapter hdvideoplayer_Adapter;
    protected File hdvideoplayer_Directory;
    protected ArrayList<File> hdvideoplayer_Files;
    protected boolean hdvideoplayer_ShowHiddenFiles = false;
    protected String[] hdvideoplayer_acceptedFileExtensions;
    int hdvideoplayer_cc = 0;
    private int hdvideoplayer_pos;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.hdvideoplayer_pos = getSharedPreferences("theme", 0).getInt("pos", 1);
        ViewGroup viewGroup = null;
        View inflate = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.empty_view, (ViewGroup) null);
        ((ViewGroup) getListView().getParent()).addView(inflate);
        getListView().setEmptyView(inflate);
        this.hdvideoplayer_Directory = new File(DEFAULT_INITIAL_DIRECTORY);
        this.hdvideoplayer_Files = new ArrayList<>();
        FilePickerListAdapter filePickerListAdapter = new FilePickerListAdapter(this, this.hdvideoplayer_Files);
        this.hdvideoplayer_Adapter = filePickerListAdapter;
        setListAdapter(filePickerListAdapter);
        this.hdvideoplayer_acceptedFileExtensions = new String[0];
        if (getIntent().hasExtra(EXTRA_FILE_PATH)) {
            this.hdvideoplayer_Directory = new File(getIntent().getStringExtra(EXTRA_FILE_PATH));
        }
        if (getIntent().hasExtra(EXTRA_SHOW_HIDDEN_FILES)) {
            this.hdvideoplayer_ShowHiddenFiles = getIntent().getBooleanExtra(EXTRA_SHOW_HIDDEN_FILES, false);
        }
        if (getIntent().hasExtra(EXTRA_ACCEPTED_FILE_EXTENSIONS)) {
            ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(EXTRA_ACCEPTED_FILE_EXTENSIONS);
            this.hdvideoplayer_acceptedFileExtensions = (String[]) stringArrayListExtra.toArray(new String[stringArrayListExtra.size()]);
        }
    }

    public void onResume() {
        refreshFilesList();
        super.onResume();
    }

    public void refreshFilesList() {
        this.hdvideoplayer_Files.clear();
        File[] listFiles = this.hdvideoplayer_Directory.listFiles(new ExtensionFilenameFilter(this.hdvideoplayer_acceptedFileExtensions));
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                if (!file.isHidden() || this.hdvideoplayer_ShowHiddenFiles) {
                    this.hdvideoplayer_Files.add(file);
                }
            }
            Collections.sort(this.hdvideoplayer_Files, new FileComparator());
        }
        this.hdvideoplayer_Adapter.notifyDataSetChanged();
    }

    public int[] countFileList(File file) {
        int[] iArr = new int[2];
        File[] listFiles = file.listFiles(new ExtensionFilenameFilter(this.hdvideoplayer_acceptedFileExtensions));
        if (listFiles != null && listFiles.length > 0) {
            int i = 0;
            int i2 = 0;
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    i++;
                    if (file2.isHidden()) {
                        i--;
                    }
                } else {
                    i2++;
                    if (file2.isHidden()) {
                        i2--;
                    }
                }
                file2.isHidden();
            }
            iArr[0] = i;
            iArr[1] = i2;
        }
        return iArr;
    }

    public void onBackPressed() {
        if (this.hdvideoplayer_cc == 0) {
            finish();
        } else if (this.hdvideoplayer_Directory.getParentFile() != null) {
            this.hdvideoplayer_cc--;
            this.hdvideoplayer_Directory = this.hdvideoplayer_Directory.getParentFile();
            refreshFilesList();
        }
    }

    public void onListItemClick(ListView listView, View view, int i, long j) {
        File file = (File) listView.getItemAtPosition(i);
        this.hdvideoplayer_cc++;
        if (file.isFile()) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_FILE_PATH, file.getAbsolutePath());
            setResult(-1, intent);
            finish();
        } else {
            this.hdvideoplayer_Directory = file;
            refreshFilesList();
        }
        super.onListItemClick(listView, view, i, j);
    }

    public class FilePickerListAdapter extends ArrayAdapter<File> {
        private List<File> mObjects;

        public FilePickerListAdapter(Context context, List<File> list) {
            super(context, R.layout.list_item, 16908308, list);
            this.mObjects = list;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            String str;
            if (view == null) {
                view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item, viewGroup, false);
            }
            File file = this.mObjects.get(i);
            ImageView imageView = (ImageView) view.findViewById(R.id.file_picker_image);
            TextView textView = (TextView) view.findViewById(R.id.file_picker_text);
            TextView textView2 = (TextView) view.findViewById(R.id.path);
            textView.setSingleLine(true);
            textView.setText(file.getName());
            int[] countFileList = FilePicker.this.countFileList(file);
            if (!file.isFile()) {
                imageView.setImageResource(R.drawable.folderr);
            } else if (file.getName().contains(".mp4") || file.getName().contains(".mkv") || file.getName().contains(".3gp")) {
                imageView.setImageResource(R.drawable.action_play);
            } else {
                imageView.setImageResource(R.drawable.file);
            }
            if (file.isDirectory()) {
                textView2.setVisibility(View.VISIBLE);
                if (countFileList[0] == 0 && countFileList[1] == 0) {
                    textView2.setText("Directory is empty");
                } else {
                    if (countFileList[0] > 0 && countFileList[1] > 0) {
                        str = countFileList[0] + " subfolder, " + countFileList[1] + " file";
                    } else if (countFileList[0] == 0 && countFileList[1] > 0) {
                        str = countFileList[1] + " file";
                    } else if (countFileList[0] <= 0 || countFileList[1] != 0) {
                        str = "";
                    } else {
                        str = countFileList[0] + " subfolder";
                    }
                    textView2.setText(str);
                }
            } else {
                textView2.setVisibility(View.GONE);
            }
            return view;
        }
    }

    public class FileComparator implements Comparator<File> {
        private FileComparator() {
        }

        public int compare(File file, File file2) {
            if (file == file2) {
                return 0;
            }
            if (file.isDirectory() && file2.isFile()) {
                return -1;
            }
            if (!file.isFile() || !file2.isDirectory()) {
                return file.getName().compareToIgnoreCase(file2.getName());
            }
            return 1;
        }
    }

    public class ExtensionFilenameFilter implements FilenameFilter {
        private String[] Extensions;

        public ExtensionFilenameFilter(String[] strArr) {
            this.Extensions = strArr;
        }

        public boolean accept(File file, String str) {
            String[] strArr;
            if (new File(file, str).isDirectory() || (strArr = this.Extensions) == null || strArr.length <= 0) {
                return true;
            }
            int i = 0;
            while (true) {
                String[] strArr2 = this.Extensions;
                if (i >= strArr2.length) {
                    return false;
                }
                if (str.endsWith(strArr2[i])) {
                    return true;
                }
                i++;
            }
        }
    }
}
