package com.vipulkanade.group12.cmpe272.view;

import android.app.Application;

/**
 * Created by vipulkanade on 5/12/15.
 */
public class RetailAt12Application extends Application {

    public static final String TAG = RetailAt12Application.class.getSimpleName();

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     *
     * @return RetailAt12 Application context
     */
    public synchronized static Application getInstance() {
        return mInstance;
    }
}
