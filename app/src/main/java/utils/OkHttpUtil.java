package utils;

import android.content.ContentProviderOperation;
import android.os.Environment;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    /*1,创建OkHttpClient 对象*/
    private final OkHttpClient okHttpClient = new OkHttpClient();

    public void get(String url) {

        /*2，创建Request对象，设置URL及请求方式*/
        Request request = new Request.Builder().url(url).method("GET", null).build();
        /*3，创建一个Call对象，参数就是request对象*/
        Call call = okHttpClient.newCall(request);
        /*4，请求加入调度，重写回掉方法*/
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.i("result", result);
            }
        });
    }

    public void post(String url) {
        RequestBody requestBody = new FormBody.Builder().add("size", "10").build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.i("result", result);
            }
        });
    }

    public void uploadFile(String url, File file) {
        /*设置文件上传类型*/
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        //获取请求体
        RequestBody requestBody = RequestBody.create(file, mediaType);

        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.i("result", result);
            }
        });
    }

    public void downloadFile(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File("/sdcard/123.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("123", "文件下载成功");
            }
        });
    }

    public void uploadMultipartFile(String url) {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //上传的图片
        File file = new File(Environment.getExternalStorageDirectory(), "zhuangqilu.png");
        //2.通过new MultipartBody build() 创建requestBody对象，
        RequestBody requestBody = new MultipartBody.Builder()
                //设置类型是表单
                .setType(MultipartBody.FORM)
                //添加数据
                .addFormDataPart("username", "zhangqilu")
                .addFormDataPart("age", "25")
                .addFormDataPart("image", "zhangqilu.png",
                        RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        Request request = new Request.Builder().url("url").post(requestBody).build();
        //4.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //5.请求加入调度,重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

}
