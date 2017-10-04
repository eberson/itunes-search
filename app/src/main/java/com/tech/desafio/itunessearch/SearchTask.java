package com.tech.desafio.itunessearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tech.desafio.itunes.client.ItunesClient;
import com.tech.desafio.itunes.client.ItunesDiscover;
import com.tech.desafio.itunes.client.SSLUtil;
import com.tech.desafio.itunes.model.Music;

import java.util.Collections;
import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class SearchTask extends AsyncTask<String, Void, List<Music>>{

    private String errorMessage;
    private Context context;
    private OnSearchEndListener listener;

    public SearchTask(Context context) {
        this.context = context;
    }

    public SearchTask(Context context, OnSearchEndListener listener) {
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
    protected void onPostExecute(List<Music> musics) {
        super.onPostExecute(musics);

        if (errorMessage != null){
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i("ITUNES", "getting results with no error...");

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
