package com.example.recyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
// This class is responsible for processing an image, converting from url to bitmap and storing
// it to the local cache
public class ProcessImage {
    String imageUrl;
    int key;
    private LruCache<String, Bitmap> memoryCache;

    public ProcessImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // This function takes image url and converts it to bitmap, after that it stores it to the cache
    // in key-value pair format, with a key being the item position in recyclerView
    public  Bitmap getBitmapFromURL(String src, LruCache<String, Bitmap> memoryCache, int key) {
        try {
            CacheImages cacheImage = new CacheImages(memoryCache, key);
            // Retrieves the cache by providing the key value.
            Bitmap myBitmap = cacheImage.getBitmapFromMemCache(String.valueOf(cacheImage.getKey()));

            if(myBitmap != null) {
                Log.d("cached", "Got the cached content");
                // if the cache is not null, return the cache content
                return  myBitmap;
            } else {
                // if the cache for that key is null, get the image from server, convert to bitmap and return it
                // Credits to @silentnuke: https://stackoverflow.com/a/8993175/7686275
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
                cacheImage.addBitmapToMemoryCache(String.valueOf(cacheImage.getKey()), myBitmap);

                return myBitmap;
            }
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
