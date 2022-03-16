package com.li.feeling.init.application;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.li.feeling.init.initmodule.GsonInitModule;
import com.li.feeling.init.initmodule.SharedPreferencesInitModule;
import com.li.framework.app.ContextService;


/**
 * description: 整个app的application
 * Zygote进程是系统开机后由init进程fork出来的，然后Zygote进程会fork出SystemServer进程
 * SystemServer进程会创建SystemServiceManager对象，该对象会启动各种服务，比如AMS、PMS、WMS
 * <p>
 * 1. 点击桌面应用的图标后(发生在Launch应用的进程中)，然后发消息给SystemServer进程，然后SystemServer进程的ActivityManagerServer(AMS)
 * 通过socket通信告知Zygote进程来fork出app进程(每一个应用程序都是一个app进程)
 * <p>
 * 2. app进程启动后，会实例化ActivityThread对象，然后执行它的main函数，也就是在该函数中，启动了主线程的loop,,然后activityThread通过
 * binder通信，告诉AMS执行attachApplication(mAppThread)方法，该方法里面依次初始化了application和activity
 * <p>
 * 3. ActivityThread.handleBindApplication()方法通知主线程创建Application对象，然后调用
 * application#attach(context)方法，
 * 让application绑定context，最后调用application的onCreate方法，至此一个应用程序就被创建了
 * <p>
 * 4. ActivityThread#ApplicationThread#scheduleLaunchActivity()
 * 方法通知主线程创建activity对象，然后调用activity的onCreate()
 */
public class FeelingApplication extends Application {

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    // 处理SdkVersion20及以下版本无法支持加载多个dex文件
    MultiDex.install(base);

    ContextService.setAppContext(this);

    // TODO: 9/27/21 所有的initModule收敛到一个类中去管理
    SharedPreferencesInitModule.getInstance().init(this);
    GsonInitModule.getInstance().init(this);

  }

}
