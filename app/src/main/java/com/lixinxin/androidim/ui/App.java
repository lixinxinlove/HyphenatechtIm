package com.lixinxin.androidim.ui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.Iterator;
import java.util.List;

/**
 * Created by lixinxin on 2016/6/7.
 */
public class App extends Application {


    App appContext = this;
    int pid = android.os.Process.myPid();

// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            //Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        EaseUI.getInstance().init(appContext, options);


        infoProvider();
    }

    /**
     * 聊天用户基本信息的提供者
     */
    private void infoProvider() {
        EaseUI easeUI = EaseUI.getInstance();
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    private EaseUser getUserInfo(final String username) {
        final EaseUser easeUser = new EaseUser(username);

        // TODO: 2016/6/7
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                easeUser.setAvatar("http://img5.imgtn.bdimg.com/it/u=427840222,1928993467&fm=21&gp=0.jpg");
                easeUser.setNick(username + "lxx");


            }
        });
        return easeUser;
    }

    /**
     * 获取AppName
     *
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }
}
