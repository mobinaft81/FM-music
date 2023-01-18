package com.shariati.fm_music.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SongResponse{

	@SerializedName("total")
	private int total;

	@SerializedName("results")
	private List<Song> results;

	public int getTotal(){
		return total;
	}

	public List<Song> getResults(){
		return results;
	}
}