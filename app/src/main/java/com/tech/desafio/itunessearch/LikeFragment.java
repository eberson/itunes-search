package com.tech.desafio.itunessearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tech.desafio.itunes.model.Music;
import com.tech.desafio.itunessearch.adapter.MusicAdapter;
import com.tech.desafio.itunessearch.tasks.LoadLikedMusicsTask;

import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class LikeFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private MusicAdapter adapter;

    public LikeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LikeFragment newInstance(int sectionNumber) {
        LikeFragment fragment = new LikeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadLikedMusics();
    }

    public void loadLikedMusics() {
        new LoadLikedMusicsTask(getContext(), new LoadLikedMusicsTask.OnLoadEndListener() {
            @Override
            public void onLoadEnd(List<Music> musics) {
                adapter.clear();

                if (musics.isEmpty()){
                    Toast.makeText(getContext(),
                            getContext().getResources().getString(R.string.not_found_items),
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                adapter.addAll(musics);
                adapter.notifyDataSetChanged();
            }
        }).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_like, container, false);

        adapter = new MusicAdapter(getContext(), false);

        final ListView listResult = (ListView) rootView.findViewById(R.id.list_liked);
        listResult.setAdapter(adapter);

        return rootView;
    }
}
