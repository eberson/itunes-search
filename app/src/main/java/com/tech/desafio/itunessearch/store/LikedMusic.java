package com.tech.desafio.itunessearch.store;

import com.tech.desafio.itunes.model.Music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class LikedMusic {

    private Set<Music> musics = new HashSet<>();

    public void addMusic(Music music){
        musics.add(music);
    }

    public void removeMusic(Music music){
        this.musics.remove(music);
    }

    public void addAllMusics(Collection<Music> musics){
        this.musics.addAll(musics);
    }

    public List<Music> getMusics(){
        List<Music> result = new ArrayList<>(musics);

        return Collections.unmodifiableList(result);
    }


}
