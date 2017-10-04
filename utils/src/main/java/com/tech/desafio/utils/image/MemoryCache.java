package com.tech.desafio.utils.image;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

/**
 * Created by oliveieb on 04/10/2017.
 */
public class MemoryCache {

    private static final String TAG = "MemoryCache";

    private Map<String, Bitmap> cache = Collections.synchronizedMap(new WeakHashMap<String, Bitmap>(10, 1.5f));
    private long size = 0;
    private long limit = 1000;

    public MemoryCache() {
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    public void setLimit(long new_limit) {
        limit = new_limit;
        Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
    }

    public Bitmap get(String id) {
        try {
            if (!cache.containsKey(id)) {
                return null;
            }

            return cache.get(id);
        } catch (NullPointerException e) {
            Log.e(TAG, "Error loading from cache", e);
            return null;
        }
    }

    public void put(String id, Bitmap bitmap) {
        try {
            if (cache.containsKey(id)) {
                size -= getSizeInBytes(cache.get(id));
            }

            cache.put(id, bitmap);
            size += getSizeInBytes(bitmap);
            checkSize();
        } catch (Exception e) {
            Log.e(TAG, "Error saving to cache", e);
        }
    }

    private void checkSize() {
        Log.i(TAG, "cache size=" + size + " length=" + cache.size());
        if (size > limit) {
            Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();//least recently accessed item will be the first one iterated

            while (iter.hasNext()) {
                Entry<String, Bitmap> entry = iter.next();
                size -= getSizeInBytes(entry.getValue());
                iter.remove();
                if (size <= limit)
                    break;
            }

            Log.i(TAG, "Clean cache. New size " + cache.size());
        }
    }

    public void clear() {
        cache.clear();
        size = 0;
    }

    long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }

        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
