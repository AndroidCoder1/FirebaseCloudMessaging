package owusu.agyei.liz.tryy;

import android.app.Application;

/**
 * Created by RSL-PROD-003 on 10/27/16.
 */
public class MyApp extends Application{

    private static boolean activityVisible;


    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
}
