package com.tech.desafio.itunes.client;

import com.tech.desafio.itunes.model.Music;

import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public interface ItunesDiscover {

    public List<Music> searchFor(String term);

}
