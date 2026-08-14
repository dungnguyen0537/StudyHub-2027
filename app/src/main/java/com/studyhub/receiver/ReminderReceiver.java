package com.studyhub.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.studyhub.utils.NotificationHelper;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String EXTRA_NOTIFICATION_ID = "extra_notification_id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_MESSAGE = "extra_message";
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_ITEM_ID = "extra_item_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0);
            String title = intent.getStringExtra(EXTRA_TITLE);
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            String type = intent.getStringExtra(EXTRA_TYPE);
            String itemId = intent.getStringExtra(EXTRA_ITEM_ID);

            if (title != null && message != null) {
                NotificationHelper.showReminderNotification(context, notificationId, title, message, type, itemId);
            }
        }
    }
}
