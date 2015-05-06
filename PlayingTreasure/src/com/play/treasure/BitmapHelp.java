package com.play.treasure;

import android.content.Context;
import com.lidroid.xutils.BitmapUtils;

/**
 * Author: wyouflf
 * Date: 13-11-12
 * Time: ����10:24
 */
public class BitmapHelp {
    private BitmapHelp() {
    }

    private static BitmapUtils bitmapUtils;

    /**
     * BitmapUtils���ǵ���� �����Ҫ���ض����ȡʵ��ķ���
     *
     * @param appContext application context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context appContext,String path) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext,path);
        }
        return bitmapUtils;
    }
}
