package com.videodown.allhd.app.AVideo.Videoplayer;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.media.audiofx.PresetReverb;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.videodown.allhd.app.AllStory.AllvideoEqulizer.EqualizerDataList;
import com.videodown.allhd.app.AllStory.AllvideoEqulizer.p017eq.BassBoosts;
import com.videodown.allhd.app.AllStory.AllvideoEqulizer.p017eq.Equalizers;
import com.videodown.allhd.app.AllStory.AllvideoEqulizer.p017eq.Loud;
import com.videodown.allhd.app.AllStory.AllvideoEqulizer.p017eq.Virtualizers;
import com.videodown.allhd.app.Activity.Other.PreferenceUtil;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AVideo.VideoAdepter.PlayListAdapter;
import com.videodown.allhd.app.AVideo.VideoModel.VideoItem;
import com.videodown.allhd.app.AllStory.Allvideofilemanager.FilePicker;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoPlayerActivity extends CdActivityBase {
    private static final int hdvideoplayer_CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    public static final String hdvideoplayer_DRM_MULTI_SESSION_EXTRA = "drm_multi_session";
    private static final String hdvideoplayer_KEY_POSITION = "position";
    public static ArrayList<EqualizerDataList> hdvideoplayer_equalizerListData;
    public static MediaSource hdvideoplayer_videoSource;
    public boolean hdvideoplayer_adbool = true;
    ImageView hdvideoplayer_adclosebtn;
    CardView hdvideoplayer_adfrm;
    public WindowManager.LayoutParams hdvideoplayer_attributes;
    private AudioManager hdvideoplayer_audioManager;
    private ImageView hdvideoplayer_back;
    public Boolean hdvideoplayer_batterseen = false;
    TextView hdvideoplayer_batteryTxt;
    private BottomSheetDialog hdvideoplayer_bottomSheetDialog;
    private float hdvideoplayer_brightnessv;
    BroadcastReceiver hdvideoplayer_broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            hdvideoplayer_player.stop();
        }
    };
    public boolean hdvideoplayer_checktrue = false;
    private ConstraintLayout hdvideoplayer_control_layout;
    public ImageView hdvideoplayer_crop;
    public long hdvideoplayer_current;
    DataSource.Factory hdvideoplayer_dataSourceFactory;
    public boolean hdvideoplayer_db = true;
    public boolean hdvideoplayer_defualt = false;
    public boolean hdvideoplayer_dislis = false;
    public TextView hdvideoplayer_dspeed;
    LinearLayout hdvideoplayer_frembatery;
    public boolean hdvideoplayer_isAsk = false;
    public boolean hdvideoplayer_isOrientation = true;
    public boolean hdvideoplayer_isloaded = false;
    public TrackGroupArray hdvideoplayer_lastSeenTrackGroupArray;
    public List<VideoItem> hdvideoplayer_list;
    private ImageView hdvideoplayer_lock;
    private Boolean hdvideoplayer_lockstatus = false;
    private BroadcastReceiver hdvideoplayer_mBatInfoReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra(PreferenceUtil.BAND_LEVEL, 0);
            TextView textView = VideoPlayerActivity.this.hdvideoplayer_batteryTxt;
            textView.setText(String.valueOf(intExtra) + "%");
        }
    };
    private PictureInPictureParams.Builder hdvideoplayer_mPictureInPictureParamsBuilder;
    public MediaMetadataRetriever hdvideoplayer_mediaMetadataRetriever;
    public int hdvideoplayer_orientation;
    private PlaybackParameters hdvideoplayer_parameters;
    public SimpleExoPlayer hdvideoplayer_player;
    public PlayerView hdvideoplayer_playerView;
    private ImageView hdvideoplayer_playlist;
    private ImageView hdvideoplayer_popup;
    public int hdvideoplayer_position;
    public PresetReverb hdvideoplayer_presetReverb;
    private TextView hdvideoplayer_pspeed;
    public ImageView hdvideoplayer_rotate;
    public ConstraintLayout hdvideoplayer_sheetmain;
    private float hdvideoplayer_speedv;
    private ImageView hdvideoplayer_takePicture;
    public TextView hdvideoplayer_title;
    DefaultTrackSelector hdvideoplayer_trackSelector;
    private DefaultTrackSelector.Parameters hdvideoplayer_trackSelectorParameters;
    private ImageView hdvideoplayer_unlock;
    FrameLayout hdvideoplayer_vadfrm;
    MediaSource[] hdvideoplayer_videoSources;
    public int hdvideoplayer_view = 0;
    private int hdvideoplayer_width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        hdvideoplayer_mediaMetadataRetriever = new MediaMetadataRetriever();
        registerReceiver(this.hdvideoplayer_broadcastReceiver, new IntentFilter("Stop_play_video"));
        hdvideoplayer_control_layout =  findViewById(R.id.control_layout);
        hdvideoplayer_batteryTxt =  findViewById(R.id.batteryTxt);
        hdvideoplayer_frembatery =  findViewById(R.id.frembatery);
        hdvideoplayer_adclosebtn =  findViewById(R.id.adclosebtn);
        hdvideoplayer_adfrm =  findViewById(R.id.adfrm);
        hdvideoplayer_vadfrm = findViewById(R.id.vadfrm);
        hdvideoplayer_adclosebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hdvideoplayer_vadfrm.setVisibility(View.GONE);
                hdvideoplayer_adbool = false;
                hdvideoplayer_isloaded = false;
            }
        });
       hdvideoplayer_vadfrm.setVisibility(View.GONE);
        if (PreferenceUtil.getInstance(this).getBatterylock()) {
          hdvideoplayer_batterseen = true;
        } else {
            hdvideoplayer_batterseen = false;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            hdvideoplayer_mPictureInPictureParamsBuilder = new PictureInPictureParams.Builder();
        }
        registerReceiver(this.hdvideoplayer_mBatInfoReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        hdvideoplayer_brightnessv = PreferenceUtil.getInstance(this).getLastBrightness();
        hdvideoplayer_audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        hdvideoplayer_speedv = PreferenceUtil.getInstance(this).getLastSpeed();
        PreferenceUtil.getInstance(getApplicationContext()).saveOrientation(0);
        hdvideoplayer_orientation = PreferenceUtil.getInstance(getApplicationContext()).getOrientation();
        hdvideoplayer_playerView = (PlayerView) findViewById(R.id.player_view);
        hdvideoplayer_position = getIntent().getIntExtra(hdvideoplayer_KEY_POSITION, 0);
        hdvideoplayer_list = (List) getIntent().getSerializableExtra("list");
        hdvideoplayer_current = getIntent().getLongExtra("current", 0);
        hdvideoplayer_trackSelector = new DefaultTrackSelector();
        DefaultTrackSelector.Parameters build = new DefaultTrackSelector.ParametersBuilder().build();
        hdvideoplayer_trackSelectorParameters = build;
        hdvideoplayer_trackSelector.setParameters(build);
        hdvideoplayer_player = ExoPlayerFactory.newSimpleInstance(this, this.hdvideoplayer_trackSelector);
//        ((NotificationManager) getSystemService(OneSignalDbContract.NotificationTable.TABLE_NAME)).cancelAll();
        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory((Context) this, Util.getUserAgent(this, getPackageName()));
        this.hdvideoplayer_dataSourceFactory = defaultDataSourceFactory;
        final List<VideoItem> list = this.hdvideoplayer_list;
        if (list != null) {
            this.hdvideoplayer_videoSources = new MediaSource[list.size()];
            for (int i = 0; i < this.hdvideoplayer_list.size(); i++) {
                this.hdvideoplayer_videoSources[i] = new ExtractorMediaSource.Factory(this.hdvideoplayer_dataSourceFactory).createMediaSource(Uri.parse(this.hdvideoplayer_list.get(i).DATA));
            }
            MediaSource[] mediaSourceArr = this.hdvideoplayer_videoSources;
            hdvideoplayer_videoSource = mediaSourceArr.length == 1 ? mediaSourceArr[0] : new ConcatenatingMediaSource(mediaSourceArr);
        } else {
            hdvideoplayer_videoSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.parse(getIntent().getDataString()));
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        hdvideoplayer_attributes = attributes;
        attributes.screenBrightness = this.hdvideoplayer_brightnessv;
        getWindow().setAttributes(this.hdvideoplayer_attributes);
        PlaybackParameters playbackParameters = new PlaybackParameters(this.hdvideoplayer_speedv);
        hdvideoplayer_parameters = playbackParameters;
        hdvideoplayer_player.setPlaybackParameters(playbackParameters);
        hdvideoplayer_playerView.setPlayer(this.hdvideoplayer_player);
        hdvideoplayer_player.prepare(hdvideoplayer_videoSource);
        hdvideoplayer_player.seekTo(this.hdvideoplayer_position, this.hdvideoplayer_current);
        hdvideoplayer_player.setPlayWhenReady(false);
        int resumestatus = PreferenceUtil.getInstance(this).getResumestatus();
        long j = PreferenceUtil.getInstance(this).getresumeVideotime(this, this.hdvideoplayer_list.get(this.hdvideoplayer_player.getCurrentWindowIndex()).getDISPLAY_NAME());
        if (!PreferenceUtil.getInstance(this).getResumBool()) {
            hdvideoplayer_player.setPlayWhenReady(true);
            hdvideoplayer_player.seekTo(this.hdvideoplayer_position, this.hdvideoplayer_current);
            PreferenceUtil.getInstance().saveResumBool(true);
        } else if (resumestatus == 0) {
            hdvideoplayer_player.setPlayWhenReady(true);
            hdvideoplayer_player.seekTo(this.hdvideoplayer_position, j);
        } else if (resumestatus == 1) {
            hdvideoplayer_player.setPlayWhenReady(true);
           hdvideoplayer_player.seekTo(this.hdvideoplayer_position, this.hdvideoplayer_current);
        } else {
            resumeAskDialog();
        }
        hdvideoplayer_playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            public void onVisibilityChange(int i) {
                if (VideoPlayerActivity.this.hdvideoplayer_batterseen.booleanValue()) {
                    if (i == 0) {
                        VideoPlayerActivity.this.hdvideoplayer_frembatery.setVisibility(View.GONE);
                    } else {
                        VideoPlayerActivity.this.hdvideoplayer_frembatery.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        hdvideoplayer_playerView.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            public void onSingleClick(View view) {
            }

            public void onDoubleClick(View view) {
                if (hdvideoplayer_db) {
                    hdvideoplayer_player.setPlayWhenReady(false);
                    hdvideoplayer_db = false;
                    return;
                }
                hdvideoplayer_player.setPlayWhenReady(true);
                hdvideoplayer_db = true;
            }
        }));
        hdvideoplayer_rotate =  findViewById(R.id.rotate);
        hdvideoplayer_lock =  findViewById(R.id.lock);
        hdvideoplayer_unlock =  findViewById(R.id.unlock);
        hdvideoplayer_crop = findViewById(R.id.exo_crop);
        hdvideoplayer_back =  findViewById(R.id.back);
        hdvideoplayer_takePicture = findViewById(R.id.takePicture);
        hdvideoplayer_pspeed = findViewById(R.id.pspeed);
        hdvideoplayer_popup =  findViewById(R.id.popup);
        hdvideoplayer_playlist =  findViewById(R.id.playlist);
        hdvideoplayer_sheetmain =  findViewById(R.id.sheetmain);
        hdvideoplayer_bottomSheetDialog = new BottomSheetDialog(this);
        ViewGroup viewGroup = null;
        getLayoutInflater().inflate(R.layout.playlist_sheet, (ViewGroup) null);
        RecyclerView recyclerView = findViewById(R.id.r1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.getLayoutManager().scrollToPosition(this.hdvideoplayer_position);
        recyclerView.setAdapter(new PlayListAdapter(this, this.hdvideoplayer_list, this.hdvideoplayer_player));
        hdvideoplayer_pspeed.setText(String.format("%sX", new Object[]{Float.valueOf(this.hdvideoplayer_speedv)}));
        hdvideoplayer_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoPlayerActivity.this.onBackPressed();
            }
        });
        hdvideoplayer_takePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MediaMetadataRetriever mediaMetadataRetriever = VideoPlayerActivity.this.hdvideoplayer_mediaMetadataRetriever;
                VideoPlayerActivity videoPlayerActivity = VideoPlayerActivity.this;
                mediaMetadataRetriever.setDataSource(videoPlayerActivity, Uri.parse(((VideoItem) list.get(videoPlayerActivity.hdvideoplayer_position)).getDATA()));
                VideoPlayerActivity videoPlayerActivity2 = VideoPlayerActivity.this;
                videoPlayerActivity2.save_image(videoPlayerActivity2.hdvideoplayer_mediaMetadataRetriever.getFrameAtTime(VideoPlayerActivity.this.hdvideoplayer_player.getCurrentPosition() * 1000));
            }
        });
        hdvideoplayer_lock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoPlayerActivity.this.lock();
            }
        });
        hdvideoplayer_unlock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoPlayerActivity.this.unlock();
            }
        });
        hdvideoplayer_crop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int i = VideoPlayerActivity.this.hdvideoplayer_view;
                if (i == 0) {
                    hdvideoplayer_playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    hdvideoplayer_view = 3;
                    hdvideoplayer_crop.setImageResource(R.drawable.fit);
                } else if (i == 3) {
                    hdvideoplayer_playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                    hdvideoplayer_crop.setImageResource(R.drawable.zoom);
                    hdvideoplayer_view = 4;
                } else if (i == 4) {
                    hdvideoplayer_playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    hdvideoplayer_crop.setImageResource(R.drawable.full);
                    hdvideoplayer_view = 0;
                }
            }
        });
        this.hdvideoplayer_pspeed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
                ViewGroup viewGroup = null;
                View inflate = LayoutInflater.from(VideoPlayerActivity.this).inflate(R.layout.playback_dialog, (ViewGroup) null);
                VideoPlayerActivity.this.hdvideoplayer_dspeed = (TextView) inflate.findViewById(R.id.dspeed);
                final AtomicInteger atomicInteger = new AtomicInteger((int) (PreferenceUtil.getInstance(VideoPlayerActivity.this).getLastSpeed() * 100.0f));
                VideoPlayerActivity.this.hdvideoplayer_dspeed.setText(Integer.toString(atomicInteger.get()));
                ((ImageButton) inflate.findViewById(R.id.sdown)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (atomicInteger.get() <= 100) {
//                            AtomicInteger atomicInteger = null;
                            atomicInteger.set(atomicInteger.get() - 5);
                            if (atomicInteger.get() < 24) {
                                AtomicInteger atomicInteger2 = atomicInteger;
                                atomicInteger2.set(atomicInteger2.get() + 5);
                            }
                        } else {
                            AtomicInteger atomicInteger3 = atomicInteger;
                            atomicInteger3.set(atomicInteger3.get() - 10);
                        }
                        VideoPlayerActivity.this.setSpeed(atomicInteger.get());
                    }
                });
                ((ImageButton) inflate.findViewById(R.id.sup)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (atomicInteger.get() < 100) {
//                            AtomicInteger atomicInteger = null;
                            atomicInteger.set(atomicInteger.get() + 5);
                        } else {
                            AtomicInteger atomicInteger2 = atomicInteger;
                            atomicInteger2.set(atomicInteger2.get() + 10);
                            if (atomicInteger.get() > 401) {
                                AtomicInteger atomicInteger3 = atomicInteger;
                                atomicInteger3.set(atomicInteger3.get() - 10);
                            }
                        }
                        VideoPlayerActivity.this.setSpeed(atomicInteger.get());
                    }
                });
                builder.setView(inflate);
                builder.show();
            }
        });
        TextView textView = (TextView) findViewById(R.id.title);
        this.hdvideoplayer_title = textView;
        textView.setSelected(true);
        if (getResources().getConfiguration().orientation == 1) {
            this.hdvideoplayer_width = displayMetrics.widthPixels;
            titlepot();
        } else {
            this.hdvideoplayer_width = displayMetrics.heightPixels;
            titleland();
        }
        List<VideoItem> list2 = this.hdvideoplayer_list;
        if (list2 != null) {
            this.hdvideoplayer_title.setText(list2.get(this.hdvideoplayer_position).DISPLAY_NAME);
            Gson gson = new Gson();
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.hdvideoplayer_list);
            PreferenceUtil.getInstance(getApplicationContext()).setVideoURL(gson.toJson((Object) arrayList).toString());
            PreferenceUtil.getInstance(getApplicationContext()).setVideoPosition(this.hdvideoplayer_position);
        } else {
            this.hdvideoplayer_title.setText(uri2filename());
        }
        this.hdvideoplayer_rotate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hdvideoplayer_isOrientation = false;
                VideoPlayerActivity videoPlayerActivity = VideoPlayerActivity.this;
                videoPlayerActivity.hdvideoplayer_orientation = PreferenceUtil.getInstance(videoPlayerActivity.getApplicationContext()).getOrientation();
                if (hdvideoplayer_orientation == 0) {
                    Toast.makeText(VideoPlayerActivity.this.getApplicationContext(), "Landscape Locked", Toast.LENGTH_SHORT).show();
                    hdvideoplayer_rotate.setImageDrawable(VideoPlayerActivity.this.getResources().getDrawable(R.drawable.ic_screen_lock_landscape_black_24dp));
                    PreferenceUtil.getInstance(VideoPlayerActivity.this.getApplicationContext()).saveOrientation(1);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                } else if (hdvideoplayer_orientation == 1) {
                    Toast.makeText(VideoPlayerActivity.this.getApplicationContext(), "Portrait Locked", Toast.LENGTH_SHORT).show();
                   hdvideoplayer_rotate.setImageDrawable(VideoPlayerActivity.this.getResources().getDrawable(R.drawable.ic_screen_lock_portrait_black_24dp));
                    PreferenceUtil.getInstance(VideoPlayerActivity.this.getApplicationContext()).saveOrientation(2);
                   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    Toast.makeText(VideoPlayerActivity.this.getApplicationContext(), "Auto Rotate", Toast.LENGTH_SHORT).show();
                    hdvideoplayer_rotate.setImageDrawable(VideoPlayerActivity.this.getResources().getDrawable(R.drawable.rotate));
                    PreferenceUtil.getInstance(VideoPlayerActivity.this.getApplicationContext()).saveOrientation(0);
                   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        });
        this.hdvideoplayer_player.addListener(new Player.EventListener() {
            public void onIsPlayingChanged(boolean z) {
            }

            public void onLoadingChanged(boolean z) {
            }

            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }

            public void onPlaybackSuppressionReasonChanged(int i) {
            }

            public void onPlayerError(ExoPlaybackException exoPlaybackException) {
            }

            public void onRepeatModeChanged(int i) {
            }

            public void onSeekProcessed() {
            }

            public void onShuffleModeEnabledChanged(boolean z) {
            }

            public void onTimelineChanged(Timeline timeline, int i) {
            }

            public void onTimelineChanged(Timeline timeline, Object obj, int i) {
            }

            public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
                if (trackGroupArray != VideoPlayerActivity.this.hdvideoplayer_lastSeenTrackGroupArray) {
                    MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = VideoPlayerActivity.this.hdvideoplayer_trackSelector.getCurrentMappedTrackInfo();
                    if (currentMappedTrackInfo != null) {
                        if (currentMappedTrackInfo.getTypeSupport(2) == 1) {
                            Toast.makeText(VideoPlayerActivity.this, "Media includes video tracks, but none are playable by this device", Toast.LENGTH_SHORT).show();
                        }
                        if (currentMappedTrackInfo.getTypeSupport(1) == 1) {
                            Toast.makeText(VideoPlayerActivity.this, "Media includes audio tracks, but none are playable by this device", Toast.LENGTH_SHORT).show();
                        }
                    }
                    VideoPlayerActivity.this.hdvideoplayer_lastSeenTrackGroupArray = trackGroupArray;
                }
            }

            public void onPositionDiscontinuity(int i) {
                if (!PreferenceUtil.getInstance().getAutoplaynext() && i == 0) {
                    VideoPlayerActivity.this.hdvideoplayer_player.setPlayWhenReady(false);
                }
                int currentWindowIndex = VideoPlayerActivity.this.hdvideoplayer_player.getCurrentWindowIndex();
                if (currentWindowIndex != VideoPlayerActivity.this.hdvideoplayer_position) {
                    VideoPlayerActivity.this.hdvideoplayer_position = currentWindowIndex;
                    VideoPlayerActivity.this.hdvideoplayer_title.setText(VideoPlayerActivity.this.hdvideoplayer_list.get(currentWindowIndex).DISPLAY_NAME);
                    Gson gson = new Gson();
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(VideoPlayerActivity.this.hdvideoplayer_list);
                    PreferenceUtil.getInstance(VideoPlayerActivity.this.getApplicationContext()).setVideoURL(gson.toJson((Object) arrayList).toString());
                    PreferenceUtil.getInstance(VideoPlayerActivity.this.getApplicationContext()).setVideoPosition(VideoPlayerActivity.this.hdvideoplayer_position);
                }
            }

            public void onPlayerStateChanged(boolean z, int i) {
                if (VideoPlayerActivity.this.hdvideoplayer_adbool && VideoPlayerActivity.this.hdvideoplayer_isloaded) {
                    if (z) {
                        VideoPlayerActivity.this.hdvideoplayer_vadfrm.setVisibility(View.GONE);
                    } else if (VideoPlayerActivity.this.hdvideoplayer_isAsk) {
                        VideoPlayerActivity.this.hdvideoplayer_vadfrm.setVisibility(View.GONE);
                    } else {
                        VideoPlayerActivity.this.hdvideoplayer_vadfrm.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        PlayerView playerView = this.hdvideoplayer_playerView;
//        playerView.setOnTouchListener(new OnSwipeTouchListener(this, this.hdvideoplayer_player, playerView, this.hdvideoplayer_audioManager));
//        this.hdvideoplayer_popup.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(VideoPlayerActivity.this.getApplicationContext())) {
//                    VideoPlayerActivity.this.popup();
//                    return;
//                }
//                VideoPlayerActivity videoPlayerActivity = VideoPlayerActivity.this;
//                videoPlayerActivity.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + VideoPlayerActivity.this.getPackageName())), VideoPlayerActivity.hdvideoplayer_CODE_DRAW_OVER_OTHER_APP_PERMISSION);
//            }
//        });
        this.hdvideoplayer_sheetmain.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                VideoPlayerActivity.this.hdvideoplayer_sheetmain.setVisibility(View.GONE);
                return false;
            }
        });
        this.hdvideoplayer_playlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoPlayerActivity.this.hdvideoplayer_sheetmain.setVisibility(View.VISIBLE);
            }
        });
        try {
            this.hdvideoplayer_presetReverb = new PresetReverb(0, this.hdvideoplayer_player.getAudioSessionId());
            Equalizers.initEq(this.hdvideoplayer_player.getAudioSessionId());
            BassBoosts.initBass(this.hdvideoplayer_player.getAudioSessionId());
            Virtualizers.initVirtualizer(this.hdvideoplayer_player.getAudioSessionId());
            Loud.initLoudnessEnhancer(this.hdvideoplayer_player.getAudioSessionId());
            loadEqualizer();
            enableDisable(Boolean.valueOf(PreferenceUtil.getInstance(getApplicationContext()).geteqSwitch()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return this.hdvideoplayer_player.getPlaybackState() == 3 && this.hdvideoplayer_player.getPlayWhenReady();
    }

    public void loadEqualizer() {
        List<String> presetNames = getPresetNames();
        if (presetNames.size() != 0) {
            int presetPos = PreferenceUtil.getInstance(getApplicationContext()).getPresetPos();
            hdvideoplayer_equalizerListData = new ArrayList<>();
            for (int i = 0; i < presetNames.size(); i++) {
                if (i == presetPos) {
                    hdvideoplayer_equalizerListData.add(i, new EqualizerDataList(presetNames.get(i), true));
                } else {
                    hdvideoplayer_equalizerListData.add(i, new EqualizerDataList(presetNames.get(i), false));
                }
            }
        }
    }

    private List<String> getPresetNames() {
        ArrayList arrayList = new ArrayList();
        short presetNo = Equalizers.getPresetNo();
        if (presetNo != 0) {
            for (short s = 0; s < presetNo; s = (short) (s + 1)) {
                arrayList.add(Equalizers.getPresetNames(s));
            }
            arrayList.add("Custom");
        }
        return arrayList;
    }

    public void enableDisable(Boolean bool) {
        Equalizers.setEnabled(bool.booleanValue());
        BassBoosts.setEnabled(bool.booleanValue());
        Virtualizers.setEnabled(bool.booleanValue());
        Loud.setEnabled(bool.booleanValue());
        this.hdvideoplayer_presetReverb.setEnabled(bool.booleanValue());
    }

    private String uri2filename() {
        String scheme = getIntent().getData().getScheme();
        if (scheme.equals("file")) {
            return getIntent().getData().getLastPathSegment();
        }
        scheme.equals("content");
        return "";
    }

    public void lock() {
        this.hdvideoplayer_lockstatus = Boolean.valueOf(!this.hdvideoplayer_lockstatus.booleanValue());
        ((LinearLayout) findViewById(R.id.bottom_control)).setVisibility(View.INVISIBLE);
        ((LinearLayout) findViewById(R.id.center_left_control1)).setVisibility(View.INVISIBLE);
        ((LinearLayout) findViewById(R.id.top_control)).setVisibility(View.INVISIBLE);
        this.hdvideoplayer_unlock.setVisibility(View.VISIBLE);
        PreferenceUtil.getInstance(getApplicationContext()).setLock(true);
    }

    public void unlock() {
        this.hdvideoplayer_lockstatus = Boolean.valueOf(!this.hdvideoplayer_lockstatus.booleanValue());
        ((LinearLayout) findViewById(R.id.bottom_control)).setVisibility(View.VISIBLE);
        ((LinearLayout) findViewById(R.id.center_left_control1)).setVisibility(View.VISIBLE);
        ((LinearLayout) findViewById(R.id.top_control)).setVisibility(View.VISIBLE);
        this.hdvideoplayer_unlock.setVisibility(View.INVISIBLE);
        PreferenceUtil.getInstance(getApplicationContext()).setLock(false);
    }

    private void titlepot() {
        ViewGroup.LayoutParams layoutParams = this.hdvideoplayer_title.getLayoutParams();
        layoutParams.width = this.hdvideoplayer_width / 7;
        this.hdvideoplayer_title.setLayoutParams(layoutParams);
    }

    private void titleland() {
        ViewGroup.LayoutParams layoutParams = this.hdvideoplayer_title.getLayoutParams();
        layoutParams.width = this.hdvideoplayer_width;
        this.hdvideoplayer_title.setLayoutParams(layoutParams);
    }

    public void setSpeed(int i) {
        this.hdvideoplayer_dspeed.setText(Integer.toString(i));
        float f = ((float) i) / 100.0f;
        this.hdvideoplayer_pspeed.setText(String.format("%sX", new Object[]{Float.valueOf(f)}));
        PlaybackParameters playbackParameters = new PlaybackParameters(f);
        this.hdvideoplayer_parameters = playbackParameters;
        this.hdvideoplayer_player.setPlaybackParameters(playbackParameters);
        PreferenceUtil.getInstance(getApplicationContext()).saveLastSpeed(f);
    }

//    public void popup() {
//        Intent intent = new Intent(this, floating.class);
//        intent.putExtra(hdvideoplayer_KEY_POSITION, this.hdvideoplayer_position);
//        intent.putExtra("list", (Serializable) this.hdvideoplayer_list);
//        intent.putExtra("current", this.hdvideoplayer_player.getCurrentPosition());
//        startService(intent);
//        onBackPresse();
//    }

//    public void onConfigurationChanged(Configuration configuration) {
//        super.onConfigurationChanged(configuration);
//        adjustFullScreen(configuration);
//    }

    public void onDestroy() {
        super.onDestroy();
        PreferenceUtil.getInstance(this).setResumeVideotime(this, Long.valueOf(this.hdvideoplayer_player.getContentPosition()), this.hdvideoplayer_list.get(this.hdvideoplayer_player.getCurrentWindowIndex()).DISPLAY_NAME);
        unregisterReceiver(this.hdvideoplayer_broadcastReceiver);
    }

    public void onResume() {
        super.onResume();
        this.hdvideoplayer_vadfrm.setVisibility(View.GONE);
        if (this.hdvideoplayer_isOrientation) {
            int orientation = PreferenceUtil.getInstance(getApplicationContext()).getOrientation();
            this.hdvideoplayer_orientation = orientation;
            if (orientation == 0) {
                setRequestedOrientation(4);
                this.hdvideoplayer_rotate.setImageDrawable(getResources().getDrawable(R.drawable.rotate));
            } else if (orientation == 1) {
                setRequestedOrientation(6);
                this.hdvideoplayer_rotate.setImageDrawable(getResources().getDrawable(R.drawable.ic_screen_lock_landscape_black_24dp));
            } else {
                setRequestedOrientation(1);
                this.hdvideoplayer_rotate.setImageDrawable(getResources().getDrawable(R.drawable.ic_screen_lock_portrait_black_24dp));
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (this.hdvideoplayer_lockstatus.booleanValue()) {
            unlock();
        }
        this.hdvideoplayer_player.setPlayWhenReady(false);
    }

    public void onBackPresse() {
        this.hdvideoplayer_vadfrm.setVisibility(View.GONE);
        this.hdvideoplayer_adbool = false;
        this.hdvideoplayer_isloaded = false;
        if (!this.hdvideoplayer_lockstatus.booleanValue()) {
            try {
                PreferenceUtil.getInstance().setResumeVideotime(this, Long.valueOf(this.hdvideoplayer_player.getContentPosition()), this.hdvideoplayer_list.get(this.hdvideoplayer_player.getCurrentWindowIndex()).DISPLAY_NAME);
                this.hdvideoplayer_player.release();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onBackPressed() {
        this.hdvideoplayer_vadfrm.setVisibility(View.GONE);
        this.hdvideoplayer_adbool = false;
        this.hdvideoplayer_isloaded = false;
        if (!this.hdvideoplayer_lockstatus.booleanValue()) {
            try {
                PreferenceUtil.getInstance().setResumeVideotime(this, Long.valueOf(this.hdvideoplayer_player.getContentPosition()), this.hdvideoplayer_list.get(this.hdvideoplayer_player.getCurrentWindowIndex()).DISPLAY_NAME);
                this.hdvideoplayer_player.release();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private void checkSetPer() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Need Permissions");
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
//        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
////                VideoPlayerActivity.this.openSettings();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        builder.show();
//    }

//    public void openSettings() {
//        Intent intent;
//        if (Build.VERSION.SDK_INT >= 23) {
//            intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName()));
//        } else {
//            intent = null;
//        }
//        startActivityForResult(intent, hdvideoplayer_CODE_DRAW_OVER_OTHER_APP_PERMISSION);
//    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == hdvideoplayer_CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (i2 == -1) {
//                popup();
                return;
            }
//            checkSetPer();
            finish();
        } else if (i != 25) {
            super.onActivityResult(i, i2, intent);
        } else if (i2 == -1 && intent.hasExtra(FilePicker.EXTRA_FILE_PATH)) {
            String stringExtra = intent.getStringExtra(FilePicker.EXTRA_FILE_PATH);
            Objects.requireNonNull(stringExtra);
            Uri.fromFile(new File(stringExtra));
        }
    }

    public void save_image(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/");
        file.mkdirs();
        File file2 = new File(file, this.hdvideoplayer_list.get(this.hdvideoplayer_position).getDISPLAY_NAME() + "_" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + ".png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Context applicationContext = getApplicationContext();
            Toast.makeText(applicationContext, "Saved to " + file2.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MakeSureFileWasCreatedThenMakeAvabile(file2);
    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file) {
        String[] strArr = null;
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.toString()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String str, Uri uri) {
            }
        });
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            adjustFullScreen(getResources().getConfiguration());
        }
    }

    private void adjustFullScreen(Configuration configuration) {
        View decorView = getWindow().getDecorView();
        if (configuration.orientation == 2) {
            decorView.setSystemUiVisibility(5894);
            return;
        }
        decorView.setSystemUiVisibility(256);
        this.hdvideoplayer_control_layout.setVisibility(View.VISIBLE);
    }

    public void resumeAskDialog() {
        this.hdvideoplayer_defualt = false;
        this.hdvideoplayer_dislis = true;
        ViewGroup viewGroup = null;
        View inflate = LayoutInflater.from(this).inflate(R.layout.pcheckbox, (ViewGroup) null);
        final long j = PreferenceUtil.getInstance(this).getresumeVideotime(this, this.hdvideoplayer_list.get(this.hdvideoplayer_position).getDISPLAY_NAME());
        if (j == 0) {
            this.hdvideoplayer_player.setPlayWhenReady(true);
            this.hdvideoplayer_player.seekTo(this.hdvideoplayer_position, this.hdvideoplayer_current);
            return;
        }
        this.hdvideoplayer_isAsk = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.hdvideoplayer_list.get(this.hdvideoplayer_position).getDISPLAY_NAME());
        builder.setMessage("Do you wish to resume from where you stopped?");
        builder.setView(inflate);
        builder.setPositiveButton("RESUME", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoPlayerActivity.this.hdvideoplayer_dislis = false;
                if (VideoPlayerActivity.this.hdvideoplayer_defualt) {
                    PreferenceUtil.getInstance(VideoPlayerActivity.this).saveResumestatus(0);
                }
                VideoPlayerActivity.this.hdvideoplayer_isAsk = false;
                VideoPlayerActivity.this.hdvideoplayer_player.setPlayWhenReady(true);
                VideoPlayerActivity.this.hdvideoplayer_player.seekTo(VideoPlayerActivity.this.hdvideoplayer_position, j);
            }
        });
        builder.setNegativeButton("START OVER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoPlayerActivity.this.hdvideoplayer_dislis = false;
                if (VideoPlayerActivity.this.hdvideoplayer_defualt) {
                    PreferenceUtil.getInstance(VideoPlayerActivity.this).saveResumestatus(1);
                }
                VideoPlayerActivity.this.hdvideoplayer_isAsk = false;
                VideoPlayerActivity.this.hdvideoplayer_player.setPlayWhenReady(true);
                VideoPlayerActivity.this.hdvideoplayer_player.seekTo(VideoPlayerActivity.this.hdvideoplayer_position, VideoPlayerActivity.this.hdvideoplayer_current);
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                VideoPlayerActivity.this.hdvideoplayer_isAsk = false;
                if (VideoPlayerActivity.this.hdvideoplayer_dislis) {
                    VideoPlayerActivity.this.hdvideoplayer_vadfrm.setVisibility(View.GONE);
                    VideoPlayerActivity.this.hdvideoplayer_player.setPlayWhenReady(true);
                    VideoPlayerActivity.this.hdvideoplayer_player.seekTo(VideoPlayerActivity.this.hdvideoplayer_position, VideoPlayerActivity.this.hdvideoplayer_current);
                }
            }
        });
        builder.show();
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.checkbox);
        checkBox.setText("Use by default");
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                VideoPlayerActivity.this.hdvideoplayer_defualt = z;
            }
        });
    }
}