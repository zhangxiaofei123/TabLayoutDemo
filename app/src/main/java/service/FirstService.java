package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.security.Provider;

import androidx.annotation.Nullable;

public class FirstService extends Service {
    private Thread thread;
    private ServiceThread serviceThread;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("serive", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service", "onStartCommand");
        serviceThread = new ServiceThread();
        thread = new Thread(serviceThread);
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        结束runnable方法的循环
        serviceThread.flag = false;
//        关起线程
        thread.interrupt();
        thread = null;
        Log.i("service","服务销毁");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service","onBind");

        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("service","onUnBind");

        return super.onUnbind(intent);

    }
}

class ServiceThread implements Runnable {
    //    用volatile 修饰保证变量在线程间的可见性
     boolean flag = true;

    @Override
    public void run() {
        while (flag) {
//            间隔1秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("service", "服务正在运行");
        }

    }
}