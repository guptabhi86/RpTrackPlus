package com.rptrack.plus.FirebaseMessaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.activity.DashboardActivity;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertTypeFactory;
import com.rptrack.plus.alert.IAlert;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.MySingleton;
import com.rptrack.plus.utilities.Preferences;


import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.VISIBLE;
import static com.rptrack.plus.utilities.Preferences.getPreference_int;


public class FirebaseMessaging extends FirebaseMessagingService {

    private NotificationData notificationData;
    String NOTIFICATION_CHANNEL_ID = "RpTrack+";
    long pattern[] = {0, 1000, 500, 1000};
    RemoteViews contentViewBig, contentViewSmall;
    Bitmap bmp = null;
    String NOTIFICATION_GROUP = "com.rptrack.plus.NOTIFICATION";
    int SUMMARY_ID = 0;

    public FirebaseMessaging() {
        super();
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Log.d("NotificationData", new Gson().toJson(remoteMessage.getData()));
        notificationData = new Gson().fromJson(String.valueOf(new JSONObject(remoteMessage.getData())), NotificationData.class);
        notificationData.setTitle("RpTrack+");
        Preferences.setPreference_int(this, Constant.NOTIFICATIONCOUNT, (getPreference_int(this, Constant.NOTIFICATIONCOUNT) + 1));
        Preferences.setPreference_int(this, Constant.BADGECOUNT, (getPreference_int(this, Constant.BADGECOUNT) + 1));
        speakNotification(notificationData);
        CommonUtils.saveNotification(this, notificationData);
        Log.d("NotificationData", new Gson().toJson(notificationData));
        MySingleton.getInstance().setNotificationData(notificationData);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notifications Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            channel.setVibrationPattern(pattern);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        contentViewBig = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
        contentViewSmall = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
        try {
            contentViewBig.setTextViewText(R.id.title, notificationData.getTitle());
            contentViewSmall.setTextViewText(R.id.title, notificationData.getTitle());
            if (!notificationData.getImage().equals("")) {
                InputStream in = new URL(notificationData.getImage()).openStream();
                bmp = BitmapFactory.decodeStream(in);
                contentViewBig.setImageViewBitmap(R.id.circular_img, bmp);
                contentViewBig.setViewVisibility(R.id.circular_img, VISIBLE);
            }
        } catch (Exception e) {
            Log.e("notification", e.getMessage());
        }

        showNotificationDialog();
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("callNotificationFragment", notificationData);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        IAlert alertType = AlertTypeFactory.getInstance(this, notificationData.getAlertType());
        boolean showNotification = alertType.showAlert();
        if (showNotification) {
            sendMessageToActivity(String.valueOf(getPreference_int(this, Constant.NOTIFICATIONCOUNT)), notificationData);
            showBigNotification(bmp, notificationBuilder, notificationData, resultPendingIntent);
        }
    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Preferences.setPreference(getApplicationContext(), Constant.FCM_REGID, s);
    }


    private void sendMessageToActivity(String msg, NotificationData notificationData) {
        Intent intent = new Intent("GPSLocationUpdates");
        intent.putExtra("notificationCount", msg);
        intent.putExtra("notificationData", notificationData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, NotificationData notificationData1, PendingIntent resultPendingIntent1) {
        if (notificationData1.isCalling()) {
            playSound();
        } else {
            if (getPreference_int(this, Constant.NotificationType) == 1) {
                playSound();
            }
        }

        if (!CommonUtils.isAppIsInBackground(this))
            return;

        if (bitmap == null) {
            showSmallNotification(mBuilder, notificationData1, resultPendingIntent1);
        } else {
            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            bigPictureStyle.setBigContentTitle(getResources().getString(R.string.app_name));
            bigPictureStyle.setSummaryText(Html.fromHtml(notificationData.getBody()).toString());
            bigPictureStyle.bigPicture(bitmap);
            Notification notification;
            notification = mBuilder.setSmallIcon(R.drawable.app_icon_180).setTicker(notificationData.getTitle()).setWhen(Calendar.getInstance().getTimeInMillis())
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentIntent(resultPendingIntent1).setStyle(bigPictureStyle)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.app_icon_180))
                    .setColor(this.getResources().getColor(R.color.colorRed))
                    .setContentText(notificationData.getBody()).setVibrate(pattern)
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.accomplished))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                    .setGroupSummary(true).setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX).build();
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) (Math.random() * 49 + 1), notification);
        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, NotificationData notificationData, PendingIntent resultPendingIntent) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification newMessageNotification2 = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon_180)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(notificationData.getBody())
                .setContentIntent(resultPendingIntent).setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationData.getBody()))
                .setGroup(NOTIFICATION_GROUP).setSound(alarmSound).build();
        Notification notification = mBuilder.setSmallIcon(R.drawable.app_icon_180).setTicker(notificationData.getTitle()).setWhen(Calendar.getInstance().getTimeInMillis())
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(notificationData.getBody())
                .setVibrate(pattern).setAutoCancel(true).setGroupSummary(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationData.getBody())).setSound(alarmSound)
                .setPriority(Notification.PRIORITY_MAX).setContentIntent(resultPendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.app_icon_180))
                .setColor(this.getResources().getColor(R.color.colorRed)).setGroup(NOTIFICATION_GROUP).build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify((int) (Math.random() * 49 + 1), newMessageNotification2);
        notificationManager.notify(SUMMARY_ID, notification);
    }

    private void playSound() {
        ApplicationActivity.playMedia(this);
    }

    private void speakNotification(NotificationData notificationData1) {
        TextToSpeech textToSpeechSystem = null;
        if (notificationData1.isVoice() && CommonUtils.isAppIsInBackground(this)) {
            TextToSpeech finalTextToSpeechSystem = textToSpeechSystem;
            textToSpeechSystem = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finalTextToSpeechSystem.speak(notificationData1.getBody(), TextToSpeech.QUEUE_ADD, null, null);
                        } else {
                            finalTextToSpeechSystem.speak(notificationData1.getBody(), TextToSpeech.QUEUE_ADD, null);
                        }
                    }
                }
            });
        }
    }

    private void showNotificationDialog() {
        if (!CommonUtils.isAppIsInBackground(this)) {
            Intent intent = new Intent(Constant.PLAY_SOUND);
            intent.putExtra("callNotificationFragment", notificationData);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
}
