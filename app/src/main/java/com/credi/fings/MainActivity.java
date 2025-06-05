package com.credi.fings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.credi.fings.activity.PagerActivity;
import com.credi.fings.binder.CompteBinder;
import com.credi.fings.binder.PretBinder;
import com.credi.fings.entity.Client;
import com.credi.fings.entity.Compte;
import com.credi.fings.entity.LoanAccount;
import com.credi.fings.publics.AddActivity;
import com.credi.fings.publics.carousel.CarouselAdapter;
import com.credi.fings.publics.carousel.CarouselItem;
import com.credi.fings.publics.composant.RecyclierViewCp;
import com.credi.fings.publics.composant.SheetCp;
import com.credi.fings.publics.service.ApiClient;
import com.credi.fings.publics.service.ApiService;
import com.credi.fings.publics.service.ClickHandler;
import com.credi.fings.publics.service.HttpApi;
import com.credi.fings.publics.service.RetrofitClient;
import com.credi.fings.publics.service.impl.Anim;
import com.credi.fings.publics.service.impl.EditeObject;
import com.credi.fings.publics.service.impl.ListActivity;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.service.interfacs.CrudInterface;
import com.credi.fings.publics.service.pojo.NavigateObject;
import com.credi.fings.publics.utils.DateObject;
import com.credi.fings.publics.utils.Dialogue;
import com.credi.fings.publics.utils.MonFichier;
import com.credi.fings.publics.utils.S;
import com.credi.fings.utils.Json;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private CarouselAdapter adapter;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;
    CircleImageView profil;
    ProgressBar pb;
    ImageView eye_pret,eye_epargne;
    View vide;
    LinearLayout lservice,sheet;
    FloatingActionButton add;
    Context context;
    TextView solde_pret,solde_epargne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profil=findViewById(R.id.profile_image);
        solde_pret=findViewById(R.id.solde_pret);
        solde_epargne=findViewById(R.id.solde_epargne);
        profil.setVisibility(View.VISIBLE);
        eye_epargne=findViewById(R.id.eye_epargne);
        eye_pret=findViewById(R.id.eye_pret);
        lservice=findViewById(R.id.lservice);
        sheet=findViewById(R.id.sheet);
        add=findViewById(R.id.add);
        vide=findViewById(R.id.vide);
        pb=findViewById(R.id.pb);
        context=this;
        sheet.setVisibility(View.GONE);
        //carousel();
        services();
        vide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.setVisibility(View.VISIBLE);
                sheet.removeAllViews();
                SheetCp sheetCp=new SheetCp(context)
                        .setTitle("Source de ravitaillement");
                View vv=sheetCp.view();
                LinearLayout content=vv.findViewById(R.id.content);
                sheet.addView(vv);
                View tm= Ut.getView(context,R.layout.row_jrs);
                ImageView im1=tm.findViewById(R.id.icone);
                CheckBox checkbox=tm.findViewById(R.id.checkbox);
                checkbox.setVisibility(View.GONE);
                im1.setImageResource(R.drawable.mixx);
                TextView til=tm.findViewById(R.id.title);
                til.setText("Mixx by yas");
                content.addView(tm);

                View fz= Ut.getView(context,R.layout.row_jrs);
                ImageView im2=fz.findViewById(R.id.icone);
                CheckBox ch=fz.findViewById(R.id.checkbox);
                ch.setVisibility(View.GONE);
                im2.setImageResource(R.drawable.flooz);
                TextView ti=fz.findViewById(R.id.title);
                ti.setText("Flooz");
                content.addView(fz);
                vide.setVisibility(View.VISIBLE);
                fz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sheet.setVisibility(View.GONE);
                        vide.setVisibility(View.GONE);
                    }
                });
                tm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sheet.setVisibility(View.GONE);
                        vide.setVisibility(View.GONE);
                    }
                });
                sheet.setAnimation(Anim.getAnimeBH(context));
            }
        });
        pret="5 000 000 FCFA";
        epargne="1 500 135 FCFA";
        closEyes();
        getClientAcount();
    }

    @Override
    public void onBackPressed() {
        if(sheet.getVisibility()==View.VISIBLE){
            sheet.setVisibility(View.GONE);
            vide.setVisibility(View.GONE);
        }else
          super.onBackPressed();
    }

    private void carousel(){
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        List<CarouselItem> items = new ArrayList<>();
        items.add(new CarouselItem("Transfert", R.drawable.trf1));
        items.add(new CarouselItem("Prêt ", R.drawable.dm_pret1));
        items.add(new CarouselItem("Commencez", R.drawable.chg1));

        adapter = new CarouselAdapter(items);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Vous pouvez laisser vide si vous voulez juste des indicateurs sans texte
                }).attach();

        viewPager.setPageTransformer((page, position) -> {
            float scale = 1 - Math.abs(position) * 0.2f;
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setAlpha(0.3f + (scale - 0.8f) / 0.2f * 0.7f);
        });

        // Initialiser le Runnable pour le défilement automatique
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == adapter.getItemCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // Changer de page toutes les 3 secondes
            }
        };

        // Démarrer le défilement automatique
        handler.postDelayed(runnable, 3000);

        // Ajouter un callback pour mettre à jour la page actuelle
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPage = position;
            }
        });
    }

    private void services(){
        List<Object> list= Arrays.asList(new CarouselItem("",R.drawable.transfert0),new CarouselItem("",R.drawable.pret)
                ,new CarouselItem("",R.drawable.compte),new CarouselItem("",R.drawable.charge));
         RecyclierViewCp recyclierViewCp=new RecyclierViewCp(this,R.layout.row_home,list,(h,o,i)->{
         CarouselItem ob= (CarouselItem) o;
         ImageView imageView=h.image;
         imageView.setImageResource(ob.imageRes);

        }).setNumberItems(2).setBackground(R.color.colorSendre);
        lservice.addView(recyclierViewCp.view());
        recyclierViewCp.setOnClick((o,i)->{
            switch (i){
                case 0:
                    break;
                case 1:
                    PretBinder pretBinder=new PretBinder();
                    EditeObject editeObject=pretBinder.editeObject();
                    startActivity(new Intent(context, AddActivity.class)
                            .putExtra("object",editeObject));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                case 2:
                   /* CompteBinder compteBinder=new CompteBinder();
                    NavigateObject nvgdp=compteBinder.navigateObject();
                    if(loanAccounts!=null&&savingsAccounts!=null){
                        List<Object> lit=new ArrayList<>(loanAccounts);
                        lit.addAll(savingsAccounts);
                        nvgdp.setValues(lit);
                    }
                    nvgdp.setObject(o);
                    ClickHandler.setCrudInterface(crudInterface("idCompteBancaire","comptabilite_compte_bancaire", context));
                    ClickHandler.setOnBindViewHolderAction((vo,v,j)->{
                        TextView title=vo.title,second=vo.secondre,
                        title2=vo.title2,secondre2=vo.secondre2,date=vo.date,value=vo.textePourcentage;
                        Object productN_ob=Ut.getValue(v,"accountNo");
                        Object productName_ob=Ut.getValue(v,"productName");

                        if(productN_ob!=null){
                            String productName=productN_ob.toString();
                            title.setText(productName);
                        }
                        if(productName_ob!=null){
                            second.setText(productName_ob.toString());
                        }
                        if(j<loanAccounts.size()){
                            Object loanBalance_ob=Ut.getValue(v,"loanBalance");
                            if(loanBalance_ob!=null)
                              title2.setText(Ut.formatMontant(Double.parseDouble(loanBalance_ob.toString())));
                        }else {
                            Object loanBalance_ob=Ut.getValue(v,"accountBalance");
                            Object currency_ob=Ut.getValue(v,"currency");
                            if(loanBalance_ob!=null&&currency_ob!=null)
                            {
                                Object symb=Ut.getValue(currency_ob,"displaySymbol");
                                String sb=symb==null?"":symb.toString();
                                title2.setText(sb+" "+Ut.formatMontant(Double.parseDouble(loanBalance_ob.toString())));

                                Object displ=Ut.getValue(currency_ob,"displayLabel");
                                String displsb=displ==null?"":displ.toString();
                                secondre2.setText(displsb);

                                Object last_ob=Ut.getValue(v,"lastActiveTransactionDate");
                                if(last_ob!=null){
                                    List<Object> obs= (List<Object>) last_ob;
                                    String sdate=obs.get(2)+" "+ S.en2(Integer.parseInt(obs.get(1).toString()))+" "+obs.get(0);
                                    String dat= S.date(sdate,"dd MM yyyy","dd MMM yyyy");
                                    date.setText(dat);
                                }
                                Object type=Ut.getValue(v,"depositType");
                                if(type!=null){
                                    Object vl=Ut.getValue(type,"value");
                                    if(vl!=null){
                                        if(vl.toString().toLowerCase().contains("sav")){
                                          value.setText("Dépôt");value.setTextColor(Ut.getColor(context,R.color.green));
                                        }else {
                                            value.setText("Retrait");value.setTextColor(Ut.getColor(context,R.color.rouge));
                                        }
                                    }
                                }
                            }
                        }


                    });
                    startActivity(new Intent(context, ListActivity.class)
                            .putExtra("id","idCompteBancaire")
                            .putExtra("navigateObject",nvgdp));*/
                    startActivity(new Intent(context, PagerActivity.class)
                            .putExtra("client",client));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                     break;
                case 3:
                    break;
            }
        });
        recyclierViewCp.setOnLongClick((o,i)->{
          //  Dialogue.neutreDialog(o+"",i+" long",MainActivity.this).show();
        });
    }

    boolean isShowingEprgne=true,isShowingPret=true;
    String pret,epargne;
    private void closEyes(){
        solde_pret.setText(pret);
        solde_epargne.setText(epargne);
        eye_pret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              isShowingPret=!isShowingPret;
              MonFichier.ecrire(context,"isShowingPret",isShowingPret+"");
              if(isShowingPret){
                  solde_pret.setText(pret);
                  eye_pret.setImageResource(R.drawable.ic_eye_off_24dp2);
              }else {
                  eye_pret.setImageResource(R.drawable.ic_eye_white_24dp);
                  solde_pret.setText("**************");
              }
            }
        });
        eye_epargne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowingEprgne=!isShowingEprgne;
                MonFichier.ecrire(context,"isShowingEprgne",isShowingEprgne+"");
                if(isShowingEprgne){
                    eye_epargne.setImageResource(R.drawable.ic_eye_off_24dp2);
                 solde_epargne.setText(epargne);
                }else {
                    eye_epargne.setImageResource(R.drawable.ic_eye_white_24dp);
                  solde_epargne.setText("***************");
                }
            }
        });
    }

    private static CrudInterface crudInterface(String id, String url, Context context){
        CrudInterface crudInterface=new CrudInterface();
        crudInterface.setOnDelete((o,i)->{
            String vals=Ut.getAllValues(o,"idServeur|"+id);
            if(vals!=null&&!vals.isEmpty()){
                new HttpApi(context).data(url+"/delete",o,(ob, s)->{
                    ListActivity.update(i);
                    return ob;
                });
            }
        });
        crudInterface.setOnSave((o,i)->{
            String vals=Ut.getAllValues(o,"idServeur|"+id);

            if(vals!=null&&!vals.isEmpty()){
                ListActivity.update(i,o);
            }
        });
        return crudInterface;
    }

    void getClient(){
        // 1. Spécifiez vos identifiants Basic Auth
        String username = "adam";
        String password = "nadia";

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        long clientId = 8L;                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<Client> call = api.getClientById("Basic " + okhttp3.Credentials.basic(username, password),
                clientId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Client client = response.body();
                    String displayName = client.getFirstname() + " " + client.getLastname();
                   // Dialogue.neutreDialog(Ut.js(client),"",context).show();
                } else {
                    // Erreur côté serveur ou JSON non parsable
                   //  Dialogue.neutreDialog("","null",context).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                // Problème réseau ou exception
               //Dialogue.neutreDialog(t.toString(),"",context).show();
            }
        });
    }

    void showPb(){
        pb.setVisibility(View.VISIBLE);
        vide.setVisibility(View.VISIBLE);
    }
    void hidePb(){
        pb.setVisibility(View.GONE);
        vide.setVisibility(View.GONE);
    }
    List<Object> savingsAccounts;
    List<Object> loanAccounts;
    Client client;
    void getClientAcount(){
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = "adam";
        String password = "nadia";

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        long clientId = 8L;                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<Client> call = api.getClientAccountsById("Basic " + okhttp3.Credentials.basic(username, password),
                clientId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful() && response.body() != null) {
                     client = response.body();
                    loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                    savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                    setComptesValues();
                } else {
                     client= (Client) Ut.fromJs(Json.json,Client.class);
                    loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                    savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                    setComptesValues();
                    // Erreur côté serveur ou JSON non parsable
                    //  Dialogue.neutreDialog("","null",context).show();
                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                 client= (Client) Ut.fromJs(Json.json,Client.class);
                loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                setComptesValues();
            }
        });
    }

    double value(String v){
        return v==null||v.isEmpty()?0:Double.parseDouble(v);
    }
    private void setComptesValues(){
        if(loanAccounts!=null){
            Double prets=loanAccounts.stream().mapToDouble(x->value(Ut.getAllValues(x,"loanBalance"))).sum();
            Double eprgne=savingsAccounts.stream().mapToDouble(x->value(Ut.getAllValues(x,"accountBalance"))).sum();
            pret=Ut.formatMontant(prets);
            epargne=Ut.formatMontant(eprgne);
            closEyes();
        }
    }
}