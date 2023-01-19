package com.shariati.fm_music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shariati.fm_music.adapters.SongAdapter;
import com.shariati.fm_music.data.SongResponse;

public class HitsActivity extends AppCompatActivity {

    private RecyclerView best_today, best_week;
    private TextView Error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hits);
        best_today = findViewById(R.id.besttoday);
        best_week = findViewById(R.id.bestweek);
        Error = findViewById(R.id.error);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        best_today.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        best_week.setLayoutManager(layoutManager1);

        RequestManager manager = new RequestManager(this);
        SongListRequestListener todayListener = new SongListRequestListener() {
            @Override
            public void didFetch(SongResponse response) {
                best_today.setAdapter(new SongAdapter(HitsActivity.this, response.getResults(),
                        new SongAdapter.ClickListener() {
                            @Override
                            public void onSongClick(int position, View v, String id) {
                                Intent intent = new Intent(HitsActivity.this, SongActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        }));
            }

            @Override
            public void didError(String errorMessage) {
                Error.setText(errorMessage);
            }
        };
        SongListRequestListener thisWeekListener = new SongListRequestListener() {
            @Override
            public void didFetch(SongResponse response) {
                best_week.setAdapter(new SongAdapter(HitsActivity.this, response.getResults(),
                        (position, v, id) -> {
                            Intent intent = new Intent(HitsActivity.this, SongActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }));
            }

            @Override
            public void didError(String errorMessage) {
                Error.setText(errorMessage);
            }
        };
        manager.getTopHitsToday(todayListener);
        manager.getTopHitsThisWeek(thisWeekListener);
    }
}