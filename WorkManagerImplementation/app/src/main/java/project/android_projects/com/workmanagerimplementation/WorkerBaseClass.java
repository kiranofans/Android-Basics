package project.android_projects.com.workmanagerimplementation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import Utils.AppConstants;

import static Utils.AppConstants.WORK_RESULT;

public class WorkerBaseClass extends Worker {
    //Second Step: Create base class of Worker

    private String taskDataStr, notifMsg;

    /**
     * Worker class specifies what task to perform.
     * WorkManager API includes an abstract Worker class and
     * it needs to extend this Worker class and perform the work
     */

    public WorkerBaseClass(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        taskDataStr = taskData.getString(AppConstants.MESSAGE_STATUS);

        notifMsg = taskDataStr != null ? taskDataStr : "Message has been sent\n" +
                "Longgggggggggggggggggggggggg";
        showNotification("WorkManager", notifMsg);
        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();

        return Result.success(outputData);
    }

    private void showNotification(String workTask, String notifMsg) {
        NotificationManager notifMgr = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        String channelID = "task_channel_1";
        String channelName = "task_name";

        //If android version is greater than O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifChannel = new NotificationChannel(channelID,
                    channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notifMgr.createNotificationChannel(notifChannel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            /** Use BigTextStyle().bigText(String str) to make
             * push notification expandable if text is too long*/
            NotificationCompat.Builder notifBuilder = new NotificationCompat
                    .Builder(getApplicationContext(), channelID).setContentTitle(workTask)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notifMsg))
                    .setContentText(notifMsg).setSmallIcon(R.drawable.ic_notification_18dp);

            notifMgr.notify(1, notifBuilder.build());

        }
    }
}
