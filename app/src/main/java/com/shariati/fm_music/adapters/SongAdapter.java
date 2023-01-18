package com.shariati.fm_music.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shariati.fm_music.R;
import com.shariati.fm_music.data.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    Context context;
    List<Song> songs;
    private final ClickListener clickListener;

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView songcover;
        TextView songtitle,Singer;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songcover = itemView.findViewById(R.id.songcover);
            songtitle = itemView.findViewById(R.id.songtitle);
            Singer = itemView.findViewById(R.id.singer);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position>=0){
                clickListener.onSongClick(position,view,songs.get(position).getId());
            }
        }
    }
    public interface ClickListener {
        void onSongClick(int position,View v,String id);
    }
    public SongAdapter(Context context, List<Song> songs, ClickListener clickListener) {
        this.context = context;
        this.songs = songs;
        this.clickListener=clickListener;
    }
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(context).inflate(R.layout.song_item_view,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.songtitle.setText(songs.get(position).getTitle());
        holder.Singer.setText(songs.get(position).getArtists().get(0).getFullName());
        Glide.with(context)
                .load(songs.get(position).getImage().getCover().getUrl())
                .into(holder.songcover);
    }
    @Override
    public int getItemCount() {
        return songs.size();
    }
}
