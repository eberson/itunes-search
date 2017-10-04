package com.tech.desafio.itunes.client;


public class ItunesClient {

    public static ItunesDiscover getDiscover(){
        return new ItunesDiscoverImpl();
    }
}
