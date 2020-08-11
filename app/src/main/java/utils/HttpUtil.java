package utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {
    public static String get(String ip) throws IOException {
        String result = "";
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(ip);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("encoding", "UTF-8");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setUseCaches(false);

            if (httpURLConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += "/n" + line;
                }

            } else {
                Log.i("", "请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }


        return result;
    }


    public static String post(String ip, Map<String, String> map) {
        String result = "";
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(ip);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1; SV1)");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            printWriter = new PrintWriter(httpURLConnection.getOutputStream());

            String data = "";

            for (Map.Entry<String, String> entry : map.entrySet()) {
                data += entry.getKey() + "=" + entry.getValue() + "&";
            }
            //发送请求参数
            printWriter.print(data);
            //flish 输出流的缓冲
            printWriter.flush();

            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += "/n" + line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (printWriter != null) {
                printWriter.close();
            }

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }


        return result;

    }
}
