package com.credi.fing.publics.adapters.generiqueAdapter;


import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.publics.service.CustomImageView;
import com.credi.fing.publics.service.CustomVideoView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterViewHolder extends RecyclerView.ViewHolder {
    public TextView title,title2,textePourcentage,date;
    public  ProgressBar pbc;
    public TextView secondre,secondre2,notif,symbole;
    public ImageView not_sente,icon_notif,up;
    public CircleImageView circ_img;
    public ImageView image;
    public CustomImageView customImageView;
    public CustomVideoView customVideoView;
    public ImageView ic_mort,icon;
    public LinearLayout lnotif,lbotom;
    public  View view,star,barreProgressionInterieure;
    public CheckBox checkbox;
    public FrameLayout barreProgression;
    Switch aSwitch;
    RadioButton radioButton;

    public AdapterViewHolder(View view, int id) {
        super(view);
        title = view.findViewById(R.id.title);
        title2 = view.findViewById(R.id.title2);
        this.view=view;
        date=view.findViewById(R.id.date);
        textePourcentage=view.findViewById(R.id.textePourcentage);
        barreProgressionInterieure=view.findViewById(R.id.barreProgressionInterieure);
        aSwitch=view.findViewById(R.id.id_switch);
        star=view.findViewById(R.id.star);
        symbole=view.findViewById(R.id.symbole);
        icon_notif=view.findViewById(R.id.notif);
        pbc = view.findViewById(R.id.pbc);
        lnotif=view.findViewById(R.id.lnotif);
        icon=view.findViewById(R.id.icone);
        barreProgression=view.findViewById(R.id.barreProgression);
        notif=view.findViewById(R.id.textnotif);
        customImageView=view.findViewById(R.id.custom_image);
        customVideoView=view.findViewById(R.id.custom_video);
        secondre = view.findViewById(R.id.secondre);
        secondre2 = view.findViewById(R.id.secondre2);
        not_sente = view.findViewById(R.id.not_sente);
        ic_mort = view.findViewById(R.id.ic_mort);
        circ_img=view.findViewById(R.id.circ_img);
        checkbox=view.findViewById(R.id.checkbox);
        radioButton=view.findViewById(R.id.radio);
        image=view.findViewById(R.id.image);
        lbotom=view.findViewById(R.id.l_bottom);
    }

    public View getView() {
        return view;
    }
}

