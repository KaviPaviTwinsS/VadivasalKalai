package pasu.vadivasal.service;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

/**
 * Created by developer on 30/8/17.
 */

public class FirebaseService extends FirebaseMessagingService {
    public static final int NOTIFICATION_ID = 123;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private JSONObject jo;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
       // Log.d("MyFirebaseIIDService", "From: " + remoteMessage.getNotification().getBody());
        onHandleIntent(remoteMessage);
        // Check if message contains a data payload.
        System.out.println("MyFirebaseIIDServices" + remoteMessage.getData());
      //  Log.d("MyFirebaseIIDService", "Message data payload: " + remoteMessage.getData());
        if (remoteMessage.getData().size() > 0) {
            Log.d("MyFirebaseIIDService", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("sssss", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    protected void onHandleIntent(RemoteMessage remoteMessage) {// Handling gcm message from
        String messageType = remoteMessage.getMessageType();
    }




}
