package com.example.tablayoutdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;

import adapter.RecyclerAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import service.FirstService;
import service.SecondService;
import utils.HandleFile;
import utils.HttpUtil;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    private Notification notification;
    private NotificationManager notificationManager;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private SecondService.MyIBinder binder;
    private SecondService secondService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (SecondService.MyIBinder) iBinder;
            binder.setData("6666");

            secondService = binder.getService();
            secondService.setOnDataCallback(new SecondService.OnDataCallback() {
                @Override
                public void onDataChange(final String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            secondService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        /**
         * 第 1 步: 检查是否有相应的权限，根据自己需求，进行添加相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
//        // 如果这3个权限全都拥有, 则直接执行备份代码
//        if (isAllGranted) {
//            doBackup();
//            return;
//        }
//
        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );

        initViews();
//        otification();
       /* doRequest();*/
    }

    void initViews() {
        List<Map<String, String>> list = new ArrayList<>();

        String[] strings = {"tablayout", "listview"};

        for (int i = 0; i < strings.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", strings[i]);
            list.add(map);
        }
        recyclerAdapter = new RecyclerAdapter(list, MainActivity.this);
        recyclerView = findViewById(R.id.recyclerview);
        /*行布局*/
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));

        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.SetOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                System.out.println("点击：" + position);
                switch (position) {
                    case 0:
//                        startService(new Intent(MainActivity.this,FirstService.class));
//                        startActivity(new Intent(MainActivity.this, TablayoutPageviewActivity.class));
//                        bindService(new Intent(MainActivity.this, SecondService.class), serviceConnection, Context.BIND_AUTO_CREATE);
                        break;
                    case 1:
//                        stopService(new Intent(MainActivity.this, FirstService.class));
//                        startActivity(new Intent(MainActivity.this, ListViewActivity.class));
//                        unbindService(serviceConnection);
                        makeCall();
                        break;
                }
                fileSteam();
            }
        });

    }

    void otification() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, "123", importance);//生成channel
            manager.createNotificationChannel(channel);//添加channel
            Notification notification = new Notification.Builder(MainActivity.this, id)
                    //注意这里多了一个参数id，指配置的NotificationChannel的id
                    //你可以自己去试一下 运行一次后 即配置完后 将这行代码以上的代
                    //码注释掉 将参数id直接改成“channel_1”也可以成功运行
                    //但改成别的如“channel_2”就不行了
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("酷我音乐")
                    .setContentText("正在播放音乐。。。")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(1, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setContentTitle("酷我音乐");
            builder.setContentText("正在播放音乐。。。");
            notification = builder.build();
            notificationManager.notify(0, notification);
        }

    }

    /*SharedPreferences的使用*/
    void sharedPreference() {
        SharedPreferences preferences = getSharedPreferences("zxf", MODE_PRIVATE);
        String name = preferences.getString("name", "");
        if (name.equals("")) {
            SharedPreferences.Editor editor = getSharedPreferences("zxf", MODE_PRIVATE).edit();
            editor.putString("name", "nihao");
            editor.commit();
        }
        String name1 = preferences.getString("name", "");
        Toast.makeText(getBaseContext(), name1, Toast.LENGTH_SHORT).show();
    }

    /*文件读写*/
    void fileSteam() {
        String content = "zxf";

        HandleFile fileHandle = new HandleFile(getBaseContext());
        fileHandle.WriteFile("filename", content);
        String result = fileHandle.ReadFile("filename");

        Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();

    }


    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                doBackup();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    /**
     * 第 4 步: 备份通讯录操作
     */
    private void doBackup() {
        // 本文主旨是讲解如果动态申请权限, 具体备份代码不再展示, 就假装备份一下
        Toast.makeText(this, "正在备份通讯录...", Toast.LENGTH_SHORT).show();
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /*
    网络请求
    * */

    void doRequest() {
        //get
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = HttpUtil.get("http://140.143.191.70/sso/api/captcha?0=user-89553a024f344047b41a92f3ed18f0f4");
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = result;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        post
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> map = new HashMap();
                map.put("a","1");
                String result = HttpUtil.post("http://140.143.191.70/sso/api/captcha?0=user-89553a024f344047b41a92f3ed18f0f4", map);
                Message message = handler.obtainMessage();
                message.what = 2;
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };


    /*拨打电话*/
    void makeCall(){
        //Intent intent = new Intent(Intent.ACTION_DIAL);/*手动点击拨打*/
        Intent intent = new Intent(Intent.ACTION_CALL);/*自动拨打*/

        intent.setData(Uri.parse("tel:17661092616"));
        startActivity(intent);
    }

}