package com.example.daniele.hosteltest.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by daniele on 30/01/17.
 */

public class Property implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("overallRating")
    private OverallRating overallRating;
    @SerializedName("images")
    private List<Images> images;

    @SerializedName("address1")
    private String address1;
    @SerializedName("address2")
    private String address2;
    @SerializedName("city")
    private City city;
    @SerializedName("description")
    private String description;
    @SerializedName("directions")
    private String directions;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public OverallRating getOverallRating() {
        return overallRating;
    }

    public List<Images> getImages() {
        return images;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public City getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getDirections() {
        return directions;
    }

    public class OverallRating {

        @SerializedName("overall")
        private int overall;
        @SerializedName("numberOfRatings")
        private int numberOfRatings;

        public int getOverall() {
            return overall;
        }

        public int getNumberOfRatings() {
            return numberOfRatings;
        }
    }

    public class Images {

        @SerializedName("prefix")
        private String prefix;
        @SerializedName("suffix")
        private String suffix;

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }
    }

    public class City {

        @SerializedName("name")
        private String name;
        @SerializedName("country")
        private String country;

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }
    }

}