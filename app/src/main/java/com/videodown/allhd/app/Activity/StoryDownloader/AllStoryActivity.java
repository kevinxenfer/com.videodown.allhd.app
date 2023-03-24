package com.videodown.allhd.app.Activity.StoryDownloader;


import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.ClipboardListener;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.Activity.AExtraScreen.IntroChingariActivity;
import com.videodown.allhd.app.Activity.AExtraScreen.IntroInstaActivity;
import com.videodown.allhd.app.Activity.AExtraScreen.IntroSharechatActivity;
import com.videodown.allhd.app.Activity.AExtraScreen.IntroTwitterActivity;
import com.videodown.allhd.app.Activity.AExtraScreen.IntroWPActivity;
import com.videodown.allhd.app.Activity.AExtraScreen.IntrofbActivity;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

public class AllStoryActivity extends CdActivityBase {

    RelativeLayout img_insta, img_fb, img_wtsup, img_twitter, img_sharechat, img_josh, img_mitron, img_chingari;
    public ClipboardManager clipBoard;
    String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    String CopyKey = "";
    String CopyValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_story);

        img_insta = findViewById(R.id.img_instagram);
        img_fb = findViewById(R.id.img_facebbok);
        img_wtsup = findViewById(R.id.img_whatspp);
        img_twitter = findViewById(R.id.img_twitter);
        img_sharechat = findViewById(R.id.img_sharechat);
        img_chingari = findViewById(R.id.img_chingari);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        new MainAds().showNativeAds(this, null, null, AD_LARGE);
        new MainAds().showBannerAds(this, null);

    }

    public void initViews() {
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (getIntent().getExtras() != null) {
            for (String str : getIntent().getExtras().keySet()) {
                this.CopyKey = str;
                String string = getIntent().getExtras().getString(this.CopyKey);
                if (this.CopyKey.equals("android.intent.extra.TEXT")) {
                    String string2 = getIntent().getExtras().getString(this.CopyKey);
                    this.CopyValue = string2;
                    this.CopyValue = extractLinks(string2);
                    callText(string);
                } else {
                    this.CopyValue = "";
                    callText(string);
                }
            }
        }

        ClipboardManager clipboardManager = this.clipBoard;
        if (clipboardManager != null) {
            clipboardManager.addPrimaryClipChangedListener(new ClipboardListener() {
                public void onPrimaryClipChanged() {
                    try {
                        CharSequence text = clipBoard.getPrimaryClip().getItemAt(0).getText();
                        Objects.requireNonNull(text);
                        showNotification(text.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions(0);
        }

        img_insta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(101);
                } else {
                    callInstaActivity();
                }
            }
        });

        img_wtsup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(102);
                } else {
                    callWhatsappActivity();
                }
            }
        });
        img_fb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(104);
                } else {
                    callFacebookActivity();
                }
            }
        });
        img_twitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(106);
                } else {
                    callTwitterActivity();
                }
            }
        });

        img_sharechat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(107);
                } else {
                    callShareChatActivity();
                }
            }
        });

        img_chingari.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(111);
                } else {
                    callChingariActivity();
                }
            }
        });
        MyUtils.createFileFolder();
    }

    private void callText(String str) {
        try {
            if (!str.contains("instagram.com")) {
                if (!str.contains("facebook.com") && !str.contains("fb")) {
                    if (str.contains("twitter.com")) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(106);
                            return;
                        } else {
                            callTwitterActivity();
                            return;
                        }
                    } else if (str.contains("sharechat")) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(107);
                            return;
                        } else {
                            callShareChatActivity();
                            return;
                        }
                    } else if (!str.contains("roposo")) {
                        if (!str.contains("snackvideo") && !str.contains("sck.io")) {
                            if (str.contains("josh")) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    checkPermissions(110);
                                    return;
                                } else {
                                    callJoshActivity();
                                    return;
                                }
                            } else if (str.contains("chingari")) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    checkPermissions(111);
                                    return;
                                } else {
                                    callChingariActivity();
                                    return;
                                }
                            } else if (str.contains("mitron")) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    checkPermissions(112);
                                    return;
                                } else {
                                    callMitronActivity();
                                    return;
                                }
                            } else if (!str.contains("moj")) {
                                return;
                            }
                        }
                    } else if (Build.VERSION.SDK_INT >= 23) {
                        checkPermissions(108);
                        return;
                    } else {
//                        callRoposoActivity();
                        return;
                    }
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(104);
                } else {
                    callFacebookActivity();
                }
            } else if (Build.VERSION.SDK_INT >= 23) {
                checkPermissions(101);
            } else {
                callInstaActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callJoshActivity() {
        new MainAds().showInterAds(AllStoryActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(AllStoryActivity.this, JoshActivity.class);
                intent.putExtra("CopyIntent", CopyValue);
                startActivity(intent);
            }
        });
    }

    public void callChingariActivity() {

        new MainAds().showInterAds(AllStoryActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {

                if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                    Intent intent = new Intent(AllStoryActivity.this, IntroChingariActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AllStoryActivity.this, ChingariActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                }

            }
        });


    }

    public void callMitronActivity() {

        new MainAds().showInterAds(AllStoryActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(AllStoryActivity.this, MitronActivity.class);
                intent.putExtra("CopyIntent", CopyValue);
                startActivity(intent);
            }
        });


    }

    public void callInstaActivity() {
        new MainAds().showInterAds(AllStoryActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {

                if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                    Intent intent = new Intent(AllStoryActivity.this, IntroInstaActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AllStoryActivity.this, InstagramSelectionActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                }
            }
        });


    }

    public void callWhatsappActivity() {

        new MainAds().showInterAds(AllStoryActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {

                if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                    Intent intent = new Intent(AllStoryActivity.this, IntroWPActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AllStoryActivity.this, WhatsappActivity.class);
                    startActivity(intent);
                }

            }
        });


    }

    public void callFacebookActivity() {


        new MainAds().showInterAds(AllStoryActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {


                if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                    Intent intent = new Intent(AllStoryActivity.this, IntrofbActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AllStoryActivity.this, FacebookSelectionActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                }


            }
        });

    }

    public void callTwitterActivity() {

        new MainAds().showInterAds(AllStoryActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {

                if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                    Intent intent = new Intent(AllStoryActivity.this, IntroTwitterActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AllStoryActivity.this, TwitterActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                }


            }
        });


    }


    public void callShareChatActivity() {

        new MainAds().showInterAds(AllStoryActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {

                if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                    Intent intent = new Intent(AllStoryActivity.this, IntroSharechatActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AllStoryActivity.this, ShareChatActivity.class);
                    intent.putExtra("CopyIntent", CopyValue);
                    startActivity(intent);
                }


            }
        });


    }

    public void showNotification(String str) {
        if (str.contains("instagram.com") || str.contains("facebook.com") || str.contains("fb") || str.contains("tiktok.com") || str.contains("twitter.com") || str.contains("likee") || str.contains("sharechat") || str.contains("roposo") || str.contains("snackvideo") || str.contains("sck.io") || str.contains("chingari") || str.contains("myjosh") || str.contains("mitron")) {
            Intent intent = new Intent(this, AllStoryActivity.class);
            intent.addFlags(67108864);
            intent.putExtra("Notification", str);
            PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            NotificationManager notificationManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            }
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel(getResources().getString(R.string.app_name), getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLockscreenVisibility(1);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            notificationManager.notify(1, new NotificationCompat.Builder((Context) this, getResources().getString(R.string.app_name)).setAutoCancel(true).setSmallIcon(R.drawable.icon).setColor(getResources().getColor(R.color.black)).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon)).setDefaults(-1).setPriority(1).setContentTitle("Copied text").setContentText(str).setChannelId(getResources().getString(R.string.app_name)).setFullScreenIntent(activity, true).build());
        }
    }


    public boolean checkPermissions(int i) {
        ArrayList arrayList = new ArrayList();
        for (String str : this.permissions) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (!arrayList.isEmpty()) {
            ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), i);
            return false;
        } else if (i == 101) {
            callInstaActivity();
            return true;
        } else if (i == 102) {
            callWhatsappActivity();
            return true;
        } else if (i == 104) {
            callFacebookActivity();
            return true;
        } else if (i == 105) {
//            callGalleryActivity();
            return true;
        } else if (i == 106) {
            callTwitterActivity();
            return true;
        } else if (i == 107) {
            callShareChatActivity();
            return true;
        } else if (i == 108) {
//            callRoposoActivity();
            return true;
        } else if (i == 110) {
            callJoshActivity();
            return true;
        } else if (i == 111) {
            callChingariActivity();
            return true;
        } else {
            if (i == 112) {
                callMitronActivity();
            }
            return true;
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 101) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callInstaActivity();
            }
        } else if (i == 102) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callWhatsappActivity();
            }
        } else if (i == 104) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callFacebookActivity();
            }
        } else if (i == 105) {
            if (iArr.length > 0 && iArr[0] == 0) {
//                callGalleryActivity();
            }
        } else if (i == 106) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callTwitterActivity();
            }
        } else if (i == 107) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callShareChatActivity();
            }
        } else if (i == 108) {
            if (iArr.length > 0 && iArr[0] == 0) {
//                callRoposoActivity();
            }
        } else if (i == 110) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callJoshActivity();
            }
        } else if (i == 111) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callChingariActivity();
            }
        } else if (i == 112 && iArr.length > 0 && iArr[0] == 0) {
            callMitronActivity();
        }
    }


//    public void setLocale(String str) {
//        Locale locale = new Locale(str);
//        Resources resources = getResources();
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//        Configuration configuration = resources.getConfiguration();
//        configuration.locale = locale;
//        resources.updateConfiguration(configuration, displayMetrics);
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }

    public static String extractLinks(String str) {
        Matcher matcher = Patterns.WEB_URL.matcher(str);
        if (!matcher.find()) {
            return "";
        }
        String group = matcher.group();
        Log.d("New URL", "URL extracted: " + group);
        return group;
    }

    @Override
    public void onBackPressed() {
        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }
}