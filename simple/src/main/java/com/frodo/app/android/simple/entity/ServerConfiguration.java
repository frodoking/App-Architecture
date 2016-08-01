package com.frodo.app.android.simple.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by frodo on 2016/3/3.
 */
public class ServerConfiguration {
	@JsonProperty("images")
	public Images images;
	@JsonProperty("change_keys")
	public String[] changeKeys;

	public static class Images {
		@JsonProperty("base_url")
		public String baseUrl;
		@JsonProperty("secure_base_url")
		public String secureBaseUrl;
		@JsonProperty("backdrop_sizes")
		public String[] backdropSizes;
		@JsonProperty("logo_sizes")
		public String[] logoSizes;
		@JsonProperty("poster_sizes")
		public String[] posterSizes;
		@JsonProperty("profile_sizes")
		public String[] profileSizes;
		@JsonProperty("still_sizes")
		public String[] stillSizes;
	}
}
