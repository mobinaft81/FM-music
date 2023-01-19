package com.shariati.fm_music;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.shariati.fm_music.data.Song;
import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SongActivity extends AppCompatActivity {

    private TextView song_title, Singer,num_plays,pulication_date;
    private ImageView btn_Play, song_cover;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        song_title = findViewById(R.id.songtitle);
        Singer = findViewById(R.id.singer);
        btn_Play = findViewById(R.id.btnplay);
        song_cover = findViewById(R.id.songcover);
        seekBar = findViewById(R.id.seekBar);
        num_plays = findViewById(R.id.numplays);
        pulication_date = findViewById(R.id.pulicationdate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("id");
            RequestManager manager = new RequestManager(this);
            SongRequestListener listener = new SongRequestListener() {
                @Override
                public void didFetch(Song response) {
                    playAudio(response.getAudio().getMedium().getUrl());
                    song_title.setText(response.getTitle());
                    Singer.setText(response.getArtists().get(0).getFullName());
                    num_plays.setText(response.getDownloadCount());
                    pulication_date.setText(miladiToShamsi(response.getReleaseDate()));
                    Glide.with(SongActivity.this)
                            .load(response.getImage().getCover().getUrl())
                            .into(song_cover);
                }
                @Override
                public void didError(String errorMessage) {
                    song_title.setText(errorMessage);
                }
            };
            manager.getSongById(listener, value);
            seekBar.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    private String miladiToShamsi(String releaseDate) {
        String[] realeaseDate  = releaseDate.split("T");
        String[] date = realeaseDate[0].split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        DateConverter dateConverter = new DateConverter();
        JalaliDate jalaliDate = dateConverter.gregorianToJalali(year,month,day);
        return jalaliDate.format(new JalaliDateFormatter("yyyy- M dd", JalaliDateFormatter.FORMAT_IN_PERSIAN));
    }

    private void playAudio(String url) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition()/1000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }}, 0, 1000);

        btn_Play.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btn_Play.setImageResource(R.drawable.ic_baseline_play_circle_24);
            } else {
                mediaPlayer.start();
                btn_Play.setImageResource(R.drawable.ic_baseline_pause_circle_24);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btn_Play.setImageResource(R.drawable.ic_baseline_play_circle_24);
            }
        });
    }
    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onBackPressed();
    }
}