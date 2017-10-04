package com.tech.desafio.utils.image;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by oliveieb on 04/10/2017.
 */
public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        //Find the dir to save cached images
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "TTImages_cache");
        }
        else {
            cacheDir = context.getCacheDir();
        }

        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return;
        }

        for (File f : files) {
            f.delete();
        }
    }
}