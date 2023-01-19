package com.shariati.fm_music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.shariati.fm_music.adapters.ArtistAdapter;
import com.shariati.fm_music.adapters.SongAdapter;
import com.shariati.fm_music.data.ArtistResponse;
import com.shariati.fm_music.data.SongResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    private TextView Results;
    private RecyclerView newest_songs, best_singers;
    private EditText Search;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Results = findViewById(R.id.results);
        newest_songs = findViewById(R.id.newestsongs );
        best_singers = findViewById(R.id.bestsingers);
        viewPager = findViewById(R.id.view_pager);
        Button hitsBtn = findViewById(R.id.button);
        FloatingActionButton btnSearch = findViewById(R.id.btnsearch);
        Search = findViewById(R.id.search);

        hitsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HitsActivity.class);
            startActivity(intent);
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("search",Search.getText().toString());
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newest_songs.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        best_singers.setLayoutManager(layoutManager1);

        RequestManager manager = new RequestManager(this);

        SongListRequestListener latestListener = new SongListRequestListener() {
            @Override
            public void didFetch(SongResponse response) {
                newest_songs.setAdapter(new SongAdapter(getApplicationContext(), response.getResults(), (position, v,id) -> {
                    Intent intent = new Intent(MainActivity.this,SongActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }));
            }
            @Override
            public void didError(String errorMessage) {
                Results.setText(errorMessage);
            }
        };

        ArtistsRequestListener artistsListener = new ArtistsRequestListener() {
            @Override
            public void didFetch(ArtistResponse response) {
                best_singers.setAdapter(new ArtistAdapter(MainActivity.this, response.getResults()));
            }
            @Override
            public void didError(String errorMessage) {
                Results.setText(errorMessage);
            }
        };
        manager.getLatestSongs(latestListener);
        manager.getTrendingArtist(artistsListener);

    }
}