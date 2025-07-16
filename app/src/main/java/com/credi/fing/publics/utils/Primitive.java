package com.credi.fing.publics.utils;

import java.util.ArrayList;
import java.util.List;

public class Primitive {

    public String viderEspace(String st) {
        if (st == null) return st;
        char[] t = st.toCharArray();
        String str = "";
        for (char c : t) {
            if (c != ' ') {
                str = str + c;
            }
        }
        return str;
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

    public String simplifierSignes(String str) {
        if (str.length() == 0) return str;
        String valeur = str;
        if (contien_double_signe(str)) {
            char[] tab = str.toCharArray();
            int n = tab.length, n2 = 0;
            valeur = tab[0] + "";
            for (int i = 0; i < n - 1; i++) {
                if ((tab[i] == '+' || tab[i] == '-') && (tab[i + 1] == '+' || tab[i + 1] == '-')) {
                    n2 = valeur.length();
                    String plusOuMoin = valeur.substring(n2 - 1) + "" + tab[i + 1];
                    switch (plusOuMoin) {
                        case "--":
                        case "++":
                            valeur = valeur.substring(0, n2 - 1) + "+";
                            break;
                        case "-+":
                        case "+-":
                            valeur = valeur.substring(0, n2 - 1) + "-";
                            break;
                    }
                } else valeur = valeur + tab[i + 1];
            }
        }
        valeur = valeur.replace("(+", "(");
        return valeur;
    }

    public String enlev_Signe(String str) {
        if (str.charAt(0) == '+') str = str.substring(1);
        return str;
    }

    public int lepgcd(int $num, int $deno) {
        int pgcd = 1;
        if ($num != 0 && $deno != 0) {
            if ($num < 0) $num = -$num;
            if ($deno < 0) $deno = -$deno;
            while (!($num == $deno)) {
                if ($num < $deno) {
                    $deno = $deno - $num;
                } else {
                    $num = $num - $deno;
                }
            }
            pgcd = $num;
        } else {
            if ($num == 0) pgcd = $deno;
            else pgcd = $num;
        }

        return pgcd;
    }


    public double lepgcd2(double $num, double $deno) {
        double pgcd = 1;
        if ($num != 0 && $deno != 0) {
            if ($num < 0) $num = -$num;
            if ($deno < 0) $deno = -$deno;
            while (!($num == $deno)) {
                if ($num < $deno) {
                    $deno = $deno - $num;
                } else {
                    $num = $num - $deno;
                }
            }
            pgcd = $num;
        } else {
            if ($num == 0) pgcd = $deno;
            else pgcd = $num;
        }

        return pgcd;
    }


    public int carreproche(double nombre) {
        int nbres = 1;
        ArrayList<Integer> lesCare = new ArrayList<>();
        int i = 2;
        while (i * i <= nombre) {
            lesCare.add(i * i);
            i++;
        }
        for (int n : lesCare) {
            if (lepgcd(n, (int) nombre) == n) {
                nbres = n;
            }
        }
        return nbres;
    }

    public double arondi(double d) {
        String ds = d + "";
        ds = arondi(ds);
        double de = Double.valueOf(ds);
        return de;
    }

    public String arondi_enInt(String d) {
        if (d.contains(".")) {
            String ds = avantChar(d, '.'), ap = apreChar(d, '.'), c = ap.charAt(0) + "";
            int n = Integer.parseInt(ds), a = Integer.parseInt(c);
            if (a >= 7) return (n + 1) + "";
            return n + "";
        }
        return d;
    }

    public String arondi(String d) {
        if (!d.contains("E")) {
            if (d.contains(".")) {
                String av = avantChar(d, '.'), ap = apreChar(d, '.'), ps, au;
                if (ap.contains("8889")) ap = ap.replace("8", "9");
                int n = ap.length(), p;
                if (n > 4) {
                    au = ap.substring(3);
                    if (au.charAt(0) == '0' || au.charAt(0) == '1') {
                        if (ap.contains("0000")) {
                            au = av + "." + ap.substring(0, ap.indexOf("0000"));
                        } else {
                            au = av + "." + ap.substring(0, 2);
                        }
                        return au;
                    }
                }
                if (ap.contains("9") && n > 6) {
                    p = ap.indexOf("9");
                    switch (p) {
                        case 0:
                            if (n > 1 && ap.charAt(1) == '9') {
                                int db = Integer.valueOf(av);
                                db++;
                                d = db + "";
                            } else {
                                if (n > 2) {
                                    ps = ap.substring(2, 3);
                                    int db = Integer.valueOf(ps);
                                    if (db > 5) {
                                        ps = ap.substring(1, 2);
                                        db = Integer.valueOf(ps);
                                        db++;
                                        d = av + "." + ap.substring(0, 1) + db;
                                    } else {
                                        d = av + "." + ap.substring(0, 2);
                                    }
                                }
                            }
                            break;
                        default:

                            ps = ap.substring(p - 1, p);

                            int db = Integer.valueOf(ps);
                            db++;
                            d = av + "." + ap.substring(0, p - 1) + db;
                            return d;
                    }
                } else {
                    if (ap.contains("0")) {
                        p = ap.indexOf("0");
                        if (p > 0) {
                            ps = ap.substring(0, p);
                            d = av + "." + ps;
                        } else if (n > 4) {
                            ps = ap.substring(0, 4);
                            d = av + "." + ps;
                            d = Double.valueOf(d) + "";
                        }
                    } else {
                        if (n > 4) {
                            ps = ap.substring(0, 4);
                            d = av + "." + ps;
                        }

                    }
                }
            }
            return d;
        } else {
            String av = avantChar(d, 'E'), ap = apreChar(d, 'E');
            ap = "E" + ap;
            av = arondi(av);
            d = av + ap;
            return d;
        }
    }

    public String arondi_deux_chiffres(String d) {
        if (!d.contains("E")) {
            if (d.contains(".")) {
                String av = avantChar(d, '.'), ap = apreChar(d, '.'), ps, au;
                if (ap.contains("8889")) ap = ap.replace("8", "9");
                int n = ap.length(), p;
                if (n > 4) {
                    au = ap.substring(3);
                    if (au.charAt(0) == '0' || au.charAt(0) == '1') {
                        if (ap.contains("0000")) {
                            ap = ap.substring(0, ap.indexOf("0000"));
                            if (ap.length() > 2) ap = ap.substring(0, 2);
                            au = av + "." + ap;
                        } else {
                            au = av + "." + ap.substring(0, 2);
                        }
                        return au;
                    }
                }
                if (ap.contains("9") && n > 6) {
                    p = ap.indexOf("9");
                    switch (p) {
                        case 0:
                            if (n > 1 && ap.charAt(1) == '9') {
                                int db = Integer.valueOf(av);
                                db++;
                                d = db + "";
                            } else {
                                if (n > 2) {
                                    ps = ap.substring(2, 3);
                                    int db = Integer.valueOf(ps);
                                    if (db > 5) {
                                        ps = ap.substring(1, 2);
                                        db = Integer.valueOf(ps);
                                        db++;
                                        d = av + "." + ap.substring(0, 1) + db;
                                    } else {
                                        d = av + "." + ap.substring(0, 2);
                                    }
                                }
                            }
                            break;
                        default:

                            ps = ap.substring(p - 1, p);

                            int db = Integer.valueOf(ps);
                            db++;
                            ap = ap.substring(0, p - 1) + db;
                            if (ap.length() > 2) ap = ap.substring(0, 2);
                            d = av + "." + ap;
                            return d;
                    }
                } else {
                    if (ap.contains("0")) {
                        p = ap.indexOf("0");
                        if (p > 0) {
                            ps = ap.substring(0, p);
                            d = av + "." + ps;
                        } else if (n > 4) {
                            ps = ap.substring(0, 2);
                            d = av + "." + ps;
                            d = Double.valueOf(d) + "";
                        }
                    } else {
                        if (n > 2) {
                            ps = ap.substring(0, 2);
                            d = av + "." + ps;
                        }

                    }
                }
            }
            return d;
        } else {
            String av = avantChar(d, 'E'), ap = apreChar(d, 'E');
            ap = "E" + ap;
            av = arondi(av);
            d = av + ap;
            return d;
        }
    }

    public String arondire(String d) {
        if (!d.contains("E")) {
            if (d.contains(".")) {
                String av = avantChar(d, '.'), ap = apreChar(d, '.'), ps, au;
                if (ap.contains("9999") || ap.contains("0000")) {
                    int n = ap.length(), p;

                    if (ap.contains("9")) {
                        p = ap.indexOf("9");
                        switch (p) {
                            case 0:
                                if (n > 1 && ap.charAt(1) == '9') {
                                    int db = Integer.valueOf(av);
                                    if (db > 0) db++;
                                    else db--;
                                    d = db + "";
                                } else {
                                    if (n > 2) {
                                        ps = ap.substring(2, 3);
                                        int db = Integer.valueOf(ps);
                                        if (db > 5) {
                                            ps = ap.substring(1, 2);
                                            db = Integer.valueOf(ps);
                                            db++;
                                            d = av + "." + ap.substring(0, 1) + db;
                                        } else {
                                            d = av + "." + ap.substring(0, 2);
                                        }
                                    }
                                }
                                break;
                            default:
                                ps = ap.substring(p - 1, p);
                                int db = Integer.valueOf(ps);
                                db++;
                                d = av + "." + ap.substring(0, p - 1) + db;
                        }
                    } else {
                        if (ap.contains("0")) {
                            p = ap.indexOf("0");
                            if (p > 0) {
                                ps = ap.substring(0, p);
                                d = av + "." + ps;
                            } else if (n > 4) {
                                ps = ap.substring(0, 4);
                                d = av + "." + ps;
                                d = Double.valueOf(d) + "";
                            }
                        } else {
                            if (n > 4) {
                                ps = ap.substring(0, 4);
                                d = av + "." + ps;
                            }

                        }
                    }
                } else {
                    if (ap.replace("9", "").equals("")) {
                        av = (Integer.parseInt(av) + 1) + "";
                        return av;
                    }
                }
            }

            return d;
        } else {
            String av = avantChar(d, 'E'), ap = apreChar(d, 'E');
            ap = "E" + ap;
            av = arondire(av);
            int n = av.length();
            if (n > 2 && av.substring(n - 2).equals(".0")) {
                av = av.substring(0, n - 2);
            }
            if (av.equals("1")) av = "";
            else if (av.equals("-1")) av = "-";
            d = av + ap;
            return d;
        }
    }

    public String arondire2(String d) {
        if (!d.contains("E")) {
            if (d.contains(".")) {
                String av = avantChar(d, '.'), ap = apreChar(d, '.'), ps, au;
                if (ap.length() > 15) {
                    return av + "." + ap.substring(0, 15);
                }
            }

            return d;
        } else {
            String av = avantChar(d, 'E'), ap = apreChar(d, 'E');
            ap = "E" + ap;
            av = arondire2(av);
            int n = av.length();
            if (n > 2 && av.substring(n - 2).equals(".0")) {
                av = av.substring(0, n - 2);
            }
            if (av.equals("1")) av = "";
            else if (av.equals("-1")) av = "-";
            d = av + ap;
            return d;
        }
    }

    public List<String> inverseS(List<String> l) {
        List<String> r = new ArrayList<>();
        for (String h : l) {
            r.add(0, h);
        }
        return r;
    }

    public String html(String s) {
        if (s == null) return "";
        if (s.equals("^") || s.equals("_")) return s;
        s = s.replace("|_", "").replace("_|", "");
        if (s.contains("^")) {
            int i = s.indexOf("^");
            String av = s.substring(0, i), ap = s.substring(i + 1), ex = facteur(ap, 0);
            ap = reste(ap, ex);
            s = av + "<SUP>" + enleverP(ex) + "</SUP>" + ap;
        }
        if (s.contains("^")) s = html(s);

        if (s.contains("_")) {
            int i = s.indexOf("_");
            String av = s.substring(0, i), ap = s.substring(i + 1), ex = facteur(ap, 0);
            ap = reste(ap, ex);
            s = av + "<SUB>" + enleverP(ex) + "</SUB>" + ap;
        }
        if (s.contains("_")) s = html(s);
        return s;
    }


    public boolean est_entoure_parenthse(String s) {
        boolean retour = false;
        if (s.length() != 0 && s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')') {
            ArrayList<String> ls = couperIntraFacteur(s);
            int l = s.length(), n = ls.size();
            if (n == 1 && s.charAt(0) == '(' && s.charAt(l - 1) == ')') retour = true;
        }
        if (s.length() != 0 && !s.contains(";") && s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}') {
            ArrayList<String> ls = couperIntraFacteur(s);
            int l = s.length(), n = ls.size();
            if (n == 1 && s.charAt(0) == '{' && s.charAt(l - 1) == '}') retour = true;
        }

        if (s.length() != 0 && s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}' && !s.contains("}^{")) {
            ArrayList<String> ls = couperIntraFacteur(s);
            int l = s.length(), n = ls.size();
            if (n == 1 && s.charAt(0) == '{' && s.charAt(l - 1) == '}') retour = true;
        }


        return retour;
    }

    public boolean est_entoure_absolu(String s) {
        if (s != null && s.length() != 0) {
            return s.charAt(0) == '|' && s.charAt(s.length() - 1) == '|';
        }
        return false;
    }

    public String enleverPs(String s) {
        ArrayList<String> l = lesSemiPolynome(s);
        String c = "";
        for (String x : l) {
            if (x.length() > 2 && x.subSequence(0, 2).equals("-(")) {
                x = enleverP(x.substring(1));
                x = opposer(x);
                c = c + "+" + enleverP(simp_sign(x));
            } else
                c = c + "+" + enleverP(simp_sign(x));
        }
        return simp_sign(c);
    }

    public String enleverP(String s) {
        s = viderEspace(s);
        /*int n=s.length();
        if(n>2){
            if(s.substring(0,2).equals("-(")&&s.charAt(n-1)==')'){
                if(nbre_monome(s)==1){
                    String f=facteur(s,1),fc=f;
                    f=f.substring(1,f.length()-1);
                    f=opposer(f);
                    s=s.replace("-"+fc,"("+f+")");
                }
            }
        }*/
        while (est_entoure_parenthse(s)) {
            int l = s.length();
            s = s.substring(1, l - 1);
        }
        //  s=enlever_doublon_p(s);
        return s;
    }

    public String metrP(String s) {
        if (s.equals("")) return s;
        int n = nbre_monome(s);
        String r = s;
        if (n != 1 && n != 0) r = "(" + s + ")";
        else {
            if (s.contains("/(") || s.contains(")/")) r = "(" + s + ")";
        }

        return r;
    }

    public String metrP_I(String s) {
        if (s.equals("")) return s;
        String r = s;
        if (r.contains("U")) r = "(" + r + ")";
        return r;
    }

    public boolean estMultifacteur(String s) {
        boolean retour = false;
        int n = s.length(), j = 0, k = 0;
        if (nombreOccurence(s, '(') >= 2) {
            int i = 0;
            while (j != 2 && i < n) {
                if (s.charAt(i) == '(') j++;
                else if (s.charAt(i) == ')') j--;
                i++;
            }
            if (j != 2) retour = true;
        }

        return retour;
    }

    public boolean est_Intrafacteur(String s) {
        boolean oui = false;
        ArrayList<String> lp = couperIntraFacteur(s);
        if (lp.size() > 1)
            for (String st : lp) {
                int n = st.length();
                if (st.charAt(0) == '(' && st.charAt(n - 1) == ')') {
                    oui = true;
                }
            }
        return oui;
    }

    public boolean estIntrafacteur(String s) {
        boolean retour = false;
        int n = s.length(), n2 = n, j = 0, k = 0, p = 0, m = 0, pls = 0, ap = 0;
        String st = s;
        if (nombreOccurence(s, '(') >= 2) {
            while (!retour && !s.equals("")) {
                int i = 0;
                while (j != 2 && i < n) {
                    if (s.charAt(i) == '(') j++;
                    else if (s.charAt(i) == ')') {
                        j--;
                        if (pls > 0) pls--;
                    }
                    if (j > 0) {
                        if ((s.charAt(i) == '-') || (s.charAt(i) == '+')) pls++;
                    }
                    i++;
                }
                int b = 0, d = s.length() - 1;
                char cr;
                while (b != 2 && d >= 0) {
                    cr = s.charAt(d);
                    if (cr == ')') b++;
                    else if (cr == '(') {
                        b--;
                        if (ap > 0) ap--;
                    }
                    if (b > 0 && ((cr == '-') || (cr == '+'))) ap++;
                    d--;
                }

                k = s.indexOf(')');
                m = s.length();
                while (k < m && s.charAt(k) != '(') {
                    char c = s.charAt(k);
                    if ((c == '+') || (c == '-')) p++;
                    k++;
                }
                if (k < m) {
                    if (j == 2 && p == 0 && pls == 0) retour = true;
                    if (b == 2 && p == 0 && ap != 0) retour = false;
                }

                if (pls != 0) {
                    String sm = deuxiemeFacteur(s);
                    if (nombreOccurence(sm, '(') != 0) retour = true;
                } else if (ap != 0) {
                    String sm = avtDernierFacteur(s);
                    if (nombreOccurence(sm, ')') != 0) retour = true;
                }

                s = s.substring(k);
                p = 0;
                n = s.length();
            }

            if (pls != 0) {
                String sm = deuxiemeFacteur(st);
                if (nombreOccurence(sm, '(') != 0) retour = true;
            } else if (ap != 0) {
                String sm = avtDernierFacteur(st);
                if (nombreOccurence(sm, ')') != 0) retour = true;
            }

        }
        return retour;
    }

    public boolean est_fraction(String s) {
        boolean rt = false;
        if (s.contains("/") && monome(s)) {
            rt = true;
        }
        return rt;
    }

    public ArrayList<String> gaucheEtDroite(String str) {
        int n = nombreOccurence(str, '=');
        ArrayList<String> lesmonoms = new ArrayList<>();
        if (n == 1) {
            String GetD = "", st = "";
            while (!str.equals("")) {
                st = str.substring(0, 1);
                if (!st.equals("=")) {
                    GetD = GetD + st;
                    str = str.substring(1);
                } else {
                    lesmonoms.add(GetD);
                    str = str.substring(1);
                    GetD = "";
                }
            }
            if (!GetD.equals("")) lesmonoms.add(GetD);
        } else lesmonoms.add("");
        return lesmonoms;
    }

    public double puissance(double d, double n) {
       /* double p=1;
        if(n !=0){
            for(int i=0; i<n;i++) p=p*d;
        }*/
        return Math.exp(n * Math.log(d));
    }

    public String apreChar(String s, char c) {
        String apres = "";
        int i = s.indexOf(c + "");
        if (i != -1) {
            apres = s.substring(i + 1);
        }
        return apres;
    }

    public String apreString(String s, String c) {
        String apres = "";
        int i = s.indexOf(c);
        if (i != -1) {
            apres = s.substring(i + c.length());
        }
        return apres;
    }

    public String avantString(String s, String c) {
        String avant = s;
        int i = s.indexOf(c);
        if (i != -1) {
            avant = s.substring(0, i);
        }
        return avant;
    }

    public String avantChar(String s, char c) {
        String avant = s;
        int i = s.indexOf(c + "");
        if (i != -1) {
            avant = s.substring(0, i);
        }
        return avant;
    }


    public boolean estOperateur(String c) {
        boolean vrai = false;
        String alphabet = "^+-*/()";
        int n = alphabet.length();
        for (int i = 0; i < n; i++) {
            if (alphabet.substring(i, i + 1).equals(c))
                vrai = true;
        }
        return vrai;
    }

    public boolean isInt(String chaine) {
        boolean valeur = false;
        if (!chaine.equals("")) {
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

    public int nombreOccurence(String str, char c) {
        char[] tableauDesCaracters = str.toCharArray();
        int n = 0;
        for (char caracter : tableauDesCaracters) {
            if (caracter == c) n++;
        }
        return n;
    }

    public int nombreOccurence(String str, String cs) {
        str = str.replace(cs, "!");
        char c = '!';
        char[] tableauDesCaracters = str.toCharArray();
        int n = 0;
        for (char caracter : tableauDesCaracters) {
            if (caracter == c) n++;
        }
        return n;
    }


    public String facteur(String s, int position) {
        String rtr = "";
        if (s.equals("") || s.length() <= position) return "";
        if (s.charAt(position) == '(') {
            int i = position, j = 0, n = s.length();
            boolean arret = false;
            char cu;
            while (!arret && i < n) {
                cu = s.charAt(i);
                if (cu == '(') j++;
                else if (cu == ')') {
                    j--;
                    if (j == 0) arret = true;
                }
                i++;
            }
            rtr = s.substring(position, i);
            if (i < s.length() && s.charAt(i) == '^') {
                String ap = s.substring(i + 1);
                rtr = rtr + "^" + debut(ap);
            }
        } else {
            if (s.charAt(position) == '{') {
                int i = position, j = 0, n = s.length();
                boolean arret = false;
                char cu;
                while (!arret && i < n) {
                    cu = s.charAt(i);
                    if (cu == '{') j++;
                    else if (cu == '}') {
                        j--;
                        if (j == 0) arret = true;
                    }
                    i++;
                }
                rtr = s.substring(position, i);
                if (i < s.length() && s.charAt(i) == '^') {
                    String ap = s.substring(i + 1);
                    rtr = rtr + "^" + debut(ap);
                }
            } else {
                if (s.charAt(position) == '\\' && s.contains("\\(")) {
                    return s.substring(s.indexOf("\\("), s.indexOf("\\)") + 2);
                }
                if (s.startsWith("\\color{red}|")) {
                    s = s.replace("\\color{red}|", "");

                    rtr = "\\color{red}|" + debutAvcPuissce_3(s.substring(position));
                } else
                    rtr = debutAvcPuissce_3(s.substring(position));
            }
        }
        return rtr;
    }

    public String facteurRcne(String s, int position) {
        String rtr = "";
        if (s.charAt(position) == '\\' && s.contains("\\sqrt{")) {
            int i = position + 5;
            while (s.charAt(i) != '}') i++;
            rtr = s.substring(position, i + 1);
        } else if (s.charAt(position) == 'π') {
            rtr = "π";
        } else if (s.charAt(position) == '√') {
            rtr = facteur(s, position);
        }
        return rtr;
    }


    public boolean isDouble(String chaine) {
        boolean valeur = false;
        if (!(chaine.equals("+") || chaine.equals("-"))) {
            if (nombreOccurence(chaine, '.') <= 1 && !(chaine.equals(""))) {
                char[] tab = chaine.toCharArray();
                int i = 0;
                int n = tab.length;
                if ((tab[n - 1] != '.') && (tab[0] != '.')) {
                    if ((tab[0] == '-') || (tab[0] == '+')) {
                        i++;
                        if (tab[i] != '.') {
                            while (i < n && (Character.isDigit(tab[i]) || (tab[i] == '.'))) {
                                valeur = true;
                                i++;
                            }
                        }
                    } else
                        while (i < n && (Character.isDigit(tab[i]) || (tab[i] == '.'))) {
                            valeur = true;
                            i++;
                        }
                }
                if (i < n) valeur = false;
            }
        }
        if (!valeur) {
            if (!chaine.equals("") && getV(chaine).equals("")) return true;
        }
        return valeur;
    }

    public String debut(String s) {
        String st = "";
        boolean moin_un = false, plus_un = false;
        if (!(s.equals(""))) {
            if (s.substring(0, 1).equals("-")) {
                st = "-";
                s = reste(s, "-");
                moin_un = true;
            } else if (s.substring(0, 1).equals("+")) {
                st = "+";
                s = reste(s, "+");
                plus_un = true;
            }
            if (!(s.equals(""))) {
                String a = s.substring(0, 1);
                if (isInt(a)) {
                    while ((isInt(a) || (a.equals("."))) && !(s.equals(""))) {
                        st = st + a;
                        s = reste(s, a);
                        if (!(s.equals("")))
                            a = s.substring(0, 1);
                    }
                } else if (moin_un) {
                    st = "-1";
                } else if (plus_un) {
                    st = "1";
                } else st = s.substring(0, 1);
            }
        }
        return st;
    }

    public String debut(List<String> l, String x) {
        int n = x.length(), p;
        for (String v : l) {
            p = v.length();
            if (p <= n) {
                String rs = x.substring(0, p);
                if (rs.equals(v)) return v;
            }
        }
        return null;
    }

    public ArrayList<String> debut2(String s) {
        ArrayList<String> deb_rest = new ArrayList<>();
        String db = "", reste;
        int i = 0, n = s.length(), j = n;
        if (s.charAt(0) != '(') {
            while (i < n && s.charAt(i) != '(') i++;
            if (i < n && s.charAt(i) == '(') {
                if (s.charAt(i - 1) == 'E') {
                    String x_ = facteur(s, i);
                    i = i + x_.length();
                }
                db = s.substring(0, i);
                if (db.equals("+")) db = "1";
                else if (db.equals("-")) db = "-1";
                reste = s.substring(i);
            } else {
                db = s;
                reste = "";
            }
        } else {
            db = "1";
            reste = s;
        }
        deb_rest.add(db);
        deb_rest.add(reste);
        return deb_rest;
    }

    public String debutAvcPuissce(String x) {
        String s2, rs, s3 = "", s = x;
        if (s.length() >= 3 && s.substring(0, 3).equals("|_|")) {
            s3 = "|_|";
            s = s.substring(3);
        }
        s2 = s3 + debut(s);
        if (s2.equals("-1") && !(s.substring(0, 2).equals("-1"))) {
            rs = reste(s, "-");
        } else {
            rs = reste(s, s2);
        }
        if (!rs.equals("") && rs.charAt(0) == '^') {
            s3 = premierFacteur(rs.substring(1));
            s2 = s2 + "^" + s3;
        }
        rs = reste(s, s2);
        int n = rs.length();
        if (n >= 3 && rs.substring(0, 3).equals("|_|")) {
            if (rs.length() > 3) {
                s3 = premierFacteur(rs.substring(3));
            } else s3 = "";
            if (!isDouble(s3)) s3 = "";
            s2 = s2 + "|_|" + s3;
        }
        return s2;
    }

    public String debutAvcPuissce(String x, List<String> list) {
        String s2, rs, s3 = "", d, s = x;
        if (s.length() >= 3 && s.substring(0, 3).equals("|_|")) {
            s3 = "|_|";
            s = s.substring(3);
        }
        d = debut(list, s);
        if (d != null) s2 = s3 + d;
        else s2 = s3 + debut(s);
        if (s2.equals("-1") && !(s.substring(0, 2).equals("-1"))) {
            rs = reste(s, "-");
        } else {
            rs = reste(s, s2);
        }
        if (!rs.equals("") && rs.charAt(0) == '^') {
            s3 = premierFacteur(rs.substring(1), list);
            s2 = s2 + "^" + s3;
        }
        rs = reste(s, s2);
        int n = rs.length();
        if (n >= 3 && rs.substring(0, 3).equals("|_|")) {
            if (rs.length() > 3) {
                s3 = premierFacteur(rs.substring(3), list);
            } else s3 = "";
            if (!isDouble(s3)) s3 = "";
            s2 = s2 + "|_|" + s3;
        }
        return s2;
    }

    public String debutAvcPuissce2(String s) {
        String s2 = "", s3 = "", s4 = s;

        if (!s.equals("") && s.charAt(0) == '/') {
            s2 = "/";
            s = s.substring(1);
        }
        s2 = s2 + debutAvcPuissce(s);
        int x = s2.length();
        if (x > 1 && s2.equals("-1") && !s.subSequence(0, 2).equals("-1")) {
            s = s.substring(1);
        } else s = reste(s4, s2);

        if (!s.equals("") && s.charAt(0) == '/') {
            s3 = debutAvcPuissce(s.substring(1));
            s2 = s2 + "/" + s3;
            s = reste(s, s3 + "/");
        }
        if (!s.equals("") && s.charAt(0) == '!') {
            s2 = s2 + "!";
            // s=reste(s,s3+"/");
        }

        return s2;
    }

    public ArrayList<String> lesDebutAvcPusce2(String monom) {
        ArrayList<String> str = new ArrayList<>();
        while (monom.length() > 0) {

            str.add(debutAvcPuissce2(monom));
            if ((debutAvcPuissce2(monom).equals("-1")) && !(monom.substring(0, 2).equals("-1"))) {
                monom = reste(monom, "-");
            } else
                monom = reste(monom, debutAvcPuissce2(monom));
        }
        return str;
    }

    public String debutAvcPuissce_2(String s) {
        String s2 = debutAvcPuissce2(s), s3 = "";
        if (s2.equals("-1") && !s.substring(0, 2).equals("-1")) {
            s = reste(s, "-");
        } else
            s = reste(s, s2);
        if (!s.equals("") && s.charAt(0) == 'E') {
            s3 = facteur(s.substring(1), 0);
            s2 = s2 + "E" + s3;
        }
        return s2;
    }

    public String debutAvcPuissce_3(String s) {
        //Systeme.out.println(s+" oui "+s.length());

        if (s.length() == 0) {
            return s;
        }

        String s2 = debutAvcPuissce_2(s), s3 = "", v;
        if (s2.endsWith("/(")) {
            s2 = s2.substring(0, s2.length() - 1);
            int i = s.indexOf("/(");
            v = facteur(s, i + 1);
            s2 = s2 + v;
        }

        if (s2.equals("-1") && !s.substring(0, 2).equals("-1")) {
            s = reste(s, "-");
        } else
            s = reste(s, s2);
        if (s2.equals("√")) {
            if (s.length() == 0) {
                return s2;
            }
            if (s.charAt(0) == '(') {
                s3 = facteur(s, 0);
                s2 = s2 + s3;
            } else {
                s3 = debutAvcPuissce_2(s);
                s2 = s2 + s3;
            }
        }

        return s2;
    }

    public ArrayList<String> lesDebutAvcPusce_3(String monom) {
        ArrayList<String> str = new ArrayList<>();
        while (monom.length() > 0) {
            String db3 = debutAvcPuissce_3(monom);
            str.add(db3);
            if ((db3.equals("-1")) && !monom.substring(0, 2).equals("-1")) {
                monom = reste(monom, "-");
            } else
                monom = reste(monom, db3);
        }
        return str;
    }

    public boolean estVariable(char c) {
        boolean vrai = true;
        String alphabet = "0123456789+-*√/()^;=|\\{}[]E.e ";
        int n = alphabet.length();
        for (int i = 0; i < n; i++) {
            if (alphabet.charAt(i) == c)
                vrai = false;
        }
        return vrai;
    }

    public boolean estVariable_modif(char c) {
        String alphabet = "0123456789+-*√/()^;=| ";
        return !alphabet.contains(c + "");
    }

    public int nombre_variable(String s) {
        int n = s.length(), k = 0, i = 0;
        while (i < n) {
            if (estVariable(s.charAt(i))) k++;
            i++;
        }
        return k;
    }

    public int position_variable(String s) {
        if (nombre_variable(s) == 0) return -1;
        int n = s.length(), i = 0;
        while (i < n && !estVariable(s.charAt(i))) i++;
        return i;
    }


    public String reste(String str, String st2) {
        st2 = st2.replace("|_|", "");
        int n = st2.length();
        str = str.substring(n);
        return str;
    }

    public ArrayList<String> lesDebutAvcPusce(String monom) {
        ArrayList<String> str = new ArrayList<>();
        String s;
        while (monom.length() > 0) {
            s = debutAvcPuissce(monom);
            str.add(s);
            if ((s.equals("-1")) && !(monom.substring(0, 2).equals("-1"))) {
                monom = reste(monom, "-");
            } else
                monom = reste(monom, s);
        }
        return str;
    }

    public ArrayList<String> couperEnFacteur(String str) {
        ArrayList<String> lesfac = new ArrayList<>();
        while (!str.equals("") && nombreOccurence(str, '(') != 0) {
            String s = str.substring(0, str.indexOf(')') + 1);
            lesfac.add(s);
            str = str.substring(str.indexOf(')') + 1);
        }
        if (!str.equals("")) lesfac.add(str);
        return lesfac;
    }

    public String premierFacteur(String ss) {
        String retour = "1", s = ss;
        if (s.charAt(0) != '(') {
            if (isDouble(debut(s))) {
                retour = debutAvcPuissce2(s);
            } else retour = debut(s);
        } else {
            boolean arret = false;
            int j = 0, i = 0;
            while (!arret) {
                if (s.charAt(i) == '(') j++;
                else if (s.charAt(i) == ')') {
                    j--;
                    if (j == 0) arret = true;
                }
                i++;
            }
            retour = s.substring(0, i);
            s = s.substring(i);
            if (!s.equals("") && s.charAt(0) == '^') {
                s = s.substring(1);
                retour = retour + "^" + debutAvcPuissce2(s);
            }

        }
        return retour;
    }

    public String premierFacteur(String ss, List<String> list) {
        String retour = "1", s = ss;
        retour = debut(list, ss);
        if (retour != null) return retour;
        if (s.charAt(0) != '(') {
            if (isDouble(debut(s)))
                retour = debutAvcPuissce2(s);
        } else {
            boolean arret = false;
            int j = 0, i = 0;
            while (!arret) {
                if (s.charAt(i) == '(') j++;
                else if (s.charAt(i) == ')') {
                    j--;
                    if (j == 0) arret = true;
                }
                i++;
            }
            retour = s.substring(0, i);
            s = s.substring(i);
            if (!s.equals("") && s.charAt(0) == '^') {
                s = s.substring(1);
                retour = retour + "^" + debutAvcPuissce2(s);
            }

        }
        return retour;
    }

    public String premierFacteur_sans_exp(String ss) {
        String retour = "1", s = ss;
        if (s.charAt(0) != '(') {
            if (isDouble(debut(s)))
                retour = debutAvcPuissce2(s);
        } else {
            boolean arret = false;
            int j = 0, i = 0;
            while (!arret) {
                if (s.charAt(i) == '(') j++;
                else if (s.charAt(i) == ')') {
                    j--;
                    if (j == 0) arret = true;
                }
                i++;
            }
            retour = s.substring(0, i);
        }
        return retour;
    }

    public boolean contien(String[] l, String x) {
        for (String v : l) {
            if (v.equals(x)) return true;
        }
        return false;
    }

    public String premierFacteur_s(String ss) {
        String retour = "1", s = ss, rs;
        if (nbre_monome(ss) != 1) return ss;
        if (s.charAt(0) != '(') {
            int n = s.length();
            if (s.charAt(0) == '%') {
                s = s.substring(1);
                retour = premierFacteur_s(s);
                retour = "%" + retour;
            } else if (s.charAt(0) == '√') {
                s = s.substring(1);
                retour = premierFacteur_s(s);
                retour = "√" + retour;
            } else if (n > 2 && s.substring(0, 3).equals("ln(")) {
                s = s.substring(2);
                retour = premierFacteur_s(s);
                retour = "ln" + retour;
            } else if (n > 2 && s.substring(0, 3).equals("e^(")) {
                s = s.substring(2);
                retour = premierFacteur_s(s);
                retour = "e^" + retour;
            } else {
                String st = "sin(cos(tan(", cf;
                if (n > 3 && st.contains(s.substring(0, 4))) {
                    cf = s.substring(0, 3);
                    s = s.substring(3);
                    retour = premierFacteur_s(s);
                    retour = cf + retour;
                } else {
                    String stx = "sin^cos^tan^", cfx;
                    if (n > 3 && stx.contains(s.substring(0, 4))) {
                        cfx = s.substring(0, 4);
                        s = s.substring(4);
                        retour = premierFacteur_s(s);
                        cfx = cfx + retour;
                        s = s.substring(retour.length());
                        retour = premierFacteur_s(s);
                        retour = cfx + retour;
                    } else {
                        st = "Arcsin(Arccos(Arctan(";
                        if (n > 6 && st.contains(s.substring(0, 7))) {
                            cf = s.substring(0, 6);
                            s = s.substring(6);
                            retour = premierFacteur_s(s);
                            retour = cf + retour;
                        } else {
                            if (s.charAt(0) == 'E') {
                                s = s.substring(1);
                                if (s.length() > 0 && s.charAt(0) == '_') {
                                    s = s.substring(1);
                                    retour = premierFacteur_s(s);
                                    retour = "E_" + retour;
                                } else {
                                    retour = premierFacteur_s(s);
                                    retour = "E" + retour;
                                }

                            } else {
                                if (s.charAt(0) == '|') {
                                    s = s.substring(1);
                                    int i = s.indexOf("|");
                                    retour = "|" + s.substring(0, i + 1);
                                    //s=s.substring(i+1);

                                } else
                                    retour = debutAvcPuissce2(s);
                            }
                        }
                    }


                }

            }
        } else {
            boolean arret = false;
            int j = 0, i = 0;
            while (!arret) {
                if (s.charAt(i) == '(') j++;
                else if (s.charAt(i) == ')') {
                    j--;
                    if (j == 0) arret = true;
                }
                i++;
            }
            retour = s.substring(0, i);
            s = s.substring(i);
            if (!s.equals("") && s.charAt(0) == '^') {
                s = s.substring(1);
                retour = retour + "^" + facteur(s, 0);
            }
            if (!s.equals("") && s.charAt(0) == '_') {
                s = s.substring(1);
                retour = retour + "_" + facteur(s, 0);
            }
        }
        if (retour.equals("-1") && !ss.substring(0, 2).equals("-1")) {
            rs = reste(ss, "-");
        } else rs = reste(ss, retour);
        int n = rs.length();
        if (n > 0 && rs.charAt(0) == 'E') {
            String x = premierFacteur(rs.substring(1));
            retour = retour + "E" + x;
        }
        return retour;
    }

    private String p_trg(String x, String tg) {
        String s = x, r = "", av, ap, e;
        while (s.contains(tg)) {
            av = avantString(s, tg);
            ap = apreString(s, tg);
            if (ap.charAt(0) != '(') {
                e = ap.substring(0, 3);
                r = r + av + tg + "(" + e + ")";
                s = s.substring(av.length() + 3 + 3);
            } else {
                e = facteur(ap, 0);
                r = r + av + tg + e + "";
                s = s.substring(av.length() + 3 + e.length());
            }
        }
        if (r.equals("")) return x;
        return r;
    }

    public String prs_trg_(String s) {
        s = p_trg(s, "sin");
        s = p_trg(s, "cos");
        s = p_trg(s, "tan");
        return s;
    }

    public String premierFacteur_s(String ss, List<String> list) {
        String retour = "1", s = ss, rs;

        if (nbre_monome(ss) != 1) return ss;
        if (s.charAt(0) != '(') {
            int n = s.length();
            if (s.charAt(0) == '%') {
                s = s.substring(1);
                retour = premierFacteur_s(s, list);
                retour = "%" + retour;
            } else if (s.charAt(0) == '√') {
                s = s.substring(1);
                retour = premierFacteur_s(s, list);
                retour = "√" + retour;
            } else if (n > 2 && s.substring(0, 3).equals("ln(")) {
                s = s.substring(2);
                retour = premierFacteur_s(s, list);
                retour = "ln" + retour;
            } else if (n > 2 && s.substring(0, 3).equals("e^(")) {
                s = s.substring(2);
                retour = premierFacteur_s(s, list);
                retour = "e^" + retour;
            } else {
                String st = "sin(cos(tan(", cf;
                if (n > 3 && st.contains(s.substring(0, 4))) {
                    cf = s.substring(0, 3);
                    s = s.substring(3);
                    retour = premierFacteur_s(s, list);
                    retour = cf + retour;
                } else {
                    st = "Arcsin(Arccos(Arctan(";
                    if (n > 6 && st.contains(s.substring(0, 7))) {
                        cf = s.substring(0, 6);
                        s = s.substring(6);
                        retour = premierFacteur_s(s, list);
                        retour = cf + retour;
                    } else {
                        if (s.charAt(0) == 'E') {
                            s = s.substring(1);
                            if (s.length() > 0 && s.charAt(0) == '_') {
                                s = s.substring(1);
                                retour = premierFacteur_s(s, list);
                                retour = "E_" + retour;
                            } else {
                                retour = premierFacteur_s(s, list);
                                retour = "E" + retour;
                            }

                        } else {
                            if (s.charAt(0) == '|') {
                                s = s.substring(1);
                                int i = s.indexOf("|");
                                retour = "|" + s.substring(0, i + 1);
                                //s=s.substring(i+1);

                            } else
                                retour = debutAvcPuissce2(s, list);
                        }
                    }

                }

            }
        } else {
            boolean arret = false;
            int j = 0, i = 0;
            while (!arret) {
                if (s.charAt(i) == '(') j++;
                else if (s.charAt(i) == ')') {
                    j--;
                    if (j == 0) arret = true;
                }
                i++;
            }
            retour = s.substring(0, i);
            s = s.substring(i);
            if (!s.equals("") && s.charAt(0) == '^') {
                s = s.substring(1);
                retour = retour + "^" + facteur(s, 0);
            }
            if (!s.equals("") && s.charAt(0) == '_') {
                s = s.substring(1);
                retour = retour + "_" + facteur(s, 0);
            }
        }
        if (retour.equals("-1") && !ss.substring(0, 2).equals("-1")) {
            rs = reste(ss, "-");
        } else rs = reste(ss, retour);
        int n = rs.length();
        if (n > 0 && rs.charAt(0) == 'E') {
            String x = premierFacteur(rs.substring(1));
            retour = retour + "E" + x;
        }
        return retour;
    }

    public String debutAvcPuissce2(String s, List<String> list) {
        String s2 = "", s3 = "", s4 = s;

        if (!s.equals("") && s.charAt(0) == '/') {
            s2 = "/";
            s = s.substring(1);
        }
        s2 = s2 + debutAvcPuissce(s, list);
        int x = s2.length();
        if (x > 1 && s2.equals("-1") && !s.subSequence(0, 2).equals("-1")) {
            s = s.substring(1);
        } else s = reste(s4, s2);

        if (!s.equals("") && s.charAt(0) == '/') {
            s3 = debutAvcPuissce(s.substring(1), list);
            s2 = s2 + "/" + s3;
            s = reste(s, s3 + "/");
        }

        return s2;
    }

    public String deuxiemeFacteur(String s) {
        int x = s.indexOf('('), y = 0, z = s.length();
        char cu;
        boolean arret = false;
        while (!arret && x < z) {
            cu = s.charAt(x);
            if (cu == '(') y++;
            else if (cu == ')') y--;
            if (y == 0) arret = true;
            x++;
        }
        return s.substring(x);
    }

    public String avtDernierFacteur(String s) {
        int z = s.length(), x = z - 1, y = 0;
        char cu;
        boolean arret = false;
        while (!arret && x > 0) {
            cu = s.charAt(x);
            if (cu == ')') y++;
            else if (cu == '(') {
                y--;
                if (y == 0) arret = true;
            }
            x--;
        }
        return s.substring(0, x + 1);
    }

    public ArrayList<String> couperIntraFacteur(String str) {
        ArrayList<String> lesfac = new ArrayList<>();
        char c;
        int i = 0, j = 0, n = str.length(), n2;
        while (i < n && !str.equals("") && nombreOccurence(str, '(') > 1) {
            c = str.charAt(i);
            boolean entrer = false;
            if (c == '(') j++;
            if (c == ')') {
                j--;
                if (j == 0) {
                    lesfac.add(str.substring(0, i + 1));
                    str = str.substring(i + 1);
                    i = 0;
                    entrer = true;
                }
            }
            if (!entrer) i++;
            n = str.length();
        }
        if (!str.equals("")) lesfac.add(str);
        return lesfac;
    }

    public ArrayList<String> couperIntraFacteur_2(String str) {
        ArrayList<String> lesfac = new ArrayList<>();
        char c;
        int i = 0, j = 0, n = str.length(), n2;
        while (!str.equals("") && nombreOccurence(str, '(') > 1) {
            c = str.charAt(i);
            boolean entrer = false;
            if (c == '(') j++;
            if (c == ')') {
                j--;
                if (j == 0) {
                    String a, b = str.substring(i + 1);
                    if (!b.equals("") && str.charAt(i + 1) == '^') {
                        a = debut(str.substring(i + 2));
                        i = i + a.length() + 1;
                        lesfac.add(str.substring(0, i + 1));
                        str = str.substring(i + 1);
                    } else {
                        lesfac.add(str.substring(0, i + 1));
                        str = str.substring(i + 1);
                    }
                    i = 0;
                    entrer = true;
                }
            }
            if (!entrer) i++;
        }
        if (!str.equals("")) lesfac.add(str);
        return lesfac;
    }

    public ArrayList<String> lesDebut(String monom) {
        ArrayList<String> str = new ArrayList<>();
        while (monom.length() > 0) {
            str.add(debut(monom));
            if ((debut(monom).equals("-1")) && !(monom.substring(0, 2).equals("-1"))) {
                monom = reste(monom, "-");
            } else
                monom = reste(monom, debut(monom));
        }
        return str;
    }

    public ArrayList<String> lesSemiMonomes(String expression) {
        ArrayList<String> lesmonoms = new ArrayList<>();
        String monom = "", st = "";
        while (!expression.equals("")) {
            st = expression.substring(0, 1);
            if (!(st.equals("+") || st.equals("-"))) {
                monom = monom + st;
                expression = reste(expression, "a");
            } else if (monom.equals("")) {
                monom = monom + st;
                expression = reste(expression, "a");
            } else {
                lesmonoms.add(monom);
                monom = "";
            }
        }
        if (!monom.equals("")) lesmonoms.add(monom);
        return lesmonoms;
    }

    public ArrayList<String> lesMegaPolyn(String s) {
        ArrayList<String> lp = lesSemiPolynome(s), r = new ArrayList<>();
        boolean oui;
        String p = "";
        for (String sr : lp) {
            oui = !sr.contains(")/") && !sr.contains("/(") && !sr.contains("ln(") && !sr.contains("e^(") && !sr.contains(")^")
                    && !sous_rc_variable(sr) && !sr.contains("_");
            if (oui) p = p + "+" + sr;
            else r.add(sr);
        }
        if (!p.equals("")) r.add(simp_sign(p));
        return r;
    }

    public ArrayList<String> lesSemiPolynome(String expression) {
        // expression=enleverP(expression);

        expression = ex2(expression);
        ArrayList<String> lesmonoms = new ArrayList<>();
        String monom = "", st = "";
        int i = 0;
        while (!expression.equals("")) {
            st = expression.substring(0, 1);
            if (st.equals("(") || st.equals("{")) i++;
            else if (st.equals(")") || st.equals("}")) i--;
            if (!(st.equals("+") || st.equals("-"))) {
                monom = monom + st;
                expression = reste(expression, "a");
            } else {
                if (monom.equals("") || i != 0) {
                    monom = monom + st;
                    expression = reste(expression, "a");
                } else if (i == 0) {
                    if (!monom.equals(" ")) lesmonoms.add(monom);
                    monom = "";
                }
            }
        }
        if (!monom.equals("")) {
            if (!monom.equals(" ")) lesmonoms.add(monom);
        }
        return lesmonoms;
    }

    public ArrayList<String> polinom_ala_fin(String expression) {
        expression = ex2(expression);
        ArrayList<String> lp = lesSemiPolynome(expression);
        ArrayList<String> lesmonoms = new ArrayList<>();
        String monom = "", st = "";
        int i = 0;
        for (String s : lp) {
            if (!s.contains("/(") && !s.contains(")/") && !sous_rc_variable(s) && !s.contains("ln") && !s.contains("e^")) {
                st = st + "+" + s;
            } else lesmonoms.add(s);
        }
        if (!st.equals("")) lesmonoms.add(simp_sign(st));
        return lesmonoms;
    }


    public int nbr_sous_rc_variable(ArrayList<String> lp) {
        int k = 0;
        for (String s : lp) {
            if (sous_rc_variable(s)) k++;
        }
        return k;
    }

    /********* Regroupe les sous_racine_variable puis met le polynome a la fin ********   */
    public ArrayList<String> nbr_sous_rc_variables(ArrayList<String> lp) {
        ArrayList<String> rt = new ArrayList<>();
        String pl = "";
        int k = 0;
        for (String s : lp) {
            if (sous_rc_variable(s)) {
                k++;
                rt.add(s);
            } else {
                pl = pl + "+" + s;
            }
        }
        if (!pl.equals("")) {
            pl = simp_sign(pl);
        }
        rt.add(pl);
        rt.add(0, k + "");
        return rt;
    }

    public ArrayList<String> nbr_sous_rc_variables(String st) {
        ArrayList<String> lp = lesSemiPolynome(st);
        return nbr_sous_rc_variables(lp);
    }

    /********* Regroupe les ln puis met le polynome a la fin ********   */
    public ArrayList<String> nbr_de_ln(ArrayList<String> lp) {
        ArrayList<String> rt = nbr_de(lp, "ln(");
        return rt;
    }

    public ArrayList<String> nbr_de_ln(String st) {
        ArrayList<String> rt = lesSemiPolynome(st);
        return nbr_de_ln(rt);
    }

    /********* Regroupe les expo puis met le polynome a la fin ********   */
    public ArrayList<String> nbr_de_expo(ArrayList<String> lp) {
        ArrayList<String> rt = nbr_de(lp, "e^(");
        return rt;
    }

    public ArrayList<String> nbr_de_expo(String st) {
        ArrayList<String> rt = lesSemiPolynome(st);
        return nbr_de_expo(rt);
    }

    /********* ... Regroupe les ...  ********   */
    public ArrayList<String> nbr_de(ArrayList<String> lp, String es) {
        ArrayList<String> rt = new ArrayList<>();
        String pl = "";
        int k = 0;
        for (String s : lp) {
            if (s.contains(es)) {
                k++;
                rt.add(s);
            } else {
                pl = pl + "+" + s;
            }
        }
        if (!pl.equals("")) {
            pl = simplifierSignes(pl);
            if (pl.charAt(0) == '+') pl = pl.substring(1);
        }
        rt.add(pl);
        rt.add(0, k + "");
        return rt;
    }


    public boolean monome(String sr) {
        if (nombre_variable(sr) <= 1 && !sr.contains("+") && !sr.contains("-")) return true;
        boolean oui = !sr.contains(")/") && !sr.contains("/(") && !sr.contains("ln(") && !sr.contains("e^(") && !sr.contains(")^")
                && !sous_rc_variable(sr) && !sr.contains("_") && !sr.contains("sin") && !sr.contains("cos") && !sr.contains("tan");
        ArrayList<String> r = les_pusance_n(sr);
        return oui && r.isEmpty();
    }

    public boolean monome_t(List<String> ls) {
        for (String x : ls) {
            if (!monome(x)) return false;
        }
        return true;
    }

    public String inter_abs(String s) {
        if (nombreOccurence(s, '|') == 2) {
            return enleverP(s.replace("|", ""));
        }
        int n = s.length(), i = s.indexOf("|");
        return s.substring(i + 1, n - 1);
    }

    public ArrayList<String> couper_abs(String s) {
        ArrayList<String> rt = new ArrayList<>();
        String st = s, au, av, v;
        while (st.contains("|")) {
            int i = st.indexOf("|"), j = i + 1;
            while (st.charAt(j) != '|') j++;
            av = st.substring(0, i);
            v = st.substring(i, j + 1);
            if (av.equals("")) {
                rt.add(v);
            } else {
                if (av.equals("+") || av.equals("-")) {
                    v = av + v;
                    rt.add(v);
                } else {
                    int p = av.length();
                    av = av.substring(0, i - 1);
                    v = st.substring(i - 1, j + 1);
                    rt.add(av);
                    rt.add(v);
                }
            }
            st = st.substring(j + 1);
        }
        if (!st.equals("")) rt.add(st);
        return rt;
    }

    public int nbre_monome(String s) {
        return lesSemiPolynome(s).size();
    }

    public boolean contien_double_signe(String s) {
        boolean oui = false;
        if (s.contains("++") || s.contains("+-") || s.contains("-+") || s.contains("--"))
            oui = true;
        return oui;
    }

    public String simp_sign(String s) {
        if (s.length() == 0) return s;
        String ss = simplifierSignes(s), a = ss;
        ss = ss.replace("(1x", "(x");
        ss = ss.replace("(-1x", "(-x");
        ss = ss.replace("+1x", "+x");
        ss = ss.replace("-1x", "-x");
        ss = ss.replace("(-1)ln", "-ln").replace("-1*" + S.rsn, "-" + S.rsn)
                .replace("+1*" + S.rsn, "+" + S.rsn);
        //ss=ss.replace("(-1)(", "-(");
        ss = ss.replace("-1ln", "-ln");
        ss = ss.replace("+1ln", "+ln");
        // ss=ss.replace("(1)", "");
        // if(ss.equals("")&&!s.equals("")) ss="1";
        if (ss.length() > 1 && ss.charAt(0) == '+' && ss.charAt(1) != '∞') ss = ss.substring(1);
        return ss;
    }

    public String opposer(String s) {
        s = enleverP(s);
        ArrayList<String> lp = lesSemiPolynome(s);
        String se = "";
        for (String x : lp) {
            if (!x.equals("0")) se = se + "-" + x;
        }
        if (se.equals("")) return "0";
        return simp_sign(se);
    }

    public String getV(String au) {
        String v = "", s = au;
        if (s.length() > 2 && s.charAt(1) == '(') s = s.substring(1);
        s = s.replace("ln", "").replace("left", "").replace("right", "").replace("int", "");
        s = s.replace("sin", "");
        s = s.replace("cos", "");
        s = s.replace("e^", "").replace("i", "");
        int n = position_variable(s);
        if (n != -1) {
            v = s.charAt(n) + "";
        }
        return v;
    }

    public String getV0(String au) {
        String v = "", s = au;
        if (s.length() > 2 && s.charAt(1) == '(') s = s.substring(1);
        int n = position_variable(s);
        if (n != -1) {
            v = s.charAt(n) + "";
        }
        return v;
    }

    public boolean sous_rc_variable(String s) {
        boolean oui = false;
        String au = s, f;
        while (!oui && au.contains("√("))
            if (au.contains("√(")) {
                int i = au.indexOf("√(");
                f = facteur(au, i + 1);
                if (nombre_variable(f) > 0 || f.contains("√(")) {
                    return true;
                }
                au = au.substring(i + f.length());
            }
        return oui;
    }

    public ArrayList<Object> sous_rc_variables(String s) {
        ArrayList<Object> rt = new ArrayList<>();
        boolean oui = false;
        String au = s, f = "", av;
        int p = 0;
        while (!oui && au.contains("√("))
            if (au.contains("√(")) {
                int i = au.indexOf("√(");
                f = facteur(au, i + 1);
                if (nombre_variable(f) > 0) {
                    oui = true;
                }
                au = au.substring(i + f.length());
                p = p + i;
            }
        av = s.replace("√" + f, "");
        while (av.contains("()")) av = av.replace("()", "");
        if (av.equals("")) av = "1";
        if (av.equals("+")) av = "1";
        if (av.equals("-")) av = "-1";
        if (av.charAt(0) == '/') av = "1" + av;
        rt.add(oui);
        rt.add(p);
        rt.add(f);
        rt.add(av);
        return rt;
    }

    public String ex(String s) {
        if (!s.contains("^-")) return s;
        int i = s.indexOf("^-");

        if (i != -1) {
            String v = facteur(s, i + 2);

            s = s.replace("^-" + v, "^(-" + v + ")");
        }
        return s;
    }

    public String ex2(String s) {
        s = ex(s);
        if (!s.contains("E-")) return s;
        int i = s.indexOf("E-");
        if (i != -1) {
            String v = facteur(s, i + 2);
            if (v.contains("/")) {
                i = v.indexOf("/");
                v = v.substring(0, i);
            }
            s = s.replace("E-" + v, "E(-" + v + ")");
        }
        if (s.contains("E-")) return ex2(s);
        return s;
    }

    public String s(String s) {
        if (s.equals("") || s.equals("-") || s.equals("+")) return s;
        s = s.replace("()^2", "");
        switch (s) {
            case "1":
                return "";
            case "-1":
                return "-";
            default:
                return metrP(s);

        }
    }

    public String s_(String s) {
        switch (s) {
            case "1":
                return "";
            case "-1":
                return "-";
            default:
                return "(" + s + ")";

        }
    }

    public String ms(String s) {
        switch (s) {
            case "":
            case "+":
                return "1";
            case "-":
                return "-1";
            default:
                return metrP(s);

        }
    }

    public boolean est_pus_n(String s) {
        ArrayList<String> r = les_pusance_n(s);
        return !r.isEmpty();
    }

    public ArrayList<String> pus_n(String s) {
        int i = s.indexOf("^");
        ArrayList<String> r = new ArrayList<>();
        String v = s, un;
        if (i != -1) {
            String n = facteur(s, i + 1);
            boolean oui = !getV(n).equals("");
            if (oui) {
                r.add("1");
                un = dernierFacteur(s, i);
                un = un + "^" + n;
                r.add(un);
                return r;
            }
            v = apreChar(v, '^');
            while (v.contains("^")) {
                i = v.indexOf("^");

                n = facteur(v, i + 1);
                oui = !getV(n).equals("");
                if (oui) {
                    r.add("1");
                    un = dernierFacteur(v, i);
                    un = un + "^" + n;
                    r.add(un);
                    return r;
                }
                v = apreChar(v, '^');
            }
        }
        r.add("0");
        return r;
    }

    public ArrayList<String> les_pusance_n(String s) {
        ArrayList<String> r = new ArrayList<>(), pus;
        String v, au, ap = s;
        pus = pus_n(ap);
        int i;
        while (pus.get(0).equals("1")) {
            au = pus.get(1);
            i = ap.indexOf(au);
            ap = ap.substring(i + au.length());
            pus = pus_n(ap);
            r.add(au);
        }
        return r;
    }

    private boolean arr(char cu) {
        String a = "|+-=(){}[],;*<>≤≥÷: ";
        return a.contains(cu + "");
    }

    public boolean arrs(char cu) {
        String a = "|+-=,;*<>≤≥÷: ";
        return a.contains(cu + "");
    }

    private boolean arrs(String cu) {
        String a = "(){}[],;";
        return a.contains(cu + "");
    }

    public String dernierFacteur(String s, int z) {
        if (z == 0) return "1";
        int x = z - 1, y = 0;
        if (s.equals("")) return s;
        char cu, c = ')';
        boolean arret = false;
        if (s.charAt(x) == ')' || s.charAt(x) == '}') {
            if (s.charAt(x) == ')') {
                while (!arret && x > 0) {
                    cu = s.charAt(x);
                    if (cu == ')') y++;
                    else if (cu == '(') {
                        y--;
                        if (y == 0) arret = true;
                    }
                    if (!arret) x--;
                }
                if (x != 0) {
                    String rs = s.substring(0, x);
                    int xc = rs.length();
                    if (xc >= 1) {
                        if ((s.charAt(x - 1) + "").equals(S.rsn)) {
                            x--;
                        }
                        if (xc >= 2 && x > 1) {
                            if ((s.substring(x - 2, x)).equals("ln")) {
                                x -= 2;
                            }
                        }
                    }
                }
                return s.substring(x, z).trim();
            } else if (s.charAt(x) == '}') {
                while (!arret && x > 0) {
                    cu = s.charAt(x);
                    if (cu == '}') y++;
                    else if (cu == '{') {
                        y--;
                        if (y == 0) arret = true;
                    }
                    if (!arret) x--;
                }
                if (x != 0) {
                    String rs = s.substring(0, x);
                    int xc = rs.length();
                    if (xc >= 1) {
                        if ((s.charAt(x - 1) + "").equals(S.rsn)) {
                            x--;
                        }
                        if (xc >= 2 && x > 1) {
                            if ((s.substring(x - 2, x)).equals("ln")) {
                                x -= 2;
                            }
                        }
                    }
                }
                return s.substring(x, z).trim();
            }
        } else {
            while (!arret && x > 0) {
                cu = s.charAt(x);

                if (arr(cu)) {
                    arret = true;
                } else
                    x--;
            }
            if (x == 0 && !arrs(s)) x = -1;
        }
        s = s.substring(x + 1, z).trim();
        s = ms(s);
        if (s.charAt(0) == '=' || s.charAt(0) == '(' || s.charAt(0) == '*') s = s.substring(1);
        return simp_sign(s);
    }

    public String dernierFacteur(String s) {
        int z = s.length();
        return dernierFacteur(s, z);
    }

    public ArrayList<String> coupen_en(String s, String a) {
        int n;
        String r;
        if (s == null) s = "";
        ArrayList<String> rt = new ArrayList<>();
        while (!s.equals("") && s.contains(a)) {
            n = s.indexOf(a);
            r = s.substring(0, n);
            s = s.substring(n + a.length());
            if (!r.equals(a) && !r.equals("")) {
                if (r.charAt(0) == a.charAt(0)) r = r.substring(a.length());
                rt.add(r);
            }
        }
        if (!s.equals("") && !s.equals(" ")) rt.add(s);
        return rt;
    }

    public ArrayList<String> coupen_en_vide(String s, String a) {
        int n;
        String r;
        ArrayList<String> rt = new ArrayList<>();
        while (!s.equals("") && s.contains(a)) {
            n = s.indexOf(a);
            r = s.substring(0, n);
            s = s.substring(n + a.length());
            if (!r.equals(a)) {
                //  if(!r.equals("")&&r.charAt(0)==a.charAt(0)) r=r.substring(a.length());
                rt.add(r);
            }
        }
        if (!s.equals("")) rt.add(s);
        return rt;
    }

    public ArrayList<String> coupen_en_espace(String s) {
        return coupen_en(s, " ");
    }

    public ArrayList<String> coupen_en_point_virgule(String s) {
        s = s.replace("↲", ";");
        return coupen_en(s, ";");
    }

    public ArrayList<String> coupen_en_u(String s) {
        return coupen_en(s, "ù");
    }

    public ArrayList<String> coupen_en_egale(String s) {
        return coupen_en(s, "=");
    }

    public ArrayList<String> coupen_en_virgule(String s) {
        return coupen_en(s, ",");
    }

    public static List<String> date_heur(String s) {
        if (s == null || s.contains("$")) {
            List<String> l = new ArrayList<>();
            if (s != null) {
                l.add(s.substring(1));
            } else
                l.add("");
            l.add("Carte memoire");
            return l;
        }

        int n = s.length();
        if (n > 9) {
            String av = s.substring(0, n - 9), ap = s.substring(n - 8);
            List<String> l = new ArrayList<>();
            l.add(av);
            l.add(ap);
            return l;
        } else {
            String av = "", ap = "";
            List<String> l = new ArrayList<>();
            l.add(av);
            l.add(ap);
            return l;
        }
    }

    public static String toUperCaseFirstChar(String s) {
        while (s.length() > 0 && s.charAt(0) == ' ') s = s.substring(1);
        if (s.length() == 0) return s;
        String sa = s.toLowerCase();
        sa = (sa.charAt(0) + "").toUpperCase() + sa.substring(1);
        return sa;
    }

    public String alph = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public boolean isUperCase(String s) {
        String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char t[] = s.toCharArray();
        for (char c : t) {
            if (!a.contains(c + "")) return false;
        }
        return true;
    }

    public boolean isLowerCase(String s) {
        String a = "abcdefghijklmnopqrstuvwxyz0123456789";
        char t[] = s.toCharArray();
        for (char c : t) {
            if (!a.contains(c + "")) return false;
        }
        return true;
    }
}
