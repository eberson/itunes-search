package com.tech.desafio.itunes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by oliveieb on 04/10/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Music implements Serializable {

    @JsonProperty("artistName")
    private String artist;

    @JsonProperty("trackName")
    private String name;

    @JsonProperty("artworkUrl60")
    private String photo;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Music)) return false;

        Music music = (Music) o;

        if (!getArtist().equals(music.getArtist())) return false;
        if (!getName().equals(music.getName())) return false;
        return getPhoto() != null ? getPhoto().equals(music.getPhoto()) : music.getPhoto() == null;

    }

    @Override
    public int hashCode() {
        int result = getArtist().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getPhoto() != null ? getPhoto().hashCode() : 0);
        return result;
    }
}
