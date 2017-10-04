package com.tech.desafio.itunessearch.store;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.desafio.itunes.model.Music;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class LikedMusicStore {

    private static String LIKED_FOLDER = "liked";
    private static String FILE_LIKED_NAME = "music_liked.json";

    private static LikedMusicStore instance;

    private LikedMusic likedMusic;

    public static LikedMusicStore getInstance(Context context){
        if (instance == null){
            instance = new LikedMusicStore(context);
        }

        return instance;
    }

    private Context context;

    private List<Music> musics;

    private LikedMusicStore(Context context) {
        this.context = context;

        try {
            this.likedMusic = load();
        } catch (IOException e) {
            Log.e("ITUNES", "Erro carregando arquivo com musicas salvas", e);
        }
    }

    private LikedMusic load() throws IOException {
        File file = new File(getLikedDirectory(), FILE_LIKED_NAME);

        if (!file.exists()){
            return new LikedMusic();
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(file, LikedMusic.class);
    }

    private void store() throws IOException {
        File file = new File(getLikedDirectory(), FILE_LIKED_NAME);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, likedMusic);
    }

    private File getLikedDirectory(){
        File dir;

        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            dir = new File(Environment.getExternalStorageDirectory(), LIKED_FOLDER);
        }
        else {
            dir = context.getCacheDir();
        }

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return  dir;
    }

    public void store(Music music){
        this.likedMusic.addMusic(music);

        try {
            store();
        } catch (IOException e) {
            Log.i("ITUNES", "Erro salvando preferencias", e);
        }
    }

    public void remove(Music music){
        this.likedMusic.removeMusic(music);

        try {
            store();
        } catch (IOException e) {
            Log.i("ITUNES", "Erro salvando preferencias", e);
        }
    }

    public List<Music> getLikedMusics(){
        return likedMusic.getMusics();
    }
}
