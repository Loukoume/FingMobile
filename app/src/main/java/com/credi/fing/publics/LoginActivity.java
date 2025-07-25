package com.credi.fing.publics;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.credi.fing.MainActivity;
import com.credi.fing.R;
import com.credi.fing.publics.service.ApiClient;
import com.credi.fing.publics.service.compteService.CompteApiService;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // private LoginViewModel loginViewModel;
    // private ActivityLoginBinding binding;
    Context context;
    public static Compte compte;
    private TextView tv_mobile_continue, ok;
    TextView new_compte;
    private EditText password;
    private EditText loginEdite;
    public static int x, y;
    public static String code;
    public static boolean admin;
    Switch souv;
    RelativeLayout Seconnecter;
    TextView newcompte, delete;
    TextView newCompte;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        DisplayMetrics metrics = new DisplayMetrics();
        context = this;
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        x = metrics.widthPixels;
        y = metrics.heightPixels;
        y = 2 * y / 3;
    }

    @Override
    protected void onResume() {
        super.onResume();
       // souiM();
    }

    private void clic() {
        String mobile = password.getText().toString().trim();
        String users = loginEdite.getText().toString().trim();
        Compte login = new Compte();
        login.setLogin(users);
        login.setPassword(mobile);

        if (mobile.isEmpty()) {
            password.setError("Entrer un login valide");
            password.requestFocus();
            return;
        }
        if (users.isEmpty()) {
            loginEdite.setError("Entrer un mot de pass valide");
            password.requestFocus();
            return;
        }
        if (mobile.equals("admin")) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("mobile", mobile);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            admin = true;
            finish();
        } else {
            getCompte(login);
        }
    }    //public static Vendeur vendeur_au;

    ProgressBar pb;

    private void clickWhat() {
        boolean oui = isInstall("com.whatsapp");
        if (oui) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+22891860857&text=Contacter l'équite yaayi"));
            startActivity(intent);
        } else S.toast(this, "Veuillez installer l'application whatsapp");
    }

    private boolean isInstall(String url) {
        PackageManager packageManager = getPackageManager();
        boolean oui;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            oui = true;
        } catch (Exception e) {
            oui = false;
        }
        return oui;
    }

    public void getCompte(final Compte compte) {
        if (pb == null) pb = findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        CompteApiService apiService = ApiClient.getApiClient().create(CompteApiService.class);
        Call<Compte> call = apiService.login(compte);
        call.enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                pb.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body() != null) {
                        LoginActivity.compte= response.body();;
                        //new LesConnectes().add(context, response.body());
                        MonFichier.ecrire(context, "compte", response.body().js());
                        switch (response.body().getProfile().getLibelle()) {
                            case "admin":
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("compte", response.body());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                finish();
                                break;
                            default:
                                intent = new Intent(context, MainActivity.class);
                                intent.putExtra("compte", response.body());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                finish();
                                break;
                        }
                    } else {
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
                            if (!activity.isFinishing() && !activity.isDestroyed()) {
                                activity.runOnUiThread(() -> {
                                    Dialogue.neutreDialog("Echèc d'autentification", "Alerte", context).show();
                                });
                            }
                        }

                    }

                } else {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog("Problème technique", "Alerte", context).show();
                            });
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                pb.setVisibility(View.GONE);
                String st = t + "";
                if (st.contains("java.io.EOFException: End of")) {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog("Mot de passe ou Numéro erroné "+st, "Alerte", context).show();
                            });
                        }
                    }

                } else
                {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog(t + "", "Alerte", context).show();
                            });
                        }
                    }

                }
            }
        });
    }

   /* private void souiM() {
        final List<Compte> l = new LesConnectes().comptes(context);
        if (l.size() == 1) {
            compte = l.get(0);
            switch (compte.getProfile().getLibelle()) {
                case "admin":
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("compte", compte);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    finish();
                    break;
                default:
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra("compte", compte);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    finish();
                    break;
            }

        } else if (l.size() > 1) {

            String m[] = new String[l.size() + 1];
            for (int i = 0; i < l.size(); i++) {
                m[i] = l.get(i).getProfile().getLibelle();
                new LesConnectes().remove(context, l.get(i));
            }
        } else {
            return;
        }
    }*/
}
