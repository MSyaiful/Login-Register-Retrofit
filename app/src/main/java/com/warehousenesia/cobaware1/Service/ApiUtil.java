package com.warehousenesia.cobaware1.Service;

public class ApiUtil {

    public ApiUtil(){

    }

    public static final String Base_url = "http://192.168.100.11:1997/";

    public static ApiService getPaketService(){
        return PaketClient.getClient(Base_url).create(ApiService.class);
    }
}
