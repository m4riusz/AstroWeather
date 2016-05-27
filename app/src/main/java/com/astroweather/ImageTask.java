package com.astroweather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mariusz on 27.05.16.
 */
public class ImageTask extends AsyncTask<String, Void, Void> {
    public static final String IMAGE_URL = "http://openweathermap.org/img/w/";
    private ImageView imageView;
    private Bitmap bitmap;

    public ImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Void doInBackground(String... iconCodes) {
        for (String iconCode : iconCodes) {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            bitmap = null;
            try {
                connection = (HttpURLConnection) new URL(IMAGE_URL + iconCode).openConnection();
                connection.setUseCaches(true);
                connection.connect();
                inputStream = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                inputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        imageView.setImageBitmap(bitmap);
    }
}
