package com.studyhub.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.studyhub.R;
import com.studyhub.activity.SplashActivity;

public class StudyHubWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_studyhub);

        // Schedule List
        Intent scheduleIntent = new Intent(context, StudyHubWidgetService.class);
        scheduleIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        scheduleIntent.putExtra("widget_type", "schedule");
        scheduleIntent.setData(Uri.parse(scheduleIntent.toUri(Intent.URI_INTENT_SCHEME)));
        
        views.setRemoteAdapter(R.id.lvWidgetSchedules, scheduleIntent);
        views.setEmptyView(R.id.lvWidgetSchedules, R.id.tvWidgetScheduleEmpty);

        // Deadline List
        Intent deadlineIntent = new Intent(context, StudyHubWidgetService.class);
        deadlineIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        deadlineIntent.putExtra("widget_type", "deadline");
        deadlineIntent.setData(Uri.parse(deadlineIntent.toUri(Intent.URI_INTENT_SCHEME)));
        
        views.setRemoteAdapter(R.id.lvWidgetDeadlines, deadlineIntent);
        views.setEmptyView(R.id.lvWidgetDeadlines, R.id.tvWidgetDeadlineEmpty);

        // Intent to launch app when titles are clicked
        Intent appIntent = new Intent(context, SplashActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.tvWidgetScheduleTitle, appPendingIntent);
        views.setOnClickPendingIntent(R.id.tvWidgetDeadlineTitle, appPendingIntent);

        // Template intent for list items
        Intent itemIntent = new Intent(context, SplashActivity.class);
        PendingIntent itemPendingIntent = PendingIntent.getActivity(context, 1, itemIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        views.setPendingIntentTemplate(R.id.lvWidgetSchedules, itemPendingIntent);
        views.setPendingIntentTemplate(R.id.lvWidgetDeadlines, itemPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            // Get all ids
            // Note: In a real app, we should probably store widget IDs or use ComponentName
            // but for simplicity we rely on the system broadcast
        }
    }
}
