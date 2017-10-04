package com.tech.desafio.itunes.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech.desafio.itunes.model.Music;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class SearchResponse implements Serializable {

    @JsonProperty("resultCount")
    private int resultCount;

    @JsonProperty("results")
    private List<Music> results;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<Music> getResults() {
        if (results == null){
            results = Collections.emptyList();
        }

        return results;
    }

    public void setResults(List<Music> results) {
        this.results = results;
    }
}
