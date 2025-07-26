package com.credi.fing.activity;

import static android.view.View.GONE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.credi.fing.MainActivity;
import com.credi.fing.R;
import com.credi.fing.entity.Beneficiary;
import com.credi.fing.entity.Client;
import com.credi.fing.entity.SavingsAccount;
import com.credi.fing.publics.composant.SheetCp;
import com.credi.fing.publics.service.impl.Anim;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;
import com.credi.fing.utils.Json;
import com.credi.fing.utils.QRCodeUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ViewQrCodeActivity extends AppCompatActivity {
    private ImageView ivQRCode,share;

    LinearLayout sheet;
    Context context;
    View vide;
    Bitmap qrBitmap;
    Beneficiary beneficiary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qr_code);
        ivQRCode = findViewById(R.id.iv_qrcode);
        share=findViewById(R.id.share);
        sheet=findViewById(R.id.sheet);
        vide=findViewById(R.id.vide);
        sheet.setVisibility(View.GONE);
        String dataToEncode = getIntent().getStringExtra("compte");
        context=this;
        boolean testPlayStor=Inscription.body==null||(Inscription.body.getPassword().equalsIgnoreCase("fingiciel")&&
                Inscription.body.getUsername().equalsIgnoreCase("fingiciel"));
        try {
            SavingsAccount account= (SavingsAccount) Ut.fromJs(dataToEncode, SavingsAccount.class);
            beneficiary=new Beneficiary();

            String js= MonFichier.lire(context,"displayName");
            if(js.isEmpty()&&testPlayStor){
                js= Json.displayNam;
            }
            if(!js.isEmpty()){
                //logLongIterative("=displayName_tag=>",js);
                //System.out.println("=displayName=> "+js);
                Client cl=new Client().fromJs(js);
                if(cl.getDisplayName()!=null&&!cl.getDisplayName().isEmpty()){
                    beneficiary.setClientName(cl.getDisplayName());
                    beneficiary.setOfficeName(cl.getOfficeName());
                }
            }
            beneficiary.setAccountType(account.getAccountType().typeOption());
            beneficiary.setAccountNumber(account.getAccountNo());


            // Génération et affichage du QR
             qrBitmap = QRCodeUtil.generateQRCode(beneficiary.js(), 512, 512);
            ivQRCode.setImageBitmap(qrBitmap);

            // Affichage des autres infos

        } catch (Exception e) {
            e.printStackTrace();
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showContact();
                sendQrViaWhatsApp();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(sheet.getVisibility()==View.VISIBLE){
            sheet.setVisibility(View.GONE);
            vide.setVisibility(View.GONE);
        }else
        {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    private void clickWhatsAppx(String phone) {
        // Packages WhatsApp
        final String WHATSAPP_BUSINESS = "com.whatsapp.w4b";
        final String WHATSAPP_STANDARD = "com.whatsapp";

        // Numéro et message à envoyer
        String text  = "whatsapp";

        String pkgToUse;
        if (QRCodeUtil.isAppInstalled(this, WHATSAPP_BUSINESS)) {
            pkgToUse = WHATSAPP_BUSINESS;
        } else if (QRCodeUtil.isAppInstalled(this, WHATSAPP_STANDARD)) {
            pkgToUse = WHATSAPP_STANDARD;
        } else {
            S.toast(this, "Veuillez installer WhatsApp ou WhatsApp Business");
            return;
        }

        // Construction de l'URI
        Uri uri = Uri.parse("https://api.whatsapp.com/send")
                .buildUpon()
                .appendQueryParameter("phone", phone)
                .appendQueryParameter("text", text)
                .build();

        // Intent explicite vers le package choisi
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(pkgToUse);
        startActivity(intent);
    }

    private void sendQrViaWhatsApp() {
        final String WHATSAPP_BUSINESS = "com.whatsapp.w4b";
        final String WHATSAPP_STANDARD = "com.whatsapp";
        final String text  = "Voici le QR Code :";

        // 2. Enregistrer en cache et récupérer l'URI
        Uri imageUri;
        try {
            imageUri = QRCodeUtil.saveBitmapToCache(this, qrBitmap, "qr_to_share");
        } catch (Exception e) {
            e.printStackTrace();
            S.toast(this, "Erreur sauvegarde image");
            return;
        }

        // 3. Choisir le package WhatsApp disponible
        String pkgToUse;
        if (QRCodeUtil.isAppInstalled(this, WHATSAPP_BUSINESS)) {
            pkgToUse = WHATSAPP_BUSINESS;
        } else if (QRCodeUtil.isAppInstalled(this, WHATSAPP_STANDARD)) {
            pkgToUse = WHATSAPP_STANDARD;
        } else {
            S.toast(this, "Veuillez installer WhatsApp ou WhatsApp Business");
            return;
        }

        // 4. Construire et lancer l’Intent de partage
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        //String jid = phone + "@s.whatsapp.net";
        //shareIntent.putExtra("jid", jid);
        shareIntent.setPackage(pkgToUse);
        // permission pour WhatsApp de lire l'URI
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(shareIntent);
        } catch (Exception e) {
            e.printStackTrace();
            S.toast(this, "Impossible d’ouvrir WhatsApp");
        }
    }


    void showContact(){
        sheet.setVisibility(View.VISIBLE);
        sheet.removeAllViews();
        SheetCp sheetCp=new SheetCp(context)
                .setTitle("Numéro whatsapp");
        View vv=sheetCp.view();
        LinearLayout content=vv.findViewById(R.id.content);
        sheet.addView(vv);
        View tm= Ut.getView(context,R.layout.input_mt);
       // ImageView im1=tm.findViewById(R.id.icone);
        ImageView close=vv.findViewById(R.id.close);
        TextInputEditText input=tm.findViewById(R.id.id);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        TextInputLayout inputLyout=tm.findViewById(R.id.textField);
        inputLyout.setHint("+33*****");
        content.addView(tm);

        View fz= Ut.getView(context,R.layout.bouton);
        MaterialButton ch=fz.findViewById(R.id.outlinedButton);
        content.addView(fz);
        vide.setVisibility(View.VISIBLE);
        ch.setText("Soumettre");
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pt=input.getText().toString().replace(" ","");
                if(!pt.isEmpty()){
                    if(pt.startsWith("+")){
                        sheet.setVisibility(View.GONE);
                        vide.setVisibility(View.GONE);
                        sendQrViaWhatsApp();
                    }else {
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
                            if (!activity.isFinishing() && !activity.isDestroyed()) {
                                activity.runOnUiThread(() -> {
                                    Dialogue.neutreDialog("Le numéro de téléphone doit inclure l'indicatif téléphonique du pays",
                                            "Recommendation",context).show();
                                    //Dialogue.neutreDialog(t.toString(), "", context).show();
                                });
                            }
                        }

                    }
                }else {
                  inputLyout.setError("Numéro obligatoire");
                }
              //  email();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.setVisibility(View.GONE);
                vide.setVisibility(View.GONE);
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  if(!s.toString().isEmpty()){
                     inputLyout.setError(null);
                  }
            }
        });
        sheet.setAnimation(Anim.getAnimeBH(context));
    }
}