package com.credi.fing.publics.service.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.credi.fing.publics.service.OnBindViewHolderAction;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.credi.fing.R;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fing.publics.adapters.generiqueAdapter.PreparAdapter;
import com.credi.fing.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fing.publics.repository.Repository;
import com.credi.fing.publics.repository.sqlite.Data;
import com.credi.fing.publics.service.ClickHandler;
import com.credi.fing.publics.service.HttpApi;
import com.credi.fing.publics.service.IBinder;
import com.credi.fing.publics.service.OnItemViewClick;
import com.credi.fing.publics.service.interfacs.CrudInterface;
import com.credi.fing.publics.service.pojo.Navig;
import com.credi.fing.publics.service.pojo.NavigateObject;
import com.credi.fing.publics.service.pojo.NavigationObjects;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.OnSwipeTouchListener;
import com.credi.fing.publics.utils.S;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListActivity extends AppCompatActivity {
    static LinearLayout sheet, lmain, lbotom;
    static Context context;
    ImageView mort, back;
    CircleImageView circleImageView;
    TextView toolbar, sub_text;
    public static View vide,star;
    RecyclerView recyclerView;
    public static Adapter adapter;
    RecyclerHandler handler;
    FloatingActionButton fab;
    public static EditeObject editeObject;
    static ProgressBar pb;
    static SwipeRefreshLayout swifeRefresh;
    boolean news = false;
    NavigateObject navigateObject;
    NavigationObjects navigationObjects;
    List<Navig> navigs;
    public static IBinder iBinder;
    BadgeDrawable badge, badgeNotif, badgeVideo;
    BottomNavigationView bottomNavigationView;
    RowObject rowObject;
    OnItemViewClick onItemViewClick;
    CrudInterface crudInterface;
    OnBindViewHolderAction onBindViewHolderAction;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context = this;
        toolbar = findViewById(R.id.tx_text);
        mort = findViewById(R.id.mort);
        lmain = findViewById(R.id.lmain);
        vide = findViewById(R.id.vides);
        sub_text = findViewById(R.id.sub_text);
        recyclerView = findViewById(R.id.liste);
        circleImageView = findViewById(R.id.profile_image);
        star=findViewById(R.id.star);
        pb = findViewById(R.id.pb);
        back = findViewById(R.id.back);
        fab = findViewById(R.id.fab);
        sheet=findViewById(R.id.sheet);
        swifeRefresh = findViewById(R.id.swifeRefresh);
        id=getIntent().getStringExtra("id");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigateObject = (NavigateObject) getIntent().getSerializableExtra("navigateObject");
        navigationObjects = (NavigationObjects) getIntent().getSerializableExtra("navigateObjects");
        if (navigationObjects != null && navigationObjects.getNavigs().size() == 1) {
            navigateObject = navigationObjects.getNavigs().get(0).getNavigateObject();
        }
        onItemViewClick= ClickHandler.getOnItemViewClick();
        crudInterface = ClickHandler.getCrudInterface();
        onBindViewHolderAction=ClickHandler.getOnBindViewHolderAction();
        if (navigateObject != null) {
            bottomNavigationView.setVisibility(View.GONE);
            sub_text.setVisibility(View.GONE);
            traiterVue();
        } else if (navigationObjects != null && !navigationObjects.getNavigs().isEmpty()) {
            navigs = navigationObjects.getNavigs();
            rowObject = navigationObjects.getRowObject();
            navigs = navigs.stream().sorted(Comparator.comparingInt(Navig::getNum)).collect(Collectors.toList());
            putMenu();
            if (!navigs.isEmpty()) {
                navigateObject = navigs.get(0).getNavigateObject();
                if(navigateObject!=null){
                    traiterVue();
                }
            }
            swip(lmain);
            swip(recyclerView);
            if (rowObject != null) {
                circleImageView.setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
                object2 = navigationObjects.getObject();
                Object st = Ut.getValue(object2, "numeroEntreprise");
                if (st != null) {
                    sub_text.setVisibility(View.VISIBLE);
                    sub_text.setText(st.toString());
                }
                putIcone();
            }

        } else {
            Dialogue.neutreDialogF("Paramètre invalide", "Alerte", this, this).show();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        TextView wifi = findViewById(R.id.wifi);
       // networkReceiver = new NetworkReceiver(wifi);
    }

    NetworkReceiver networkReceiver;

    @Override
    protected void onStop() {
        super.onStop();
        // Désinscrire le BroadcastReceiver
        // unregisterReceiver(networkReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
    }


    private void traiterVue() {
        toolbar.setText(navigateObject.getTitle());
        if (navigateObject.getSubTitle() != null) {
            sub_text.setVisibility(View.VISIBLE);
            sub_text.setText(navigateObject.getSubTitle());
        }
        editeObject = navigateObject.getEditeObject();
        if (navigateObject.getAddButton() != null && navigateObject.getAddButton()) {
            bottomNavigationView.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editeObject != null) {
                        news = true;
                        editeObject.setObject(navigateObject.getObject());
                      /*  startActivity(new Intent(context, AddActivity.class)
                                .putExtra("object", editeObject));*/
                    }

                }
            });
        } else {
            fab.setVisibility(View.GONE);
        }
        switch (navigateObject.getTypeData()) {
            case 1: default:
                //line data
                getData();
                break;
            case 2:
                //local data
                localData();
                break;
            case 3:
                //line and local data
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ClickHandler.annuler();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    Repository repository;
    static ProgressBar pbc;
    public static List<Object> objects;
    static int p;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iBinder = null;
        ClickHandler.annuler();
    }

    static Object object, object2;

    @Override
    protected void onRestart() {
        super.onRestart();
        if(id==null){
           id="idServeur";
        }else id=id+"|idServeur";
         if (navigateObject != null && navigateObject.getAddButton() != null && navigateObject.getAddButton()) {
            if (AddActivity.object != null && Ut.getValue(AddActivity.object, id) == null
                    && Ut.getValue(AddActivity.object, "idLocal") == null) {

                repository = new Repository(Data.class, context);
                Object data = repository.save(new Data(Ut.js(AddActivity.object), navigateObject.getLocalData().getValue()));
                objects.add(0, AddActivity.object);
                adapter.setData(data);
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
                AddActivity.object = null;
            } else {
                if (AddActivity.object != null && Ut.getValue(AddActivity.object, id) != null) {
                    updateData(AddActivity.object);
                }
            }
        }

    }


    private void preparerDatas() {
        PreparAdapter preparAdapter = preparAdapterBulder();
        if (handler == null) {

            handler = new RecyclerHandler(recyclerView, context, preparAdapter.getAdapter(), navigateObject.getaClass());
            handler.setOnItemTouchListener(
                    (position, view) -> {
                        if(onItemViewClick!=null){
                            if(position<objects.size()){
                                onItemViewClick.onItemeClick(sheet,objects.get(position),position);
                            }else {
                                S.toast(context,"Erreur technique "+position+" "+objects.size());
                            }
                        }else{
                            object = objects.get(position);
                            p = position;
                            pbc = view.findViewById(R.id.pbc);
                           // Dialogue.neutreDialog(Ut.js(object),"xxx",context).show();
                            if (iBinder != null&&crudInterface==null) {
                                iBinder.onClick(view, object, position);
                            }
                        }
                    },
                    (p, view) -> {
                        if (iBinder != null) {
                            iBinder.onLongClick(view, object, p);
                        }
                    }
            );
        }

    }

    private void localData() {
        Repository rp = new Repository(navigateObject.getaClass(), context);
        if (navigateObject.getLocalData() == null) {
            objects = rp.findAll();
        } else {
            if (navigateObject.getLocalData().getCondition() == null) {
                objects = rp.findByAttribut(navigateObject.getLocalData().getTable(), navigateObject.getLocalData().value);
            } else {
                objects = rp.findByAttribut(navigateObject.getLocalData().getTable(), "'" + navigateObject.getLocalData().value + "'", navigateObject.getLocalData().getCondition());

            }
        }
        if (navigateObject.getaClass() == Data.class) {
            objects = objects.stream().map(o -> {
                Object js = Ut.getValue(o, "js");

               // System.out.println(" js js "+js);
                try {
                    js = Ut.creatObject(Ut.fromJs(js+"",Object.class), navigateObject.getLocalData().getDataClasse());
                }catch (Exception e){
                    e.printStackTrace();
                }
               // System.out.println(" o o "+js);

               // System.out.println(" js fin "+js);
                return js;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        preparerDatas();
    }

    private void getData() {
        if(navigateObject.getValues()!=null&&!navigateObject.getValues().isEmpty()){
            objects=navigateObject.getValues();
            preparerDatas();
        }else {
            new HttpApi(context).setProgressBar(pb).datas(navigateObject.getDataUrl(),navigateObject.getPostData(),(obs,s)->{
                if(obs!=null){
                    objects=obs;
                    preparerDatas();
                }
            });
        }
    }

    private void updateData(Object obje) {
        new HttpApi(context).setProgressBar(pbc).data(navigateObject.getEndPointSave()+"/save",obje,(o,s)->{
            if(o!=null&&s.equals("ok")){
                object = o;
                objects.set(p, obje);
                 ///Dialogue.neutreDialog(Ut.js(object)+"","",context).show();
                adapter.notifyItemChanged(p);
            }else if(!s.equalsIgnoreCase("local")){
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            Dialogue.neutreDialog(s,"Echèc",context).show();
                        });
                    }
                }

            }
            return o;
        });

    }

    int index = 0;

    private void jumpte(int index) {
        if (index >= 0 && index < navigationObjects.getNavigs().size()) {
            bottomNavigationView.setSelectedItemId(navigationObjects.getNavigs().get(index).getNum());
            bottomNavigationView.setSelected(true);
            navigateObject = navigationObjects.getNavigs().get(index).getNavigateObject();
            if (navigateObject != null) {
                traiterVue();
            }
        }
    }

    private void swip(View view) {
        System.out.println(" swipwip " + context);
        view.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeTop() {
                // Toast.makeText(context, "top", Toast.LENGTH_SHORT).show();

            }

            public void onSwipeRight() {
                if (index > 0) {
                    index--;
                    jumpte(index);
                }
                Toast.makeText(context, "reigt", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(context, "lest", Toast.LENGTH_SHORT).show();
                // drawer.setAnimation(getAnimeD(context));
                if (index < navigationObjects.getNavigs().size() - 1) {
                    index++;
                    jumpte(index);
                }
            }

            public void onSwipeBottom() {

            }
        });
    }


    void putMenu() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        Menu menu = bottomNavigationView.getMenu();
        menu.clear();

        // Ajout dynamique des items
        for (Navig navig : navigs) {
            menu.add(0, navig.getNum(), navig.getNum(), navig.getLibelle()).setIcon(navig.getIcon());
        }


// Gestion des clics
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int i = item.getItemId();
            Navig navig = findIndex(i);
            //System.out.println(i + " navig " + navig);
            if (navig != null) {
                navigateObject = navig.getNavigateObject();
                //System.out.println(" nvg "+navigateObject);
                if (navigateObject != null) {
                    traiterVue();
                }
                return true;
            }
            return false;
        });


    }

    private Navig findIndex(int index) {
        for (Navig n : navigs) {
            if (n.getNum() == index) return n;
        }
        return null;
    }

    void putIcone() {
        if (rowObject.getDrawableIconSwitchBinders() != null) {
            if (rowObject.getDrawableIconSwitchBinders().size() == 1) {
                String field = rowObject.getDrawableIconSwitchBinders().get(0).getField();
                Object val = rowObject.getDrawableIconSwitchBinders().get(0).getValue();
                Object dft = rowObject.getDrawableIconSwitchBinders().get(0).getDefaultValue();
                Object reps = rowObject.getDrawableIconSwitchBinders().get(0).getRespons();
                Object rp = Ut.getValue(object2, field);

                if (rp != null && reps != null && Objects.equals(reps, rp)) {
                    int dw = Integer.parseInt(val.toString());
                    circleImageView.setImageResource(dw);
                } else {
                    int dw = Integer.parseInt(dft.toString());
                    circleImageView.setImageResource(dw);
                }
            }
        }
        if (rowObject != null && rowObject.getStarSwitchBinders() != null && rowObject.getStarSwitchBinders().size() == 1) {
            String field = rowObject.getStarSwitchBinders().get(0).getField();
            Object val = rowObject.getStarSwitchBinders().get(0).getValue();
            Object dft = rowObject.getStarSwitchBinders().get(0).getDefaultValue();
            Object reps = rowObject.getStarSwitchBinders().get(0).getRespons();
            Object rp = Ut.getFirstValue(object2, field);
            star.setVisibility(View.VISIBLE);
            if (rp != null && reps != null && Objects.equals(reps, rp)) {
                int dw = (int) val;
                star.setBackgroundResource(dw);
            } else {
                int dw = (int) dft;
                star.setBackgroundResource(dw);
            }
        }
        if(rowObject!=null&&rowObject.getNavigetActivity()!=null&&circleImageView!=null){
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context,rowObject.getNavigetActivity())
                            .putExtra("object",Ut.js(object2)));
                }
            });
        }
    }

    private PreparAdapter preparAdapterBulder(){
        PreparAdapter preparAdapter = new PreparAdapter(context).setRecyclerView(recyclerView)
                .setBinder(navigateObject.getBinder())
                .setAdapter(adapter)
                .setCrudInterface(crudInterface)
                .setObjects(objects)
                .setUrl(navigateObject.getEndPointSave())
                .setId(R.layout.card_image_horiz_row)
                .setaClass(navigateObject.getaClass())
                .setSave(navigateObject.getEndPointSave() != null)
                .init(crudInterface,onBindViewHolderAction);

        // preparAdapter.setAttributs(new Operation().setAttribut(operateurs));
        adapter = preparAdapter.getAdapter();
        // preparAdapter.init();
        if (navigateObject.getAddButton()!=null&&navigateObject.getAddButton()) {
            preparAdapter.fixedScrol(fab);
        }
        if (bottomNavigationView != null && bottomNavigationView.getVisibility() == View.VISIBLE) {
            preparAdapter.fixedScrol(bottomNavigationView);
        }
        return preparAdapter;
    }

    public static void update(int p){
        adapter.notifyItemRemoved(p);
        objects.remove(p);
    }
    public static void update(int p,Object object){
        ListActivity.p=p;
        editeObject.setObject(object);
       // Dialogue.neutreDialog(Ut.js(object),p+"",context).show();
        context. startActivity(new Intent(context, AddActivity.class));
    }

}