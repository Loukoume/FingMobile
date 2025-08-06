package com.credi.fing;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.activity.Inscription;
import com.credi.fing.entity.Client;
import com.credi.fing.entity.SavingsAccount;
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
import com.credi.fing.pojo.transaction.Transaction;
import com.credi.fing.pojo.transaction.TransactionType;
import com.credi.fing.pojo.transaction.TransactionsResponse;
import com.credi.fing.publics.adapters.generiqueAdapter.AdapterViewHolder;
import com.credi.fing.publics.carousel.CarouselItem;
import com.credi.fing.publics.composant.NetworkCp;
import com.credi.fing.publics.composant.RecyclierViewCp;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Anim;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsActivity extends AppCompatActivity {
    SavingsAccount compte;
    TextView accountNumber,soldeDisponible,text_close,
    texpand,type_compte,libelle;
    LinearLayout lexpand,balanceSection,balanceContent,recycler_lineair, network;
    Context context;
    RecyclierViewCp recyclierViewCp;
    RecyclerView recyclerView;
    List<Transaction> list;
    View view1,view2;

    //List<View> views;
    boolean close=false;
    LinearLayout dotsLayout;
    public static List<Object> savingsAccounts;
    public static List<Object> loanAccounts;
    Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        if(getIntent().hasExtra("compte")){
            context=this;
            String js=getIntent().getStringExtra("compte");
            compte= (SavingsAccount) Ut.fromJs(js,SavingsAccount.class);
            savingsAccounts=MainActivity.savingsAccounts;
            lexpand=findViewById(R.id.lexpand);
            network=findViewById(R.id.network);
            texpand=findViewById(R.id.text_expand);
            balanceContent=findViewById(R.id.balance_contente);

            dotsLayout   = findViewById(R.id.dotsLayout);
            text_close=findViewById(R.id.text_close);
            balanceSection=findViewById(R.id.balanceSection);
            recycler_lineair=findViewById(R.id.recycler_lineair);
            recyclerView=findViewById(R.id.transactionList);
            vide=findViewById(R.id.vide);
            view1=findViewById(R.id.view1);
            view2=findViewById(R.id.view2);
            libelle=findViewById(R.id.libelle);
            pb=findViewById(R.id.pb);
           // views=List.of(accountNumber,type_compte,view1,libelle,soldeDisponible,view2);

            lexpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!close){
                        text_close.setVisibility(View.VISIBLE);
                        texpand.setVisibility(View.GONE);
                        balanceContent.setVisibility(View.GONE);
                    }else {
                        text_close.setVisibility(View.GONE);
                        texpand.setVisibility(View.VISIBLE);
                        balanceContent.setVisibility(View.VISIBLE);
                    }
                    close=!close;
                }
            });
            z=savingsAccounts.size();
            index=defaultIndex();

            listComptes();
            //getAcountTransacgtion(index);
            vide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
           /* Ut.swip(recycler_lineair,(v,k)->{
                switch (k){
                    case 0:
                        text_close.setVisibility(View.VISIBLE);
                        texpand.setVisibility(View.GONE);
                        Ut.deplier(views);
                        Ut.sleep(50*views.size(),(vs,j)->{
                            // S.toast(context,(50*views.size())+" milisecondes");
                            balanceContent.setVisibility(View.GONE);
                        },balanceSection);
                        close=!close;
                        break;
                    case 1:
                        break;
                    case 2:

                        break;
                    case 3:
                        text_close.setVisibility(View.GONE);
                        texpand.setVisibility(View.VISIBLE);
                        Ut.sleep(0*views.size(),(vs,j)->{
                            // S.toast(context,(0*views.size())+" milisecondes");
                            balanceContent.setVisibility(View.VISIBLE);
                        },balanceSection);
                        Ut.plier(views);
                        close=!close;
                        break;
                }

            });
            Ut.swip(balanceSection,(v,k)->{
                switch (k){
                    case 0:
                        text_close.setVisibility(View.VISIBLE);
                        texpand.setVisibility(View.GONE);
                        Ut.deplier(views);
                        Ut.sleep(50*views.size(),(vs,j)->{
                            // S.toast(context,(50*views.size())+" milisecondes");
                            balanceContent.setVisibility(View.GONE);
                        },balanceSection);
                        close=!close;
                        break;
                    case 2:
                        if(index<z-1){
                            index++;
                            String s=Ut.js(savingsAccounts.get(index));
                            compte= (SavingsAccount) Ut.fromJs(s,SavingsAccount.class);
                            accountNumber.setText("Compte N° "+compte.getAccountNo());
                            soldeDisponible.setText(Html.fromHtml(Ut.formatMontant(compte.getAccountBalance().doubleValue())+" <sup>XOF</sup>"));
                            type_compte.setText(compte.getAccountType().getValue());
                            setCurrentDot(index);
                            balanceSection.setAnimation(Anim.getAnimeD(context));
                            getAcountTransacgtion();
                        }
                        break;
                    case 1:
                       if(index>0){
                           index--;
                           String s=Ut.js(savingsAccounts.get(index));
                           compte= (SavingsAccount) Ut.fromJs(s,SavingsAccount.class);
                           accountNumber.setText("Compte N° "+compte.getAccountNo());
                           soldeDisponible.setText(Html.fromHtml(Ut.formatMontant(compte.getAccountBalance().doubleValue())+" <sup>XOF</sup>"));
                           type_compte.setText(compte.getAccountType().getValue());
                           setCurrentDot(index);
                           balanceSection.setAnimation(Anim.getAnimeGD(context));
                           getAcountTransacgtion();
                       }
                        break;
                    case 3:
                        text_close.setVisibility(View.GONE);
                        texpand.setVisibility(View.VISIBLE);
                        Ut.sleep(0*views.size(),(vs,j)->{
                            // S.toast(context,(0*views.size())+" milisecondes");
                            balanceContent.setVisibility(View.VISIBLE);
                        },balanceSection);
                        Ut.plier(views);
                        close=!close;
                        break;
                }

            });*/
            createDots(savingsAccounts.size());
            if(defaultIndex()!=-1){
                setCurrentDot(index);
            }
        }
    }


    int defaultIndex(){
        int k=0;
        if(savingsAccounts!=null&&compte!=null){
            for (Object o:savingsAccounts){
                String js=Ut.js(o);
                SavingsAccount cp= (SavingsAccount) Ut.fromJs(js,SavingsAccount.class);
                if(Objects.equals(compte.getAccountNo(),cp.getAccountNo())){
                    return k;
                }
                k++;
            }
            return -1;
        }
        return -1;
    }

    int z,index;

    // Crée les dots
    private TextView[] dots;
    private void createDots(int count) {
        dotsLayout.removeAllViews();
        dots = new TextView[count];
        for (int i = 0; i < count; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));  // •
            dots[i].setTextSize(40);
            dots[i].setIncludeFontPadding(false);
            dots[i].setTextColor(Color.LTGRAY);
            dotsLayout.addView(dots[i]);
        }
        // Sélectionne le premier
        if (dots.length > 0) dots[0].setTextColor(Color.DKGRAY);
    }

    // Met à jour la couleur du dot actif
    private void setCurrentDot(int position) {
        for (int i = 0; i < dots.length; i++) {
            dots[i].setTextColor(i == position ? Color.DKGRAY : Color.LTGRAY);
        }
    }
    private void displayData(){
        List<Object> objects = new ArrayList<>(list);
           if(recyclierViewCp==null){
               recyclierViewCp=new RecyclierViewCp(context,R.layout.item_transaction,
                       objects,(h,o,i)->{
                   setText(h,o);
               }).setRecyclerView(recyclerView);
               recyclierViewCp.view();
               recyclierViewCp.setOnClick((o,k)->{
                   // getAcountTransacgtion();
               });
           }else {
               recyclierViewCp.updateList(objects);
           }


    }
    private void setText(AdapterViewHolder h, Object o) {
        Transaction t = (Transaction) o;
        View view = h.view;

        ImageView iconType     = view.findViewById(R.id.iconType);
        View      statusStripe = view.findViewById(R.id.statusStripe);
        TextView  dateText     = view.findViewById(R.id.dateText);
        TextView  labelText    = view.findViewById(R.id.labelText);
        TextView  montantText  = view.findViewById(R.id.montantText);
        TextView  balanceText  = view.findViewById(R.id.balanceText);
        TextView acountNumber = view.findViewById(R.id.acountNumber);
        Context ctx = view.getContext();

        // 1) Déterminer dépôt / retrait / intérêts
        TransactionType type = t.getTransactionType();
        boolean isDeposit  = type.isDeposit();
        boolean isWithdraw = type.isWithdrawal();
        boolean isInterest = type.isInterestPosting();

        // 2) Couleur du stripe et du montant
        int color = Ut.getColor(ctx,
                isDeposit  ? R.color.green :
                        isWithdraw ? R.color.red   :
                                R.color.gray_dark);
        statusStripe.setBackgroundColor(color);
        montantText.setTextColor(color);

        // 3) Icône en fonction du type
        int iconRes = isDeposit  ? R.drawable.ic_deposit :
                isWithdraw ? R.drawable.ic_withdraw :
                        R.drawable.ic_interest;
        iconType.setImageResource(iconRes);
        iconType.setColorFilter(color);

        // 4) Montant formaté avec signe
        String sign = isDeposit ? "+ " : (isWithdraw ? "– " : "");
        String formattedAmount = Ut.formatMontant(t.getAmount());
        montantText.setText(sign + formattedAmount + " XOF");

        // 5) Libellé : préférence transferDescription, sinon valeur du type
        String label = (t.getTransfer() != null && t.getTransfer().getTransferDescription() != null)
                ? t.getTransfer().getTransferDescription()
                : type.getValue();
        labelText.setText(label);

        // 6) Date : [YYYY,M,D] → "dd/MM/yyyy"
        List<Integer> d = t.getDate();
        if (d != null && d.size() == 3) {
            String dd = String.format("%02d", d.get(2));
            String mm = String.format("%02d", d.get(1));
            String yy = String.valueOf(d.get(0));
            dateText.setText(dd + "/" + mm + "/" + yy);
        } else {
            dateText.setText("");
        }

        // 7) Solde courant
        String formattedBalance = Ut.formatMontant(t.getRunningBalance());
        balanceText.setText("Solde : " + formattedBalance + " XOF");
        acountNumber.setText("N° cpt: "+t.getAccountNo());
    }

    ProgressBar pb;
    View vide;
    void getAcountTransacgtion(int position){
        showVide();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();
        // String password = Inscription.user;

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        long acountId = compte.getIdServeur();                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        //System.out.println(" acountId "+acountId);

        Call<List<Transaction>> call = api.getSavingsTransactions("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                acountId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Transaction>  transactions = response.body();
                   // MonFichier.ecrire(context,"transactions_response_js",transactions.js());
                        setCurrentDot(position);
                    list=transactions;
                        System.out.println(list.size()+" listx = "+list);
                    displayData();

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

                        erreurTechnique(errorContent,"Erreur technique");

                }
                hideVide();
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                // Problème réseau ou exception
                hideVide();
                if (context_aux instanceof Activity) {
                    Activity activity = (Activity) context_aux;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            if(titre[0].contains("java.net")|| titre[0].contains("javax.net")
                                    || titre[0].contains("failed")|| titre[0].contains("Unable to resolve host"))
                            {
                                titre[0] ="Vérifier votre connexion internet et réessayer";
                                showNetWork("Problème de connexion",titre[0],
                                        R.drawable.wifi_100);
                            }else {
                                showNetWork("Erreur technique",t.getMessage(),
                                        R.drawable.erreur_tech_100);
                            }
                        });
                    }
                }

            }
        });
    }

    void showPb(){
        pb.setVisibility(VISIBLE);
        vide.setVisibility(VISIBLE);
    }
    void hidePb(){
        pb.setVisibility(GONE);
        vide.setVisibility(GONE);
    }

    private void showNetWork(String title,String msg,int icone){
        NetworkCp networkCp=new NetworkCp(context,network,(o, k)->{
            if(k==1){
                getAcountTransacgtion(index);
                network.setVisibility(GONE);
            }else {
                finish();
            }
        });
        networkCp.parametrer(title,msg,icone);
    }

    private void erreurTechnique(String errorContent,String title){
        // Affiche le message d’erreur et le code HTTP
         if(errorContent.contains("offline")||errorContent.contains("404")){
             errorContent="Service indisponible pour le moment, veuillez réessayer ultérieurement.";
         }
        if (context_aux instanceof Activity) {
            Activity activity = (Activity) context_aux;
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
                    showNetWork(title,finalErrorContent1,R.drawable.erreur_tech_100);
                });
            }
        }
    }


    View viewVide;
    ProgressBar prb;
    Context context_aux;
    private void listComptes(){
        RecyclierViewCp recyclierViewCp=new RecyclierViewCp(this,
                R.layout.compte_row,savingsAccounts,(h,o,i)->{
                bindView(h,o,i);
        }).setOrientation(RecyclerView.HORIZONTAL)
                .setNumberItems(1)
                .setBackground(R.color.colorSendre);
        balanceContent.addView(recyclierViewCp.view());
        recyclierViewCp.fixedScrol(null);
        recyclierViewCp.smoothScrollToPosition(index);
        /*View curentView=recyclierViewCp.getCurentView();
        viewVide=curentView.findViewById(R.id.vide);
        prb=curentView.findViewById(R.id.prb);
        context_aux=curentView.getContext();*/

        recyclierViewCp.setOnClickView((view,position)->{
          viewVide=view.findViewById(R.id.vide);
          prb=view.findViewById(R.id.prb);
          context_aux=view.getContext();
          index=position;
            String js=Ut.js(recyclierViewCp.getObjects().get(position));
            compte= (SavingsAccount) Ut.fromJs(js,SavingsAccount.class);
           // System.out.println(compte.getIdServeur()+" =x=x= "+compte.getAccountNo()+"  "+compte.getProductId());
          getAcountTransacgtion(position);
        });
    }

    void showVide(){
        viewVide.setVisibility(VISIBLE);
        prb.setVisibility(VISIBLE);
    }
    void hideVide(){
        viewVide.setVisibility(GONE);
        prb.setVisibility(GONE);
    }

    private void bindView(AdapterViewHolder h, Object o, int i) {
        String js=Ut.js(o);
        compte= (SavingsAccount) Ut.fromJs(js,SavingsAccount.class);
        soldeDisponible=h.view.findViewById(R.id.soldeDisponible);
        accountNumber=h.view.findViewById(R.id.accountNumber);
        type_compte=h.view.findViewById(R.id.type_compte);
        accountNumber.setText("Compte N° "+compte.getAccountNo());
        soldeDisponible.setText(Html.fromHtml(Ut.formatMontant(compte.getAccountBalance().doubleValue())+" <sup>XOF</sup>"));
        type_compte.setText(compte.getAccountType().getValue());
    }
}