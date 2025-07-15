package com.credi.fings;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.credi.fings.activity.Inscription;
import com.credi.fings.activity.PagerActivity;
import com.credi.fings.activity.ProfileActivity;
import com.credi.fings.binder.CompteBinder;
import com.credi.fings.binder.LoanAccountBinder;
import com.credi.fings.binder.PretBinder;
import com.credi.fings.entity.Client;
import com.credi.fings.entity.Compte;
import com.credi.fings.entity.Currency;
import com.credi.fings.entity.LoanAccount;
import com.credi.fings.entity.LoanType;
import com.credi.fings.pojo.LoanPojo;
import com.credi.fings.pojo.LoanProductResponse;
import com.credi.fings.pojo.LoginRequest;
import com.credi.fings.publics.AddActivity;
import com.credi.fings.publics.carousel.CarouselAdapter;
import com.credi.fings.publics.carousel.CarouselItem;
import com.credi.fings.publics.composant.RecyclierViewCp;
import com.credi.fings.publics.composant.SheetCp;
import com.credi.fings.publics.drawer.NavigationDrawer;
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
import com.credi.fings.publics.utils.LesConnectes;
import com.credi.fings.publics.utils.MonFichier;
import com.credi.fings.publics.utils.S;
import com.credi.fings.utils.Json;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    ImageView eye_pret,eye_epargne,mort,drawer;
    View vide;
    LinearLayout lservice,sheet,drawer_lineair;
    FloatingActionButton add;
    Context context;
    TextView solde_pret,solde_epargne,symbole,name;

    NavigationDrawer navigationDrawer;
    View viewDrawer;
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
        drawer=findViewById(R.id.drawer);
        drawer_lineair=findViewById(R.id.drawer_lineair);
        vide=findViewById(R.id.vide);
        pb=findViewById(R.id.pb);
        context=this;
        sheet.setVisibility(View.GONE);
        mort=findViewById(R.id.mort);
        symbole=findViewById(R.id.symbole);
        name=findViewById(R.id.name);
        //carousel();
        services();clickBotom();
        vide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        pret="";
        epargne="";
        closEyes();
        boolean testPlayStor=Inscription.body.getPassword().equalsIgnoreCase("fingiciel")&&
                Inscription.body.getUsername().equalsIgnoreCase("fingiciel");
        if(Inscription.user!=null){
            String js=MonFichier.lire(context,"client");
            if(js.isEmpty()&&testPlayStor){
                js=Json.client;
            }
            if(!js.isEmpty()){
                 client= new Client().fromJs(js);
               // System.out.println("=client=> "+js);
                loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                setComptesValues();
            }
            js=MonFichier.lire(context,"loanProductResponse");
            if(js.isEmpty()&&testPlayStor){
                js=Json.loanProductResponse;
            }
            if(!js.isEmpty()){
                //logLongIterative("=loanProductResponse_tag=>",js);
                //System.out.println("=loanProductResponse=> "+js);
                loanProductResponse=new LoanProductResponse().fromJs(js);
            }
            js=MonFichier.lire(context,"displayName");
            if(js.isEmpty()&&testPlayStor){
                js=Json.displayNam;
            }
            if(!js.isEmpty()){
                //logLongIterative("=displayName_tag=>",js);
                //System.out.println("=displayName=> "+js);
                Client cl=new Client().fromJs(js);
                if(cl.getDisplayName()!=null&&!cl.getDisplayName().isEmpty()){
                    String  sy=cl.getDisplayName().charAt(0)+"";
                    symbole.setText(sy.toUpperCase());
                    name.setText(cl.getDisplayName());
                }
            }
            if(!testPlayStor){
                getClientAcount();
                getClient();
                getTemplate();
            }
        }else {
            finish();
        }
        mort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] m={"Se déconnecter","Qui somme nous"};
                PopupMenu pop=S.popupMenu(view,m);
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                new LesConnectes().remove(context,Inscription.user);
                                MonFichier.ecrire(context,"displayName","");
                                MonFichier.ecrire(context,"client","");
                                startActivity(new Intent(context, Inscription.class));
                                finish();
                                break;
                            case 2:
                                break;
                        }
                        return false;
                    }
                });

            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ProfileActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        navigationDrawer=new NavigationDrawer(context,drawer,drawer_lineair);
        navigationDrawer.setVide(vide);
        navigationDrawer.setSheet(sheet);
        navigationDrawer.view();
    }
    public static List<String> backColors = Arrays.asList("#EEEEEE", "#FFFFFF", "#EEEEEE", "#FFFFFF", "#E6E9FB");

    private static final int MAX_LOG_LENGTH = 4000;
    public static void logLongIterative(String tag, String text) {
        int length = text.length();
        for (int i = 0; i < length; i += MAX_LOG_LENGTH) {
            int end = Math.min(length, i + MAX_LOG_LENGTH);
            Log.v(tag, text.substring(i, end));
        }
    }

    @Override
    public void onBackPressed() {
        if(sheet.getVisibility()==View.VISIBLE){
            sheet.setVisibility(View.GONE);
            vide.setVisibility(View.GONE);
            bottomNav.setSelectedItemId(R.id.navigation_home);
        }else
        {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
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
            if(loanAccounts==null){
                loanAccounts=new ArrayList<>();
            }
            if(savingsAccounts==null){
                savingsAccounts=new ArrayList<>();
            }
            if(loanProductResponse==null){
                loanProductResponse=new LoanProductResponse();
            }
            switch (i){
                case 0:
                    S.toast(context,"Module en cours de developpement");
                    break;
                case 1:
                    LoanPojo loanAccount=new LoanPojo();
                    loanAccount.setClientId(Inscription.user.getClientId());
                    LoanType loanType=new LoanType();
                    loanType.setId(1L);
                    loanType.setCode("accountType.individual");
                    loanType.setValue("individual");
                    loanAccount.setLoanType(loanType.getValue());
                    loanAccount.setInterestCalculationPeriodType(1);

                    loanAccount.setProductId(1);

// disbursementData : tableau vide

                    loanAccount.setFundId(1);


                    loanAccount.setLoanTermFrequency(24);
                    loanAccount.setLoanTermFrequencyType(2);

                    loanAccount.setNumberOfRepayments(24);
                    loanAccount.setRepaymentEvery(1);
                    loanAccount.setRepaymentFrequencyType(2);

                    loanAccount.setInterestRatePerPeriod(4);
                    loanAccount.setAmortizationType(1);

                    loanAccount.setEqualAmortization(false);
                    loanAccount.setInterestType(0);

                    loanAccount.setAllowPartialPeriodInterestCalcualtion(false);

                    loanAccount.setTransactionProcessingStrategyId(1);
                    loanAccount.setLoanPurposeId(24);

                    loanAccount.setLocale("fr");
                    loanAccount.setDateFormat("dd MMMM yyyy");

                    /*loanAccount.setExpectedDisbursementDate("05 octobre 2024");
                    loanAccount.setSubmittedOnDate("05 octobre 2024");*/

                    /*

                    {"clientId":"9",
                    "productId":1,
                    "disbursementData":[],
                    "fundId":1,
                    "principal":2000000,
                    "loanTermFrequency":24,
                    "loanTermFrequencyType":2,
                    "numberOfRepayments":24,
                    "repaymentEvery":1,
                    "repaymentFrequencyType":2,
                    "interestRatePerPeriod":4,
                    "amortizationType":1,
                    "isEqualAmortization":false,
                    "interestType":0,
                    "interestCalculationPeriodType":1,
                    "allowPartialPeriodInterestCalcualtion":false,
                    "transactionProcessingStrategyId":1,
                    "loanPurposeId":24,
                    "locale":"fr",
                    "dateFormat":"dd MMMM yyyy",
                    "loanType":"individual",
                    "expectedDisbursementDate":"05 octobre 2024",
                    "submittedOnDate":"05 octobre 2024"}

                    * */


                   /* ClickHandler.setSendHttp((object,activity)->{
                        LoanPojo loan= (LoanPojo) Ut.creatObject(object,LoanAccount.class);
                        saveLoan(loan,activity);
                    });*/
                    List<String> comptes=loanAccounts.stream().map(ac->Ut.getValue(ac,"accountNo")+"").collect(Collectors.toList());
                    System.out.println("comptes == "+comptes);
                    LoanAccountBinder pretBinder=new LoanAccountBinder();
                    EditeObject editeObject=pretBinder.editeObject(loanProductResponse,comptes);
                    editeObject.setObject(loanAccount);

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
                    S.toast(context,"Module en cours de developpement");
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
        if(pret!=null&&!pret.isEmpty()){
            solde_pret.setText(pret+" CFA");
        }
        if(epargne!=null&&!epargne.isEmpty()){
            solde_epargne.setText(epargne+" CFA");
        }
        eye_pret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              isShowingPret=!isShowingPret;
              MonFichier.ecrire(context,"isShowingPret",isShowingPret+"");
              if(isShowingPret){
                  solde_pret.setText(pret+" CFA");
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
                 solde_epargne.setText(epargne+" CFA");
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
        String username = Inscription.user.getUsername();
        String password = Inscription.body.getPassword();

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        long clientId = Inscription.user.getClientId();                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<Client> call = api.getClientById("Basic " + okhttp3.Credentials.basic(username, password),
                clientId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Client client = response.body();
                    MonFichier.ecrire(context,"displayName",client.js());
                    System.out.println(" displayName => "+client.js());
                   // String displayName = client.getFirstname() + " " + client.getLastname();
                    if(client.getDisplayName()!=null&&!client.getDisplayName().isEmpty()){
                        String  sy=client.getDisplayName().charAt(0)+"";
                        symbole.setText(sy);
                        name.setText(client.getDisplayName());
                    }
                } else {
                    // Erreur côté serveur ou JSON non parsable
                    Dialogue.neutreDialog(response.message()+" "+response.errorBody(),"null",context).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                // Problème réseau ou exception
               Dialogue.neutreDialog(t.toString(),"",context).show();
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
    public static List<Object> savingsAccounts;
    public static List<Object> loanAccounts;
    public static LoanProductResponse loanProductResponse;
    Client client;
    void getClientAcount(){
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();
       // String password = Inscription.user;

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        long clientId = Inscription.user.getClientId();                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<Client> call = api.getClientAccountsById("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                clientId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful() && response.body() != null) {
                     client = response.body();

                     MonFichier.ecrire(context,"client",client.js());
                    System.out.println(" client => "+client.js());
                    loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                    savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                    setComptesValues();
                } else {
                     //client= (Client) Ut.fromJs(Json.json,Client.class);
                    //loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                   // savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                   // setComptesValues();
                    // Erreur côté serveur ou JSON non parsable
                      Dialogue.neutreDialog(response.message()+" "+response.code(),response.errorBody()+"",context).show();
                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                Dialogue.neutreDialog(t.getMessage()+"","Echec",context).show();
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

    void getTemplate(){
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();
        // String password = Inscription.user;

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        String clientId = "individual";                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<LoanProductResponse> call = api.getTemplatePret("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                clientId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<LoanProductResponse>() {
            @Override
            public void onResponse(Call<LoanProductResponse> call, Response<LoanProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                   loanProductResponse = response.body();
                    System.out.println(" loanProductResponse => "+loanProductResponse.js());
                   MonFichier.ecrire(context,"loanProductResponse",loanProductResponse.js());
                   // loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                    //= (List<Object>) Ut.getValue(client,"savingsAccounts");
                   // setComptesValues();

                } else {
                    //client= (Client) Ut.fromJs(Json.json,Client.class);
                    //loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                    // savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                    // setComptesValues();
                    // Erreur côté serveur ou JSON non parsable
                    Dialogue.neutreDialog(response.message()+" "+response.code(),response.errorBody()+"",context).show();
                }
                hidePb();
            }

            @Override
            public void onFailure(Call<LoanProductResponse> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                /*client= (Client) Ut.fromJs(Json.json,Client.class);
                Dialogue.neutreDialog(t+"","",context).show();
                loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                setComptesValues();*/
            }
        });
    }
    BottomNavigationView bottomNav;
    void clickBotom(){
         bottomNav = findViewById(R.id.bottom_navigation);

        // (Re)appliquer les modes si besoin
       // bottomNav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
       // bottomNav.setItemHorizontalTranslationEnabled(false);

        // Gérer les clics sur les items
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Accueil":
                        // TODO: Afficher la page d'accueil
                        return true;
                    case "Bénéficiaires":
                       startActivity(new Intent(context, ListActivity.class));
                        return true;
                    case "Enquêtes":
                        // TODO: Afficher les enquêtes
                        return true;
                    case "Nous contacter":
                       showContact();
                        return true;
                }
                return false;
            }
        });
    }

    void showContact(){
        sheet.setVisibility(View.VISIBLE);
        sheet.removeAllViews();
        SheetCp sheetCp=new SheetCp(context)
                .setTitle("Nous contacter");
        View vv=sheetCp.view();
        LinearLayout content=vv.findViewById(R.id.content);
        sheet.addView(vv);
        View tm= Ut.getView(context,R.layout.row_jrs);
        ImageView im1=tm.findViewById(R.id.icone);
        ImageView close=vv.findViewById(R.id.close);
        CheckBox checkbox=tm.findViewById(R.id.checkbox);
        checkbox.setVisibility(View.GONE);
        im1.setImageResource(R.drawable.outline_attach_email_24);
        Ut.setImageTint(im1,R.color.colorPrimary,context);
        TextView til=tm.findViewById(R.id.title);
        til.setText("support@fingiciel.com");
        til.setTextColor(Ut.getColor(context,R.color.colorPrimary));
        content.addView(tm);

        View fz= Ut.getView(context,R.layout.row_jrs);
        ImageView im2=fz.findViewById(R.id.icone);
        CheckBox ch=fz.findViewById(R.id.checkbox);
        ch.setVisibility(View.GONE);
        im2.setImageResource(R.drawable.baseline_add_call_24);
        Ut.setImageTint(im2,R.color.colorAccent,context);
        TextView ti=fz.findViewById(R.id.title);
        ti.setText("0033695544758");
        ti.setTextColor(Ut.getColor(context,R.color.colorAccent));
        content.addView(fz);
        vide.setVisibility(View.VISIBLE);
        fz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appel("0033695544758",context);
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
                bottomNav.setSelectedItemId(R.id.navigation_home);
            }
        });
        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email();
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
                bottomNav.setSelectedItemId(R.id.navigation_home);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
                bottomNav.setSelectedItemId(R.id.navigation_home);
            }
        });
        sheet.setAnimation(Anim.getAnimeBH(context));
    }

    private void email() {
        try {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@fingiciel.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "");
            email.putExtra(Intent.EXTRA_TEXT, "");
            //need this to prompts email client only
            email.setType("message/rfc822");
            startActivity(email);
        } catch (Exception e) {
        }
    }

   // @RequiresApi(api = Build.VERSION_CODES.M)
    private void appel(String tel, Context context) {
        tel = tel.replace("Tel: ", "").replace(" ", "");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tel));
        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                if (checkPermission(context)) {
                    //Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }
            }
            return;
        }
        context.startActivity(callIntent);
    }
    private boolean checkPermission(Context c) {
        return (ContextCompat.checkSelfPermission(c, "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(c, "android.permission.CALL_PHONE") == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CALL_PHONE"}, 1);
    }
}