package com.shariati.fm_music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shariati.fm_music.adapters.SearchAdapter;
import com.shariati.fm_music.adapters.SongAdapter;
import com.shariati.fm_music.data.SearchResultItem;
import com.shariati.fm_music.data.Song;
import com.shariati.fm_music.data.SongResponse;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView Results;
    private TextView Error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Results = findViewById(R.id.results);
        Error = findViewById(R.id.error);
        Results.setLayoutManager(new GridLayoutManager(this,2));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String query = extras.getString("search");
            RequestManager manager = new RequestManager(SearchActivity.this);
            SearchResultsListener listener = new SearchResultsListener() {
                @Override
                public void didFetch(List<SearchResultItem> response) {
                    Results.setAdapter(new SearchAdapter(getApplicationContext(), response, (position, v, id) -> {
                        Intent intent = new Intent(SearchActivity.this,SongActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }));
                }

                @Override
                public void didError(String errorMessage) {
                    Error.setText(errorMessage);
                }
            };
            manager.search(listener,query);
        }
    }
}