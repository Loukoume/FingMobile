package com.credi.fing;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.activity.Inscription;
import com.credi.fing.entity.Client;
import com.credi.fing.entity.SavingsAccount;
import com.credi.fing.pojo.transaction.Transaction;
import com.credi.fing.pojo.transaction.TransactionsResponse;
import com.credi.fing.publics.adapters.generiqueAdapter.AdapterViewHolder;
import com.credi.fing.publics.composant.RecyclierViewCp;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsActivity extends AppCompatActivity {
    SavingsAccount compte;
    TextView accountNumber,soldeDisponible,text_close,
    texpand,type_compte;
    LinearLayout lexpand,balanceSection,recycler_lineair;
    Context context;
    RecyclierViewCp recyclierViewCp;
    RecyclerView recyclerView;
    List<Transaction> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        if(getIntent().hasExtra("compte")){
            context=this;
            String js=getIntent().getStringExtra("compte");
            compte= (SavingsAccount) Ut.fromJs(js,SavingsAccount.class);
            soldeDisponible=findViewById(R.id.soldeDisponible);
            accountNumber=findViewById(R.id.accountNumber);
            lexpand=findViewById(R.id.lexpand);
            texpand=findViewById(R.id.text_expand);
            type_compte=findViewById(R.id.type_compte);
            text_close=findViewById(R.id.text_close);
            balanceSection=findViewById(R.id.balanceSection);
            recycler_lineair=findViewById(R.id.recycler_lineair);
            recyclerView=findViewById(R.id.transactionList);
            vide=findViewById(R.id.vide);
            pb=findViewById(R.id.pb);
            accountNumber.setText("Compte N° "+compte.getAccountNo());
            soldeDisponible.setText(Html.fromHtml(Ut.formatMontant(compte.getAccountBalance().doubleValue())+" <sup>XOF</sup>"));
            type_compte.setText(compte.getAccountType().getValue());
            lexpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(balanceSection.getVisibility()==View.VISIBLE){
                        balanceSection.setVisibility(View.GONE);
                        text_close.setVisibility(View.VISIBLE);
                        texpand.setVisibility(View.GONE);
                    }else {
                        balanceSection.setVisibility(View.VISIBLE);
                        text_close.setVisibility(View.GONE);
                        texpand.setVisibility(View.VISIBLE);
                    }
                }
            });


         getClientAcount();
        }
    }

    private void displayData(){
       /* list=List.of(new Transaction("Intérêt postés","Depot","187"),
                new Transaction("Dépôt","Depot","190000"),
                new Transaction("Retrait","Retrait","24500"),
                new Transaction("Intérêt postés","Depot","187"),
                new Transaction("Dépôt","Depot","190000"),
                new Transaction("Retrait","Retrait","24500"));*/
        List<Object> objects = new ArrayList<>(list);
        recyclierViewCp=new RecyclierViewCp(context,R.layout.item_transaction,
                objects,(h,o,i)->{
             setText(h,o);
        }).setRecyclerView(recyclerView);
        recyclierViewCp.view();
    }
    private void setText(AdapterViewHolder h,Object o){
       Transaction t= (Transaction) o;
       View view=h.view;
       View statusStripe=view.findViewById(R.id.statusStripe);
        TextView montantText=view.findViewById(R.id.montantText);
        TextView labelText=view.findViewById(R.id.labelText);
       /* if(t.getType().equals("Depot")){
           statusStripe.setBackgroundColor(Ut.getColor(context,R.color.green));
            montantText.setTextColor(Ut.getColor(context,R.color.green));
       }else {
           statusStripe.setBackgroundColor(Ut.getColor(context,R.color.red));
            montantText.setTextColor(Ut.getColor(context,R.color.red));
       }*/
        //montantText.setText(Ut.formatMontant(Double.parseDouble(t.getMontant())));
        //labelText.setText(t.getMotif());
    }
    ProgressBar pb;
    View vide;
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

        Call<TransactionsResponse> call = api.getSavingsTransactions("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                clientId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<TransactionsResponse>() {
            @Override
            public void onResponse(Call<TransactionsResponse> call, Response<TransactionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TransactionsResponse  transactionsResponse = response.body();
                    MonFichier.ecrire(context,"transactions_response_js",transactionsResponse.js());

                    list=transactionsResponse.getPageItems();
                    displayData();
                } else {
                    //client= (Client) Ut.fromJs(Json.json,Client.class);
                    //loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                    // savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                    // setComptesValues();
                    // Erreur côté serveur ou JSON non parsable

                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog(response.message()+" "+response.code(),response.errorBody()+"",context).show();
                            });
                        }
                    }

                }
                hidePb();
            }

            @Override
            public void onFailure(Call<TransactionsResponse> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            Dialogue.neutreDialog(t.getMessage()+"","Echec",context).show();
                        });
                    }
                }

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
}