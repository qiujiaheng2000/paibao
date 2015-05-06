package com.play.treasure.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/** 
 * @ClassName: BitmapUtil
 * @Description: 图片压缩
 * @author wangchao29
 * @date 2014年12月29日 下午4:21:40
 * 
 */
public class BitmapUtil 
{
	/** 
	* @Title: compressImage
	* @Description: 质量压缩
	* @param @param image
	* @param @return
	* @return Bitmap
	* @throws 
	*/ 
	public static Bitmap compressImage(Bitmap image) 
	{  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;  
        while ( baos.toByteArray().length / 1024>500) {  
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 100;
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;  
    } 
	
	/** 
	* @Title: compressHeadImage
	* @Description: 压缩头像
	* @param @param image
	* @param @return
	* @return Bitmap
	* @throws 
	*/ 
	public static Bitmap compressHeadImage(Bitmap image) 
	{  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;  
        while ( baos.toByteArray().length / 1024>400) {  
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 100;
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;  
    }
	
	/** 
	* @Title: getBitmap
	* @Description: 通过路径获取压缩
	* @param @param srcPath
	* @param @return
	* @return Bitmap
	* @throws 
	*/ 
	public static Bitmap getBitmap(String srcPath) 
	{  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        float hh = 800f;
        float ww = 480f;
        int be = 1;
        if (w > h && w > ww) 
        {
            be = (int) (newOpts.outWidth / ww);  
        } 
        else if (w < h && h > hh) 
        {
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);
    }
	
	/** 
	* @Title: getBitmap
	* @Description: 获取头像bitmap
	* @param @param srcPath
	* @param @return
	* @return Bitmap
	* @throws 
	*/ 
	public static Bitmap getHeadBitmap(String srcPath) 
	{  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        float hh = 800f;
        float ww = 480f;
        int be = 1;
        if (w > h && w > ww) 
        {
            be = (int) (newOpts.outWidth / ww);  
        } 
        else if (w < h && h > hh) 
        {
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressHeadBitmap(bitmap);
    }
	
	/** 
	* @Title: compressHeadBitmap
	* @Description: 压缩头像bitmap
	* @param @param image
	* @param @return
	* @return Bitmap
	* @throws 
	*/ 
	public static Bitmap compressHeadBitmap(Bitmap image)
	{  
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	    if( baos.toByteArray().length / 1024>1024) 
	    {
	        baos.reset();  
	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
	    }  
	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	    newOpts.inJustDecodeBounds = true;  
	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    newOpts.inJustDecodeBounds = false;  
	    int w = newOpts.outWidth;  
	    int h = newOpts.outHeight;  
	    float hh = 800f;
	    float ww = 480f; 
	    int be = 1;
	    if (w > h && w > ww) {
	        be = (int) (newOpts.outWidth / ww);  
	    } else if (w < h && h > hh) {
	        be = (int) (newOpts.outHeight / hh);  
	    }  
	    if (be <= 0)  
	        be = 1;  
	    newOpts.inSampleSize = be;
	    isBm = new ByteArrayInputStream(baos.toByteArray());  
	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    return compressHeadImage(bitmap);
	}  
	
	/** 
	* @Title: compressBitmap
	* @Description: 压缩bitmap版
	* @param @param image
	* @param @return
	* @return Bitmap
	* @throws 
	*/ 
	public static Bitmap compressBitmap(Bitmap image)
	{  
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	    if( baos.toByteArray().length / 1024>1024) 
	    {
	        baos.reset();  
	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
	    }  
	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	    newOpts.inJustDecodeBounds = true;  
	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    newOpts.inJustDecodeBounds = false;  
	    int w = newOpts.outWidth;  
	    int h = newOpts.outHeight;  
	    float hh = 800f;
	    float ww = 480f; 
	    int be = 1;
	    if (w > h && w > ww) {
	        be = (int) (newOpts.outWidth / ww);  
	    } else if (w < h && h > hh) {
	        be = (int) (newOpts.outHeight / hh);  
	    }  
	    if (be <= 0)  
	        be = 1;  
	    newOpts.inSampleSize = be;
	    isBm = new ByteArrayInputStream(baos.toByteArray());  
	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    return compressImage(bitmap);
	}  

}
