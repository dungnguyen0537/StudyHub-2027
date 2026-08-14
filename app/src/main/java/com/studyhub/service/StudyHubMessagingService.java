package com.studyhub.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Service xử lý Firebase Cloud Messaging (FCM).
 */
public class StudyHubMessagingService extends FirebaseMessagingService {
    private static final String TAG = "StudyHubFCM";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Message received from: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            if (title != null && body != null) {
                android.app.NotificationManager notificationManager =
                        (android.app.NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
                String channelId = "channel_general";
                
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    android.app.NotificationChannel channel = new android.app.NotificationChannel(
                            channelId,
                            "General",
                            android.app.NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                androidx.core.app.NotificationCompat.Builder notificationBuilder =
                        new androidx.core.app.NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(com.studyhub.R.drawable.ic_launcher)
                                .setContentTitle(title)
                                .setContentText(body)
                                .setAutoCancel(true);

                notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
            }
        }

        // Xử lý data payload
        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Data payload: " + remoteMessage.getData());
            // Có thể trigger sync khi nhận data message
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "New FCM token: " + token);
        // TODO: Send token to server if needed
    }
}
