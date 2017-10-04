package com.tech.desafio.itunessearch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tech.desafio.itunes.client.ItunesClient;
import com.tech.desafio.itunes.client.ItunesDiscover;
import com.tech.desafio.itunes.model.Music;

import java.util.Collections;
import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class SearchTask extends AsyncTask<String, Void, List<Music>>{

    private String errorMessage;
    private Context context;

    public SearchTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Music> doInBackground(String... strings) {
        try{
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

        Toast.makeText(context, String.format("Foram encontradas %d musicas...", musics.size()), Toast.LENGTH_SHORT).show();


    }
}
