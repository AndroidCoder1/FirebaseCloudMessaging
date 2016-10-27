package owusu.agyei.liz.tryy;

import android.app.NotificationManager;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by RSL-PROD-003 on 10/27/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String notificationsCount = "notificationCount";
    public static final String notificationsContent = "notificationContent";

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.activityResumed();
        clearNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApp.activityPaused();
    }

    public void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) BaseActivity.this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }

    public static void addNotificationToCache(Context context, RemoteMessage remoteMessage){

        StringBuilder currentNotificationContent = new StringBuilder(PreferenceManager.getDefaultSharedPreferences(context).getString(notificationsContent,""));
        currentNotificationContent.append((remoteMessage.getData().containsKey("texting")) ? remoteMessage.getData().get("texting") +"\n": "");
        int currentNotificationCount = PreferenceManager.getDefaultSharedPreferences(context).getInt(notificationsCount, 0);
        currentNotificationCount++;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(notificationsCount, currentNotificationCount).commit();
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(notificationsContent, currentNotificationContent.toString()).commit();

    }

    public static void clearNotificationToCache(Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(notificationsCount, 0).commit();
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(notificationsContent,"").commit();
    }

    public static int getNotificationCount(Context context){
        int currentNotificationCount = PreferenceManager.getDefaultSharedPreferences(context).getInt(notificationsCount, 0);
        return currentNotificationCount;
    }

    public static String getNotificationContent(Context context){
        String notificationContent = PreferenceManager.getDefaultSharedPreferences(context).getString(notificationsContent,"");
        return notificationContent;
    }
}
