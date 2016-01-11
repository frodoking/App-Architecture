package com.frodo.app.android.simple.cloud.amdb.enumerations;

public enum AppendToResponseItem {
    /**
     * @deprecated
     */
    @Deprecated
    TRAILERS("trailers"),
    VIDEOS("videos"),
    RELEASES("releases"),
    CREDITS("credits"),
    SIMILAR("similar_movies"),
    COMBINED_CREDITS("combined_credits"),
    TV_CREDITS("tv_credits"),
    MOVIE_CREDITS("movie_credits"),
    IMAGES("images");

    private final String value;

    private AppendToResponseItem(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
