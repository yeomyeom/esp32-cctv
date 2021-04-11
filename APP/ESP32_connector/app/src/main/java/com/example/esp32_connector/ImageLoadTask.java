package com.example.esp32_connector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;
import java.util.HashMap;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private final String url;
    private ImageView imageView;
    private static final HashMap<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();

    public ImageLoadTask(String url, ImageView imageview){
        this.url = url;
        this.imageView = imageview;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... voids){
        Bitmap bitmap = null;
        try{
            if(bitmapHash.containsKey(url)){
                Bitmap olditmap = bitmapHash.remove(url);
                if(olditmap != null) {
                    olditmap.recycle();
                    olditmap = null;
                }
            }
            URL url_url = new URL(url);
            Log.d("hi", String.valueOf(url_url.openConnection()));
            bitmap = BitmapFactory.decodeStream(url_url.openConnection().getInputStream());
            bitmapHash.put(url, bitmap);

        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
