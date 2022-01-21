package com.example.androidapp_citb704_F102014.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class BitmapCacheHelper {

    private class TargetImage
    {
        String url;
        ImageView imgView;

        public String getUrl() {return url;}
        public ImageView getImageView() {return imgView;}


        public TargetImage(String url, ImageView imgView){
            this.url = url;
            this.imgView = imgView;
        }
    }
    private static LruCache<String, Bitmap> bitmapLruCache;

    class BitmapWorkerTask extends AsyncTask<TargetImage, Void, Bitmap> {
        String url;
        ImageView imgView;

        @Override
        protected Bitmap doInBackground(TargetImage... targetImages) {
            url = targetImages[0].getUrl();
            imgView = targetImages[0].getImageView();
            try {
                InputStream in = new URL(url).openStream();
                return BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                // log error
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            if (bmp != null)
            {
                if (bitmapLruCache.get(url) == null)
                {
                    bitmapLruCache.put(url, bmp);
                }

                imgView.setImageBitmap(bmp);
            }
        }
    }

    public BitmapCacheHelper(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap){
                return bitmap.getByteCount()/1024;
            }
        };
    }

    public void getBitmapFromMemCache(String url, ImageView imgView) {
        if (bitmapLruCache.get(url) == null) {
            BitmapWorkerTask task = new BitmapWorkerTask();

            TargetImage targetImage = new TargetImage(url, imgView);
            task.execute(targetImage);
            return;
        }
        imgView.setImageBitmap(bitmapLruCache.get(url));
    }
}
