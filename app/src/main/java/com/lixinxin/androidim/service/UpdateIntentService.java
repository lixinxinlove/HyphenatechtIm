package com.lixinxin.androidim.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.lixinxin.androidim.cache.ACache;


public class UpdateIntentService extends IntentService {


    ACache mACache;
    String username;

    public UpdateIntentService() {
        super("UpdateIntentService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mACache = ACache.get(getApplicationContext());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("lxx", "onHandleIntent");
        if (intent != null) {

            username = intent.getStringExtra("username");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mACache.put(username + "Nick", username + "lxx", 60 * 5);

            if (username.equals("a")) {
                mACache.put(username + "Avatar", "http://img3.duitang.com/uploads/item/201409/28/20140928095004_KQmC8.jpeg", 60 * 5);
            } else {
                mACache.put(username + "Avatar", "http://image.tianjimedia.com/uploadImages/2012/233/38/H439I0N71ARI.jpg", 60 * 5);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
