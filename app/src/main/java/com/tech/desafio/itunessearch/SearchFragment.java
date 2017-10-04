package com.tech.desafio.itunessearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tech.desafio.itunes.model.Music;
import com.tech.desafio.itunessearch.adapter.MusicAdapter;
import com.tech.desafio.itunessearch.tasks.SearchTask;

import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class SearchFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private MusicAdapter adapter;

    public SearchFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SearchFragment newInstance(int sectionNumber) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        adapter = new MusicAdapter(getContext());

        final EditText inputSearch = (EditText) rootView.findViewById(R.id.input_search);
        final ListView listResult = (ListView) rootView.findViewById(R.id.list_result);
        listResult.setAdapter(adapter);

        Button search = (Button) rootView.findViewById(R.id.btn_pesquisar);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputSearch.setError(null);

                String query = inputSearch.getText().toString();

                if (TextUtils.isEmpty(query)){
                    inputSearch.setError(getResources().getString(R.string.search_field));
                    return;
                }

                new SearchTask(getContext(), new SearchTask.OnSearchEndListener() {
                    @Override
                    public void onSearchEnd(List<Music> musics) {
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
                }).execute(query);
            }
        });

        return rootView;
    }
}
