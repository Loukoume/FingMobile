package com.credi.fings.publics.utils;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {


    public static String apreciation(double note){
        if(note>0){
            double d=note/20.0;
            if(d>=0.9)return "Excellent";
            if(d>=0.8)return "Très bien";
            if(d>=0.7)return "Bien";
            if(d>=0.6)return "Assez-bien";
            if(d>=0.5)return "Passable";
            if(d>=0.4)return "Insufisant";
            if(d>=0.3)return "Très Insufisant";
            if(d>=0.2)return "Faible";
            return "Très Faible";
        }
        return "";
    }
    public static String apreciationBulletin(double note){
        if(note>0){
            double d=note/20.0;
            if(d>=0.9)return "excellent";
            if(d>=0.8)return "très bien";
            if(d>=0.7)return "bien";
            if(d>=0.6)return "assez-bien";
            if(d>=0.5)return "passable";
            if(d>0.35)return "insufisant";
            if(d<=0.35)return "très Insufisant";
            return "Très Faible";
        }
        return "";
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String creerDossier(String paquage, String name) throws IOException {

        String fileNme = paquage + "\\" + name;
        Path path = Paths.get(fileNme);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        } else {

        }
        return fileNme;
    }

    public static List<List<String>> integrationFichier(String repertoireFichier) throws Exception {
        File f = new File("C:\\Users\\SNPT\\Downloads\\LISTE DES ELEVES 2022-2023(1).xls");
        if (f.exists() == false) {
//            throw new Exception("Le fichier n'a pas été retrouvé sur le serveur. "+repertoireFichier);
        }
        List<List<String>> fileObjectList = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(f.getAbsolutePath()));
        String ligne;
        while ((ligne = in.readLine()) != null) {
            String[] tabSpliter = ligne.trim().split(";");

            fileObjectList.add(Arrays.asList(tabSpliter));
        }
        return fileObjectList;
    }

    public static boolean isInt(String chaine) {
        if (chaine.equals("")) {
            return false;
        }

        for (char caract : chaine.toCharArray()) {
            if (!Character.isDigit(caract)) {
                return false;
            }
        }

        return true;
    }
    public static String arondi(double xd) {
        String x = String.valueOf(xd);
        System.out.println("=====arondi======"+x);
        int decimalIndex = x.indexOf(".");
        if (decimalIndex != -1) {
            boolean hasPercentage = x.endsWith("%");
            x = x.replace("%", "");
            String decimalPart = x.substring(decimalIndex + 1);
            System.out.println("=====decimalPart======"+decimalPart);
            if (decimalPart.length() == 1) {
                if(x.endsWith(".0"))return x.replace(".0","");
                return x+"0";

            } else if (decimalPart.length() > 2) {
                int thirdDecimal = Character.getNumericValue(decimalPart.charAt(2));
                if (thirdDecimal >= 5) {
                    int secondDecimal = Character.getNumericValue(decimalPart.charAt(1));
                    x=x.substring(0, decimalIndex + 2)+(secondDecimal+1);
                    // System.out.println( Double.parseDouble(x.substring(0, decimalIndex + 3))+"=====x.substring(0, decimalIndex + 3)======"+x.substring(0, decimalIndex + 3));
                    return x;
                } else {
                    x = x.substring(0, decimalIndex + 3);
                }
            }
            if (hasPercentage) {
                x += "%";
            }
        }
        return x;
    }



    public static double arondi_d(double xd){
        String x= String.valueOf(xd);

        if(x.contains(".")&&isInt(x.replace(".",""))){
            int i=x.indexOf(".");
            boolean oui=x.endsWith("%");
            x=x.replace("%","");
            String av=x.substring(0,i),ap=x.substring(i+1);
            if(ap.length()==1){
                return Double.parseDouble(av+"."+ap+"0");
            }
            if(x.length()>i+3){
                int e=Integer.parseInt(String.valueOf(x.charAt(i+3)));
                if (e<5){
                    x=x.substring(0,i+3);
                    return Double.parseDouble(x);
                }else {
                    double vd= Double.parseDouble(x.substring(0,i+3))+0.01;
                    return vd;
                }

            }
        }
        return xd;
    }
}
