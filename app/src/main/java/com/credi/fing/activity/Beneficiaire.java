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
import android.widget.Toast;

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
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.adapters.generiqueAdapter.AdapterViewHolder;
import com.credi.fing.publics.composant.NetworkCp;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Beneficiaire extends AppCompatActivity {

    LinearLayout nodata,waite,sheet,network;
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
        network=findViewById(R.id.network);
        context=this;
        sheet=findViewById(R.id.sheet);
        sheet.setVisibility(View.GONE);
        //ImageView back=findViewById(R.id.back);
        //ImageView mort=findViewById(R.id.ic_mort);
       // mort.setVisibility(GONE);
        String extra=getIntent().getStringExtra("titre");
        tx.setText(extra.toUpperCase());
        text.setText(extra+" s'affichent ici");
        editTextSearch=findViewById(R.id.search_bar);
        clear=findViewById(R.id.clear_search_icon);
        vide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        if(getIntent().hasExtra("add")){
            Ut.sleep(1000,(v,k)->{
                showContact();
            },null);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String js=MonFichier.lire(context,"refresh");
        if(!js.isEmpty()&&js.equals("ok")){
            getBeneficiare();
        }
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

    int[] colos={R.color.blue,R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark
    ,R.color.purple_500,R.color.purple_700};
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
            int j=k%6;
            Ut.setBackgroundTint(context,symbole,colos[j]);
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
    void showPb(){
        waite.setVisibility(View.VISIBLE);
        vide.setVisibility(View.VISIBLE);
    }
    void hidePb(){
        waite.setVisibility(View.GONE);
        vide.setVisibility(View.GONE);
    }
    void getBeneficiare() {
        showPb();
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
                    nodata.setVisibility(GONE);
                    displayBeneficiaires(list);
                } else {
                    String errorContent;
                    try {
                        // Lit le corps de la réponse d’erreur en String
                        errorContent = response.errorBody() != null
                                ? response.errorBody().string()
                                : "Corps de l’erreur vide";
                    } catch (IOException e) {
                        // En cas de problème de lecture
                        e.printStackTrace();
                        errorContent = "Impossible de lire le contenu de l’erreur";
                    }
                    if(errorContent.contains("{")){
                        try {
                            ApiErrorResponse apiErrorResponse=
                                    new ApiErrorResponse().fromJs(errorContent);
                            errorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                        }catch (Exception e){

                        }
                    }
                    int httpCode = response.code();
                    erreurTechnique(errorContent,httpCode);
                }
                hidePb();
            }

            @Override
            public void onFailure(Call<List<Beneficiary>> call, Throwable t) {
                hidePb();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            int pseudoCode = S.mapThrowableToCode(t);
                            erreurTechnique(titre[0],pseudoCode);
                        });
                    }
                }
            }
        });
    }
    RecyclierViewCp recyclierViewCp;

    Object objetCourant;
    int indexCourant;
    List<Object> list;
    void displayBeneficiaires(Object object){
        list= (List<Object>) object;
        //list.addAll(list);list.addAll(list);
        listBeneficiaires=list;
         recyclierViewCp = new RecyclierViewCp(context, R.layout.card_image_horiz_row, list, (h, o, i) -> {
            setText(o, h, i);
        }).setNumberItems(1).setBackground(R.color.white);

        recyclierViewCp.setOnSwipeRight((p,v)->{
         if(p<list.size()&&p>=0){
             objetCourant=list.get(p);
             indexCourant=p;
             recyclierViewCp.remove(p);
             list=recyclierViewCp.getObjects();
             showContact(0);
         }
        });
        recyclierViewCp.setOnSwipeLeft((int p, View v) ->{
            if(p<list.size()&&p>=0){
                objetCourant=list.get(p);
                indexCourant=p;
                recyclierViewCp.remove(p);
                list=recyclierViewCp.getObjects();
                showContact(0);
            }
        });
        recyclierViewCp.setOnLongClick(( o,i) ->{
            objetCourant=o;
            indexCourant=i;
            recyclierViewCp.remove(i);
             showContact(1);
        });

        recyclierViewCp.view(recyclerView);
        recyclierViewCp.fixedScrol(fab);
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
                    String errorContent;
                    try {
                        // Lit le corps de la réponse d’erreur en String
                        errorContent = response.errorBody() != null
                                ? response.errorBody().string()
                                : "Corps de l’erreur vide";
                    } catch (IOException e) {
                        // En cas de problème de lecture
                        e.printStackTrace();
                        errorContent = "Impossible de lire le contenu de l’erreur";
                    }
                    if(errorContent.contains("{")){
                        try {
                            ApiErrorResponse apiErrorResponse=
                                    new ApiErrorResponse().fromJs(errorContent);
                            errorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                        }catch (Exception e){

                        }
                    }

                    int httpCode = response.code();
                    erreurTechnique(errorContent,httpCode);
                }
            }

            @Override
            public void onFailure(Call<BeneficiaryTemplate> call, Throwable t) {
                hidePb();
                // Problème réseau ou exception
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            int pseudoCode = S.mapThrowableToCode(t);
                            erreurTechnique(titre[0],pseudoCode);
                        });
                    }
                }

            }
        });
    }

    void deleteBeneFiciaire(long beneficiaryId){
        String tenant = "default";
        showPb();
        String username = Inscription.user.getUsername();
        String password = Inscription.body.getPassword();

        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        Call<Void> call = api.deleteBeneficiary(beneficiaryId, tenant);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // suppression OK (204 No Content attendu)
                    Toast.makeText(context,
                            "Bénéficiaire supprimé avec succès",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String errorContent;
                    try {
                        // Lit le corps de la réponse d’erreur en String
                        errorContent = response.errorBody() != null&&!response.errorBody().toString().isEmpty()
                                ? response.errorBody().string()
                                : "Corps de l’erreur vide";
                    } catch (IOException e) {
                        S.toast(context,"echec writing");
                        // En cas de problème de lecture
                        e.printStackTrace();
                        errorContent = "Impossible de lire le contenu de l’erreur";
                    }
                    if(errorContent.contains("{")){
                        try {
                            ApiErrorResponse apiErrorResponse=
                                    new ApiErrorResponse().fromJs(errorContent);
                            errorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                        }catch (Exception e){

                        }
                    }
                    System.out.println(" aff: "+errorContent);
                   // Dialogue.neutreDialog(errorContent,"",context).show();
                    int httpCode = response.code();
                    erreurTechnique(errorContent,httpCode);
                    recyclierViewCp.inserer(indexCourant,objetCourant);
                    list=recyclierViewCp.getObjects();
                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                hidePb();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            int pseudoCode = S.mapThrowableToCode(t);
                            erreurTechnique(titre[0],pseudoCode);
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

    void showContact(int action){
        sheet.setVisibility(View.VISIBLE);
        sheet.removeAllViews();
        SheetCp sheetCp=new SheetCp(context)
                .setTitle(action==0?"Suppression".toUpperCase():"Editez ou supprimez".toUpperCase());
        View vv=sheetCp.view();
        LinearLayout content=vv.findViewById(R.id.content);
        ImageView close=vv.findViewById(R.id.close);
        sheet.addView(vv);
        View tm= Ut.getView(context,R.layout.delete_layout);
        MaterialButton button=tm.findViewById(R.id.outlinedButton);
        button.setText("Supprimer");
        MaterialButton supprimer=tm.findViewById(R.id.ajouter);
        MaterialButton editer=tm.findViewById(R.id.saves);
         supprimer.setText("Supprimer");
        LinearLayout ledt=tm.findViewById(R.id.l_edite);
        LinearLayout ldelete=tm.findViewById(R.id.l_delete);

        if(action==0){
            ldelete.setVisibility(VISIBLE);
            ledt.setVisibility(GONE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheet.setVisibility(View.GONE);
                    vide.setVisibility(View.GONE);
                    Object ids=Ut.getValue(objetCourant,"id");
                    if(ids!=null){
                        deleteBeneFiciaire(Long.parseLong(ids.toString()));
                    }
                }
            });
        }else {
            ldelete.setVisibility(GONE);
            ledt.setVisibility(VISIBLE);
            supprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheet.setVisibility(View.GONE);
                    vide.setVisibility(View.GONE);
                    Object ids=Ut.getValue(objetCourant,"id");
                    if(ids!=null){
                        deleteBeneFiciaire(Long.parseLong(ids.toString()));
                    }
                }
            });
            editer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheet.setVisibility(View.GONE);
                    vide.setVisibility(View.GONE);
                    Object ids=Ut.getValue(objetCourant,"id");
                    if(ids!=null){
                        editeObject=new BeneficiaryBinder().editeObject();
                        if (editeObject != null) {
                            if(typeAcounts!=null){
                                List<Attribut> attributs=editeObject.getAttribute();
                                attributs.get(3).setValuess(typeAcounts.stream().map(x->x).collect(Collectors.toList()));
                            }
                            editeObject.setObject(objetCourant);
                            startActivity(new Intent(context, AddBeneciaireActivity.class)
                                    .putExtra("object", editeObject));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }

                    }
                }
            });
        }


        content.addView(tm);
        vide.setVisibility(View.VISIBLE);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
                recyclierViewCp.inserer(indexCourant,objetCourant);
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

    private void showNetWork(String msg,int code){
        Dialogue.dialog(msg,code,context).show();
    }

    private void erreurTechnique(String errorContent,int code){
        // Affiche le message d’erreur et le code HTTP

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                String finalErrorContent = errorContent;
                if(finalErrorContent.contains("{")){
                    try {
                        ApiErrorResponse apiErrorResponse=
                                new ApiErrorResponse().fromJs(finalErrorContent);
                        finalErrorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                    }catch (Exception e){

                    }
                }
                String finalErrorContent1 = finalErrorContent;
                activity.runOnUiThread(() -> {
                    showNetWork(finalErrorContent1,code);
                });
            }
        }
    }


}