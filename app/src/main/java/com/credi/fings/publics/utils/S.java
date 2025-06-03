package com.credi.fings.publics.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.otaliastudios.zoom.ZoomImageView;
import com.credi.fings.R;
import com.credi.fings.publics.service.CustomImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;

/**
 * Created by Loukoume on 02/04/2018.
 */

public class S {
    Primitive prb = new Primitive();
    public static final String pls_inf = "+∞", ms_inf = "-∞", rsn = "√", alpha = "α", beta = "β", inf = "∞", del = "∆", del_F = "b^2-4ac", pi = "π", x1_F = "(-b-" + rsn + "(" + del + "))/(2a)", x2_F = "(-b+" + rsn + "(" + del + "))/(2a)",
            inter = "\\cap", x1x2 = "-b/(2a)", inplq = "{\\Rightarrow}", equiv = "{\\iff}", astreris = "\\ast", etoil = "{\\star}", poin = "\\bullet", diff = "{\\neq}", in = "{\\in}", notin = "\\notin", all = "{\\forall}", gr = "\\mathbb", fs = "×",
            infr_eg = "\\leqslant", sup_eg = "\\geqslant", associ = "\\mapsto", es = "{\\;}", losang = "{\\diamondsuit}", phi = "∅", ph = "{\\emptyset}",
            der_poly_f = "a_{n}x^n+a_{n-1}x^{n-1}+...+a_{1}x+a_0", der_poly_r = "na_{n}x^{n-1}+(n-1)a_{n-1}x^{n-2}+...+a_{1}", de_nu_de = "\\left(u/(v)\\right)'=(u'v-v'u)/(v^2)", de_rcn = "(" + rsn + "(u))'=(u')/(2" + rsn + "(u))",
            de_fog = "(uov(x))'=v'u'(v(x))", de_pus = "(u^n(x))'=nu'u^{n-1}", de_fac = "(uv)'=u'v+v'u", de_mon = "(ax^n)'=nax^{n-1}", de_som = "(f_1+f_2+...+f_n)'=f_{1}^{'}+f_{2}^{'}+...+f_{n}^{'}", de_rcn_k = "(k" + rsn + "(u))'=(ku')/(2" + rsn + "(u))", deriv_ln = "(ln(u(x)))'=(u'(x))/(u(x))", de_som2 = "(f_1+f_2)'=f_{1}^{'}+f_{2}^{'}", de_som3 = "(f_1+f_2+f_3)'=f_{1}^{'}+f_{2}^{'}+f_{3}^{'}",
            a_sta = "\\frac{\\sum_{i\\to{0}}^N (x_i-\\bar {x})(y_i-\\bar {y})}{\\sum_{i\\to{0}}^N (x_i-\\bar {x})^2}", a_sta2 = "\\frac{\\sum_{i\\to{0}}^N (x_i-\\bar {x})(y_i-\\bar {y})}{\\sum_{i\\to{0}}^N (y_i-\\bar {y})^2}", b_sta = "{\\bar {y}}-a\\bar {x}", b_sta2 = "{\\bar {y}}-a'\\bar {x}", r = "\\frac{cov(X;Y)}{\\sqrt{V(X)V(Y)}}", r2 = "\\frac{{\\sigma}_{XY}}{{\\sigma}_{X}{\\sigma}_{Y}}", r3 = "\\displaystyle{ \\frac{\\sum_{i\\to{0}}^N (x_i-\\bar {x})(y_i-\\bar {y})}{\\sqrt{\\sum_{i\\to{0}}^N (x_i-\\bar {x})^2\\sum_{i\\to{0}}^N (y_i-\\bar {y})^2}}}",
            mx = "\\frac{\\sum {n_ix_i}}{\\sum {n_i}}", v1 = "\\frac{\\sum {n_i}(x_i-\\bar {x})^2}{\\sum {n_i}}", V2 = "\\frac{\\sum {n_i}(x_i)^2}{\\sum {n_i}}-{\\bar{x}}^2",
            m = "\\frac{n_1x_1+n_2x_2+n_3x_3+...+n_px_p}{n_1+n_2+n_3+...+n_p}", V = "\\frac{n_1(x_1-\\bar {x})^2+n_2(x_2-\\bar {x})^2+n_3(x_3-\\bar {x})^2+...+n_p(x_p-\\bar {x})^2}{n_1+n_2+n_3+...+n_p}", ro = "ρ", fa = "α", tta = "θ", gama = "γ", unio = "∪", iter = "∩", bc = "↲";
    public static final String s_un_a = "";

    public static String pt() {
        switch (randum(0, 3)) {
            case 0:
                return poin;
            case 1:
                return etoil;
            //case 2:return new Primitive().gras("-");

        }
        return losang;
    }

    public static String mj_som(String v) {
        return "\\sum_{i=0}^N " + v;
    }

    public static String Vx(String v) {
        return "\\frac{1}{N}\\sum{n_i" + v + "_i^2}-\\bar{" + v + "}^2";
    }

    public static String Vxy(String v) {
        return "\\frac{1}{N}\\sum{x_iy_i}-\\bar{x}\\bar{y}";
    }

    public static String r() {
        return "\\frac{" + Vxy("") + "}{\\sqrt{" + Vx("x") + Vx("y") + "}}";
    }

    public static String sm_ni(String v) {
        return "\\sum{" + v + "}";
    }

    public static String mj_myn(String v) {
        return "\\frac{1}{N}\\sum_{i\\to{0}}^N" + v;
    }

    public static String mj_myni(String v) {
        return "\\frac{1}{N}\\sum_{i\\to{0}}^N" + v;
    }

    public static int randum(int inf, int sup) {
        int i = inf + (int) (Math.random() * (sup - inf + 1));
        return i;
    }

    public static boolean binaire() {
        return randum(0, 1) == 0;
    }

    public static int randum_z(int inf, int sup) {
        int i = randum(inf, sup);
        while (i == 0) i = randum(inf, sup);
        return i;
    }

    public static int randum(int sup) {
        int i = (int) (Math.random() * sup);
        return i;
    }

    public static String n_un_ar(String nm, String v) {
        return nm + "_" + v + "=" + nm + "_p+(" + v + "-p)r";
    }

    public static String n_un_geo(String nm, String v) {
        return nm + "_" + v + "=(q^(" + v + "-p))" + nm + "_p";
    }

    public static String s_un_ar(String nm, String v) {
        return "S_" + v + "=(" + v + "-p+1)(2" + nm + "_p+(" + v + "-p)r)/(2)";
    }

    public static String s_un_geo(String nm, String v) {
        return "S_" + v + "=((q^(" + v + "-p+1)-1)/(q-1))" + nm + "_p";
    }

    public static String gr_it(String a) {
        return "\\mathscr{" + a + "}";
    }

    public static void toast(Context c, String s) {
        /*Toast toast = Toast.makeText(c, s, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 10, 100);
        toast.show();*/

        // Inflate the custom layout for the toast
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                ((Activity)c).findViewById(R.id.custom_toast_container));

// Récupérez les éléments de la mise en page et personnalisez-les si besoin
        ImageView imageView = layout.findViewById(R.id.toast_image);
        imageView.setImageResource(R.drawable.done_all_24); // Utilisez une autre image si besoin

        TextView textView = layout.findViewById(R.id.toast_text);
        textView.setText(s); // Personnalisez le texte

// Créez le Toast avec la mise en page personnalisée
        Toast toast = new Toast(c);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 10, 100);
        toast.show();

    }
    public static void toast(Context c, String s,int colors) {

        // Inflate the custom layout for the toast
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                ((Activity)c).findViewById(R.id.custom_toast_container));

// Récupérez les éléments de la mise en page et personnalisez-les si besoin
        ImageView imageView = layout.findViewById(R.id.toast_image);
        imageView.setImageResource(colors!=-1?R.drawable.done_all_24:R.drawable.ic_note_sent); // Utilisez une autre image si besoin

        TextView textView = layout.findViewById(R.id.toast_text);
        textView.setText(s); // Personnalisez le texte

        if(colors!=-1){
          layout.setBackgroundColor(colors);
        }

// Créez le Toast avec la mise en page personnalisée
        Toast toast = new Toast(c);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 10, 100);
        toast.show();

    }

    public static String nombre() {
        if (randum(1, 4) == 3) return rsn + "(" + randum(2, 3) + ")";
        return randum(1, 3) + "";
    }

    public static String nombrem() {
        if (randum(0, 4) == 3) return "m";
        return randum(1, 5) + "";
    }

    public static String filemane(String ph) {
        if (ph != null && ph.contains("/")) {
            return ph.substring(ph.lastIndexOf("/") + 1).replace("+", " ").replace("%", " ");
        }
        return ph;
    }

    public static String filemane2(String ph) {
        String ns = filemane(ph);

        return ns.contains(".") ? ns.substring(0, ns.lastIndexOf(".")) : ns;
    }

    public static String tilda(String x) {
        return "\\tilde{" + x + "}";
    }

    public static String vc(String x) {
        if (x.length() > 1) return "\\overrightarrow{" + x + "}";
        return "\\vec{" + x + "}";
    }

    public static String para() {
        return "||";
    }

    public static String hat(String x) {
        return "\\widehat{" + x + "}";
    }


    public static String annee() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dat = simpleDateFormat.format(new Date());
        return dat.substring(0, dat.indexOf("-"));
    }

    public static String jour() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dat = simpleDateFormat.format(new Date());
        return dat.substring(0, dat.indexOf("-"));
    }

    private static int in(String x) {
        return Integer.parseInt(x);
    }

    public static boolean avant(String s1, String s2) {
        Primitive prb = new Primitive();
        List<String> l = prb.coupen_en(s1, "-"), l2 = prb.coupen_en(s2, "-");
        if (l.size() == 3 && l2.size() == 3) {
            if (in(l.get(2)) < in(l2.get(2))) return true;
            if (in(l.get(2)) == in(l2.get(2))) {
                if (in(l.get(1)) < in(l2.get(1))) return true;
                if (in(l.get(1)) == in(l2.get(1))) {
                    if (in(l.get(0)) <= in(l2.get(0))) return true;
                }
            }
        }
        return false;
    }

    public static void focus_end(final EditText ti) {
        ti.requestFocus();
        ti.post(new Runnable() {
            @Override
            public void run() {
                ti.setSelection(ti.getText().length());
            }
        });
    }


    public static String duree(String d) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            simpleDateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
            String dat = simpleDateFormat.format(date);
            String aj = today_hms2();
            List<String> l2 = new Primitive().coupen_en(dat, " "),
                    l1 = new Primitive().coupen_en(aj, " ");
            int j1 = in(l1.get(0)), m1 = in(l1.get(1)), a1 = in(l1.get(2)), a, m, j, s;
            int j2 = in(l2.get(0)), m2 = in(l2.get(1)), a2 = in(l2.get(2));
            LesDates ld1 = new LesDates(j1, m1, a1), ld2 = new LesDates(j2, m2, a2);
            int nj = ld2.nombr_jour(ld1), nm, na;
            if (nj == 0) {
                Planning p = new Planning();
                p.setHeur_debu(l2.get(3));
                p.setHeur_fin(l1.get(3));
                int mi = p.duree_min(), ds = p.duree_s();
                if (mi == 0) return ds + " s";

                return mi < 60 ? mi + " mn" : (mi / 60) + " h";
            } else {
                if (nj < 7) {
                    switch (nj) {
                        case 1:
                            return "hier";
                        case 2:
                            return "avant hier";
                        default:
                            return nj + " jours";
                    }
                } else {
                    nm = (int) (nj / 30.5);
                    if (nm == 0) {
                        return nj / 7 == 1 ? (nj / 7) + " semaines" : (nj / 7) + " semaines";
                    } else {
                        na = (int) (nj / 365.5);
                        if (na == 0) {
                            return nm + " mois";
                        } else {
                            return na == 1 ? na + " année" : na + " années";
                        }
                    }
                }
            }
        }


        return "";
    }

    public static boolean bonne_periode() {
        String d1 = "07-01-2021", d2 = "31-12-2021";

        if (avant(d1, aujourdui()) && avant(aujourdui(), d2)) {
            return true;
        }
        return false;
    }

    public static boolean bonne_periode(String dtt) {
        String d1 = "20-09-2020", d2 = "31-12-2021";
        if (avant(dtt, aujourdui()) && avant(aujourdui(), d2)) {
            return true;
        }
        return false;
    }

    public static String moiss() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String dat = simpleDateFormat.format(new Date());
        return dat.substring(0, dat.indexOf("-"));
    }

    public static String heur() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        String dat = simpleDateFormat.format(new Date());
        return dat.substring(dat.indexOf(" ") + 1);
    }

    public static String heurs() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        String dat = simpleDateFormat.format(new Date());
        return dat.substring(dat.indexOf(" ") + 1);
    }

    public static PopupMenu popMenu(View v, int layout) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.getMenuInflater().inflate(layout,
                popup.getMenu());
        popup.show();
        return popup;
    }

    public static PopupMenu popupMenu(View v, String s[]) {
        PopupMenu menu = new PopupMenu(v.getContext(), v);
        menu.getMenu().clear();
        int n = s.length;
        for (int i = 0; i < n; i++) {
            menu.getMenu().add(i, i + 1, Menu.NONE, s[i]);
        }
        menu.show();
        return menu;
    }


    public static int viewHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    // read a view's width
    public static int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    public static int longueur(String xx, TextView textView) {
        String x = xx;
        Rect bounds = new Rect();
        x = x.replace("+", "++").replace("-", "--");
        Paint textPaint = textView.getPaint();
        textPaint.getTextBounds(x, 0, x.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();
        if (!x.contains("(_Ir")) return width;
        return width / 2;
    }
   /* public static int hauteur(String x, MathView textView){
        Rect bounds = new Rect();
        Paint textPaint = textView.getPaint();
        textPaint.getTextBounds(x, 0, x.length(), bounds);
        int height = bounds.height();
       // int width = bounds.width();
        return height;
    }*/

    public static String text_equivalent(String x, TextView t, int xm) {
        int n = x.length(), i = 0;
        String r = "";
        boolean oui = false;
        while (!oui && i < n) {
            r = r + x.charAt(i);
            i++;
            oui = longueur(r, t) >= xm;
        }
        return r;
    }

    public static int longueur_ed(String x, EditText textView) {
        Rect bounds = new Rect();
        Paint textPaint = textView.getPaint();
        textPaint.getTextBounds(x, 0, x.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();
        return width;
    }

    public static String aujourdui() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dat = simpleDateFormat.format(new Date());
        return dat;
    }

    public static String dateCode() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy HH mm ss SSSS");
        String dat = simpleDateFormat.format(new Date());
        return dat;
    }

    public static String today() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        String dat = simpleDateFormat.format(new Date());
        return dat;
    }

    public static String today_hms() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        String dat = simpleDateFormat.format(new Date());
        return dat;
    }

    public static String today_hms2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        String dat = simpleDateFormat.format(new Date());
        return dat;
    }



    public static String nomtel() {
        String v[] = {"91", "90", "92", "93", "70", "99", "98", "97", "79"}, k = "0123456789", n = "", p = "";
        int x = randum(0, v.length - 1);
        n = v[x];
        for (int i = 0; i < 6; i++) {
            n = n + k.charAt(randum(0, k.length() - 1));
        }
        return n;
    }

    public final static String FOLDER_YAAYI = Environment.getExternalStorageDirectory() + "/Yaayi";

    public static File writeResponseBodyToDisk(ResponseBody body, String ext, String nom) {
        try {
            // todo change the file location/name according to your needs
            //File pdf = new File(mContext.getExternalFilesDir(null) + File.separator +"manual_utilisation_yaayi"+ ext);
            File folder = new File(FOLDER_YAAYI);
            if (!folder.exists())
                folder.mkdirs();
            folder = new File(FOLDER_YAAYI + "/Documents");
            if (!folder.exists())
                folder.mkdirs();

            File file = new File(folder + "/" + nom + ext);

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();
                System.out.println(fileSize + "trutruetrue---------------" + file.exists());
                return file;
            } catch (IOException e) {
                System.out.println("errreurrrrrr---------------" + e.getMessage());
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println("errreurrrrrr222---------------" + e.getMessage());
            return null;
        }
    }

    private static boolean end(String x, String[][] l) {
        for (String s[] : l) {
            for (String e : s) {
                if (x.contains(e)) {
                    if (x.length() - e.length() <= 2) return true;
                }
            }
        }
        return false;
    }

    public static List<String> nu_text(String l) {
        String x[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"},
                xa[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t"},
                xI[] = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVII", "XIX", "XX"},
                xl[][] = {x, xa, xI};
        while (l.length() > 0 && l.charAt(0) == ' ') l = l.substring(1);
        int i = l.indexOf(" ");
        List<String> r = new ArrayList<>();
        if (i != -1) {
            String a = l.substring(0, i), b = l.substring(i), xx = a;
            System.out.println(xx + "----/--//--ouw2i=" + a + "----------------------------d=" + end(a, xl));
            if (end(a, xl)) {
                r.add(a);
                r.add(b);
            } else {
                r.add("");
                r.add(l);
            }
        } else {
            r.add("");
            r.add(l);
        }
        System.out.println(l + "----/--//--ouwi=" + "----------------------------d=" + r);
        return r;
    }

    public static String unpoint() {
        String x = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return x.charAt(randum(0, 25)) + "";
    }

    public static String status(String s) {
        if (s == null) return "";
        if (!s.equals("en ligne")) {
            String x = today();
            if (s.contains(x)) {
                x = s.replace(x, "");
                return "Vu aujourd'hui à " + x;
            } else {
                return "vu le " + s;
            }
        }
        return s;
    }

    public static String poind(File file) {
        if (file != null) {
            long n = file.length();
            if (n < 1000) {
                double d = n / 1000.0;
                return (n / 1000 >= 0.1 ? new Primitive().arondi_deux_chiffres(d + "") + " ko" : n + " oct");
            }
            if (n < 1000000) {
                double d = n / 1000000.0;
                return (n / 1000 >= 0.1 ? new Primitive().arondi_deux_chiffres(d + "") + " Mo" : n + " ko");
            }
            double d = n / 1000000.0;
            return new Primitive().arondi_deux_chiffres(d + "") + " Mo";
        }
        return "0 ko";
    }

    public static void sy(String v, String xx) {
        try {
            System.out.println("" + xx + " -- " + v);
        } catch (Exception e) {

        }
    }


    public static String debu(String x, String l[]) {
        for (String e : l) {
            if (x.startsWith(e)) return e;
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColor(Activity activity, int color, int divColor) {
        Window window = activity.getWindow();
        window.setNavigationBarColor(color);
        window.setStatusBarColor(color);
        // window.setNavigationBarColor(ContextCompat.getColor(activity,R.color.primary_text2)); //setting bar color

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // window.setNavigationBarDividerColor(divColor);
        }
        S.sy("-###########--", "" + color);
        //additional setting items to be black if using white ui
        // if(Color.parseColor("#FFFFFF")==color)
        // window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColorH(Activity activity, int color, int divColor) {
        Window window = activity.getWindow();
        // window.setNavigationBarColor(color);
        window.setStatusBarColor(color);
        // window.setNavigationBarColor(ContextCompat.getColor(activity,R.color.primary_text2)); //setting bar color

        //additional setting items to be black if using white ui
        // window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
   /* @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColorH(Activity activity, int color) {
        Window window= activity.getWindow();
        // window.setNavigationBarColor(color);
        window.setStatusBarColor(color);
        // window.setNavigationBarColor(ContextCompat.getColor(activity,R.color.primary_text2)); //setting bar color

        //additional setting items to be black if using white ui
         window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }*/


    public static List<String> fx(String rp) {
        List<String> r = new ArrayList<>();
        Primitive p = new Primitive();
        if (rp.contains("/")) {
            String x = p.avantChar(rp, '/'), y = p.apreChar(rp, '/');
            try {
                int n = Integer.parseInt(x) + 1;
                r.add(n + "/" + y);
                n = Integer.parseInt(y) + 1;
                r.add(x + "/" + n);
            } catch (Exception e) {
                r.add(p.opposer(rp));
            }
        } else {
            try {
                int n = Integer.parseInt(rp) + 1;
                r.add(n + "");
                n = Integer.parseInt(rp) - 1;
                r.add("" + n);
            } catch (Exception e) {
                r.add(p.opposer(rp));
            }
        }
        return r;
    }

    public static int dpToPx(int dp, Resources r) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static int numResol(String cl) {
        if (!cl.contains(" ")) return 1;
        if (cl.contains("2nde") || cl.contains("1ere")) return 2;
        return 3;
    }

    public static String mycls = "{\"id\":\"MYCLASSE-2021-11-14_10-37-58-503\",\"description\":\"Publications des saviez-vous\",\"enseignant\":{\"adresse\":{\"mail\":\"aloukoume@gmail.com\",\"telephone\":\"91542794\"},\"code\":{\"date_genere\":\"26 09 2021\",\"etat\":\"0\",\"id\":\"CODE-2021-09-26_20-40-35-689\",\"fil_id\":\"0000\",\"prix\":\"pac2∜ϑz∢3u%k*q4!xn2qn,r℉4ϵ√t⊿3.gwμ3⋰⋯aℶ°w5g∴1⋰ℶ↓2ghl2*ε↓∜h5°wθz∆5:-β3ωβ%s4≤∪2≫∴∛uℶ5znkj4π1m1≪r2∩1γun∤ω5∂↑2∎w≤3y1b∩≫\\u003e4≥≫zA4ε≥2↑∜2A∥2↓∴π±4-°∛3k1∤1α1∈1∁wy⋯4∜⋯hx4δc⋰3ϵ;μ∙4d1∢∤≡⋱δ5ff∎e4v1∙ϑτ!4⊿∋s∤z5⋱1∪≫μ⋯τ5+,2eα:ua5∀+2o1∥,-3∋1∃1¬α*ϵ4uσ2rδφ≡≅5∅jτ∋γ5\\u003e⋯.i4i∈ℵ√4;τzz∀5φaqb4∓∩≫3t∂≫¬4,↓∎3wi≪3ρ∂f3≅≡∩∄4℉v∓3∇wt≥*sρ2∄∀≡3θ⋱γϑ4p∜u3≡:2±∥p3j∂2⋰g2!qϵ.4ℵ1[-][+}∇wt≥*\",\"valeur\":\"ϵ∛gd3∂rℵϵπ°5zμdp∃4∂θ≅f3≫d∎μ∅4∇u%ℵ3±;α2∴i⋰r,\\u003e5yℵn2wω∋≫φ-5≫∢∁yτ4¬φ.↓3δA↓a3∴↓∴∇nt5ykℶu3r∃q∜⊿,5∪fl2∆√∄2≫s±≡3∇z∜δ∈4±v1∴j1,%1xβ∃∅↑4∤∁1≤αr∙∀∎5ϵ∙r2∂¬1z∂y2∂h≡;√4,≪⋰2xγ*p¬γ5∤σ1≤πfo!4⋯ϑ1±∎ρp∁s5∴e1ϵ*⋱2∂∋1zτm∇%∛5∂≅!tφ4:w±2⋱gzh∙4∤-*2∴o1yx1r∓⋯;3∪p∩ϵ3:δl≫3⋱∅≥2∤ε\\u003e≤∓4ltkρ4≥γ∈μ4n%w3⊿hh3≤u⊿3℉1!uθ≤g5∄ε∃ε4q!t∎∢5t≪2∩1∜εσ≅4ℶ1b1±∴2↑ω2∇z2+1∈p2ρx:3aσrτkϵ∂z∂4≫∇2,x∤≤4∴yr∪4∆1yw≫¬δ5⋯1∀1.1:⋱∤3[-][+}aσrτk\"},\"connecter\":\"1\",\"id\":\"COMPTE-2021-05-05 20:30:19.650\",\"inclu\":false,\"login\":\"#pavalo\",\"motdepasse\":\"#pavalo\",\"classes\":[],\"nom\":\"TOVA\",\"photoProfil\":{\"id\":\"PHOTOPROFIL-2021-11-11_13-29-57-035\",\"idpub\":0,\"path\":\"https://api.yaayischools.com/2021/11/COMPTE-2021-05-05 20-30-19.650/Mon+Nov+08+05%253A15%253A26+GMT+2021.jpg\",\"type\":\"application/pdf\"},\"photoCouverture\":{\"id\":\"PHOTOCOUVERTURE-2021-11-11_13-28-54-893\",\"idpub\":0,\"path\":\"https://api.yaayischools.com/2021/11/COMPTE-2021-05-05 20-30-19.650/Thu+Nov+11+00%253A05%253A41+GMT+2021_2.jpg\",\"type\":\"application/pdf\"},\"prenom\":\"Pavalo\",\"profil\":\"Enseignant\"},\"ids\":0,\"inclu\":false,\"libelle\":\"Tle Ti ZZZ\"}";

    public static boolean croisst(List<Integer> positions) {
        int i = positions.size();
        for (int j = 1; j < i; j++) {
            if (positions.get(j) < positions.get(j - 1)) return false;
        }
        return true;
    }

    public static void putPosions(List<Integer> positions, int p) {
        if (positions.contains(p) || p < 0) return;
        if (positions.size() < 2) positions.add(p);
        else {
            if (croisst(positions)) {
                int n = positions.get(positions.size() - 1);
                if (n < p) positions.add(p);
                else {
                    positions.clear();
                    positions.add(p);
                }
            } else {
                int n = positions.get(positions.size() - 1);
                if (n > p) positions.add(p);
                else {
                    positions.clear();
                    positions.add(p);
                }
            }
        }
        sy("" + positions, "--------------------------------");
    }

    public static List<String> ok_intervl(String i) {
        if (i != null) {
            if (i.startsWith("I=[") && i.endsWith("]")) {
                Primitive prb = new Primitive();
                String x = i.replace("I=[", "").replace("]", "");
                List<String> lx = prb.coupen_en(x, ";");
                if (lx.size() == 2) {
                    if (prb.isDouble(lx.get(0)) && prb.isDouble(lx.get(1))) {
                        return lx;
                    }
                }
            }
        }
        return null;
    }

    public static String ok_x0(String i) {
        if (i != null) {
            if (i.startsWith("∆x=")) {
                Primitive prb = new Primitive();
                String x = i.replace("∆x=", "");
                if (prb.isDouble(x)) {
                    return x;
                }
            }
        }
        return null;
    }

    public static String ok_x(String i) {
        if (i != null) {
            if (i.startsWith("x_0=")) {
                Primitive prb = new Primitive();
                String x = i.replace("x_0=", "");
                if (prb.isDouble(x)) {
                    return x;
                }
            }
        }
        return null;
    }


    public static String getPath(String nm, Context context) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File file = contextWrapper.getExternalFilesDir(Environment.getExternalStorageDirectory().getPath());
        File file1 = new File(file, nm);
        if (file1.exists()) {

        } else {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage() + " ex2";
            }
        }
        return file1.getPath();
    }

    public static List<Integer> somme_deux(int n) {
        List<Integer> r = new ArrayList<>();
        r.add(2);
        r.add(n - 2);
        return r;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Path creerDossier(String paquage, String name) throws IOException {
        String fileNme = paquage + "/" + name;
        Path path = Paths.get(fileNme);
        if (!Files.exists(path)) {
            File file=new File(paquage,name);
           boolean ok= file.mkdir();
           if(ok)return path;
           return Files.createDirectory(path);
        } else {
           return path;
        }
    }

    public static Timestamp timestamp(String dateString) throws ParseException {
        // String dateString = "2023-05-07 12:30:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date parsedDate = dateFormat.parse(dateString);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        return timestamp;
    }
    public static Timestamp timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }
    public static String timeStampToString(Timestamp timestamp) {
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateString = dateFormat.format(timestamp);
        return dateString;
    }

    public static Timestamp date(String dateString) throws ParseException {
        // String dateString = "2023-05-07 12:30:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date parsedDate = dateFormat.parse(dateString);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        return timestamp;
    }
    public static Date date(String dateString,String format) {
        // String dateString = "2023-05-07 12:30:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return parsedDate;
    }
    public static String date(String dateString,String format,String formatRetour) {
        // String dateString = "2023-05-07 12:30:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(dateString);
            return dateToString(parsedDate,formatRetour);
        } catch (ParseException e) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try {
                parsedDate = dateFormat.parse(dateString);
                return dateToString(parsedDate,formatRetour);
            } catch (ParseException ex) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                try {
                    parsedDate = dateFormat.parse(dateString);
                    return dateToString(parsedDate,formatRetour);
                } catch (ParseException x) {
                    try {
                        ZonedDateTime zon= DateObject.parseFlexible(dateString);
                        parsedDate=DateObject.convertZonedDateTimeToDate(zon);
                        return dateToString(parsedDate,formatRetour);
                    } catch (Exception exception) {
                        throw new RuntimeException(exception);
                    }
                    //Sun Mar 16 00:00:00 GMT 2025
                    // dateFormat=new SimpleDateFormat("");
                }
            }
        }
    }
    public static String dateToString(Date date,String format) {
        if(date==null)date=new Date();
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String dateString = dateFormat.format(date);
        return dateString;
    }
    public static String dateToString(Date date) {
        if(date==null)date=new Date();
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        String dateString = dateFormat.format(date);
        return dateString;
    }

    public static String af_pourcent(String d) {
        if (d.contains(".")) {
            String x = d.substring(0, d.indexOf(".")), c = d.substring(d.indexOf("."));
            if (c.length() > 2) {
                return x + "" + c.substring(0, 3);
            }
        }
        return d;
    }

   /* public static int getColor(Context context, int n) {
        int r = context.getResources().getColor(R.color.blue);
        switch (n) {
            case 0:
                r = context.getResources().getColor(R.color.vrt);
                break;
            case 1:
                r = context.getResources().getColor(R.color.colorRouge);
                break;
            case 2:
                r = context.getResources().getColor(R.color.colorRose);
                break;
            case 3:
                r = context.getResources().getColor(R.color.colorOnPromary);
                break;
            case 4:
                r = context.getResources().getColor(R.color.argent_sombre);
                break;
            case 5:
                r = context.getResources().getColor(R.color.blue);
                break;
            case 6:
                r = context.getResources().getColor(R.color.colorAccent);
                break;
            case 7:
                r = context.getResources().getColor(R.color.black);
                break;
            case 8:
                r = context.getResources().getColor(R.color.argent);
                break;
            case 9:
                r = context.getResources().getColor(R.color.argent_sombre2);
                break;
            case 10:
                r = context.getResources().getColor(R.color.backgrund_soumetre);
                break;
        }
        return r;
    }*/

    public static String en3(String s) {
        if(s==null||s.equals("null"))return "";
        if (s.length() <= 3){
            if(s.endsWith(".0")) return s.replace(".0","");
            return s;
        }
        String r = "", av = s.replace(".0", ""), ap;
        while (av.length() >= 3) {
            ap = av.substring(av.length() - 3);
            av = av.substring(0, av.length() - 3);
            r = ap + " " + r;
        }
        if (!av.equals("")) r = av + " " + r;
        if (r.startsWith(" ")) return r.substring(1);
        return r;
    }

    public static void params(LinearLayout ly, int x) {
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                x, ViewGroup.LayoutParams.MATCH_PARENT),
                layoutParams_max = new CoordinatorLayout.LayoutParams(
                        100, ViewGroup.LayoutParams.MATCH_PARENT);
        ly.setLayoutParams(layoutParams);
    }

    public static void plier(final List<View> vc) {
        int k = vc.size();
        if (k>0) {
            vc.get(0).setVisibility(View.VISIBLE);
            //vc.remove(0);
            k--;
        }
        if (k > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    plier(vc);
                }
            }, 100);
        }

    }

    public static void deplier(final List<View> vc) {
        int k = vc.size();
        if (!vc.isEmpty()) {
            vc.get(k - 1).setVisibility(View.GONE);
           // vc.remove(k - 1);
            k--;
        }
        if (k > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    deplier(vc);
                }
            }, 100);
        }

    }

    public static boolean endWith(String x, String[] l) {
        if (x == null) return false;
        int n = x.length(), k;
        for (String s : l) {
            k = s.length();
            if (n >= k) {
                boolean oui = x.substring(n - k).equalsIgnoreCase(s);
                if (oui) return true;
            }
        }
        return false;
    }
   public static String fileType(String path){
        int k=path.lastIndexOf(".");
        if(k!=-1)return path.substring(k+1);
        return "";
   }

    public static void openFolder(Context context,String folderPath) {
        // Replace this path with the path to the folder you want to open
        File folder = new File(folderPath);

        if (folder.exists()) {
            Uri folderUri = Uri.fromFile(folder);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(folderUri, "resource/folder");

            // Use a Content Provider to grant temporary access permissions
            Uri uri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".fileprovider",
                    folder
            );
            Uri fileUri = FileProvider.getUriForFile(context, "com.gschool.emploidetemp.fileprovider", folder);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri, context.getContentResolver().getType(fileUri));

            context.startActivity(intent);
        }
    }

    public static void saveImage(Context context, ZoomImageView imageView, String imageUrl) {
        if (context == null || imageView == null || imageUrl == null || imageUrl.isEmpty()) {
            Log.e("saveImage2", "Invalid input parameters: context, imageView, or imageUrl is null/empty.");
            return;
        }

        try {
            // Vérifiez si l'URL est un chemin local ou distant
            if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                // Charger une image distante avec Glide
                Glide.with(context)
                        .load(imageUrl)
                       .into(imageView);
            } else {
                // Charger une image locale à partir de son chemin ou URI
                File file = new File(imageUrl);
                if (file.exists()) {
                    Glide.with(context)
                            .load(file)
                            .into(imageView);
                } else {
                    Log.e("saveImage2", "Local file does not exist: " + imageUrl);

                }
            }
        } catch (Exception e) {
            Log.e("saveImage2", "Failed to load image: " + e.getMessage());
            e.printStackTrace();
           // imageView.setImageResource(R.drawable.mybook);
        }
    }
    public static void saveImage2(Context context, CustomImageView imageView, String imageUrl) {
        if (context == null || imageView == null || imageUrl == null || imageUrl.isEmpty()) {
            Log.e("saveImage2", "Invalid input parameters: context, imageView, or imageUrl is null/empty.");
            return;
        }

        try {
            // Vérifiez si l'URL est un chemin local ou distant
            if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                // Charger une image distante avec Glide
                Glide.with(context)
                        .load(imageUrl)
                        .into(imageView);
            } else {
                // Charger une image locale à partir de son chemin ou URI
                File file = new File(imageUrl);
                if (file.exists()) {
                    Glide.with(context)
                            .load(file)
                             .into(imageView);
                } else {
                    Log.e("saveImage2", "Local file does not exist: " + imageUrl);

                }
            }
        } catch (Exception e) {
            Log.e("saveImage2", "Failed to load image: " + e.getMessage());
            e.printStackTrace();

        }
    }


    private static String ajouterZero(int z){
        return z<=9?"0"+z:z+"";
    }
    public static void showDatePickerDialog(EditText editText,Context context) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Handle the selected date
                    String selectedDate = selectedDayOfMonth + "/" + ajouterZero(selectedMonth + 1) + "/" + selectedYear;
                    editText.setText(selectedDate);
                },
                year, month, dayOfMonth);

        // Show the dialog
        datePickerDialog.show();
    }

    public static String afficherDate(String dates) {
        if(dates==null)return "";
        SimpleDateFormat dateFormat =dates.contains("T")? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"):
                new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date=dateFormat.parse(dates);
            dateFormat = new SimpleDateFormat("dd MMM yyyy");
            return dateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void plier(final View v[],int time){
        List<View> vc=new ArrayList<>();
        for(View b:v){
            vc.add(b);
        }
        int k=vc.size();
        if(!vc.isEmpty()){
            vc.get(0).setVisibility(View.VISIBLE);
            vc.remove(0);k--;
        }
        if(k>0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    View  bc[]=new View[vc.size()];
                    int i=0;
                    for(View b:vc){
                        bc[i]=b;i++;
                    }
                    plier(bc,time);
                }
            }, time);
        }
    }
    public static void deplier(final View v[],final View v2[],int time){
        List<View> vc=new ArrayList<>();
        for(View b:v){
            vc.add(b);
        }
        int k=vc.size();
        if(!vc.isEmpty()){
            vc.get(k-1).setVisibility(View.GONE);
            vc.remove(k-1);k--;
        }
        if(k>0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    View  bc[]=new View[vc.size()];
                    int i=0;
                    for(View b:vc){
                        bc[i]=b;i++;
                    }
                    deplier(bc,time);
                }
            }, time);
        }else {
            plier(v2,time);
        }

    }
    public static void plier(final View v[],final View v2[],int time){
        List<View> vc=new ArrayList<>();
        for(View b:v){
            vc.add(b);
        }
        int k=vc.size();
        if(!vc.isEmpty()){
            vc.get(0).setVisibility(View.VISIBLE);
            vc.remove(0);k--;
        }
        if(k>0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    View  bc[]=new View[vc.size()];
                    int i=0;
                    for(View b:vc){
                        bc[i]=b;i++;
                    }
                    plier(bc,time);
                }
            }, time);
        }else {
            deplier(v2,time);
        }
    }
    public static void handleVew(View view,int time){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               view.setVisibility(View.GONE);
            }
        }, time);
    }
    public static void deplier(final View v[],int time){
        List<View> vc=new ArrayList<>();
        for(View b:v){
            vc.add(b);
        }
        int k=vc.size();
        if(!vc.isEmpty()){
            vc.get(k-1).setVisibility(View.GONE);
            vc.remove(k-1);k--;
        }
        if(k>0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    View  bc[]=new View[vc.size()];
                    int i=0;
                    for(View b:vc){
                        bc[i]=b;i++;
                    }
                    deplier(bc,2);
                }
            }, 2);
        }

    }
   public static String arrondi(String nbre){
       // Création d'un BigDecimal avec une valeur initiale
       BigDecimal nombreDecimal = new BigDecimal(nbre);
       // Arrondi à 2 chiffres après la virgule avec RoundingMode.HALF_UP
       BigDecimal resultatArrondi = nombreDecimal.setScale(2, RoundingMode.HALF_UP);
       return resultatArrondi.toEngineeringString();

   }
    public static boolean isInt(String chaine) {
        boolean valeur = false;
        if( !chaine.equals("")){
            valeur = true;
            char[] tab = chaine.toCharArray();
            for (char caract : tab) {
                if (!Character.isDigit(caract) && valeur) {
                    valeur = false;
                }
            }
        }
        return valeur;
    }


    public static CleValeur getOperatorAndIndex(String expression,List<String> caracteresRecherches) {
        List<CleValeur> index = caracteresRecherches.stream()
                .map(e -> new CleValeur(expression.indexOf(e), null, e))
                .filter(q -> q.getIndex() != -1)
                .collect(Collectors.toList());
        return index.stream().min(Comparator.comparingInt(x -> x.getIndex())).orElse(null);
    }
    public static String end(String s){
        while (s.endsWith(" "))s=s.substring(0,s.length()-1);
        return s;
    }

    public static String start(String s){
        s=s.replace("\n" +
                " \t","").replace("\n","");
        while (s.startsWith(" ")||s.startsWith("\t")||s.startsWith("\n"))s=s.substring(1);
        return s.trim();
    }
    public static long differenceInMinutes(Date date1, Date date2) {
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        return diffInMillies / (60 * 1000);
    }
    public static long differenceInSeconds(Date date1, Date date2) {
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        return diffInMillies / 1000;
    }

    public static String en2(int i) {
        return i<10?"0"+i:i+"";
    }

    public static String toUperCaseFirstChar(String s) {
        while (s.length() > 0 && s.charAt(0) == ' ') {
            s = s.substring(1);
        }
       // s=s.replace(" ","");
        if (s.length() == 0) {
            return s;
        }
        String sa = s.toLowerCase();
        sa = (sa.charAt(0) + "").toUpperCase() + sa.substring(1);
        return sa;
    }
    public static String toLowerCaseFirstChar(String s) {
        while (s.length() > 0 && s.charAt(0) == ' ') {
            s = s.substring(1);
        }
        s=s.replace(" ","");
        if (s.length() == 0) {
            return s;
        }
        String sa = s.toLowerCase();
        sa = (sa.charAt(0) + "").toLowerCase() + sa.substring(1);
        return sa;
    }
    public static boolean isUperCase(String s) {
        String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_";
        char t[] = s.toCharArray();
        for (char c : t) {
            if (!a.contains(c + "")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerCase(String s) {
        String a = "abcdefghijklmnopqrstuvwxyz0123456789_";
        char t[] = s.toCharArray();
        for (char c : t) {
            if (!a.contains(c + "")) {
                return false;
            }
        }
        return true;
    }
    public static String toUndersCors(String s) {
        if(s==null)return "";
        s=s.replace(" ","");
        if(isUperCase(s))return s.toLowerCase();
        int n = s.length();
        String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 1; i < n; i++) {
            String x = s.charAt(i) + "";
            if (a.contains(x)) {
                s = s.replace(x, "_" + x.toLowerCase());
            }
        }
        if (s.startsWith("_")) {
            s = s.substring(1);
        }
        return toLowerCaseFirstChar(s.replace("__","_"));
    }

    public static String phrase(String s){
        if(s.startsWith("id"))s=s.substring(2);
        s=toUndersCors(s).replace("_"," ");
        return toUperCaseFirstChar(s);
    }

    public static String toCamelCase(String s) {
        s=s.replace(" ","");
        int n = s.length();
        int j = s.indexOf("_");
        //s=s.toLowerCase();
        while (j != -1 && j != n - 1) {
            String x = "_" + s.charAt(j + 1);
            s = s.replace(x, (x.charAt(1)+"").toUpperCase());
            j = s.indexOf("_");
            n = s.length();
        }
        return s;
    }
    public static String toCamelCase2(String s) {
        if(s==null||s.length()==0)return s;
        s=s.replace(" ","");
        if(s.equals("int"))s="Interger";
        int n = s.length();
        int j = s.indexOf("_");
        // s=s.toLowerCase();
        while (j != -1 && j != n - 1) {
            String x = "_" + s.charAt(j + 1);
            s = s.replace(x, (x.charAt(1)+"").toUpperCase());
            j = s.indexOf("_");
            n = s.length();
        }
        s=(s.charAt(0)+"").toUpperCase()+s.substring(1);
        return s;
    }
    public static String toCamelCase3(String s) {
        try {
            s=s.replace(" ","");
            if(s.length()==0)return s;
            s=toCamelCase(s);
            s=(s.charAt(0)+"").toLowerCase()+s.substring(1);
        }catch (Exception e){

        }
        return s;
    }

    public static View findView(Context context,int R){
        return   LayoutInflater.from(context).inflate(R,null,false);
    }

    public static File getFile(String nm,Context context){
        ContextWrapper contextWrapper=new ContextWrapper(context);
        File file1=contextWrapper.getExternalFilesDir(Environment.getExternalStorageDirectory().getPath());
        //File file1=new File(file);

        if(file1.exists()){
            if(!file1.isDirectory()&&file1.canWrite()){
                file1.delete();
                file1.mkdirs();
                file1=new File(file1,nm);
                if(nm.contains("/"))  file1.mkdirs();
                else file1.mkdir();
            }else {
                file1=new File(file1,nm);
                if(nm.contains("/"))  file1.mkdirs();
                else file1.mkdir();
            }
        }else {
            file1.mkdir();
            file1=new File(file1,nm);
            if(nm.contains("/"))  file1.mkdirs();
            else file1.mkdir();
        }
        System.out.println(file1.exists()+"=== =====---------------"+file1.getAbsolutePath());
        return   file1;
    }

}

