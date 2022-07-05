package ice.ccylice.creative;

import android.app.Application;



public class MyApp extends Application {
    private static Application mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }


    public static Application getApp(){
        return mApp;
    }
}
