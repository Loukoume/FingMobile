package com.credi.fings.publics.drawer.menu;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.credi.fings.R;

import java.util.List;

public class TraitementMenu {
    private List<Menu> menus;
    private LinearLayout drawer;
    private View vide;
    RecyclerView recyclerView;

    public TraitementMenu(List<Menu> menus, LinearLayout drawer, View vide) {
        this.menus = menus;
        this.drawer = drawer;
        this.vide = vide;
        this.recyclerView=this.drawer.findViewById(R.id.recycler);
    }




}
