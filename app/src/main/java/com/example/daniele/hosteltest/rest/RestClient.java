package com.example.daniele.hosteltest.rest;

import com.example.daniele.hosteltest.rest.model.PropertiesResponse;
import com.example.daniele.hosteltest.rest.model.Property;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by daniele on 30/01/17.
 */

public class RestClient {

    private static final String mBaseUrl = "http://private-anon-0e91437372-practical3.apiary-mock.com";

    public static final int API_ID_GOTHENBURG = 1530;

    private static RestClient instance;

    private Retrofit retrofit;

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private RestClient() { }

    /* Static 'instance' method */
    public static RestClient getInstance( ) {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public Retrofit getClient() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface HostelInterface {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter

        @GET("/cities/{city}/properties/")
        Call<PropertiesResponse> getPropertiesByCity(@Path("city") int city);

        @GET("/properties/{propertyId}")
        Call<Property> getProperty(@Path("propertyId") String propertyId);

    }

}

