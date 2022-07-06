package com.example.musicapp;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyVieHolder> {
    private Context context;
    private ArrayList<MusicFiles> mFiles;

    MusicAdapter(Context context, ArrayList<MusicFiles> mFiles) {
        this.mFiles = mFiles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_items, parent, false);
        return new MyVieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVieHolder holder, final int position) {
        holder.file_name.setText(mFiles.get(position).getTitle());
        final byte[] image = getAlbumArt(mFiles.get(position).getPath());

        if (image != null)
            Glide.with(context).asBitmap().load(image).into(holder.album_art);
        else
            Glide.with(context).load(R.drawable.musics).into(holder.album_art);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MyVieHolder extends RecyclerView.ViewHolder {
        TextView file_name;
        ImageView album_art;

        public MyVieHolder(@NonNull View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.music_file_name);
            album_art = itemView.findViewById(R.id.music_img);
        }
    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    private String getDuration(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);

        String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long dur = Long.parseLong(duration);
        String seconds = String.valueOf((dur % 60000) / 1000);
        return seconds;
    }
}
