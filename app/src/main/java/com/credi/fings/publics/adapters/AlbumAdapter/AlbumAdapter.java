package com.credi.fings.publics.adapters.AlbumAdapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fings.MainActivity;
import com.credi.fings.R;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.Album;
import com.credi.fings.publics.utils.Dialogue;
import com.credi.fings.publics.utils.S;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapterViewHolder> {
    Context context;
    List<Album> liste;
    View view;
    String classe=null,couleur;

    public AlbumAdapter(Context context, List<Album> liste) {
        this.context = context;
        this.liste = liste;
    }
    public AlbumAdapter(Context context, List<Album> liste, String classe, String couleur) {
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
                   itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu, parent, false);
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
        int i=position;


    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    List<LinearLayout> memoirs=new ArrayList<>();
     private void remv(){
         for (LinearLayout l:memoirs){
             l.removeAllViews();
         }
         memoirs.clear();
     }
    private void addMemr(LinearLayout l){
       memoirs.add(l);
    }

}

