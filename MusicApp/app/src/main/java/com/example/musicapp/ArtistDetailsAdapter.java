package com.example.musicapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArtistDetailsAdapter extends RecyclerView.Adapter<ArtistDetailsAdapter.MyartistHolder> {
    View view;
    private Context context;
    static ArrayList<MusicFiles> artistFiles;

    public ArtistDetailsAdapter(Context context, ArrayList<MusicFiles> artistFiles) {
        this.context = context;
        this.artistFiles = artistFiles;
    }

    @NonNull
    @Override
    public MyartistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.music_items, parent, false);
        return new MyartistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyartistHolder holder, final int position) {
        holder.artist_name.setText(artistFiles.get(position).getArtists());
        final byte[] image = getAlbumArt(artistFiles.get(position).getPath());

        if (image != null)
            Glide.with(context).asBitmap().load(image).into(holder.artist_image);
        else
            Glide.with(context).load(R.drawable.musics).into(holder.artist_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("artistSender", "artistDetails");
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistFiles.size();
    }

    public class MyartistHolder extends RecyclerView.ViewHolder {
        TextView artist_name;
        ImageView artist_image;
        public MyartistHolder(@NonNull View itemView) {
            super(itemView);
            artist_image = itemView.findViewById(R.id.music_img);
            artist_name = itemView.findViewById(R.id.music_file_name);
        }
    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
