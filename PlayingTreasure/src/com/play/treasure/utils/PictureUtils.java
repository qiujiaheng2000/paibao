package com.play.treasure.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.media.ExifInterface;

public class PictureUtils {

    /**
     * ѹ��ͼƬ��
     * 
     * @param path
     *            ͼƬ·��
     * @param size
     *            ͼƬ���ߴ�
     * @return ѹ�����ͼƬ
     * @throws IOException
     */
    public static Bitmap compressImage(String path, int size)
            throws IOException {
        Bitmap bitmap = null;
        // ȡ��ͼƬ
        InputStream is = new FileInputStream(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        // ��������?��Ϊbitmap�����ڴ�ռ䣬ֻ��¼һЩ��ͼƬ����Ϣ������ͼƬ��С����˵���˾���Ϊ���ڴ��Ż�
        options.inJustDecodeBounds = true;
        // ͨ��ͼƬ�ķ�ʽ��ȡ��options�����ݣ��������������java�ĵ�ַ��������ֵ��
        BitmapFactory.decodeStream(is, null, options);
        // �ر���
        is.close();

        // // ���ѹ����ͼƬ
        int i = 0;
        while (true) {
            if ((options.outWidth >> i <= size)
                    && (options.outHeight >> i <= size)) {
                is = new FileInputStream(path);
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Config.ARGB_8888;
                options.inPurgeable = true;
                options.inInputShareable = true;
                bitmap = BitmapFactory.decodeStream(is, null, options);
                break;
            }
            i += 1;
        }

        return bitmap;
    }

    /**
     * 
     * 
     * @param bm
     *           
     * @param maxSize
     *            
     * @return
     */
    public static Bitmap imageZoom(Bitmap bm, double maxSize) {
     /**   ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        double mid = b.length / 1024;
        if (mid > maxSize) {
            double i = mid / maxSize;
            bm = zoomImage(bm, bm.getWidth() / Math.sqrt(i), bm.getHeight() / Math.sqrt(i));
        }**/
    	
        return comp(bm,maxSize);
    }
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
            double newHeight) {
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 
     * @Title: rotateImage
     * @param path
     * @return void
     */
    public static int getImageOrientation(String path) {

        try {
            ExifInterface exifInterface = new ExifInterface(path);

            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            return orientation;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ExifInterface.ORIENTATION_NORMAL;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix m = new Matrix();
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            m.setRotate(90);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            m.setRotate(180);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            m.setRotate(270);
        } else {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        try {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);
        } catch (OutOfMemoryError ooe) {

            m.postScale(1, 1);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);

        }
        return bitmap;
    }
    
private static Bitmap comp(Bitmap image,double maxSize) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		image.compress(Bitmap.CompressFormat.PNG, 100, baos);
		if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出	
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.PNG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
		
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap,maxSize);
		}
		else
		{
			return compressImage(image,maxSize);
		}
		//压缩好比例大小后再进行质量压缩
	}
	private static Bitmap compressImage(Bitmap image,double maxSize) {
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>maxSize) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	
	private Bitmap getimage(String srcPath,int maxSize) {
		maxSize =100;
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
		
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap,maxSize);//压缩好比例大小后再进行质量压缩
	}
}
