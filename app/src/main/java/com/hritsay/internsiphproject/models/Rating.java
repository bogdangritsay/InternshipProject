package com.hritsay.internsiphproject.models;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("Source")
    private String source;
    @SerializedName("Value")
    private String rating;

    public Rating() {}

    public Rating(String source, String rating) {
        this.source = source;
        this.rating = rating;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
