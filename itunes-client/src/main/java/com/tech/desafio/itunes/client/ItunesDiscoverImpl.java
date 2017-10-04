package com.tech.desafio.itunes.client;

import com.tech.desafio.itunes.model.Music;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oliveieb on 04/10/2017.
 */

class ItunesDiscoverImpl implements ItunesDiscover {

    private static final String DEFAULT_URL = "https://itunes.apple.com";

    public static final String URL_SEARCH = DEFAULT_URL + "/search";

    @Override
    public List<Music> searchFor(String term) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_SEARCH);

        builder.queryParam("term", term)
               .queryParam("media", "music")  ;

        RestTemplate rest = RestConfiguration.getTemplate();

        ResponseEntity<SearchResponse> result = rest.exchange(builder.build().encode().toUri(),
                                                              HttpMethod.GET,
                                                              null,
                                                              SearchResponse.class);

        HttpStatus statusCode = result.getStatusCode();

        if (statusCode != HttpStatus.OK){
            throw new RuntimeException(String.format("Error getting musics from itunes: (%s - %s)",
                                                     statusCode.toString(),
                                                     statusCode.getReasonPhrase()));
        }

        return result.getBody().getResults();
    }
}
