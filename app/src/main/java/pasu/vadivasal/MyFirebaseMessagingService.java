package pasu.vadivasal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import pasu.vadivasal.android.Utils;

/**
 * Created by developer on 9/28/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    BroadcastReceiver mMessageReceiver;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        //  sendNotification(remoteMessage.getData().get("message"));
        Intent intent = new Intent();
        intent.setAction("myFunction");
        // add data
        sendNotification(remoteMessage);
//        String msg = remoteMessage.getData().get("message");
//        System.out.println("--------" + msg);
//        intent.putExtra("some_msg", msg);
////        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//        sendBroadcast(intent);
    }

    private void beep(int volume)
    {
        try {
            AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);

            Uri notification = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer player = MediaPlayer.create(getApplicationContext(), notification);
            player.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(final RemoteMessage remoteMessage) {
        System.out.println("messageBody" + remoteMessage.toString());
//        final NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//        RemoteViews expandedView = new RemoteViews(this.getPackageName(),
//                R.layout.notification_custom_remote);
//        expandedView.setTextViewText(R.id.text_view, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbcccccccccccccccccccccccccccccccc");
//        expandedView.setImageViewResource(R.id.img_view,R.mipmap.ic_launcher);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getApplicationContext()).setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_stat_logosmall)
                .setColor(getColor(R.color.colorAccent)).
                        setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setContentText(Utils.limitToLength(remoteMessage.getData().get("message"), 50));
beep(80);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(remoteMessage.getData().get("message"));
//        bigText.setBigContentTitle("GORIO Engenharia");
//        bigText.setSummaryText("Por: GORIO Engenharia");
        mBuilder.setStyle(bigText);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getApplicationContext(),
                NotificationReceiverActivity.class);
        try {
            if (remoteMessage.getData() != null) {
                System.out.println(TAG+"toopen"+remoteMessage.getData().get("open"));
                resultIntent.putExtra("open", remoteMessage.getData().get("open"));
                resultIntent.putExtra("id", remoteMessage.getData().get("id"));
                resultIntent.putExtra("content", remoteMessage.getData().get("content"));
                resultIntent.putExtra("message", remoteMessage.getData().get("message"));
                resultIntent.putExtra("videos", remoteMessage.getData().get("videos"));
                resultIntent.putExtra("about", remoteMessage.getData().get("about"));
                //  resultIntent.putExtra("some_msg",remoteMessage.getData().get("open"));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                |Intent.FLAG_ACTIVITY_SINGLE_TOP
//                |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // The stack builder object will contain an artificial back
        // stack for
        // the
        // started Activity.
        // getApplicationContext() ensures that navigating backward from
        // the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder
                .create(getApplicationContext());

        // Adds the back stack for the Intent (but not the Intent
        // itself)
        stackBuilder.addParentStack(NotificationReceiverActivity.class);

        // Adds the Intent that starts the Activity to the top of the
        // stack
        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder
//                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());

        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            System.out.println("RingtoneManager");
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
            r.play();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

     /*   Notification.Builder builder = new Notification.Builder(MyFirebaseMessagingService.this);
        builder.setAutoCancel(false);
        builder.setTicker("Notification");
        builder.setContentTitle("title");
        builder.setStyle(new Notification.BigTextStyle().bigText(messageBody));
        builder.setContentText(messageBody);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setOngoing(false);
        builder.setContent(expandedView);
        //builder.setSubText("This is subtext...");   //API level 16
//        builder.setNumber(100);
        builder.build();*/
    /*    Notification myNotication = builder.getNotification();
        myNotication.otflags |= Nification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(1, myNotication);
        myNotication.bigContentView = expandedView;*/
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//
//        boolean isScreenOn = pm.isScreenOn();
//        System.out.println("screen on.............." + isScreenOn);
//
//
//        Notification notification = new NotificationCompat.Builder(this)
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("nandhiniaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
//                .setContentTitle("Custom View").build();
//
//        notification.bigContentView = expandedView;
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
//            if (pm.isInteractive()) {
//                System.out.println("alarm screen ON---interactive" + pm.isInteractive());
//            }
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
//            if (pm.isScreenOn()) {
//                System.out.println("alarm screen ON" + pm.isScreenOn());
//            }
//        } else {
//            System.out.println("alarm screen OFF");
//        }
//                if(isScreenOn==false)
//                {
                   /* PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");
                    wl.acquire();*/
                   /* PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");

                    wl_cpu.acquire(10000);*/
//                }

//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "My Tag");
//        wl.acquire();
//        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        try {
//            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
//            builder.setSound(notification1);
//            r.play();
//        } catch (NullPointerException ex) {
//            ex.printStackTrace();
//        }
//

//        PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
//        boolean isScreenOn = pm.isScreenOn();
//        Log.e("screen on.................................", ""+isScreenOn);
//        if(isScreenOn==false)
//        {
//            WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");
//            wl.acquire(10000);
//            WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
//
//            wl_cpu.acquire(10000);
//        }


//
//        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
//                | PowerManager.ACQUIRE_CAUSES_WAKEUP
//                | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
//        wakeLock.acquire();
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setAutoCancel(false);
//        builder.setContentTitle("Firebase Push Notification");
//        builder.setContentText(messageBody);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContentIntent(pendingIntent);
//        builder.setOngoing(true);
//        builder.build();
//        Notification myNotication = builder.getNotification();
//        myNotication.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        mNotificationManager.notify(0, myNotication);
//        boolean isScreenOn = pm.isScreenOn();
//        if (isScreenOn == false) {
//            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
//            wl.acquire(10000);
//            PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");
//            wl_cpu.acquire(10000);
//        }
//        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        try {
//            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
//            r.play();
//        } catch (NullPointerException ex) {
//            ex.printStackTrace();
//        }

//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Firebase Push Notification")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notificationBuilder.build());
//        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        try {
//            PowerManager pm = (PowerManager)getApplicationContext().getSystemService(Context.POWER_SERVICE);
//
//            boolean isScreenOn = pm.isScreenOn();
//
//            if(isScreenOn==false)
//            {
//                System.out.println("Power is off:::::"+isScreenOn);
//
//
//                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");
//
//                wl.acquire(10000);
//                PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
//
//                wl_cpu.acquire(10000);
//            }
//            System.out.println("Power is On:::::"+isScreenOn);
//            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
//            r.play();
//        }catch (NullPointerException ex) {
//            ex.printStackTrace();
//        }
    }

}

