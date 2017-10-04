package com.tech.desafio.itunessearch.tasks;

import android.content.Context;
import android.util.Log;

import com.tech.desafio.itunes.model.Music;
import com.tech.desafio.itunessearch.R;
import com.tech.desafio.itunessearch.store.LikedMusicStore;
import com.tech.desafio.utils.tasks.BaseTask;

import java.util.Collections;
import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class LoadLikedMusicsTask extends BaseTask<Void, Void, List<Music>> {


    private OnLoadEndListener listener;

    public LoadLikedMusicsTask(Context context) {
        this(context, null);
    }

    public LoadLikedMusicsTask(Context context, OnLoadEndListener listener) {
        super(context,
                context.getResources().getString(R.string.progress_title),
                context.getResources().getString(R.string.progress_message));
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<Music> doInBackground(Void... params) {
        try{
            return LikedMusicStore.getInstance(context).getLikedMusics();
        }
        catch (Exception e){
            Log.e("ITUNES", "falhou...", e);

            errorMessage = e.getMessage();
            return Collections.emptyList();
        }
    }

    @Override
    protected void doAfterProcessing(List<Music> musics) {
        if (listener != null){
            listener.onLoadEnd(musics);
        }
    }

    public interface OnLoadEndListener{
        public void onLoadEnd(List<Music> musics);
    }
}
