package ice.ccylice.creative.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

public class SaveUtil {
    static final void rawLaunchIntent(Intent intent, Activity activity) {
        if (intent != null) {
            activity.startActivity(intent);
        }
    }

    static final void launchIntent(Intent intent,Activity activity) {
        try {
            rawLaunchIntent(intent,activity);
        } catch (ActivityNotFoundException ignored) {
        }
    }

    public static final void openURL(String url, Activity activity) {
        // Strangely, some Android browsers don't seem to register to handle HTTP:// or HTTPS://.
        // Lower-case these as it should always be OK to lower-case these schemes.
        if (url.startsWith("HTTP://")) {
            url = "http" + url.substring(4);
        } else if (url.startsWith("HTTPS://")) {
            url = "https" + url.substring(5);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            launchIntent(intent,activity);
        } catch (ActivityNotFoundException ignored) {
        }
    }
}
