package com.credi.fing.publics.drawer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.publics.adapters.AlbumAdapter.AlbumAdapter;
import com.credi.fing.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fing.publics.drawer.menu.Menu;
import com.credi.fing.publics.ecouteur.GridSpacingItemDecoration;
import com.credi.fing.publics.ecouteur.Interface;
import com.credi.fing.publics.ecouteur.RecyclerTouchListener;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Album;
import com.credi.fing.publics.utils.S;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NavigationDrawer {
    private Context context;
    private ImageView mort, toggle;
    private LinearLayout sheet;
    private View vide,vides;

    public NavigationDrawer(Context context,ImageView toggle,LinearLayout drawer) {
        this.context = context;
        this.toggle=toggle;
        this.drawer=drawer;
    }

    public View getVide() {
        return vide;
    }

    public void setVide(View vide) {
        this.vide = vide;
    }

    public LinearLayout getSheet() {
        return sheet;
    }

    public void setSheet(LinearLayout sheet) {
        this.sheet = sheet;
    }

    LinearLayout drawer,l_pt_vente, lmain,status,lup;

     RecyclerView recyclerView,recyclerViewData;
     Adapter adapter;
    public View view(){
        View view= Ut.getView(context, R.layout.navigation_drawer);
        recyclerView = view.findViewById(R.id.recycler);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* drawer.setVisibility(View.VISIBLE);
                vide.setVisibility(View.VISIBLE);
                drawer.setAnimation(Anim.getAnimeGD(context));*/
            }
        });
        vide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* drawer.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
                sheet.setVisibility(View.GONE);*/
            }
        });
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        drawer.addView(view);

        preparerAlbum();
        return view;
    }

    private boolean estModul(Menu o, String mod){
        if(o!=null){
            String mo=o.getModule();
            return mo!=null&&mo.equals(mod);
        }
        return false;
    }
    String mdl="";
   /* private void getAllMenus() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<List<Menu>> call = apiService.menuList(LoginActivity.compte.getProfile().getLibelle().equalsIgnoreCase("admin")?
                ApiClient.BASE_URL_PROD + "menu/all/GSTOCK/"+LoginActivity.compte.getIdBoutique().getIdServeur():ApiClient.BASE_URL_PROD + "gstock_user_menu/user/GSTOCK/"+"/"+LoginActivity.compte.getIdServeur());
        call.enqueue(new Callback<List<Menu>>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if (response.body() != null) {
                    String js = new Menu().js(response.body());
                    MonFichier.ecrire(context, "menu_list", js);
                    List<Menu> menus=removeDash(response.body());
                    adminMenu=menus.stream().filter(m->estModul(m,"VENTE")).toList();
                    stockMenu=menus.stream().filter(m->estModul(m,"STOCK")).toList();;
                    compteMenu=menus.stream().filter(m->estModul(m,"COMPTABILITE")).toList();;
                    switch (mdl){
                        case "VENTE":
                            albumList = new Menu().alboms(adminMenu);
                            break;
                        case "STOCK":
                            albumList = new Menu().alboms(stockMenu);
                            break;
                        case "COMPTABILITE":
                            albumList = new Menu().alboms(compteMenu);
                            break;
                    }
                   // module.setText(mdl);

                    // albumList = new Menu().alboms(adminMenu);
                    // Dialogue.neutreDialog(response.body().size()+"  "+adminMenu.size(),""+albumList.size(),context).show();
                    preparerAlbum();
                } else {
                    Dialogue.neutreDialog("Erreur technique", "", context).show();
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                //Dialogue.neutreDialog(t + "", "", context).show();
                S.toast(context,t+"",R.color.red);
            }
        });
    }*/
    public static List<Album> albumList;
    public static AlbumAdapter album_adapter;
    public static boolean click;
    public static List<String> backColors = Arrays.asList("#EEEEEE", "#FFFFFF", "#EEEEEE", "#FFFFFF", "#E6E9FB");

    Album album;
    public static Menu menu_aux;
    List<Menu> adminMenu=new ArrayList<>(),
            stockMenu=new ArrayList<>(),
            compteMenu=new ArrayList<>();

   public static int index=-1,p;
    private void preparerAlbum() {
        albumList=albums();
        album_adapter = new AlbumAdapter(context, albumList, "menu", "#E9E9E9");
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, S.dpToPx(0, context.getResources()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(album_adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new RecyclerTouchListener.SwipeClickListener() {
            @Override
            public void onClick(View view, final int position) {
                p=position;
                album = albumList.get(position);
                Menu m = new Menu().fromJs(album.getJson());

            }

            @Override
            public void onLongClick(View view, int position) {
                //Pths=paths.get(position);p=position;

            }
            @Override
            public void onSwipeLeft(View view, int position) {
                // Optionnel : gérer le swipe gauche ici
                // Ex : Toast.makeText(context, "Swiped Left: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight(View view, int position) {
                // Optionnel : gérer le swipe droite ici
                // Ex : Toast.makeText(context, "Swiped Right: " + position, Toast.LENGTH_SHORT).show();
            }
        }));
        // swip(liste);
    }

    private List<Album> albums(){
        return List.of(new Album("Comptes",R.drawable.back),
                new Album("Demander un prêt",0),
                new Album("Transfers",0),
                new Album("Charges",0),
                new Album("Bénéficiaires",0),
                new Album("Enquêtes",0));

    }


    public static void clickDerwer(Menu menu) {
        //drawer.setVisibility(View.GONE);
        //vide.setVisibility(View.GONE);
        String url=menu.getTitre().toLowerCase();
       // editeObject=new EditeObject();
        switch (url){
            case "demarrer-journee":

                break;
            case "administration":

                break;
            case "gestion du stock":

                break;
            case "menus":

                break;
            case "stock":

                break;
            case "catalogue":

                break;

            case "menus utilisateur":

                break;
            case "clôturer la journée":

                break;
            case "jour ouvré":

                break;
            case "cloture":

                break;
            case "profil utilisateur":

                break;
            case "partager":
               // share();
                break;
        }
    }

}
