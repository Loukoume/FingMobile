package com.credi.fing.publics.adapters.AlbumAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlbumAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView name,libelleSeparator;
    ProgressBar pbc;
    TextView sub_name;
    CoordinatorLayout coordinatorLayout;
    ImageView not_sente,up;
    CircleImageView img;
    ImageView ic_mort;
    LinearLayout fils,separa;
    LinearLayout cord;
    View line,sline;

    public AlbumAdapterViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.name);
        cord=view.findViewById(R.id.cord);
        pbc = view.findViewById(R.id.pbc);
        sline=view.findViewById(R.id.sline);
        coordinatorLayout=view.findViewById(R.id.coordinator);
        separa=view.findViewById(R.id.separator);
        not_sente = view.findViewById(R.id.not_sente);
        ic_mort = view.findViewById(R.id.ic_mort);
        libelleSeparator=view.findViewById(R.id.libelleSeparator);
        fils=view.findViewById(R.id.fils);
        up=view.findViewById(R.id.up);
        line=view.findViewById(R.id.line);
        img=view.findViewById(R.id.image);
    }

}

