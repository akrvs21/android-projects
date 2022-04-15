package com.example.recyclerview;

import android.graphics.Bitmap;
import android.util.LruCache;
// This class is responsible for caching
public class CacheImages {
    private LruCache<String, Bitmap> memoryCache;
    int key;

    public LruCache<String, Bitmap> getMemoryCache() {
        return memoryCache;
    }

    public int getKey() {
        return key;
    }

    public CacheImages(LruCache<String, Bitmap> memoryCache, int key) {
        this.memoryCache = memoryCache;
        this.key = key;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        // if bitmap doesn't exist in cache, store that bitmap to cache
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }
    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }
}
