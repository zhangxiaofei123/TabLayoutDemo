package utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class HandleFile {

    private Context context;

    public HandleFile(Context context) {
        this.context = context;
    }

    public void WriteFile(String fileName, String content) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] bytes = content.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ReadFile(String fileName) {
        String result = "";
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            int length = fileInputStream.available();
            byte[] buffer = new byte[length];
            fileInputStream.read(buffer);
            result = new String(buffer);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
