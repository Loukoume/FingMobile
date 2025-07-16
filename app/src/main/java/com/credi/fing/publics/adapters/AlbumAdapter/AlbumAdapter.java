package com.credi.fing.publics.adapters.AlbumAdapter;

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

import com.credi.fing.MainActivity;
import com.credi.fing.R;
import com.credi.fing.publics.drawer.NavigationDrawer;
import com.credi.fing.publics.drawer.menu.Menu;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Album;
import com.credi.fing.publics.utils.S;

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
        if(classe!=null){
            switch (classe){
                case "menu":
                    Menu menu=new Menu().fromJs(album.getJson());
                    if(album.isSeparator()){

                        holder.coordinatorLayout.setVisibility(View.GONE);
                        holder.separa.setVisibility(View.VISIBLE);
                        holder.libelleSeparator.setText(S.toUperCaseFirstChar(menu.getTitre()));
                        if(position==0){
                            holder.sline.setVisibility(View.GONE);
                        }
                    }else {
                        holder.coordinatorLayout.setVisibility(View.VISIBLE);
                        holder.separa.setVisibility(View.GONE);
                        if(couleur!=null){
                            holder.line.setBackgroundColor(Color.parseColor(couleur));
                        }else {
                            holder.line.setBackgroundColor(Color.parseColor("#E9E9E9"));
                        }

                        holder.name.setText(Ut.getValue(menu,"titre").toString().toUpperCase());
                        if(album.isUp()){
                            holder.fils.setVisibility(View.VISIBLE);
                            holder.up.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp_bleu);
                        }else {
                            holder.up.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp_bleu);
                            holder.fils.setVisibility(View.GONE);
                        }
                        if(menu.getFils()==null||menu.getFils().isEmpty()){
                            holder.up.setVisibility(View.GONE);
                        }else {
                            holder.up.setVisibility(View.VISIBLE);
                        }

                        //S.toast(context,album.isUp()+" ok "+(menu.getFils()!=null));

                        holder.cord.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(menu.getFils()!=null&&!menu.getFils().isEmpty()){
                                    holder.fils.setVisibility(View.VISIBLE);

                                    if (holder.fils.getChildCount()==0){
                                        traiterSousMenu(holder.fils,menu.getFils(), NavigationDrawer.p);
                                    }else {
                                        holder.fils.removeAllViews();
                                        remv();
                                    }
                                }else {
                                    holder.fils.removeAllViews();
                                   // NavigationDrawer.clickDerwer(menu,NavigationDrawer.p);
                                }
                            }
                        });

                        break;
                    }
            }
        }else {
            //Dialogue.neutreDialog(album.affTitre(),album.getImg()+"",context).show();
            holder.img.setImageResource(album.getImg());
        }

    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    public static void clickDerwer(Menu menu,Integer... ii){

        clickDerwer(menu);
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

    private void traiterSousMenu(LinearLayout lim,List<Menu> menus,Integer... ii){
        //List<Album> albums=new Menu().alboms(Collections.singletonList(menus));
        //String cc="#E9E9E9";
        // menus=menus.stream().filter(m->m.getId()!=null).collect(Collectors.toList());
        if(menus.size()>0){
            //String i=menus.get(0).getId(),is[]=i.split("\\.");
            String coul=ii.length<=MainActivity.backColors.size()?MainActivity.backColors.get(ii.length-1):"#FFFFFF";
            final int[] n = {0};
            for (Menu m:menus){
                View view=  LayoutInflater.from(context).inflate(R.layout.row_menu,null,false);
                TextView textView=view.findViewById(R.id.name);
                textView.setText(m.getTitre());
                ImageView icn=view.findViewById(R.id.icn);
                icn.setImageResource(R.drawable.baseline_arrow_forward_ios_24);
                View line=view.findViewById(R.id.line);
                view.setBackgroundColor(Color.parseColor(coul));
                lim.setBackgroundColor(Color.parseColor(coul));
                if(coul.equals("#FFFFFF")){
                    line.setBackgroundColor(Color.parseColor("#E9E9E9"));
                }else {
                    line.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                lim.addView(view);
                if(m.getFils()==null||m.getFils().isEmpty()){
                    view.findViewById(R.id.up).setVisibility(View.GONE);
                }else {
                    view.findViewById(R.id.up).setVisibility(View.VISIBLE);
                }
                int finalN = n[0];
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (m.getFils()!=null&&!m.getFils().isEmpty()){
                            LinearLayout lim=view.findViewById(R.id.fils);
                            if(lim.getChildCount()>0){
                                lim.removeAllViews();
                                remv();
                            }else {
                                remv();
                                Integer[] newArray = new Integer[ii.length + 1];
                                System.arraycopy(ii, 0, newArray, 0, ii.length);
                                newArray[ii.length] = finalN;

                                addMemr(lim);
                                traiterSousMenu(lim,m.getFils(),newArray);
                            }
                        }else {
                            lim.removeAllViews();
                            Integer[] newArray = new Integer[ii.length + 1];
                            System.arraycopy(ii, 0, newArray, 0, ii.length);
                            newArray[ii.length] = finalN;

                         //   NavigationDrawer.clickDerwer(m,newArray);
                        }
                    }
                });
                n[0]++;


            }
        }
    }
}

