package com.tech.desafio.itunessearch.tasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tech.desafio.itunes.client.ItunesClient;
import com.tech.desafio.itunes.client.ItunesDiscover;
import com.tech.desafio.itunes.client.SSLUtil;
import com.tech.desafio.itunes.model.Music;
import com.tech.desafio.itunessearch.R;
import com.tech.desafio.utils.tasks.BaseTask;

import java.util.Collections;
import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class SearchTask extends BaseTask<String, Void, List<Music>> {

    private OnSearchEndListener listener;

    public SearchTask(Context context) {
        this(context, null);
    }

    public SearchTask(Context context, OnSearchEndListener listener) {
        super(context,
                context.getResources().getString(R.string.progress_title),
                context.getResources().getString(R.string.progress_message));

        this.context = context;
        this.listener = listener;
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected List<Music> doInBackground(String... strings) {
        try{
            if (!isOnline()){
                throw new RuntimeException(context.getResources().getString(R.string.without_internet));
            }

            SSLUtil.turnOffSslChecking();

            ItunesDiscover discover = ItunesClient.getDiscover();
            List<Music> musics = discover.searchFor(strings[0]);

            return musics;
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
            Log.i("ITUNES", "running listener to fill screeen...");
            listener.onSearchEnd(musics);
            Log.i("ITUNES", "listener ran without problems...");
        }
    }

    public interface OnSearchEndListener{
        public void onSearchEnd(List<Music> musics);
    }
}
