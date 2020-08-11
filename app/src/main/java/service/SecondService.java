package service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

public class SecondService extends Service {
    private String message;
    private boolean isRunning = true;
    private ServiceThread serviceThread;
    private Thread thread;
    private MyIBinder myIBinder = new MyIBinder();


    //    private IBinder iBinder = new My
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        serviceThread = new ServiceThread();
        thread = new Thread(serviceThread);
        thread.start();
        return myIBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("service","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service","onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("service","onUnbind");

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceThread.flag = false;
        Log.i("service","onDestroy");
    }

    class ServiceThread implements Runnable {
        volatile boolean flag = true;
        @Override
        public void run() {
            Log.i("service","开始运行");
            int i = 0;
            while (flag){
                if(mOnDataCallback != null){
                    mOnDataCallback.onDataChange(message+i);
                }
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public class MyIBinder extends Binder {
        //从activity传入的值
        public void setData(String message) {
            SecondService.this.message = message;
        }

        public SecondService getService() {
            return SecondService.this;
        }


    }

    private OnDataCallback mOnDataCallback = null;

    public void setOnDataCallback(OnDataCallback onDataCallback){
        this.mOnDataCallback = onDataCallback;
    }

    public interface OnDataCallback {
        void onDataChange(String message);
    }

}
