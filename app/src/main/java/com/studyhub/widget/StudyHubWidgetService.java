package com.studyhub.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studyhub.R;
import com.studyhub.database.StudyHubDatabase;
import com.studyhub.database.dao.DeadlineDao;
import com.studyhub.database.dao.ScheduleDao;
import com.studyhub.database.dao.SubjectDao;
import com.studyhub.database.entity.DeadlineEntity;
import com.studyhub.database.entity.ScheduleEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudyHubWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StudyHubRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StudyHubRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private String type;
    
    private List<ScheduleEntity> scheduleList = new ArrayList<>();
    private List<DeadlineEntity> deadlineList = new ArrayList<>();
    
    private ScheduleDao scheduleDao;
    private DeadlineDao deadlineDao;
    private SubjectDao subjectDao;

    public StudyHubRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.type = intent.getStringExtra("widget_type");
        
        StudyHubDatabase db = StudyHubDatabase.getInstance(context);
        scheduleDao = db.scheduleDao();
        deadlineDao = db.deadlineDao();
        subjectDao = db.subjectDao();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            scheduleList.clear();
            deadlineList.clear();
            return;
        }

        if ("schedule".equals(type)) {
            int currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            // Convert to format: 2(Mon) to 8(Sun)
            int appDayOfWeek = currentDayOfWeek == Calendar.SUNDAY ? 8 : currentDayOfWeek;
            scheduleList = scheduleDao.getByDayOfWeekSync(appDayOfWeek);
        } else if ("deadline".equals(type)) {
            long now = System.currentTimeMillis();
            deadlineList = deadlineDao.getUpcomingSync(user.getUid(), now);
        }
    }

    @Override
    public void onDestroy() {
        scheduleList.clear();
        deadlineList.clear();
    }

    @Override
    public int getCount() {
        return "schedule".equals(type) ? scheduleList.size() : deadlineList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        if ("schedule".equals(type)) {
            if (position < scheduleList.size()) {
                ScheduleEntity schedule = scheduleList.get(position);
                SubjectEntity subject = subjectDao.getByIdSync(schedule.getSubjectId());
                
                String title = subject != null ? subject.getName() : "Không xác định";
                String time = schedule.getStartTime() + " - " + schedule.getEndTime() + " (" + schedule.getRoom() + ")";
                int color = Color.parseColor("#3F51B5");
                if (subject != null && subject.getColorHex() != null) {
                    try {
                        color = Color.parseColor(subject.getColorHex());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                views.setTextViewText(R.id.tvItemTitle, title);
                views.setTextViewText(R.id.tvItemTime, time);
                views.setInt(R.id.vColor, "setBackgroundColor", color);

                Intent fillInIntent = new Intent();
                fillInIntent.putExtra("type", "schedule");
                views.setOnClickFillInIntent(R.id.tvItemTitle, fillInIntent);
            }
        } else if ("deadline".equals(type)) {
            if (position < deadlineList.size()) {
                DeadlineEntity deadline = deadlineList.get(position);
                SubjectEntity subject = subjectDao.getByIdSync(deadline.getSubjectId());
                
                String title = deadline.getTitle();
                String time = DateUtils.formatDateTime(deadline.getDueDate());
                int color = Color.parseColor("#F44336");
                if (subject != null && subject.getColorHex() != null) {
                    try {
                        color = Color.parseColor(subject.getColorHex());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                views.setTextViewText(R.id.tvItemTitle, title);
                views.setTextViewText(R.id.tvItemTime, time);
                views.setInt(R.id.vColor, "setBackgroundColor", color);

                Intent fillInIntent = new Intent();
                fillInIntent.putExtra("type", "deadline");
                views.setOnClickFillInIntent(R.id.tvItemTitle, fillInIntent);
            }
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
