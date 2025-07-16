package com.credi.fing.pojo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rubrique {

    class CleValeur {
        Integer index;
        Character valeur;
        String valeur2;

        public CleValeur(Integer index, Character valeur) {
            this.index = index;
            this.valeur = valeur;
        }

        public CleValeur(Integer index, Character valeur, String valeur2) {
            this.index = index;
            this.valeur = valeur;
            this.valeur2 = valeur2;
        }
    }

    public static int compterOccurrence(char caractere, String texte) {
        return (int) texte.chars()
                .filter(c -> c == caractere)
                .count();
    }

    public String getBackRubliqueAvecParenthese(String expression, int i) {
        String pf = ")", po = "(";
        int cpt = 1, j = i - 1;
        if (expression.charAt(i) != ')') {
            CleValeur result = getLastOperatorAndIndex(expression.substring(0, i + 1));
            if (result == null || result.index == -1)
                return expression.substring(0, i + 1);
            return expression.substring(result.index + 1, i + 1);
        }
        String apres = expression.substring(0, i + 1);
        int fin = apres.lastIndexOf(po);
        int debut = apres.lastIndexOf(pf);
        if (fin > debut || (debut == -1 && fin != -1)) {
            return expression.substring(fin + 1, i);
        }
        if (fin != -1) {
            cpt--;
            cpt = cpt + compterOccurrence(')', apres.substring(fin, i));
            j = j - (i - fin);
        }
        while (cpt != 0 && fin >= apres.length()) {

            apres = apres.substring(0, fin);
            fin = apres.lastIndexOf(po);
            if (fin != -1) {
                cpt--;
                cpt = cpt + compterOccurrence(')', apres.substring(fin));
                j = j - (apres.length() - fin);
            }
            if (fin == -1) break;
        }
        return expression.substring(j + 1, i + 1);
    }

    public String getRubliqueAvecParenthese(String s, int i) {
        String pf = "(", po = ")";
        int cpt = 1, j = i + 1;
        String apres = s.substring(i + 1);
        int fin = apres.indexOf(po);
        int debut = apres.indexOf(pf);
        if (fin < debut || (debut == -1 && fin != -1)) return s.substring(i, i + fin + 2);
        if (fin != -1) {
            cpt--;
            cpt = cpt + compterOccurrence('(', apres.substring(0, fin));
            j = j + fin + 1;
        }
        while (cpt != 0 && !apres.isEmpty()) {
            apres = apres.substring(fin + 1);
            fin = apres.indexOf(po);
            if (fin != -1) {
                cpt--;
                cpt = cpt + compterOccurrence('(', apres.substring(0, fin));
                j = j + fin + 1;
            }
        }
        return s.substring(i, j);
    }

    public Map.Entry<Integer, Character> getFirstOperatorAndIndex(String expression) {
        char[] caracteresRecherches = {'+', '-', '*', '/', '(', ')'};
        Map.Entry<Integer, Character> result = IntStream.range(0, caracteresRecherches.length)
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(expression.indexOf(caracteresRecherches[i]), caracteresRecherches[i]))
                .filter(entry -> entry.getKey() != -1)
                .min(Map.Entry.comparingByKey())
                .orElse(null);

        return result;
    }

    public CleValeur getLastOperatorAndIndex(String expression) {
        List<Character> caracteresRecherches = Arrays.asList('+', '-', '*', '/', '(', ')');
        List<CleValeur> index = caracteresRecherches.stream()
                .map(e -> new CleValeur(expression.lastIndexOf(e), e))
                .filter(q -> q.index != -1)
                .collect(Collectors.toList());
        return index.stream().max(Comparator.comparingInt(x -> x.index)).orElse(null);
    }




    public String getFirstRubrique(String expression) {
        Map.Entry<Integer, Character> result = getFirstOperatorAndIndex(expression);

        if (result == null || result.getKey() == -1)
            return expression;
        if (result.getKey() == 0) {
            if (result.getValue() == '(') {
                return getRubliqueAvecParenthese(expression, 0);
            }
            return getFirstRubrique(expression.substring(1));
        }
        return expression.substring(0, result.getKey());
    }

    public CleValeur getOperatorAndIndex(String expression) {
        List<String> caracteresRecherches = Arrays.asList("+0/", "-0/", "*0/");
        List<CleValeur> index = caracteresRecherches.stream()
                .map(e -> new CleValeur(expression.lastIndexOf(e), null, e))
                .filter(q -> q.index != -1)
                .collect(Collectors.toList());
        return index.stream().min(Comparator.comparingInt(x -> x.index)).orElse(null);
    }
    public String simplifierExpression(String expression) {
        Integer index = expression.indexOf("0*");
        if (index != -1) {
            String first = getFirstRubrique(expression.substring(index + 2));
            String ack = getBackRubliqueAvecParenthese(expression, index);
            if (ack.equals("0")) {
                return expression.replace(ack + "*" + first, "0");
            }
            index = expression.indexOf("*0");
            if (index != -1) {
                first = getFirstRubrique(expression.substring(index + 1));
                ack = getBackRubliqueAvecParenthese(expression, index - 1);
                return expression.replace(ack + "*" + first, "0");
            } else return expression;
        } else {
            index = expression.indexOf("*0");
            if (index != -1) {
                String first = getFirstRubrique(expression.substring(index + 1));
                String ack = getBackRubliqueAvecParenthese(expression, index - 1);

                return expression.replace(ack + "*" + first, "0");
            }
            CleValeur cv = getOperatorAndIndex(expression);
            index = cv.index;
            if (index != null && index != -1) {
                String first = getFirstRubrique(expression.substring(index + 3));
                return expression.replace(cv.valeur2 + first, cv.valeur2.replace("/", ""));
            } else return expression;
        }

    }

    public boolean estSimplifiable(String expression) {
        String expr = expression;
        boolean go = expr.contains("0*");
        while (!expr.isEmpty() && go) {
            int index = expr.indexOf("0*");
            if (getBackRubliqueAvecParenthese(expr, index).equals("0"))
                return true;
            expr = expr.substring(index + 2);
            go = expr.contains("0*");
        }
        go = expr.contains("0/");
        while (!expr.isEmpty() && go) {
            int index = expr.indexOf("0/");
            if (getBackRubliqueAvecParenthese(expr, index).equals("0"))
                return true;
            expr = expr.substring(index + 2);
            go = expr.contains("0/");
        }
        return false;
    }

    public String simplifierAll(String exp) {
        if (exp != null && !exp.isEmpty()) {
            while (estSimplifiable(exp) || exp.contains("*0")) {
                System.out.println("===coc===" + exp);
                exp = simplifierExpression(exp);
            }
        }
        return exp;
    }

    public static void main(String[] arg) {
        String expression = "1*160/100+0/100";
        System.out.println("===" + new Rubrique().simplifierAll(expression));
    }

}
