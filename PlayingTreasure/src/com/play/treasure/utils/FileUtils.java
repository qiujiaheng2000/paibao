package com.play.treasure.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileUtils {

    private static String getRootFilePath() {
        StringBuffer stbPath = new StringBuffer();
        stbPath.append(Environment.getExternalStorageDirectory().getPath());
        stbPath.append(File.separator);
        stbPath.append("example");
        stbPath.append(File.separator);
        return stbPath.toString();
    }

    public static void saveImage(final Bitmap bitmap,String filePath) throws IOException,
            FileNotFoundException {

        //  
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            //  
            File f = new File(getRootFilePath());
            f.mkdirs();
            //  
            if (bitmap != null) {
                //  
                Calendar cl = Calendar.getInstance();
                cl.setTime(Calendar.getInstance().getTime());
                long milliseconds = cl.getTimeInMillis();
                //  
             //   String fileName = String.valueOf(milliseconds) + ".jpg";
                //  
                //  
                Log.i("filePath", ""+filePath);
                File file = new File(filePath);
             //   if(file.exists())
              //  {
               // 	file.delete();
               // }
                Log.i("filePath", "1");
              //  file.createNewFile();
            //    if(!file.exists())
           //     {
                	file.createNewFile();
           ////     }
                if (!file.isFile()) {
                    throw new FileNotFoundException();
                }
                Log.i("filePath", "2");
                FileOutputStream fOut = null;

                fOut = new FileOutputStream(file);
              
                //  
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                Log.i("filePath", "3");
                //  
                fOut.flush();
                fOut.close();
                
                if(file.exists())
                {

                    Log.i("filePath", ""+"success");
                //	file.delete();
                }
                Log.i("filePath", ""+""+file.getPath());

                Log.i("filePath", "4");
              //  Log.i();

            }
        }
    }

    public static String getTakePhotoPath() {
        return getCacheFile().getPath() + File.separator + "takephoto.jpg";
    }

    /**
     *  
     */
    public static File getCacheFile() {
        StringBuffer sb = new StringBuffer();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append(File.separator);
        sb.append("Android");
        sb.append(File.separator);
        sb.append("data");
        sb.append(File.separator);
        sb.append("cache");
        sb.append(File.separator);
        File file = new File(sb.toString());
        return file;
    }
}
