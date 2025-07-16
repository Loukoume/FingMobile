package com.credi.fing.publics.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Loukoume on 23/03/2018.
 */

public class MonFichier {

    public static void ecrire(Context mContext, String fileName, String fileContent){
        try {
           if(fileContent==null||fileName==null) return;
            // To open you can choose the mode MODE_PRIVATE, MODE_APPEND,
            // MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE
            // This is the creation mode (Private, World Readable et World Writable),
            // Append is used to open the file and write at its end
            FileOutputStream fos= mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            // Open the writer
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fos);
            // Write
            outputStreamWriter.write(fileContent);
            // Close streams
            outputStreamWriter.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // ecrireXml(fileContent,fileName,mContext);
    }


    public static String lire(Context mContext, String fileName){
        String rt="";
        if (fileName != null) {
            try {
                //open the file and retrieve the inputStream
                InputStream inputStream = mContext.openFileInput(fileName);
                if (inputStream != null) {
                    // open a reader on the inputStream
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    // String to use to store read lines
                    String str;
                    StringBuilder buf = new StringBuilder();

                    // Read the file
                    /*while ((str = reader.readLine()) != null) {
                        buf.append(str + "\r\n");
                    }*/
                    while ((str = reader.readLine()) != null) {
                        buf.append(str);
                    }
                    // Close the reader
                    reader.close();
                    // Close the inputStream
                    inputStream.close();
                    // Affect the text to the textView
                   rt=buf.toString();
                }
            } catch (FileNotFoundException e) {
               // Toast.makeText(, "", Toast.LENGTH_SHORT).show();.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG);
            } catch (IOException e) {
              //  Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG);
            }
        }
      //  Dialogue.neutreDialog(rt+"   ------    "+data(fileName+".xml"),""+fileName,mContext).show();
        return rt;
    }


    public static void delete(Context c, String fileName){
       c.deleteFile(fileName);
    }

    public static File getPath(String nm,Context context){
        ContextWrapper contextWrapper=new ContextWrapper(context);
        File file=contextWrapper.getExternalFilesDir(Environment.getExternalStorageDirectory().getPath());
        File file1=new File(file,nm);
        if(file1.exists()){

        }else {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file1;
    }
    public static File getPath(String nm,String path,Context context){
        ContextWrapper contextWrapper=new ContextWrapper(context);
        File file=contextWrapper.getExternalFilesDir(Environment.getExternalStorageDirectory().getPath()+"/"+path);
        File file1=new File(file,nm);
        if(file1.exists()){

        }else {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file1;
    }


    private static FileOutputStream creerFile(String fileName,Context context) throws IOException {
        //File file=new File(Environment.getExternalStorageDirectory()+"/"+fileName);
        File file=getPath(fileName,context);
        if(!file.exists()){
            file.createNewFile();
            file.isHidden();
        }
        file.setExecutable(false);
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        return fileOutputStream;
    }

    public static void ecrire(List<Donnee> list, String fileName,Context context) throws IOException {
        //List<Donnee> list=lireXML(Environment.getExternalStorageDirectory()+"/xmlFile.xml");
        // list.add(0,study);
        FileOutputStream fileOutputStream=creerFile(fileName,context);
        XmlSerializer xmlSerializer= Xml.newSerializer();
        xmlSerializer.setOutput(fileOutputStream,"UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output",true);
        xmlSerializer.startTag(null,"Datas");
        for(Donnee message:list){
            xmlSerializer.startTag(null,"data");
            xmlSerializer.attribute(null,"clet",message.getClet());
            // xmlSerializer.attribute(null,"sms",message.getSms());
            xmlSerializer.text(message.getValeur());
            xmlSerializer.endTag(null,"data");
        }
        xmlSerializer.endTag(null,"Datas");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
        fileOutputStream.close();
    }

    public static void ecrireXml(String text,Context context){
        List<Donnee> l=new ArrayList<>();
        l.add(new Donnee("1",text));
        try {
            ecrire(l,"wx.xml",context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void ecrireXml(String text, String filname,Context context){
        List<Donnee> l=new ArrayList<>();
        l.add(new Donnee("1",text));
        try {
            ecrire(l,filname+".xml",context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String data(){
        List<Donnee> l=null;
        try {
            l=lireXML("wx.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return l!=null?l.get(0).getValeur():"";
    }
    public static String data(String fn){
        List<Donnee> l=null;
        try {
            l=lireXML(fn);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return l!=null?l.get(0).getValeur():"";
    }

    public static List<Donnee> lireXML(String fileName)
            throws ParserConfigurationException, IOException, SAXException {
        String name= Environment.getExternalStorageDirectory()+"/"+fileName;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder =factory.newDocumentBuilder();
        Document dom=builder.parse(new FileInputStream(name));
        Element root =dom.getDocumentElement();
        NodeList item=root.getElementsByTagName("data");
        List<Donnee> list=new ArrayList<>();
        for(int i=0;i<item.getLength();i++){
            Node ite=item.item(i);
            String num=ite.getAttributes().getNamedItem("clet").getNodeValue(),
                    sms=ite.getTextContent();
            list.add(new Donnee(num,sms));
        }
        return list;
    }
}
