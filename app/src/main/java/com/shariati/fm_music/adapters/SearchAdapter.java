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
import com.shariati.fm_music.data.SearchResultItem;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

        Context context;
        List<SearchResultItem> results;
        private final ClickListener clickListener;

public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView song_cover;
    TextView sName;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        song_cover = itemView.findViewById(R.id.songcover);
        sName = itemView.findViewById(R.id.name);
        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position>=0 && results.get(position).getType().equals("song")){
            clickListener.onSongClick(position,view,results.get(position).getSong().getId());
        }
    }
}
public interface ClickListener {
    void onSongClick(int position,View v,String id);
}
    public SearchAdapter(Context context, List<SearchResultItem> results, SearchAdapter.ClickListener clickListener) {
        this.context = context;
        this.results = results;
        this.clickListener=clickListener;
    }
    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAdapter.SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.search_item_view,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        if (results.get(position).getType().equals("song")){
            holder.sName.setText(results.get(position).getSong().getTitle());
            Glide.with(context)
                    .load(results.get(position).getSong().getImage().getCover().getUrl())
                    .into(holder.song_cover);
        }else {
            holder.sName.setText(results.get(position).getArtist().getFullName());
            Glide.with(context)
                    .load(results.get(position).getArtist().getImage().getCover().getUrl())
                    .circleCrop()
                    .into(holder.song_cover);
        }
    }
    @Override
    public int getItemCount() {
        return results.size();
    }
}
