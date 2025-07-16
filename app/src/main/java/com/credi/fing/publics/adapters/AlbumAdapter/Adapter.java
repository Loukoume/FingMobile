package com.credi.fing.publics.adapters.AlbumAdapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.publics.utils.Album;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<AlbumAdapterViewHolder> {
    Context context;
    List<Album> liste;
    View view;
    String classe=null,couleur;

    public Adapter(Context context, List<Album> liste) {
        this.context = context;
        this.liste = liste;
    }
    public Adapter(Context context, List<Album> liste,String classe,String couleur) {
        this.context = context;
        this.liste = liste;
        this.classe=classe;
        this.couleur=couleur;
    }
    @Override
    public AlbumAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =classe==null? LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home, parent, false):
                null;
        if(itemView==null){
            switch (classe){
                case "menu":
                    itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                    break;
            }
        }
        view = itemView;
        return new AlbumAdapterViewHolder(itemView);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final AlbumAdapterViewHolder holder, final int position) {
        final Album album = liste.get(position);
        holder.name.setText(album.affTitre());
        if(classe!=null){

        }
    }

    @Override
    public int getItemCount() {
        return liste.size();

    }


}
