package com.studyhub.service;

import android.content.Context;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class SyncManager {
    
    private static final String SYNC_WORK_NAME = "studyhub_sync_work";
    
    public static void enqueueSyncWork(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
                
        OneTimeWorkRequest syncRequest = new OneTimeWorkRequest.Builder(DataSyncWorker.class)
                .setConstraints(constraints)
                .build();
                
        WorkManager.getInstance(context).enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                syncRequest
        );
    }
}
