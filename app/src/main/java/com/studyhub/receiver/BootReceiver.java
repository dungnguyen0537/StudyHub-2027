package com.studyhub.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.studyhub.database.StudyHubDatabase;
import com.studyhub.database.dao.ReminderDao;
import com.studyhub.database.entity.ReminderEntity;
import com.studyhub.utils.AlarmHelper;
import com.studyhub.utils.AppExecutors;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Khôi phục các alarm nhắc nhở sau khi thiết bị khởi động lại.
 */
public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            return;
        }

        Log.d(TAG, "Boot completed - rescheduling alarms");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            return;
        }

        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                StudyHubDatabase db = StudyHubDatabase.getInstance(context);
                ReminderDao reminderDao = db.reminderDao();
                List<ReminderEntity> activeReminders = reminderDao.getActiveReminders(currentUser.getUid());

                for (ReminderEntity reminder : activeReminders) {
                    if (reminder.getTriggerTime() > System.currentTimeMillis()) {
                        AlarmHelper.setReminder(
                                context,
                                reminder.getId().hashCode(),
                                reminder.getTriggerTime(),
                                reminder.getTitle(),
                                "",
                                reminder.getEventType(),
                                reminder.getEventId()
                        );
                    }
                }
                Log.d(TAG, "Rescheduled " + activeReminders.size() + " alarms");
            } catch (Exception e) {
                Log.e(TAG, "Error rescheduling alarms", e);
            }
        });
    }
}
