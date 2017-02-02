package com.example.daniele.hosteltest.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by daniele on 30/01/17.
 */

public class PropertiesResponse implements Serializable {

    @SerializedName("properties")
    private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }
}
