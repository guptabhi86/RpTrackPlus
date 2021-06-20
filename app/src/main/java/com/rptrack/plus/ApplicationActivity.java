package com.rptrack.plus;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.MySingleton;
import com.rptrack.plus.utilities.Preferences;

import java.io.IOException;


public class ApplicationActivity extends Application {

    public static APIUtility apiUtility;
    private static Context context;
    public static MediaPlayer mMediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        apiUtility = new APIUtility(getApplicationContext());
        ApplicationActivity.context = getApplicationContext();
        //FirebaseApp.initializeApp(this);
        if (Preferences.getPreference(ApplicationActivity.this, Constant.POLYLINE_COLOR).toString().isEmpty()) {
            Preferences.setPreference(ApplicationActivity.this, Constant.POLYLINE_COLOR, "#AA0E0E");
        }
        MySingleton singleton = new MySingleton();
        MySingleton.setInstance(singleton);
    }

    public static Context getContext() {
        return ApplicationActivity.context;
    }

    public static APIUtility getApiUtility() {
        return apiUtility;
    }

    public static void stopMedia() {
        if (mMediaPlayer != null)
            mMediaPlayer.stop();
        mMediaPlayer = null;
    }

    public static void playMedia(Context mContext) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        try {
            if (mMediaPlayer.isPlaying())
                return;

            Uri defaultNotificationUri = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.accomplished);
            mMediaPlayer.setDataSource(mContext, defaultNotificationUri);
            final AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.setLooping(true);
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }
}


