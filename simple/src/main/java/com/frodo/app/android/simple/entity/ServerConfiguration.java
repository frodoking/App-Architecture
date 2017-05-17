package com.frodo.app.android.simple.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by frodo on 2016/3/3.
 */
public class ServerConfiguration {
	@SerializedName("images")
	public Images images;
	@SerializedName("change_keys")
	public String[] changeKeys;

	public static class Images {
		@SerializedName("base_url")
		public String baseUrl;
		@SerializedName("secure_base_url")
		public String secureBaseUrl;
		@SerializedName("backdrop_sizes")
		public String[] backdropSizes;
		@SerializedName("logo_sizes")
		public String[] logoSizes;
		@SerializedName("poster_sizes")
		public String[] posterSizes;
		@SerializedName("profile_sizes")
		public String[] profileSizes;
		@SerializedName("still_sizes")
		public String[] stillSizes;
	}
}
