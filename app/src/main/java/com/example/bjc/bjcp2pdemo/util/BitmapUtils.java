package com.example.bjc.bjcp2pdemo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by 毕静存 on 2016/12/6.
 */

public class BitmapUtils {
    /**
     * 压缩图片；
     * @param source
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap source, float w, float h) {
        int height = source.getHeight();
        int width = source.getWidth();
        Matrix matrix=new Matrix();
        matrix.postScale(w/width,h/height);
        Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return bitmap;
    }

    public static Bitmap changeToCircle(Bitmap zoomBitmap) {
        int width = zoomBitmap.getWidth();
        int height = zoomBitmap.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(width/2,width/2,width/2,paint);//在画布上先画一个圆；
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(zoomBitmap,0,0,paint);
        return bitmap;
    }
}
