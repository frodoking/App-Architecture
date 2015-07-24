package com.frodo.android.app.simple.cloud.amdb.entities;

import java.util.List;

public class Configuration implements TmdbEntity {
    private static final long serialVersionUID = 1527591734693489266L;
    public Configuration.ImagesConfiguration images;
    public List<String> change_keys;

    public Configuration() {
    }

    public static class ImagesConfiguration implements TmdbEntity {
        private static final long serialVersionUID = 2793552705485555335L;
        public String base_url;
        public String secure_base_url;
        public List<String> poster_sizes;
        public List<String> backdrop_sizes;
        public List<String> profile_sizes;
        public List<String> logo_sizes;

        public ImagesConfiguration() {
        }
    }
}
