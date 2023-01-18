package com.shariati.fm_music.data;

import com.google.gson.annotations.SerializedName;

public class Cover{

	@SerializedName("url")
	private String url;

	public String getUrl(){
		return url;
	}
}