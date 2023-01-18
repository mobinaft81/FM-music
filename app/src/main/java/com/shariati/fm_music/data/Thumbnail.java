package com.shariati.fm_music.data;

import com.google.gson.annotations.SerializedName;

public class Thumbnail{

	@SerializedName("url")
	private String url;

	public String getUrl(){
		return url;
	}
}