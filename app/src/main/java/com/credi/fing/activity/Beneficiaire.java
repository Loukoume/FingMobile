package com.credi.fing.activity;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.activity.pagerBeneficiaireAdd.AddBeneciaireActivity;
import com.credi.fing.binder.BeneficiaryBinder;
import com.credi.fing.binder.OperationBinder;
import com.credi.fing.entity.AccountTypeOption;
import com.credi.fing.entity.Beneficiary;
import com.credi.fing.entity.BeneficiaryTemplate;
import com.credi.fing.entity.Client;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.adapters.generiqueAdapter.AdapterViewHolder;
import com.credi.fing.publics.composant.RecyclierViewCp;
import com.credi.fing.publics.composant.SheetCp;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Anim;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.EditeObject;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Beneficiaire extends AppCompatActivity {

    LinearLayout nodata,waite,sheet;
    TextView text,tx;
    RecyclerView recyclerView;
    Context context;
    FloatingActionButton fab;
    Object obj;
    EditeObject editeObject;
    View vide;
    EditText editTextSearch;
    ImageView clear;
    List<AccountTypeOption> typeAcounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_beneficiaire);
        nodata=findViewById(R.id.nodata);
        nodata.setVisibility(VISIBLE);
        text=findViewById(R.id.text);
        tx=findViewById(R.id.tx_text);
        recyclerView=findViewById(R.id.liste);
        waite=findViewById(R.id.waite);
        vide=findViewById(R.id.vid);
        context=this;
        sheet=findViewById(R.id.sheet);
        sheet.setVisibility(View.GONE);
        ImageView back=findViewById(R.id.back);
        ImageView mort=findViewById(R.id.ic_mort);
        mort.setVisibility(GONE);
        String extra=getIntent().getStringExtra("titre");
        tx.setText(extra.toUpperCase());
        text.setText(extra+" s'affichent ici");
        editTextSearch=findViewById(R.id.search_bar);
        clear=findViewById(R.id.clear_search_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        String js=MonFichier.lire(context,"beneficiaries");

        if(!js.isEmpty()){
            try {
                MonFichier.ecrire(context,"beneficiaries","");
                //List<Object> list=Ut.listFromJs(js);
                //displayBeneficiaires(list);
            } catch (Exception e) {
                MonFichier.ecrire(context,"beneficiaries","");
            }
        }else {
            nodata.setVisibility(VISIBLE);
        }
        js=MonFichier.lire(context,"beneficiaries_template");
        if(!js.isEmpty()){
           try {
               typeAcounts=new BeneficiaryTemplate().fromJs(js).getAccountTypeOptions();
           }catch (Exception e){
               MonFichier.ecrire(context,"beneficiaries_template","");
           }
        }
        if(Inscription.body!=null){
            getBeneficiareTemplate();
            getBeneficiare();
            fab=findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   showContact();
                }
            });
        }

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(recyclierViewCp!=null){
                    String search=s.toString();
                    if(search.isEmpty()){
                        clear.setVisibility(INVISIBLE);
                        if(listBeneficiaires!=null){
                            recyclierViewCp.updateList(listBeneficiaires);
                        }
                    }else {
                        clear.setVisibility(VISIBLE);
                        recyclierViewCp.updateList(filter(search));
                    }
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSearch.setText("");
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private String getInitiale(String nom){
        String s="";
       if(!(nom.replace(" ","")).isEmpty()){
           String[] t=nom.split(" ");
           if(t.length!=0){
             s=t[0].substring(0,1).toUpperCase();
           }
           if(t.length>1){
               s=s+(t[t.length-1].charAt(0)+"").toUpperCase();
           }
       }
       return s;
    }

    void setText(Object object, AdapterViewHolder holder, int k) {
        TextView title = holder.title, second = holder.secondre,
                symbole = holder.symbole, secondre2 = holder.secondre2, date = holder.date, value = holder.textePourcentage;
        Object productN_ob = Ut.getValue(object, "accountNumber");
        Object accountType = Ut.getValue(object, "accountType:value");
        //Object loanBalance_ob = Ut.getValue(object, "accountBalance");
        Object clientName = Ut.getValue(object, "clientName");

        //Dialogue.neutreDialog(Ut.js(object),"vc",context).show();

        View view = holder.view;
        ImageView plus = view.findViewById(R.id.plus),
                mort = view.findViewById(R.id.menu);

        if (productN_ob != null) {
            String productName = productN_ob.toString();
            second.setText(productName);
        }
        if (clientName != null) {
            title.setText(clientName.toString());
            symbole.setText(getInitiale(clientName.toString()));
            if(symbole.getText().toString().length()>1){
               // symbole.setTextSize(12);
            }
        }
        if(accountType!=null){
            secondre2.setVisibility(VISIBLE);
            secondre2.setText(accountType.toString());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj=object;

            }
        });

    }

     public static List<Object> listBeneficiaires;
    // 3. La méthode getBeneficiare() dans votre Activity/Repository
    void getBeneficiare() {
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.user.getUsername();
        String password = Inscription.body.getPassword();

        // 2. Obtenez l'instance de RetrofitClient (configuré pour Basic Auth en ctor)
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        String authHeader = "Basic " + okhttp3.Credentials.basic(username, password);
        String tenant     = "default";  // tenantIdentifier

        Call<List<Beneficiary>> call = api.getBeneficiaries(authHeader, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<List<Beneficiary>>() {
            @Override
            public void onResponse(Call<List<Beneficiary>> call,
                                   Response<List<Beneficiary>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Beneficiary> list = response.body();
                    if(list.isEmpty()){
                        list=new ArrayList<>();
                        list.add(Beneficiary.generate());
                        list.add(Beneficiary.generate());
                        list.add(Beneficiary.generate());
                    }
                    if(list.isEmpty()){
                        nodata.setVisibility(VISIBLE);
                    }else {
                        nodata.setVisibility(GONE);
                        displayBeneficiaires(list);
                        // Par ex., écrire la liste en JSON dans un fichier ou l'afficher
                       // String js = Ut.listJs(list);
                       // MonFichier.ecrire(context, "beneficiaries", js);
                        //Log.d("Beneficiaires", json);
                    }
                } else {
                    // Erreur côté serveur ou parsing
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog(
                                        response.message() + " " + response.errorBody(),
                                        "null",
                                        context
                                ).show();
                            });
                        }
                    }

                }
                waite.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<List<Beneficiary>> call, Throwable t) {
                // Problème réseau ou exception
                ;
                waite.setVisibility(GONE);
                nodata.setVisibility(GONE);
                List<Beneficiary> list = new ArrayList<>();
                if(list.isEmpty()){
                    list=new ArrayList<>();
                    list.add(Beneficiary.generate());
                    list.add(Beneficiary.generate());
                    list.add(Beneficiary.generate());
                }
                displayBeneficiaires(list);
            }
        });
    }
    RecyclierViewCp recyclierViewCp;
    void displayBeneficiaires(Object object){
        List<Object> list= (List<Object>) object;
        listBeneficiaires=list;
         recyclierViewCp = new RecyclierViewCp(context, R.layout.card_image_horiz_row, list, (h, o, i) -> {
            setText(o, h, 1);
        }).setNumberItems(1).setBackground(R.color.white);

        recyclierViewCp.setOnSwipeRight((p,v)->{

        });

        recyclierViewCp.view(recyclerView);
    }


    // 2. Implémentez la méthode dans votre Activity/Repository
    void getBeneficiareTemplate() {
        // 1. Récupérez vos identifiants
        String username = Inscription.user.getUsername();
        String password = Inscription.body.getPassword();

        // 2. Instanciez RetrofitClient (préconfiguré avec Basic Auth dans le constructor)
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l’appel
        String authHeader = "Basic " + okhttp3.Credentials.basic(username, password);
        String tenant     = "default";

        Call<BeneficiaryTemplate> call = api.getBeneficiariesTemplate(authHeader, tenant);

        // 4. Exécutez l’appel asynchrone
        call.enqueue(new Callback<BeneficiaryTemplate>() {
            @Override
            public void onResponse(Call<BeneficiaryTemplate> call, Response<BeneficiaryTemplate> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BeneficiaryTemplate template = response.body();
                    typeAcounts=template.getAccountTypeOptions();
                    // Exemple : stocker le JSON brut dans un fichier
                    MonFichier.ecrire(context, "beneficiaries_template", template.js());
                    Log.d("Template", template.toString());

                } else {
                    // Erreur serveur ou JSON malformé
                    String msg = response.message();
                    String err = response.errorBody() != null ? response.errorBody().toString() : "";
                    Dialogue.neutreDialog(msg + " " + err, "null", context).show();
                }
            }

            @Override
            public void onFailure(Call<BeneficiaryTemplate> call, Throwable t) {
                // Problème réseau ou exception
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            Dialogue.neutreDialog(t.toString(), "", context).show();
                        });
                    }
                }

            }
        });
    }

    void showContact(){
        sheet.setVisibility(View.VISIBLE);
        sheet.removeAllViews();
        SheetCp sheetCp=new SheetCp(context)
                .setTitle("Ajouter un bénéficiaire".toUpperCase());
        View vv=sheetCp.view();
        LinearLayout content=vv.findViewById(R.id.content);
        ImageView close=vv.findViewById(R.id.close);
        sheet.addView(vv);
        View tm= Ut.getView(context,R.layout.layout_select_sheet);
        ImageView editer=tm.findViewById(R.id.editer);
        ImageView qrCode=tm.findViewById(R.id.qr_code);
        ImageView balayer=tm.findViewById(R.id.balayer);

        content.addView(tm);

        vide.setVisibility(View.VISIBLE);

        editer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
                editeObject=new BeneficiaryBinder().editeObject();
                if (editeObject != null) {
                    if(typeAcounts!=null){
                        List<Attribut> attributs=editeObject.getAttribute();
                        attributs.get(3).setValuess(typeAcounts.stream().map(x->x).collect(Collectors.toList()));
                    }
                    editeObject.setObject(new Beneficiary());
                    startActivity(new Intent(context, AddBeneciaireActivity.class)
                            .putExtra("object", editeObject));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
                startActivity(new Intent(context, ViewQrCodeReadActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        balayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
                startActivity(new Intent(context, ScanQrCodeActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
            }
        });
        sheet.setAnimation(Anim.getAnimeBH(context));
    }

    private  List<Object> filter(String searche) {
      List<Object> list=new ArrayList<>();
      if(listBeneficiaires!=null){
            for (Object object:listBeneficiaires){
                boolean ok=false;
                Object productN_ob = Ut.getValue(object, "accountNumber");
                Object accountType = Ut.getValue(object, "accountType:value");
                Object clientName = Ut.getValue(object, "clientName");
                if (productN_ob != null) {
                    String productName = productN_ob.toString();
                    if((productName.toLowerCase()).contains(searche.toLowerCase())){
                        list.add(object);
                        ok=true;
                    }
                }
                if (clientName != null&&!ok) {
                    String productName = clientName.toString();
                    if((productName.toLowerCase()).contains(searche.toLowerCase())){
                        list.add(object);
                        ok=true;
                    }
                }
                if(accountType!=null&&!ok){
                    String productName = accountType.toString();
                    if((productName.toLowerCase()).contains(searche.toLowerCase())){
                        list.add(object);
                       // ok=true;
                    }
                }
            }
        }

       return list;

    }


}