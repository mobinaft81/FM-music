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
import com.shariati.fm_music.data.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    Context context;
    List<Artist> artists;

    public ArtistAdapter(Context context, List<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }
    public static class ArtistViewHolder extends RecyclerView.ViewHolder {

        ImageView songcover;
        TextView Name;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            songcover = itemView.findViewById(R.id.songcover);
            Name = itemView.findViewById(R.id.name);

        }
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistViewHolder(LayoutInflater.from(context).inflate(R.layout.artist_view_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        holder.Name.setText(artists.get(position).getFullName());
        Glide.with(context)
                .load(artists.get(position).getImage().getCover().getUrl())
                .circleCrop()
                .into(holder.songcover);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}