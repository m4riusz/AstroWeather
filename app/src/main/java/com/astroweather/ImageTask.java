package com.astroweather;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.astroweather.util.Json;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mariusz on 27.05.16.
 */
public class ImageTask extends AsyncTask<String, Void, Void> {

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
                connection = getHttpURLConnection(Json.IMAGE_URL + iconCode);
                inputStream = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                inputStream.close();
            } catch (Exception ex) {
                bitmap = BitmapFactory.decodeResource(Resources.getSystem(), android.R.drawable.stat_notify_error);
                ex.printStackTrace();
            }
        }
        return null;
    }

    @NonNull
    private HttpURLConnection getHttpURLConnection(String requestUrl) throws IOException {
        HttpURLConnection connection;
        connection = (HttpURLConnection) new URL(requestUrl).openConnection();
        connection.setUseCaches(true);
        connection.connect();
        return connection;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        imageView.setImageBitmap(bitmap);
    }
}
