package ice.ccylice.creative.util;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityUtils {
    static ArrayList<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }
}
