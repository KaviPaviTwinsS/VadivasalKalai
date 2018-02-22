//package pasu.vadivasal.service;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import org.json.JSONObject;
//
//import pasu.vadivasal.MainActivity;
//import pasu.vadivasal.R;
//
///**
// * Created by developer on 30/8/17.
// */
//
//public class FirebaseService extends FirebaseMessagingService {
//    public static final int NOTIFICATION_ID = 123;
//    private NotificationManager mNotificationManager;
//    NotificationCompat.Builder builder;
//    private JSONObject jo;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//       // Log.d("MyFirebaseIIDService", "From: " + remoteMessage.getNotification().getBody());
//        // Check if message contains a data payload.
//        System.out.println("MyFirebaseIIDServices" );
//        onHandleIntent(remoteMessage);
//        // Check if message contains a data payload.
//        System.out.println("MyFirebaseIIDServices" + remoteMessage.getData());
//      //  Log.d("MyFirebaseIIDService", "Message data payload: " + remoteMessage.getData());
//        if (remoteMessage.getData().size() > 0) {
//            Log.d("MyFirebaseIIDService", "Message data payload: " + remoteMessage.getData());
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d("sssss", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//
//        generateNotification();
//    }
//
////    private void generateNotification() {
////        //Channel with its original values performs no operation, so it's safe to perform the preceding sequence of steps when starting an app. The following code sample demonstrates creating a notification channel with a high importance level and a custom vibration pattern:
////
//////        NotificationManager mNotificationManager =
//////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//////// The id of the channel.
//////        String id = "my_channel_01";
//////// The user-visible name of the channel.
//////        CharSequence name = getString(R.string.channel_name);
//////// The user-visible description of the channel.
//////        String description = getString(R.string.channel_description);
//////        int importance = NotificationManager.IMPORTANCE_HIGH;
//////        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
//////// Configure the notification channel.
//////        mChannel.setDescription(description);
//////        mChannel.enableLights(true);
//////// Sets the notification light color for notifications posted to this
//////// channel, if the device supports this feature.
//////        mChannel.setLightColor(Color.RED);
//////        mChannel.enableVibration(true);
//////        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//////        mNotificationManager.createNotificationChannel(mChannel);
////
////
////
////
////        Intent intent = new Intent(this, MainActivity.class);
////// use System.currentTimeMillis() to have a unique ID for the pending intent
////        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
////
////// build notification
////// the addAction re-use the same intent to keep the example short
////        Notification n  = new Notification.Builder(this)
////                .setContentTitle("New mail from " + "test@gmail.com")
////                .setContentText("Subject")
////                .setSmallIcon(R.drawable.ic_stat_logosmall)
////                .setContentIntent(pIntent)
////                .setAutoCancel(true).build();
//////                .addAction(R.drawable.icon, "Call", pIntent)
//////                .addAction(R.drawable.icon, "More", pIntent)
//////                .addAction(R.drawable.icon, "And more", pIntent).build();
////
////
////        NotificationManager notificationManager =
////                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////
////        notificationManager.notify(0, n);
////
////
////    }
//
//
//    protected void onHandleIntent(RemoteMessage remoteMessage) {// Handling gcm message from
//        String messageType = remoteMessage.getMessageType();
//    }
//
//
//
//
//}
