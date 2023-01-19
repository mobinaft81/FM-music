package com.shariati.fm_music;


import androidx.annotation.NonNull;

import com.shariati.fm_music.data.ArtistResponse;
import com.shariati.fm_music.data.SearchResponse;
import com.shariati.fm_music.data.Song;
import com.shariati.fm_music.data.SongResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("song/new/0/11")
    Call<SongResponse> getLatestSongs();

    @GET("artist/trending/0/4")
    Call<ArtistResponse> getTrendingArtists();

    @GET("song/top/day/0/100")
    Call<SongResponse> getTop10DaySongs();

    @GET("song/top/week/0/100")
    Call<SongResponse> getTop10WeekSongs();

    @GET("song/{id}")
    Call<Song> getSongById(@Path("id") String songId);

    @GET("search/query/{query}/0/50")
    Call<SearchResponse> search(@Path("search") String search);
}
