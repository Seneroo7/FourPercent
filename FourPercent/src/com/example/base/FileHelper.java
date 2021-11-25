package com.example.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileHelper {
	public static final int MODE_PRIVATE = 0x0000;
    private Context context;

    public FileHelper() {
    }

    public FileHelper(Context context) {
        super();
        this.context = context;
    }
	
    public void savaFileToSD(String filename, String filecontent) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        	String fileName = Environment.getExternalStorageDirectory().toString()+"/FourPercent";
        	File file = new File(fileName);
        	file.mkdir();
        	file.mkdirs();
        	
        	filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/FourPercent/" + filename;
            FileOutputStream output = new FileOutputStream(filename);
            output.write(filecontent.getBytes());
            output.close();
        } else 
        	Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }

    public String readFromSD(String filename) throws IOException {
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/FourPercent/" + filename;
            FileInputStream input = new FileInputStream(filename);
            byte[] temp = new byte[1024];

            int len = 0;
            while ((len = input.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            input.close();
        }
        return sb.toString();
    }


}